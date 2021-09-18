package com.moapps.newsapp.breakingnews.utils

sealed class Resource<T>(val status: Status, val Data: T?, val message: String?) {
    class success<T>(data: T) : Resource<T>(Status.SUCCESS, data, null)
    class error<T>(message: String,data: T?) : Resource<T>(Status.ERROR, data, message)
    class loading<T>(data:T?) : Resource<T>(Status.LOADING, data, null)
}