package com.example.design.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.design.R
import com.example.design.databinding.TopbarBinding

@SuppressLint("SetTextI18n")
class Topbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    private var binding: TopbarBinding = TopbarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        if (attrs != null) {
            val attrib = context.obtainStyledAttributes(attrs,R.styleable.Topbar)
            attrib.recycle()
        }
    }
}