package me.gumify.hiper.http.data

import java.util.*
import kotlin.collections.HashMap

class Mixer(vararg header: Pair<String, Any>): HashMap<String, Any>() {
    init {
        for (h in header) {
            put(h.first, h.second.toString())
        }
    }

    override fun put(key: String, value: Any): Any? {
        val k = key.toLowerCase(Locale.ROOT)
        return super.put(key, value.toString())
    }
}