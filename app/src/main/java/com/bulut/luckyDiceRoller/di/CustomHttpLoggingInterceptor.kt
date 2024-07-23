package com.bulut.luckyDiceRoller.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import java.nio.charset.StandardCharsets

class CustomHttpLoggingInterceptor : Interceptor {
    private val logger = HttpLoggingInterceptor.Logger.DEFAULT

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        logRequest(request)
        val response = chain.proceed(request)
        return logAndCloneResponse(response)
    }

    private fun logRequest(request: Request) {
        logger.log("SHERLOCK REQUEST")
        val separator = "\n-----------------------\n"

        logger.log("METHOD: ${request.method}")
        logger.log("URL: ${request.url}")

        logger.log("HEADER ->")

        if (request.headers.size > 0) {
            request.headers.forEach { header ->
                logger.log("    * ${header.first}: ${header.second}")
            }
            logger.log("\n")
        }

        request.body?.let { body ->
            logger.log("BODY: ${bodyToString(body)}")
        }

        logger.log("END REQUEST$separator")
    }

    private fun logAndCloneResponse(response: Response): Response {
        val separator = "\n-----------------------\n"

        val responseBody = response.body
        val responseBodyString = responseBody?.string()

        logger.log("SHERLOCK RESPONSE")

        logger.log("STATUS: ${response.code}")
        logger.log("MESSAGE: ${response.message}")

        logger.log("HEADER")
        response.headers.forEach { header ->
            logger.log("    * ${header.first}: ${header.second}")
        }

        if (response.headers.size > 0) logger.log("\n")

        logger.log("BODY ->")
        responseBodyString?.let {
            logger.log(prettyPrintJson(it))
        }

        logger.log("END RESPONSE$separator")

        return response.newBuilder()
            .body(ResponseBody.create(responseBody?.contentType(), responseBodyString ?: ""))
            .build()
    }

    private fun bodyToString(requestBody: RequestBody): String {
        return try {
            val buffer = okio.Buffer()
            requestBody.writeTo(buffer)
            buffer.readString(StandardCharsets.UTF_8)
        } catch (e: Exception) {
            "Couldn't parse request body"
        }
    }

    private fun prettyPrintJson(json: String): String {
        val gson: Gson = GsonBuilder().setPrettyPrinting().create()
        val jsonElement = JsonParser.parseString(json)
        return gson.toJson(jsonElement)
    }
}
