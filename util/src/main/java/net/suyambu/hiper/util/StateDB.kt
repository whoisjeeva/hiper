package net.suyambu.hiper.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class StateDB(private val context: Context, name: String) {
    private val Context.dataStore by preferencesDataStore(
        name = name,
        produceMigrations = { context ->
            listOf(SharedPreferencesMigration(context, name))
        }
    )

    fun getString(key: String, default: String = ""): Flow<String> {
        return context.dataStore.data.map { pref ->
            pref[stringPreferencesKey(key)] ?: default
        }
    }

    fun getInt(key: String, default: Int = 0): Flow<Int> {
        return context.dataStore.data.map { pref ->
            pref[stringPreferencesKey(key)]?.toInt() ?: default
        }
    }

    fun getBoolean(key: String, default: Boolean): Flow<Boolean> {
        return context.dataStore.data.map { pref ->
            pref[stringPreferencesKey(key)]?.toBoolean() ?: default
        }
    }

    fun getFloat(key: String, default: Float = 0f): Flow<Float> {
        return context.dataStore.data.map { pref ->
            pref[stringPreferencesKey(key)]?.toFloat() ?: default
        }
    }

    suspend fun put(key: String, value: Any) {
        context.dataStore.edit { settings ->
            settings[stringPreferencesKey(key)] = value.toString()
        }
    }
}


@Composable
fun rememberStateDB(name: String): StateDB {
    val context = LocalContext.current
    val stateDB = remember { StateDB(context, name) }
    return stateDB
}
