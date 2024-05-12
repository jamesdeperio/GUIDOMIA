package com.example.design.itemodel.card

import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.example.design.R
import com.example.design.helper.BaseEpoxyHolder

@EpoxyModelClass
abstract class BannerItemModel : EpoxyModelWithHolder<BannerItemModel.Holder>() {

    @EpoxyAttribute
    lateinit var bitmap: Bitmap

    @EpoxyAttribute
    lateinit var title: String


    @EpoxyAttribute
    lateinit var description: String

    @EpoxyAttribute
    var expanded: Boolean = false


    override fun getDefaultLayout(): Int = R.layout.view_banner

    override fun bind(holder: Holder) {
        with(holder) {
            tvTitle.text = title
            tvDescription.text = description

            holder.context?.let {
                Glide.with(it)
                    .load(bitmap)
                    .override(547,364)
                    .into(ivImage)
            }

            bitmap.let {
                ivImage.setImageBitmap(it)
            }

        }
    }
    class Holder : BaseEpoxyHolder() {
        val tvTitle by bind<TextView>(R.id.tv_title)
        val tvDescription by bind<TextView>(R.id.tv_description)
        val ivImage by bind<ImageView>(R.id.iv_image)
    }

}
