package com.themoviedbdemo.utills

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher

open class DelayedTextWatcher(
    doAfterTextChanged: () -> Unit
) : TextWatcher {

    private val delay: Long = 300
    private val handler = Handler()

    private val runnable = Runnable {
        doAfterTextChanged.invoke()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, delay)
    }

}