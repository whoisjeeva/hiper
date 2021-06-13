package sh.fearless.hiper.utils

import sh.fearless.hiper.data.Mixer


fun mix(vararg args: Pair<String, Any>): HashMap<String, Any> {
    return Mixer(*args)
}


