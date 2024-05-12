package com.example.common.router.route

sealed class Route {
    abstract fun getClassPath(): String
}
