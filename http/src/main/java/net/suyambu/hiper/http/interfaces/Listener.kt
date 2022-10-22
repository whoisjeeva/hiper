package net.suyambu.hiper.http.interfaces

import net.suyambu.hiper.http.data.HiperResponse

interface Listener {
    fun onSuccess(response: HiperResponse)
    fun onFail(e: Exception)
}