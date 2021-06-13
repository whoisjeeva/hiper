package sh.fearless.hiper.interfaces

import sh.fearless.hiper.data.HiperResponse

interface Listener {
    fun onSuccess(response: HiperResponse)
    fun onFail(e: Exception)
}