package com.kurowskiandrzej.eclectusandroid.di

import android.app.Application
import com.kurowskiandrzej.eclectusandroid.data.remote.EclectusApi
import com.kurowskiandrzej.eclectusandroid.data.remote.EclectusRetrofit
import com.kurowskiandrzej.eclectusandroid.data.repository.EclectusRepositoryImpl
import com.kurowskiandrzej.eclectusandroid.data.roomdb.EclectusDao
import com.kurowskiandrzej.eclectusandroid.data.roomdb.EclectusDatabase
import com.kurowskiandrzej.eclectusandroid.domain.repository.EclectusRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRetrofitApi(): EclectusApi = EclectusRetrofit.get().create()

    @Singleton
    @Provides
    fun injectRoomDatabase(context: Application) = EclectusDatabase.getInstance(context)

    @Singleton
    @Provides
    fun injectDao(database: EclectusDatabase) = database.dao

    @Singleton
    @Provides
    fun injectRepository(
        dao: EclectusDao,
        api: EclectusApi
    ) = EclectusRepositoryImpl(dao, api) as EclectusRepository
}