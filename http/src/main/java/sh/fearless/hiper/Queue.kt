package sh.fearless.hiper

import sh.fearless.hiper.controllers.Caller
import sh.fearless.hiper.data.HiperResponse
import sh.fearless.hiper.interfaces.Listener

class Queue(private val async: (Listener) -> Caller) {
    private lateinit var useCallback: (hiperResponse: HiperResponse) -> Unit
    private lateinit var rejectCallback: (hiperResponse: HiperResponse) -> Unit

    fun resolve(callback: (hiperResponse: HiperResponse) -> Unit): Queue {
        useCallback = callback
        return this
    }

    fun reject(callback: (hiperResponse: HiperResponse) -> Unit): Queue {
        rejectCallback = callback
        return this
    }

    fun catch(callback: ((Exception) -> Unit)? = null): Caller {
        return async(object : Listener {
            override fun onFail(e: Exception) {
                callback?.invoke(e)
            }

            override fun onSuccess(response: HiperResponse) {
                if (response.isSuccessful) useCallback(response) else rejectCallback(response)
            }
        })
    }
}