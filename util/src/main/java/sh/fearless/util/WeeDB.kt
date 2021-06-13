package sh.fearless.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Base64
import androidx.preference.PreferenceManager
import java.io.ByteArrayOutputStream


class WeeDB(context: Context) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun put(key: String, value: Any) {
        preferences.edit().apply {
            putString(key, turnValue(value))
            apply()
        }
    }

    /*
    fun put(key: String, value: Int) = put(key, value as Any)
    fun put(key: String, value: Long) = put(key, value as Any)
    fun put(key: String, value: Boolean) = put(key, value as Any)
    fun put(key: String, value: String) = put(key, value as Any)
    fun put(key: String, value: Short) = put(key, value as Any)
    fun put(key: String, value: Float) = put(key, value as Any)
    fun put(key: String, value: Double) = put(key, value as Any)
    fun put(key: String, value: CharArray) = put(key, value as Any)
    fun put(key: String, value: ByteArray) = put(key, value as Any)
    fun put(key: String, value: Byte) = put(key, value as Any)
    fun put(key: String, value: Bitmap) = put(key, value as Any)
    fun put(key: String, value: Parcelable) = put(key, value as Any)
     */

    fun remove(key: String) {
        preferences.edit().apply {
            remove(key)
            apply()
        }
    }

    fun get(key: String): String? = getString(key)
    fun <T : Parcelable?> get(key: String, clazz: Class<T>): T? {
        val v = preferences.getString(key, null) ?: return null
        val bytes = Base64.decode(v, Base64.DEFAULT)
        val parcel = Parcel.obtain()
        parcel.unmarshall(bytes, 0, bytes.size)
        parcel.setDataPosition(0)
        val bundle = parcel.readBundle(clazz.classLoader)
        parcel.recycle()
        return bundle?.getParcelable<T>("__o__")
    }

    fun getInt(key: String, defaultValue: Int = 0): Int = preferences.getString(key, null)?.toInt() ?: defaultValue
    fun getLong(key: String, defaultValue: Long = 0): Long = preferences.getString(key, null)?.toLong() ?: defaultValue
    fun getBoolean(key: String): Boolean = preferences.getString(key, null)?.toBoolean() ?: false
    fun getString(key: String): String? = preferences.getString(key, null)
    fun getShort(key: String, defaultValue: Short = 0): Short = preferences.getString(key, null)?.toShort() ?: defaultValue
    fun getFloat(key: String, defaultValue: Float = 0f): Float = preferences.getString(key, null)?.toFloat() ?: defaultValue
    fun getDouble(key: String, defaultValue: Double = 0.0): Double = preferences.getString(key, null)?.toDouble() ?: defaultValue
    fun getCharArray(key: String): CharArray? = preferences.getString(key, null)?.toCharArray()
    fun getByteArray(key: String): ByteArray? {
        val v = preferences.getString(key, null)?.toByteArray() ?: return null
        return Base64.decode(v, Base64.DEFAULT)
    }
    fun getByte(key: String): Byte? = preferences.getString(key, null)?.toByte()
    fun getBitmap(key: String): Bitmap? {
        val v = preferences.getString(key, null) ?: return null
        val d = Base64.decode(v, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(d, 0, d.size)
    }

    operator fun contains(key: String): Boolean {
        return preferences.getString(key, null) != null
    }

    fun newList(key: String): WeeList {
        if (key in this) {
            return getList(key)!!
        }
        preferences.edit().apply {
            putString(key, "")
            apply()
        }
        return WeeList(key, listOf())
    }

    fun getList(key: String): WeeList? {
        val s = preferences.getString(key, null) ?: return null
        return WeeList(key, s.split("‚‗‚"))
    }

    fun turnValue(v: Any): String {
        return when(v) {
            is ByteArray -> Base64.encodeToString(v, Base64.DEFAULT)
            is StringBuilder -> String(v)
            is StringBuffer -> String(v)
            is CharArray -> String(v)
            is Bitmap -> {
                val stream = ByteArrayOutputStream()
                v.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()
                v.recycle()
                return Base64.encodeToString(byteArray, Base64.DEFAULT)
            }
            is Parcelable -> {
                val bundle = Bundle()
                bundle.putParcelable("__o__", v)
                val parcel = Parcel.obtain()
                parcel.writeBundle(bundle)
                val bytes = parcel.marshall()
                parcel.recycle()
                return Base64.encodeToString(bytes, Base64.DEFAULT)
            }
            else -> v.toString()
        }.trim()
    }


    inner class WeeList(private val key: String, private val list: List<String>) {
        private val ref = list.toMutableList()

        override fun toString(): String {
            return ref.toString()
        }

        operator fun iterator(): Iterator<String> {
            return ref.iterator()
        }

        private fun commitChanges() {
            val s = ref.joinToString("‚‗‚")
            preferences.edit().apply {
                putString(key, s)
                apply()
            }
        }

        operator fun contains(element: Any): Boolean {
            return ref.contains(turnValue(element))
        }

        val size: Int
            get() = ref.size

        fun containsAll(elements: Collection<String>): Boolean {
            return ref.containsAll(elements)
        }

        fun indexOf(element: String): Int {
            return ref.indexOf(element)
        }

        fun isEmpty(): Boolean {
            return ref.isEmpty()
        }

        fun lastIndexOf(element: String): Int {
            return ref.lastIndexOf(element)
        }

        fun listIterator(): ListIterator<String> {
            return ref.listIterator()
        }

        fun listIterator(index: Int): ListIterator<String> {
            return ref.listIterator(index)
        }

        fun subList(fromIndex: Int, toIndex: Int): List<String> {
            return ref.subList(fromIndex, toIndex)
        }

        fun add(vararg args: Any) {
            for (v in args) ref.add(turnValue(v))
            commitChanges()
        }

        fun remove(vararg args: Any) {
            for (v in args) ref.remove(turnValue(v))
            commitChanges()
        }

        operator fun get(index: Int): String {
            return ref[index]
        }

        fun <T : Parcelable?> get(i: Int, clazz: Class<T>): T {
            val bytes = Base64.decode(get(i), Base64.DEFAULT)
            val parcel = Parcel.obtain()
            parcel.unmarshall(bytes, 0, bytes.size)
            parcel.setDataPosition(0)
            val bundle = parcel.readBundle(clazz.classLoader)
            parcel.recycle()
            return bundle!!.getParcelable<T>("__o__")!!
        }

        fun getInt(i: Int) = get(i).toInt()
        fun getIntOrNull(i: Int) = get(i).toIntOrNull()
        fun getLong(i: Int) = get(i).toLong()
        fun getLongOrNull(i: Int) = get(i).toLongOrNull()
        fun getFloat(i: Int) = get(i).toFloat()
        fun getFloatOrNull(i: Int) = get(i).toFloatOrNull()
        fun getDouble(i: Int) = get(i).toDouble()
        fun getDoubleOrNull(i: Int) = get(i).toDoubleOrNull()
        fun getShort(i: Int) = get(i).toShort()
        fun getShortOrNull(i: Int) = get(i).toShortOrNull()
        fun getBoolean(i: Int) = get(i).toBoolean()
        fun getCharArray(i: Int) = get(i).toCharArray()
        fun getByteArray(i: Int): ByteArray = Base64.decode(get(i), Base64.DEFAULT)
        fun getByte(i: Int) = get(i).toByte()
        fun getByteOrNull(i: Int) = get(i).toByteOrNull()

        fun getBitmap(i: Int): Bitmap {
            val d = Base64.decode(get(i), Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(d, 0, d.size)
        }
    }
}