package app.spidy.hiper.utils

import app.spidy.hiper.data.Headers
import app.spidy.hiper.data.Mixer


fun mix(vararg args: Pair<String, Any>): HashMap<String, Any> {
    return Mixer(*args)
}


