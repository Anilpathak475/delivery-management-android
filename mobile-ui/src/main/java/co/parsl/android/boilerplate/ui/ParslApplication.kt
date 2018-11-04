package co.parsl.android.boilerplate.ui

import android.app.Application
import co.parsl.android.boilerplate.ui.di.applicationModule
import co.parsl.android.boilerplate.ui.di.browseModule
import co.parsl.android.boilerplate.ui.di.mainModule
import co.parsl.android.boilerplate.ui.di.onboardingModule
import org.koin.android.ext.android.startKoin
import timber.log.BuildConfig
import timber.log.Timber
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric



class ParslApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(applicationModule, browseModule, onboardingModule, mainModule))
        Fabric.with(this, Crashlytics())
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}
