package com.daffa.moviecatalogue.core.di

import androidx.room.Room
import com.daffa.moviecatalogue.core.data.source.remote.network.ApiService
import com.daffa.moviecatalogue.core.domain.repository.IMainRepository
import com.daffa.moviecatalogue.core.utils.AppExecutors
import com.daffa.moviecatalogue.core.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<com.daffa.moviecatalogue.core.data.source.local.room.local.FilmDatabase>().filmDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            com.daffa.moviecatalogue.core.data.source.local.room.local.FilmDatabase::class.java,
            "Film.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { com.daffa.moviecatalogue.core.data.source.local.LocalDataSource(get()) }
    single { com.daffa.moviecatalogue.core.data.source.remote.RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMainRepository> {
        com.daffa.moviecatalogue.core.data.repository.MainRepository(
            get(),
            get(),
            get()
        )
    }
}