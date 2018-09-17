package com.plattysoft.blocklyrainbowhat

import android.graphics.Color
import android.webkit.JavascriptInterface
import com.google.android.things.contrib.driver.apa102.Apa102

class Apa102WebInterface(private val ledStrip: Apa102) {
    @JavascriptInterface
    fun write(colors: Array<Int>) {
        ledStrip.write(colors.toIntArray())
    }
}
