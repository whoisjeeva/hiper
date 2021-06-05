package app.spidy.kotlinutils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permission(private val context: Context) {
    interface Listener {
        fun onGranted()
        fun onRejected()
    }

    private val requestCodes = HashMap<String, Int>()
    private val listeners = HashMap<String, Listener>()

    fun request(permission: String, message: String? = null, listener: Listener) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            listener.onGranted()
            return
        }

        requestCodes[permission] = 440 + requestCodes.size
        listeners[permission] = listener
        val nodes = permission.split(".")
        val reason = message ?: "${context.getString(R.string.app_name)} is require ${nodes.last()}\n\nWould you like to grant?"

        if (ActivityCompat.shouldShowRequestPermissionRationale((context as Activity), permission)) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(reason)
            builder.setPositiveButton("Ok") { dialog, index ->
                dialog.cancel()
                ActivityCompat.requestPermissions(context, arrayOf(permission),
                    requestCodes[permission]!!
                )
            }
            builder.setNegativeButton("Cancel") { dialog, index ->
                dialog.cancel()
            }
            builder.create().show()
        } else {
            ActivityCompat.requestPermissions(context, arrayOf(permission),
                requestCodes[permission]!!
            )
        }
    }

    fun execute(requestCode: Int, grantResults: IntArray) {
        for ((key, value) in requestCodes) {
            if (requestCode == value) {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    listeners[key]?.onGranted()
                } else {
                    listeners[key]?.onRejected()
                }
            }
        }
    }
}