package com.example.design.itemodel

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.design.R
import com.example.design.helper.BaseEpoxyHolder
import com.example.design.listener.setOnThrottleClickListener

@EpoxyModelClass
abstract class TextViewItemModel : EpoxyModelWithHolder<TextViewItemModel.Holder>() {

    @EpoxyAttribute
    lateinit var item:String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var listener: Listener? = null
    override fun getDefaultLayout(): Int = R.layout.item_list_filter

    override fun bind(holder: Holder) {
        with(holder) {
            tvTitle.text = item
            tvTitle.setOnThrottleClickListener {
                listener?.onItemClicked(item)
            }
        }

    }

    interface Listener { fun onItemClicked(item: String)
    }


    class Holder : BaseEpoxyHolder() {
        val tvTitle by bind<TextView>(R.id.tv_title)
    }

}

