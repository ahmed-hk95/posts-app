package ahmed.hk.posts.di

import ahmed.hk.posts.data.remote.APIs
import ahmed.hk.posts.data.remote.NetworkRepository
import ahmed.hk.posts.utils.Constants
import android.content.Context
import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val READ_TIMEOUT = 30L
    private const val WRITE_TIMEOUT = 30L
    private const val CONNECTION_TIMEOUT = 10L
    private const val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 MB

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LoggingInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class HeadersInterceptor

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @LoggingInterceptor loggingInterceptor: Interceptor,
        @HeadersInterceptor headerInterceptor: Interceptor,
        cache: Cache
    ): OkHttpClient {

        val okHttpClientBuilder = OkHttpClient().newBuilder()
        with(okHttpClientBuilder){
            connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            cache(cache)
            addInterceptor(headerInterceptor)
            addInterceptor(loggingInterceptor)

        }

        return okHttpClientBuilder.build()
    }


    @Provides
    @HeadersInterceptor
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor {
            val requestBuilder = it.request().newBuilder()
            requestBuilder.addHeader("Content-Type","application/json")
            it.proceed(requestBuilder.build())
        }
    }

    @Provides
    @LoggingInterceptor
    @Singleton
    fun provideLoggingInterceptor(): Interceptor {
        fun RequestBody?.format():String {
            return try {
                val buffer = Buffer()
                this?.writeTo(buffer)
                buffer.readUtf8()
            }catch (e:Exception){
                "DID NOT WORK"
            }
        }

        return Interceptor {
            with(it.request()){
                val reqBody = body.format()
                Log.d("RQST","Request Method: $method")
                Log.d("RQST","Request Url: $url")
                Log.d("RQST","Request Headers: $headers")
                Log.d("RQST","Request Body: $reqBody")
            }
            it.proceed(it.request())
        }


    }


    @Provides
    @Singleton
    internal fun provideCache(@ApplicationContext context: Context): Cache {
        return Cache(context.cacheDir, CACHE_SIZE_BYTES)
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): APIs {
        return retrofit.create(APIs::class.java)
    }

    @Provides
    fun provideNetworkRepository(api: APIs): NetworkRepository =
        NetworkRepository(api)

}