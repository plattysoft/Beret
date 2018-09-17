package com.plattysoft.blocklyrainbowhat

import android.webkit.JavascriptInterface
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay

class AlphanumericDisplayWebInterface(val display: AlphanumericDisplay) {

    @JavascriptInterface
    fun setEnabled(state: Boolean) {
        display.setEnabled(state)
    }

    @JavascriptInterface
    fun displayText(text: String) {
        display.display(text)
    }

    @JavascriptInterface
    fun displayNumber(value: Double) {
        display.display(value)
    }
}
