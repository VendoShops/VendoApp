package com.example.vendoapp.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.vendoapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class CustomPrimaryButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : MaterialButton(context, attrs, defStyleAttr) {

    init {
        setup()
    }

    private fun setup() {
        val density = context.resources.displayMetrics.density

        setTextColor(ContextCompat.getColor(context, android.R.color.white))
        isAllCaps = false
        elevation = 6f
        textSize = 16f
        val radiusPx = 8f * density
        typeface = ResourcesCompat.getFont(context, R.font.dmsansmedium)


        val shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCornerSizes(radiusPx)
            .build()

        val backgroundDrawable = MaterialShapeDrawable(shapeAppearanceModel).apply {
            fillColor = ContextCompat.getColorStateList(context, R.color.black)
            this.elevation = this@CustomPrimaryButton.elevation
        }

        background = backgroundDrawable
    }
}