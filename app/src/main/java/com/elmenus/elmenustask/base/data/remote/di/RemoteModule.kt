package  com.elmenus.elmenustask.base.data.remote.di


import com.elmenus.elmenustask.BuildConfig
import com.elmenus.elmenustask.base.data.remote.RemoteConstants
import com.elmenus.elmenustask.base.data.remote.interceptor.ErrorMappingInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val remoteModule = module {

    single { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }
    single { ErrorMappingInterceptor(get(), get()) }
    single<OkHttpClient> {
        val builder = OkHttpClient.Builder()

        builder.addInterceptor(get<ErrorMappingInterceptor>())
            .connectTimeout(RemoteConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(RemoteConstants.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(RemoteConstants.WRITE_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(get<HttpLoggingInterceptor>())
        }
        builder.build()
    }

    single { GsonBuilder().serializeNulls().create() }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(RemoteConstants.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }


}