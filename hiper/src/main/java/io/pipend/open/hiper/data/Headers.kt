package me.gumify.hiper.http.data

import java.util.*
import kotlin.collections.HashMap

class Headers(vararg args: Pair<String, Any>): HashMap<String, String>() {
    init {
        for (h in args) {
            put(h.first, h.second.toString())
        }
    }

    override fun put(key: String, value: String): String? {
        val k = key.toLowerCase(Locale.ROOT)
        return super.put(key, value)
    }

    fun put(key: String, value: Any): String? {
        val k = key.toLowerCase(Locale.ROOT)
        return put(k, value.toString())
    }
}