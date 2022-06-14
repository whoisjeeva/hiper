package me.gumify.hiper.http.utils

import me.gumify.hiper.http.data.Mixer


fun mix(vararg args: Pair<String, Any>): HashMap<String, Any> {
    return Mixer(*args)
}


