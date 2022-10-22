package net.suyambu.hiper.http.utils

import net.suyambu.hiper.http.data.Mixer


fun mix(vararg args: Pair<String, Any>): HashMap<String, Any> {
    return Mixer(*args)
}


