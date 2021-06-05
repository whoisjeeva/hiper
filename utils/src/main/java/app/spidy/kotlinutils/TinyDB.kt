package app.spidy.kotlinutils

import android.content.Context
import androidx.preference.PreferenceManager

class TinyDB(context: Context) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getInt(key: String, defaultValue: Int = 0): Int = preferences.getInt(key, defaultValue)
    fun putInt(key: String, value: Int) {
        preferences.edit().apply {
            putInt(key, value)
            apply()
        }
    }

    fun getLong(key: String, defaultValue: Long = 0): Long = preferences.getLong(key, defaultValue)
    fun putLong(key: String, value: Long) {
        preferences.edit().apply {
            putLong(key, value)
            apply()
        }
    }

    fun getBoolean(key: String): Boolean = preferences.getBoolean(key, false)
    fun putBoolean(key: String, value: Boolean) {
        preferences.edit().apply {
            putBoolean(key, value)
            apply()
        }
    }

    fun getString(key: String): String? = preferences.getString(key, null)
    fun putString(key: String, value: String?) {
        preferences.edit().apply {
            putString(key, value)
            apply()
        }
    }

    fun getListString(key: String): List<String> {
        val s = preferences.getString(key, "")
        return s!!.split("‚‗‚")
    }
    fun removeStringFromList(key: String, item: String) {
        val list = getListString(key).toMutableList()
        list.remove(item)
        putListString(key, list)
    }
    fun putListString(key: String, value: List<String>) {
        val s = value.joinToString("‚‗‚")
        preferences.edit().apply {
            putString(key, s)
            apply()
        }
    }
}