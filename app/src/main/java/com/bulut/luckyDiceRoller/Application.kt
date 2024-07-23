package com.bulut.luckyDiceRoller

import android.app.Application
import com.bulut.luckyDiceRoller.constants.DataStoreHelper
import com.bulut.luckyDiceRoller.constants.getInitialLocalizationStrings
import com.bulut.luckyDiceRoller.constants.localizationStrings
import com.bulut.luckyDiceRoller.di.apiModule
import com.bulut.luckyDiceRoller.di.repositoryModule
import com.bulut.luckyDiceRoller.di.viewmodelModule
import com.bulut.luckyDiceRoller.ui.theme.DrawablesLight
import com.bulut.luckyDiceRoller.ui.theme.drawables
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            val modules = listOf(repositoryModule, viewmodelModule, apiModule)
            androidLogger()
            androidContext(this@Application)
            koin.loadModules(modules)
        }
        val dataStoreHelper: DataStoreHelper by inject<DataStoreHelper>()
        drawables = DrawablesLight()
        MainScope().launch {
            localizationStrings = getInitialLocalizationStrings(dataStoreHelper = dataStoreHelper)
        }
    }
}
