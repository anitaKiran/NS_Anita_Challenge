package com.nordsecurity.test

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Anita Kiran on 6/27/2022.
 */

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    // okhttp client
    @Provides
    fun providesOkHttpClient(sharedPreferences: SharedPreferences): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                var request: Request = chain.request().newBuilder().build()
                val tokenValue = sharedPreferences.getString(AppConstants.LOGIN_TOKEN, "")!!
                if(tokenValue.isNotEmpty() && tokenValue.isNotBlank()){
                    request = chain.request().newBuilder().addHeader("Authorization",  "Bearer $tokenValue").build()
                }
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()
    }

    // rertrofit builder
    @Provides
    @Singleton
    fun providesRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit) : ApiService =
        retrofit.create(ApiService::class.java)

    // app preference
    @Singleton
    @Provides
    fun provideSharedPreferences(  @ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(AppConstants.APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    // room
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): NordSecurityDatabase {
        return Room.databaseBuilder(
            appContext,
            NordSecurityDatabase::class.java,
            AppConstants.ROOM_DB_NAME
        ).build()
    }

    // dao
    @Provides
    fun providesToDoListDao(nordRoomDatabase: NordSecurityDatabase): NordDao =
        nordRoomDatabase.getNordDao()

}