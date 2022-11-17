package nexlsoft.loginsample

import android.app.Application
import android.util.Log
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import nexlsoft.loginsample.data.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}
