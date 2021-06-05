package app.spidy.kotlinutils

import android.content.Context
import android.util.Log
import java.util.*


class TimeMachine(context: Context) {
    companion object {
        const val LAUNCH = "app.spidy.kotlinutils.LAUNCH"
        const val BACKGROUND = "app.spidy.kotlinutils.BACKGROUND"
        const val FOREGROUND = "app.spidy.kotlinutils.FOREGROUND"

        const val HOUR = 360000L
        const val MINUTE = 60000L
        const val SECOND = 1000L
        const val DAY = 8640000L
        const val MONTH = 259200000L
    }

    private val tinyDB = TinyDB(context)

    fun now(): Long {
        return Calendar.getInstance().timeInMillis
    }

    fun schedule(tag: String, type: String = LAUNCH): Executor {
        val prevTime = tinyDB.getLong(tag, 0)
        val currentTime = Calendar.getInstance().timeInMillis
        if (prevTime == 0L) {
            tinyDB.putLong(tag, currentTime)
        }

        return Executor(tag, prevTime, currentTime, type)
    }

    inner class Executor(
        private val tag: String,
        private val prevTime: Long,
        private val currentTime: Long,
        private val type: String
    ) {
        private var duration: Long = 0

        fun after(duration: Long): Executor {
            this.duration = duration
            return this
        }

        private fun onLaunch(callback: () -> Unit) {
            if (prevTime != 0L && currentTime - prevTime >= duration) {
                callback()
                tinyDB.putLong(tag, currentTime)
            }
        }

        fun run(callback: () -> Unit): Executor {
            when (type) {
                LAUNCH -> onLaunch(callback)
            }
            return this
        }

        fun remove() {
            tinyDB.putLong(tag, 0L)
        }
    }
}