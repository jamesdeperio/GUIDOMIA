package com.example.common.router.route

object MainRoute {
    object MainScreen:Route() {
        override fun getClassPath(): String
                = "com.example.androiddemo.ui.main.MainActivity"
    }
}