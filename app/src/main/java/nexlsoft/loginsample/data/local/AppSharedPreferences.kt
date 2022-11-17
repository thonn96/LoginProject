package nexlsoft.loginsample.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import nexlsoft.loginsample.data.local.AppPrefKey.ACCESS_TOKEN

class AppSharedPreferences(context: Context) {
    private val sharedPreferences by lazy {
        context.getSharedPreferences(AppPrefKey.APP_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun registerOnChange(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        val pref = sharedPreferences
        pref?.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterOnChange(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        val pref = sharedPreferences
        pref?.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun saveToken(kitting: String) {
        sharedPreferences!!.edit().putString(ACCESS_TOKEN, kitting).apply()
    }

    fun loadToken(): String? {
        return sharedPreferences!!.getString(ACCESS_TOKEN, "0")
    }

    fun clearKey(key : String ) {
        sharedPreferences.edit {
            remove(key)
            apply()
        }
    }
}
