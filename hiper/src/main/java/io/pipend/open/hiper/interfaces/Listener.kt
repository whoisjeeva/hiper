package me.gumify.hiper.http.interfaces

import me.gumify.hiper.http.data.HiperResponse

interface Listener {
    fun onSuccess(response: HiperResponse)
    fun onFail(e: Exception)
}