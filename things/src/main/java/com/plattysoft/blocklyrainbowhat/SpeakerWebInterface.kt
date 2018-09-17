package com.plattysoft.blocklyrainbowhat

import android.webkit.JavascriptInterface
import com.google.android.things.contrib.driver.pwmspeaker.Speaker

class SpeakerWebInterface(private val buzzer: Speaker) {

    @JavascriptInterface
    fun play(frequency: Double) {
        buzzer.play(frequency)
    }

    @JavascriptInterface
    fun stop() {
        return buzzer.stop()
    }
}
