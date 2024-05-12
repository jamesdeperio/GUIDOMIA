package com.example.design.helper
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.style.LeadingMarginSpan
class CenteredBulletSpan(
    private val bulletRadius: Int,
    private val bulletColor: Int,
    private val gapWidth: Int
) : LeadingMarginSpan {
    private var first = true

    override fun getLeadingMargin(first: Boolean): Int {
        this.first = first
        return  2 * bulletRadius + gapWidth
    }

    override fun drawLeadingMargin(
        c: Canvas,
        paint: Paint,
        x: Int,
        dir: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        first: Boolean,
        layout: Layout?
    ) {
        if (first) {
            val oldColor = paint.color
            paint.color = bulletColor
            val oldStyle = paint.style
            paint.style = Paint.Style.FILL
            c.drawCircle((x + dir * bulletRadius).toFloat(), (top + bottom) / 2.toFloat(), bulletRadius.toFloat(), paint)
            paint.color = oldColor
            paint.style = oldStyle
        }
    }
}