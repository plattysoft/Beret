package com.plattysoft.blocklyrainbowhat

import android.webkit.JavascriptInterface
import com.google.android.things.contrib.driver.bmx280.Bmx280

class Bmx280WebInterface(private val temperatureSensor: Bmx280) {

    @JavascriptInterface
    fun readTemperature(): Float {
        return temperatureSensor.readTemperature()
    }

    @JavascriptInterface
    fun readPressure(): Float {
        return temperatureSensor.readPressure()
    }
}
