package com.example.design.itemodel

import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.design.R
import com.example.design.helper.BaseEpoxyHolder

@EpoxyModelClass
abstract class SeparatorModel : EpoxyModelWithHolder<SeparatorModel.Holder>() {

    override fun getDefaultLayout(): Int = R.layout.item_list_separator


    override fun bind(holder: Holder) {
        /*
        do nothing
         */
    }

    class Holder : BaseEpoxyHolder()
}


