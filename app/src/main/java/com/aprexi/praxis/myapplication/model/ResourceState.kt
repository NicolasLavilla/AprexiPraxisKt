package com.aprexi.praxis.myapplication.model

sealed class ResourceState<T> {

    class Offer<T> : ResourceState<T>()
    class Loading<T> : ResourceState<T>()
    data class Success<T>(val result: T) : ResourceState<T>()
    data class SuccessFaild<T>(val result: T) : ResourceState<T>()
    data class Error<T>(val error: String) : ResourceState<T>()
}
