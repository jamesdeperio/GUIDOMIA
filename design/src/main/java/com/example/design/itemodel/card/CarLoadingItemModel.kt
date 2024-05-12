package com.example.design.itemodel.card

import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.design.R
import com.example.design.helper.BaseEpoxyHolder


@EpoxyModelClass
abstract class CarLoadingItemModel : EpoxyModelWithHolder<CarLoadingItemModel.Holder>() {

    override fun getDefaultLayout(): Int = R.layout.loading_list


    override fun bind(holder: Holder) {
      //do nothing
    }


    class Holder : BaseEpoxyHolder()
}
