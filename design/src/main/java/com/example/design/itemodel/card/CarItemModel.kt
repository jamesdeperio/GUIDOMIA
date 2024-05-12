package com.example.design.itemodel.card

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.example.common.ext.dpToPx
import com.example.design.R
import com.example.design.helper.BaseEpoxyHolder
import com.example.design.helper.CenteredBulletSpan
import com.example.design.listener.setOnThrottleClickListener


@EpoxyModelClass
abstract class CarItemModel : EpoxyModelWithHolder<CarItemModel.Holder>() {

    @EpoxyAttribute
    lateinit var carItem: CarItem

    @EpoxyAttribute
    var expanded: Boolean = false

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var listener: Listener? = null


    override fun getDefaultLayout(): Int = R.layout.item_list_expanded


    override fun bind(holder: Holder) {
        with(holder) {
            tvTitle.text = carItem.maker
            tvDescription.text = carItem.price
            itemView?.setOnThrottleClickListener {
                listener?.onItemClicked(carItem)
            }


            ratingContainer.removeAllViews()

            holder.context?.let {
                Glide.with(it)
                    .load(carItem.bitmap)
                    .into(ivImage)

                for (i in 1..carItem.ratings) {
                    ratingContainer.addView(createRating(it))
                }
            }

            if (expanded) {
                tvProsCons.visibility = View.VISIBLE
                val stringBuilder = SpannableStringBuilder()
                 carItem.pros?.also {
                     if (it.isNotEmpty()) {
                         stringBuilder.append(createHeaderSpannable("Pros:\n"))
                     }
                 }?.filter { it.isNotBlank() }?.forEach {
                    stringBuilder.append(createOrangeBullet(it))
                }
                carItem.cons?.also {
                    if (it.isNotEmpty()){
                        if (stringBuilder.isEmpty()) {
                            stringBuilder.append(createHeaderSpannable("Cons:\n"))
                        }else {
                            stringBuilder.append(createHeaderSpannable("\n\nCons:\n"))
                        }
                    }
                }?.filter { it.isNotBlank() }?.forEach {
                    stringBuilder.append(createOrangeBullet(it))
                }
                tvProsCons.text =  stringBuilder
            }
            else {
                tvProsCons.visibility = View.GONE
            }
        }
    }

    fun createRating(context: Context): ImageView {
        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.baseline_star_24)
        val layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, context.dpToPx(5), 0)

        imageView.layoutParams = layoutParams

        return imageView
    }

    fun createOrangeBullet(text: String): SpannableString {
        val spannableString = SpannableString(text)
        spannableString.setSpan(CenteredBulletSpan(5,Color.parseColor("#FC6016"), 10),0,  text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }

    fun createHeaderSpannable(text: String): SpannableString {
        val spannableString = SpannableString(text)
        spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, text.length, 0)
        spannableString.setSpan(RelativeSizeSpan(1.3f), 0, text.length, 0)

        return spannableString
    }

    class Holder : BaseEpoxyHolder() {
        val tvTitle by bind<TextView>(R.id.tv_title)
        val tvDescription by bind<TextView>(R.id.tv_description)
        val ivImage by bind<ImageView>(R.id.iv_image)
        val ratingContainer by bind<ViewGroup>(R.id.view_rating_container)
        val tvProsCons by bind<TextView>(R.id.tv_pros_cons)
    }


    interface Listener {
        fun onItemClicked(carItem: CarItem)
    }

    data class CarItem(
        val bitmap:Bitmap? = null,
        val maker:String? = null,
        val price:String? = null,
        val ratings:Int = 0,
        val pros:List<String>? = null,
        val cons:List<String>? = null
        )

}
