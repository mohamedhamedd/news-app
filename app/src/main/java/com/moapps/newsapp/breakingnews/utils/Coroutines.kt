package com.moapps.newsapp.breakingnews.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object Coroutines {

    fun main(work: suspend (() -> Unit)) =
            CoroutineScope(Dispatchers.Main).launch {
                work()
            }

    fun io(work: suspend (() -> Unit)) =
            CoroutineScope(Dispatchers.IO).launch {
                work()
            }

    suspend fun withContextMain(work: suspend (() -> Unit)) =
            withContext(Dispatchers.Main) {
                work()
            }

}