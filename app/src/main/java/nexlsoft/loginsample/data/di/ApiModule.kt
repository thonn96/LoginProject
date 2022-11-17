package nexlsoft.loginsample.data.di

import android.content.Context
import com.datn.cookingguideapp.data.remote.interceptor.InterceptorImpl
import com.google.gson.Gson
import nexlsoft.loginsample.data.local.AppSharedPreferences
import nexlsoft.loginsample.data.repository.remote.ApiService
import nexlsoft.loginsample.data.repository.remote.RemoteImpl
import nexlsoft.loginsample.data.repository.remote.RemoteSource
import nexlsoft.loginsample.ui.categories.CategoriesViewModel
import nexlsoft.loginsample.ui.login.LoginViewModel
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val appModule =  module {
    single { createRetrofit(get(), get()) }
    single { buildOKHttpClient(androidContext(), get()) }
    single { buildGSON() }
    single { AppSharedPreferences(androidContext()) }
    single<Interceptor> { InterceptorImpl(get()) }

    factory<RemoteSource> { RemoteImpl(get())}

    viewModel {  LoginViewModel(get()) }
    viewModel { CategoriesViewModel(get()) }
}



private fun createRetrofit(gson: Gson, okHttpClient: OkHttpClient): ApiService {
    return Retrofit.Builder()
        .baseUrl("http://streaming.nexlesoft.com:4000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
        .create(ApiService::class.java)
}

private fun buildOKHttpClient(context: Context, interceptor: Interceptor): OkHttpClient {
    return OkHttpClient.Builder().apply {
        cache(Cache(context.applicationContext.cacheDir, 10 * 1024 * 1024))
        addInterceptor(interceptor)
        readTimeout(15L, TimeUnit.SECONDS)
        connectTimeout(15L, TimeUnit.SECONDS)
        callTimeout(15L, TimeUnit.SECONDS)
    }.build()
}

private fun buildGSON(): Gson = Gson()
