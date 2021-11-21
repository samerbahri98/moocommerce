package com.thebahrimedia.moocommerce.callbacks


import android.view.View
import java.util.*
import kotlin.concurrent.schedule


abstract class DoubleTapToToggle : View.OnClickListener {
    var lastClickTime: Long = 0
    private val t :Timer = Timer()
    override fun onClick(v: View?) {
        val clickTime = System.currentTimeMillis()
        t.schedule(DOUBLE_CLICK_TIME_DELTA){
            if (t != null)
                onSingleClick()
        }
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA ) {
            cancelSingleClickSchedule()
            onDoubleClick()

        }
        lastClickTime = clickTime
    }
    fun cancelSingleClickSchedule(){
        t.cancel()
        t.purge()
    }
    abstract fun onDoubleClick()

    abstract fun onSingleClick()

    companion object {
        private const val DOUBLE_CLICK_TIME_DELTA: Long = 300 //milliseconds
    }
}