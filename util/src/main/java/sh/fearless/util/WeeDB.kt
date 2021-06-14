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
import java.lang.Exception


open class WeeDB(context: Context) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    /**
     * Insert Any value into WeeDB as a String.
     */
    private fun put(key: String, value: Any) {
        preferences.edit().apply {
            putString(key, turnValue(value))
            apply()
        }
    }


    /**
     * Insert Int value into WeeDB
     */
    fun put(key: String, value: Int) = put(key, value as Any)

    /**
     * Insert Long value into WeeDB
     */
    fun put(key: String, value: Long) = put(key, value as Any)

    /**
     * Insert Boolean value into WeeDB
     */
    fun put(key: String, value: Boolean) = put(key, value as Any)

    /**
     * Insert String value into WeeDB
     */
    fun put(key: String, value: String) = put(key, value as Any)

    /**
     * Insert Short value into WeeDB
     */
    fun put(key: String, value: Short) = put(key, value as Any)

    /**
     * Insert Float value into WeeDB
     */
    fun put(key: String, value: Float) = put(key, value as Any)

    /**
     * Insert Double value into WeeDB
     */
    fun put(key: String, value: Double) = put(key, value as Any)

    /**
     * Insert CharArray value into WeeDB
     */
    fun put(key: String, value: CharArray) = put(key, value as Any)

    /**
     * Insert ByteArray value into WeeDB
     */
    fun put(key: String, value: ByteArray) = put(key, value as Any)

    /**
     * Insert Byte value into WeeDB
     */
    fun put(key: String, value: Byte) = put(key, value as Any)

    /**
     * Insert Bitmap value into WeeDB
     */
    fun put(key: String, value: Bitmap) = put(key, value as Any)

    /**
     * Insert Parcelable value into WeeDB
     */
    fun put(key: String, value: Parcelable) = put(key, value as Any)

    /**
     * Insert List with Any datatype into WeeDB
     */
    fun put(key: String, value: List<*>) = put(key, value as Any)

    /**
     * Remove stored value from WeeDB.
     */
    fun remove(key: String) {
        preferences.edit().apply {
            remove(key)
            apply()
        }
    }

    /**
     * Return stored String from WeeDB, if it doesn't exist return null.
     */
    fun get(key: String): String? = getString(key)

    /**
     * Return stored parcelable value as type T from WeeDB, if it doesn't exist throws and exception.
     */
    fun <T : Parcelable?> get(key: String, clazz: Class<T>): T {
        return turnBackValue(key, clazz)
    }

    /**
     * Return stored value as type T from WeeDB, if it doesn't exist throws and exception.
     */
    fun <T> get(key: String, clazz: Class<T>): T {
        return turnBackValue(key, clazz)
    }

    /**
     * Return stored Int from WeeDB, if it doesn't exist return null.
     */
    fun getInt(key: String, defaultValue: Int = 0): Int = preferences.getString(key, null)?.toInt() ?: defaultValue

    /**
     * Return stored Long from WeeDB, if it doesn't exist return null.
     */
    fun getLong(key: String, defaultValue: Long = 0): Long = preferences.getString(key, null)?.toLong() ?: defaultValue

    /**
     * Return stored Boolean from WeeDB, if it doesn't exist return null.
     */
    fun getBoolean(key: String): Boolean = preferences.getString(key, null)?.toBoolean() ?: false

    /**
     * Return stored String from WeeDB, if it doesn't exist return null.
     */
    fun getString(key: String): String? = preferences.getString(key, null)

    /**
     * Return stored Short from WeeDB, if it doesn't exist return null.
     */
    fun getShort(key: String, defaultValue: Short = 0): Short = preferences.getString(key, null)?.toShort() ?: defaultValue

    /**
     * Return stored Float from WeeDB, if it doesn't exist return null.
     */
    fun getFloat(key: String, defaultValue: Float = 0f): Float = preferences.getString(key, null)?.toFloat() ?: defaultValue

    /**
     * Return stored Double from WeeDB, if it doesn't exist return null.
     */
    fun getDouble(key: String, defaultValue: Double = 0.0): Double = preferences.getString(key, null)?.toDouble() ?: defaultValue

    /**
     * Return stored CharArray from WeeDB, if it doesn't exist return null.
     */
    fun getCharArray(key: String): CharArray? = preferences.getString(key, null)?.toCharArray()

    /**
     * Return stored ByteArray from WeeDB, if it doesn't exist return null.
     */
    fun getByteArray(key: String): ByteArray? {
        val v = preferences.getString(key, null)?.toByteArray() ?: return null
        return Base64.decode(v, Base64.DEFAULT)
    }

    /**
     * Return stored Byte from WeeDB, if it doesn't exist return null.
     */
    fun getByte(key: String): Byte? = preferences.getString(key, null)?.toByte()

    /**
     * Return stored Bitmap from WeeDB, if it doesn't exist return null.
     */
    fun getBitmap(key: String): Bitmap? {
        val v = preferences.getString(key, null) ?: return null
        val d = Base64.decode(v, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(d, 0, d.size)
    }

    /**
     * Check if given key exist in WeeDB
     */
    operator fun contains(key: String): Boolean {
        return preferences.getString(key, null) != null
    }

    /**
     * Return WeeList with Any datatype.
     */
    fun newList(key: String): WeeList {
        preferences.edit().apply {
            putString(key, "")
            apply()
        }
        return WeeList(key, listOf())
    }

    /**
     * Return TypedList with given datatype.
     */
    fun <T> newList(key: String, clazz: Class<T>): TypedList<T> {
        preferences.edit().apply {
            putString(key, "")
            apply()
        }
        return TypedList(key, listOf(), clazz)
    }

    /**
     * Return parcelable version of ParcelableList with given datatype.
     */
    fun <T: Parcelable> newList(key: String, clazz: Class<T>): ParcelableList<T> {
        preferences.edit().apply {
            putString(key, "")
            apply()
        }
        return ParcelableList(key, listOf(), clazz)
    }

    /**
     * Get WeeList with datatype String, all values turned into string.
     */
    fun getList(key: String): WeeList? {
        val s = preferences.getString(key, null) ?: return null
        return WeeList(key, s.split("‚‗‚"))
    }

    /**
     * Get TypedList with given datatype
     */
    fun <T> getList(key: String, clazz: Class<T>): TypedList<T>? {
        val s = preferences.getString(key, null) ?: return null
        return TypedList(key, s.split("‚‗‚"), clazz)
    }

    /**
     * Get parcelable version of given datatype List
     */
    fun <T: Parcelable?> getList(key: String, clazz: Class<T>): ParcelableList<T>? {
        val s = preferences.getString(key, null) ?: return null
        return ParcelableList(key, s.split("‚‗‚"), clazz)
    }

    /**
     * Return String version of given value, if it's a ByteArray or Bitmap encode using Base64
     * and return as string. if it's a list recursively turn all values into String and join them
     * together.
     */
    private fun turnValue(v: Any): String {
        return when(v) {
            is ByteArray -> Base64.encodeToString(v, Base64.DEFAULT)
            is StringBuilder -> String(v)
            is StringBuffer -> String(v)
            is CharArray -> String(v)
            is List<*> -> {
                val arr = mutableListOf<String>()
                for (c in v) arr.add(turnValue(c!!))
                arr.joinToString("‚‗‚")
            }
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

    /**
     * Turn back to given datatype from String, if value doesn't provided then get the value using
     * the key from sharedpreferences.
     */
    private fun <T> turnBackValue(k: String, clazz: Class<T>, value: String? = null): T {
        val v = value
            ?: (preferences.getString(k, null) ?: throw Exception("No value exist for key '$k'"))
        return when {
            clazz.isAssignableFrom(Int::class.java) -> v.toInt() as T
            clazz.isAssignableFrom(Long::class.java) -> v.toLong() as T
            clazz.isAssignableFrom(Boolean::class.java) -> v.toBoolean() as T
            clazz.isAssignableFrom(String::class.java) -> v as T
            clazz.isAssignableFrom(Short::class.java) -> v.toShort() as T
            clazz.isAssignableFrom(Float::class.java) -> v.toFloat() as T
            clazz.isAssignableFrom(Double::class.java) -> v.toDouble() as T
            clazz.isAssignableFrom(CharArray::class.java) -> v.toCharArray() as T
            clazz.isAssignableFrom(ByteArray::class.java) -> Base64.decode(v, Base64.DEFAULT) as T
            clazz.isAssignableFrom(Byte::class.java) -> v.toByte() as T
            clazz.isAssignableFrom(Bitmap::class.java) -> {
                val d = Base64.decode(v, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(d, 0, d.size) as T
            }
            clazz.isAssignableFrom(WeeList::class.java) -> WeeList(k, v.split("‚‗‚")) as T
            else -> throw Exception("$clazz can not be turned back")
        }
    }

    /**
     * Turn back to given parcelable datatype from String, if value doesn't provided then get the value using
     * the key from sharedpreferences.
     */
    private fun <T: Parcelable?> turnBackValue(k: String, clazz: Class<T>, value: String? = null): T {
        val v = value
            ?: (preferences.getString(k, null) ?: throw Exception("No value exist for key '$k'"))
        val bytes = Base64.decode(v, Base64.DEFAULT)
        val parcel = Parcel.obtain()
        parcel.unmarshall(bytes, 0, bytes.size)
        parcel.setDataPosition(0)
        val bundle = parcel.readBundle(clazz.classLoader)
        parcel.recycle()
        return bundle!!.getParcelable<T>("__o__")!!
    }


    /**
     * WeeList is a dynamic List, that can store Any type of data.
     * Caution: Whenever you loop through WeeList you will only get the data back as a String.
     */
    inner class WeeList(private val key: String, private val list: List<String>): Iterable<String> {
        private val ref = list.toMutableList()

        override fun toString(): String {
            return ref.toString()
        }

        override operator fun iterator(): Iterator<String> {
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

        operator fun plusAssign(arr: Iterable<Any>) {
            for (v in arr) ref.add(turnValue(v))
            commitChanges()
        }

        val size: Int
            get() = ref.size
        val lastIndex: Int
            get() = ref.lastIndex

        fun containsAll(elements: Collection<Any>): Boolean {
            return ref.containsAll(elements.map { turnValue(it) })
        }

        fun indexOf(element: Any): Int {
            return ref.indexOf(turnValue(element))
        }

        fun isEmpty(): Boolean {
            return ref.isEmpty()
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

        fun removeAt(i: Int) {
            ref.removeAt(i)
            commitChanges()
        }

        fun updateAt(i: Int, newValue: Any) {
            ref.removeAt(i)
            ref.add(i, turnValue(newValue))
            commitChanges()
        }

        operator fun get(index: Int): String {
            return ref[index]
        }

        fun <T : Parcelable?> get(i: Int, clazz: Class<T>): T {
            return turnBackValue("", clazz, get(i))
        }

        fun <T> get(i: Int, clazz: Class<T>): T {
            return turnBackValue("", clazz, get(i))
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


    /**
     * Similar to WeeList but ParcelableList only accepts Parcelable data.
     * Whenever you loop through ParcelableList you will get back the given Parcelable datatype
     */
    inner class ParcelableList<T: Parcelable?>(private val key: String, private val list: List<String>, private val clazz: Class<T>): Iterable<T> {
        private val ref = list.toMutableList()

        override fun toString(): String {
            return ref.toString()
        }

        override operator fun iterator(): Iterator<T> {
            val mod = mutableListOf<T>()
            for (r in ref) mod.add(turnBackValue("", clazz, r))
            return mod.iterator()
        }

        fun forEach(iterator: (T) -> Unit) {
            for (r in ref) iterator(turnBackValue("", clazz, r))
        }

        private fun commitChanges() {
            val s = ref.joinToString("‚‗‚")
            preferences.edit().apply {
                putString(key, s)
                apply()
            }
        }

        operator fun contains(element: T): Boolean {
            return ref.contains(turnValue(element as Any))
        }

        operator fun plusAssign(arr: Iterable<T>) {
            for (v in arr) ref.add(turnValue(v as Any))
            commitChanges()
        }

        val size: Int
            get() = ref.size
        val lastIndex: Int
            get() = ref.lastIndex

        fun containsAll(elements: Collection<T>): Boolean {
            val els = mutableListOf<String>()
            for (e in elements) els.add(turnValue(e as Any))
            return ref.containsAll(els)
        }

        fun indexOf(element: T): Int {
            return ref.indexOf(turnValue(element as Any))
        }

        fun isEmpty(): Boolean {
            return ref.isEmpty()
        }

        fun subList(fromIndex: Int, toIndex: Int): TypedList<T> {
            return TypedList(key, ref.subList(fromIndex, toIndex), clazz)
        }

        fun add(vararg args: T) {
            for (v in args) ref.add(turnValue(v as Any))
            commitChanges()
        }

        fun remove(vararg args: T) {
            for (v in args) ref.remove(turnValue(v as Any))
            commitChanges()
        }

        fun removeAt(i: Int) {
            ref.removeAt(i)
            commitChanges()
        }

        fun updateAt(i: Int, newValue: T) {
            ref.removeAt(i)
            ref.add(i, turnValue(newValue as Any))
            commitChanges()
        }

        operator fun get(index: Int): T {
            return turnBackValue("", clazz, ref[index])
        }
    }

    /**
     * Similar to WeeList but TypedList only accepts given datatype.
     * Whenever you loop through TypedList you will get back the given datatype
     */

    inner class TypedList<T>(private val key: String, private val list: Iterable<String>, private val clazz: Class<T>): Iterable<T> {
        private val ref = list.toMutableList()

        override fun toString(): String {
            return ref.toString()
        }

        override operator fun iterator(): Iterator<T> {
            val mod = mutableListOf<T>()
            for (r in ref) mod.add(turnBackValue("", clazz, r))
            return mod.iterator()
        }

        fun forEach(iterator: (T) -> Unit) {
            for (r in ref) iterator(turnBackValue("", clazz, r))
        }

        private fun commitChanges() {
            val s = ref.joinToString("‚‗‚")
            preferences.edit().apply {
                putString(key, s)
                apply()
            }
        }

        operator fun contains(element: T): Boolean {
            return ref.contains(turnValue(element as Any))
        }

        operator fun plusAssign(arr: Iterable<T>) {
            for (v in arr) ref.add(turnValue(v as Any))
            commitChanges()
        }

        val size: Int
            get() = ref.size
        val lastIndex: Int
            get() = ref.lastIndex

        fun containsAll(elements: Collection<T>): Boolean {
            val els = mutableListOf<String>()
            for (e in elements) els.add(turnValue(e as Any))
            return ref.containsAll(els)
        }

        fun indexOf(element: T): Int {
            return ref.indexOf(turnValue(element as Any))
        }

        fun isEmpty(): Boolean {
            return ref.isEmpty()
        }

        fun subList(fromIndex: Int, toIndex: Int): TypedList<T> {
            return TypedList(key, ref.subList(fromIndex, toIndex), clazz)
        }

        fun add(vararg args: T) {
            for (v in args) ref.add(turnValue(v as Any))
            commitChanges()
        }

        fun remove(vararg args: T) {
            for (v in args) ref.remove(turnValue(v as Any))
            commitChanges()
        }

        fun removeAt(i: Int) {
            ref.removeAt(i)
            commitChanges()
        }

        fun updateAt(i: Int, newValue: T) {
            ref.removeAt(i)
            ref.add(i, turnValue(newValue as Any))
            commitChanges()
        }

        operator fun get(index: Int): T {
            return turnBackValue("", clazz, ref[index])
        }
    }
}