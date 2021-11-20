package com.thebahrimedia.moocommerce.callbacks


import android.view.View


abstract class DoubleTapToToggle : View.OnClickListener {
    var lastClickTime: Long = 0
    override fun onClick(v: View?) {
        val clickTime = System.currentTimeMillis()
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
            onDoubleClick()
        }
        lastClickTime = clickTime
    }

    abstract fun onDoubleClick()

    companion object {
        private const val DOUBLE_CLICK_TIME_DELTA: Long = 300 //milliseconds
    }
}