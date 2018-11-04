package co.parsl.android.boilerplate.ui.di

import android.arch.persistence.room.Room
import co.parsl.android.boilerplate.cache.BufferooCacheImpl
import co.parsl.android.boilerplate.cache.PreferencesHelper
import co.parsl.android.boilerplate.cache.db.BufferoosDatabase
import co.parsl.android.boilerplate.cache.mapper.BufferooEntityMapper
import co.parsl.android.boilerplate.data.BufferooDataRepository
import co.parsl.android.boilerplate.data.browse.interactor.GetBufferoos
import co.parsl.android.boilerplate.data.executor.JobExecutor
import co.parsl.android.boilerplate.data.executor.PostExecutionThread
import co.parsl.android.boilerplate.data.executor.ThreadExecutor
import co.parsl.android.boilerplate.remote.BufferooRemoteImpl
import co.parsl.android.boilerplate.data.source.BufferooDataStoreFactory
import co.parsl.android.boilerplate.remote.BufferooServiceFactory
import co.parsl.android.boilerplate.data.source.BufferooDataStore
import co.parsl.android.boilerplate.data.repository.BufferooRepository

import co.parsl.android.boilerplate.ui.UiThread
import co.parsl.android.boilerplate.ui.browse.BrowseAdapter
import co.parsl.android.boilerplate.ui.browse.BrowseBufferoosViewModel
import co.parsl.android.boilerplate.ui.home.MainViewModel
import co.parsl.android.boilerplate.ui.onboarding.LaunchViewModel
import co.parsl.android.boilerplate.ui.onboarding.launch.SplashViewModel
import co.parsl.android.boilerplate.ui.onboarding.launch.WelcomeViewModel
import co.parsl.android.boilerplate.ui.onboarding.login.LoginViewModel
import co.parsl.android.boilerplate.ui.onboarding.register.ConfirmationViewModel
import co.parsl.android.boilerplate.ui.onboarding.register.RegisterNameViewModel
import co.parsl.android.boilerplate.ui.onboarding.register.RegisterEmailViewModel
import co.parsl.android.ui.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val applicationModule = module(override=true) {

    single { PreferencesHelper(androidContext()) }

    factory { co.parsl.android.boilerplate.remote.mapper.BufferooEntityMapper() }

    single { JobExecutor() as ThreadExecutor }
    single { UiThread() as PostExecutionThread }

    single { Room.databaseBuilder(androidContext(),
            BufferoosDatabase::class.java, "bufferoos.db")
            .build() }
    factory { get<BufferoosDatabase>().cachedBufferooDao() }

    factory<BufferooDataStore>("remote") { BufferooRemoteImpl(get(), get()) }
    factory<BufferooDataStore>("local") { BufferooCacheImpl(get(), get(), get()) }
    factory { BufferooDataStoreFactory(get("local"), get("remote")) }

    factory { BufferooEntityMapper() }
    factory { BufferooServiceFactory.makeBuffeoorService(BuildConfig.DEBUG) }

    factory<BufferooRepository> { BufferooDataRepository(get()) }
}

val browseModule = module("Browse", override = true) {
    factory { BrowseAdapter() }
    factory { GetBufferoos(get(), get(), get()) }
    viewModel { BrowseBufferoosViewModel(get()) }
}

val onboardingModule = module("Onboarding", override = true) {
    viewModel { LaunchViewModel() }
    viewModel { SplashViewModel() }
    viewModel { WelcomeViewModel() }
    viewModel { RegisterNameViewModel() }
    viewModel { RegisterEmailViewModel() }
    viewModel { LoginViewModel() }
    viewModel { ConfirmationViewModel() }
}

val mainModule = module("main", override = true) {
    viewModel { MainViewModel() }
}