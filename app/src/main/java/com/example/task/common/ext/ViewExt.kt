package com.example.task.common.ext

import android.content.Context
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.task.common.util.SafeOnClickListener


val View.ctx: Context
    get() = context

var TextView.textColor: Int
    get() = currentTextColor
    set(value) = setTextColor(ContextCompat.getColor(ctx, value))

fun View.gone() {
    visibility = GONE
}

fun View.visible() {
    visibility = VISIBLE
}


fun View.setOnSafeClickListener(safeTime: Long = 500, clickListener: (View?) -> Unit) {
    setOnClickListener(SafeOnClickListener.newInstance(safeTime) {
        clickListener(it)
    })
}
