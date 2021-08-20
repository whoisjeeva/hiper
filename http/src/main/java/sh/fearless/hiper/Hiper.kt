package sh.fearless.hiper

import org.json.JSONObject
import sh.fearless.hiper.controllers.Caller
import sh.fearless.hiper.data.HiperResponse
import java.io.File


/* Hiper */

class Hiper {
    fun get(
        url: String, isStream: Boolean = false, byteSize: Int = 4096,
        args: HashMap<String, Any> = hashMapOf(),
        headers: HashMap<String, Any> = hashMapOf(),
        cookies: HashMap<String, Any> = hashMapOf(),
        username: String? = null,
        password: String? = null,
        timeout: Long? = null
    ): HiperResponse {
        return GetRequest(url, isStream, byteSize, args=args, headers=headers, cookies=cookies,
            username = username, password = password, timeout=timeout).sync()
    }

    fun post(
        url: String, isStream: Boolean = false, byteSize: Int = 4096,
        args: HashMap<String, Any> = hashMapOf(),
        form: HashMap<String, Any> = hashMapOf(),
        files: List<File> = listOf(),
        json: JSONObject? = null,
        headers: HashMap<String, Any> = hashMapOf(),
        cookies: HashMap<String, Any> = hashMapOf(),
        username: String? = null,
        password: String? = null,
        timeout: Long? = null
    ): HiperResponse {
        return PostRequest(url, isStream, byteSize, args=args, form=form, files=files, json=json, headers=headers,
            cookies=cookies, username=username, password = password, timeout = timeout).sync()
    }

    fun head(
        url: String, isStream: Boolean = false, byteSize: Int = 4096,
        args: HashMap<String, Any> = hashMapOf(),
        headers: HashMap<String, Any> = hashMapOf(),
        cookies: HashMap<String, Any> = hashMapOf(),
        username: String? = null,
        password: String? = null,
        timeout: Long? = null
    ): HiperResponse {
        return HeadRequest(url, isStream, byteSize, args=args, headers=headers, cookies=cookies,
            username = username, password = password, timeout=timeout).sync()
    }

    fun async() = asyncInstance ?: synchronized(this) {
        asyncInstance ?: Asynchronous().also { asyncInstance = it }
    }


    inner class Asynchronous {
        fun get(
            url: String, isStream: Boolean = false, byteSize: Int = 4096,
            args: HashMap<String, Any> = hashMapOf(),
            headers: HashMap<String, Any> = hashMapOf(),
            cookies: HashMap<String, Any> = hashMapOf(),
            username: String? = null,
            password: String? = null,
            timeout: Long? = null,
            callback: (HiperResponse.() -> Unit)? = null
        ): Caller {
            val req = GetRequest(url, isStream, byteSize, args=args, headers=headers, cookies=cookies,
                username = username, password = password, timeout=timeout)
            return req.async(callback)
        }

        fun post(
            url: String, isStream: Boolean = false, byteSize: Int = 4096,
            args: HashMap<String, Any> = hashMapOf(),
            form: HashMap<String, Any> = hashMapOf(),
            files: List<File> = listOf(),
            json: JSONObject? = null,
            headers: HashMap<String, Any> = hashMapOf(),
            cookies: HashMap<String, Any> = hashMapOf(),
            username: String? = null,
            password: String? = null,
            timeout: Long? = null,
            callback: (HiperResponse.() -> Unit)? = null
        ): Caller {
            val req = PostRequest(url, isStream, byteSize, args=args, form=form, files=files, json=json,
                headers=headers, cookies=cookies, username=username, password=password, timeout = timeout)
            return req.async(callback)
        }

        fun head(
            url: String, isStream: Boolean = false, byteSize: Int = 4096,
            args: HashMap<String, Any> = hashMapOf(),
            headers: HashMap<String, Any> = hashMapOf(),
            cookies: HashMap<String, Any> = hashMapOf(),
            username: String? = null,
            password: String? = null,
            timeout: Long? = null,
            callback: (HiperResponse.() -> Unit)? = null
        ): Caller {
            val req = HeadRequest(url, isStream, byteSize, args=args, headers=headers, cookies=cookies,
                username = username, password = password, timeout=timeout)
            return req.async(callback)
        }
    }

    companion object {
        @Volatile private var instance: Hiper? = null
        @Volatile private var asyncInstance: Hiper.Asynchronous? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: Hiper().also { instance = it }
        }
    }
}