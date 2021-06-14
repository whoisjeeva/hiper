package sh.fearless.hiper

import sh.fearless.hiper.controllers.Caller
import sh.fearless.hiper.data.HiperResponse
import sh.fearless.hiper.interfaces.Listener

class Queue(private val async: (Listener) -> Caller) {
    private lateinit var resolveCallback: (hiperResponse: HiperResponse) -> Unit
    private lateinit var rejectCallback: (hiperResponse: HiperResponse) -> Unit
    private lateinit var catchCallback: (e: Exception) -> Unit

    fun resolve(callback: (hiperResponse: HiperResponse) -> Unit): Queue {
        resolveCallback = callback
        return this
    }

    fun reject(callback: (hiperResponse: HiperResponse) -> Unit): Queue {
        rejectCallback = callback
        return this
    }

    fun catch(callback: ((Exception) -> Unit)): Queue {
        catchCallback = callback
        return this
    }

    fun execute(): Caller {
        return async(object : Listener {
            override fun onFail(e: Exception) {
                catchCallback.invoke(e)
            }

            override fun onSuccess(response: HiperResponse) {
                if (response.isSuccessful) resolveCallback(response) else rejectCallback(response)
            }
        })
    }
}