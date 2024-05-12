package com.example.common.router

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.common.router.route.Route


object AppRouter {
    const val EXTRA_BUNDLE = "EXTRA_BUNDLE"
    fun navigate(activity: Activity, route: Route, bundle: Bundle?=null): Activity {
        try {
            val intent = Intent(activity,Class.forName(route.getClassPath()))
            bundle?.let {
                intent.putExtra(EXTRA_BUNDLE,it)
            }
            activity.startActivity(intent)
        }catch (e:ClassNotFoundException) {
            e.printStackTrace()
        }
        return activity
    }

}

fun Activity.navigate(route: Route, bundle: Bundle?=null): Activity {
    AppRouter.navigate(this,route,bundle)
    return this
}

