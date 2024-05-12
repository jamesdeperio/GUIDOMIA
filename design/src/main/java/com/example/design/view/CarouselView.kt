package com.example.design.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CarouselView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : Carousel(context, attrs, defStyleAttrs) {

    override fun createLayoutManager(): LayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

    override fun getDefaultSpacingBetweenItemsDp(): Int = 0
}
