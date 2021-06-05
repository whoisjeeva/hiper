package app.spidy.kotlinutils

import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class Dialog(private val context: Context) {
    private var builder = MaterialAlertDialogBuilder(context)

    fun withTitle(title: String): Dialog {
        builder.setTitle(title)
        return this
    }

    fun withMessage(message: String): Dialog {
        builder.setMessage(message)
        return this
    }

    fun withIcon(icon: Int): Dialog {
        builder.setIcon(icon)
        return this
    }

    fun withCustomView(v: View): Dialog {
        builder.setView(v)
        return this
    }

    fun withCancelable(isCancelable: Boolean): Dialog {
        builder.setCancelable(isCancelable)
        return this
    }

    fun withPositiveButton(btnName: String, callback: (dialog: DialogInterface) -> Unit): Dialog {
        builder.setPositiveButton(btnName) { dialog, _ ->
            callback(dialog)
        }
        return this
    }

    fun withNegativeButton(btnName: String, callback: (dialog: DialogInterface) -> Unit): Dialog {
        builder.setNegativeButton(btnName) { dialog, _ ->
            callback(dialog)
        }
        return this
    }

    fun withNeutralButton(btnName: String, callback: (dialog: DialogInterface) -> Unit): Dialog {
        builder.setNeutralButton(btnName) { dialog, _ ->
            callback(dialog)
        }
        return this
    }

    fun show(): AlertDialog {
        return builder.show()
    }
}