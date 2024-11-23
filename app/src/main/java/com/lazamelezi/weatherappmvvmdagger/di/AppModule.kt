package com.lazamelezi.weatherappmvvmdagger.di

import android.app.Application
import android.content.Context

import com.lazamelezi.weatherappmvvmdagger.features.weather_info_show.view.MainActivity
import com.lazamelezi.weatherappmvvmdagger.network.ApiInterface
import com.lazamelezi.weatherappmvvmdagger.network.RetrofitClient

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    abstract fun provideContext(application: Application): Context

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivityInjector(): MainActivity

    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun provideApiService() : ApiInterface {
            return RetrofitClient.client.create(ApiInterface::class.java)
        }
    }
}