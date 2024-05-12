package com.example.design.helper

import android.content.Context
import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class BaseEpoxyHolder : EpoxyHolder() {

    var itemView: View? = null
    var context: Context? = null

    override fun bindView(itemView: View) {
        this.itemView = itemView
        this.context = itemView.context
    }

    protected fun <V : View> bind(id: Int): ReadOnlyProperty<BaseEpoxyHolder, V> =
        Lazy { holder: BaseEpoxyHolder, prop ->
            holder.itemView?.findViewById(id) as? V ?: throw IllegalStateException("ViewID $id (${prop.name}): Not found.")
        }

    private class Lazy<V>(private val initializer: (BaseEpoxyHolder, KProperty<*>) -> V) :
        ReadOnlyProperty<BaseEpoxyHolder, V> {

        private object EMPTY

        private var value: Any? = EMPTY

        @Suppress("UNCHECKED_CAST")
        override fun getValue(thisRef: BaseEpoxyHolder, property: KProperty<*>): V {
            if (value == EMPTY) {
                value = initializer(thisRef, property)
            }
            return value as V
        }
    }
}
