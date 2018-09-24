package com.plattysoft.blocklyrainbowhat

import android.webkit.JavascriptInterface


class WebAppInterface (val controller: RainbowHatController) {
    @JavascriptInterface
    fun setRedLed(state: Boolean) {
        controller.redLed.value = state
    }

    @JavascriptInterface
    fun getStateLedRed(): Boolean{
        return controller.redLed.value
    }

    @JavascriptInterface
    fun getStateLedGreen(): Boolean{
        return controller.greenLed.value
    }

    @JavascriptInterface
    fun getStateLedBlue(): Boolean{
        return controller.blueLed.value
    }

    @JavascriptInterface
    fun getStateButtonA(): Boolean {
        return controller.stateButtonA
    }

    @JavascriptInterface
    fun getStateButtonB(): Boolean {
        return controller.stateButtonB
    }

    @JavascriptInterface
    fun getStateButtonC(): Boolean {
        return controller.stateButtonC
    }

    @JavascriptInterface
    fun setGreenLed(state: Boolean) {
        controller.greenLed.value = state
    }

    @JavascriptInterface
    fun setBlueLed(state: Boolean) {
        controller.blueLed.value = state
    }
}
