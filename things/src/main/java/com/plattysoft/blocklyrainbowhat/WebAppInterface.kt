package com.plattysoft.blocklyrainbowhat

import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.google.android.things.contrib.driver.button.Button


class WebAppInterface (val controller: RainbowHatController, val webView: WebView) {

    private var stateButtonA = false
    private var stateButtonB = false
    private var stateButtonC = false

    init{
        controller.buttonA.setOnButtonEventListener { button: Button, b: Boolean ->
            stateButtonA = b
            if (b) {
                webView.evaluateJavascript("javascript: onButtonAPressed();",null)
            }
            else {
                webView.evaluateJavascript("javascript: onButtonAReleased();",null)
            }
            webView.evaluateJavascript("javascript: onButtonAChanged();",null)
        }
        controller.buttonB.setOnButtonEventListener { button: Button, b: Boolean ->
            stateButtonB = b
            if (b) {
                webView.evaluateJavascript("javascript: onButtonBPressed();",null)
            }
            else {
                webView.evaluateJavascript("javascript: onButtonBReleased();",null)
            }
            webView.evaluateJavascript("javascript: onButtonBChanged();",null)
        }
        controller.buttonC.setOnButtonEventListener { button: Button, b: Boolean ->
            stateButtonC = b
            if (b) {
                webView.evaluateJavascript("javascript: onButtonCPressed();",null)
            }
            else {
                webView.evaluateJavascript("javascript: onButtonCReleased();",null)
            }
            webView.evaluateJavascript("javascript: onButtonCChanged();",null)
        }
    }

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
        return stateButtonA
    }

    @JavascriptInterface
    fun getStateButtonB(): Boolean {
        return stateButtonB
    }

    @JavascriptInterface
    fun getStateButtonC(): Boolean {
        return stateButtonC
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
