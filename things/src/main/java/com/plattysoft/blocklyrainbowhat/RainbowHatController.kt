package com.plattysoft.blocklyrainbowhat

import android.content.Context
import android.graphics.Color
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.google.android.things.contrib.driver.apa102.Apa102
import com.google.android.things.contrib.driver.bmx280.Bmx280
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat

class RainbowHatController (context: Context) {

    val redLed = RainbowHat.openLedRed()
    val greenLed = RainbowHat.openLedGreen()
    val blueLed = RainbowHat.openLedBlue()

    val buttonA =  RainbowHat.openButtonA()
    val buttonB =  RainbowHat.openButtonB()
    val buttonC =  RainbowHat.openButtonC()

    var stateButtonA = false
    var stateButtonB = false
    var stateButtonC = false

    private val alphanumericDisplay = RainbowHat.openDisplay()
    init {
        alphanumericDisplay.setEnabled(true)
        alphanumericDisplay.setBrightness(AlphanumericDisplay.HT16K33_BRIGHTNESS_MAX)
    }

    private val temperatureSensor = RainbowHat.openSensor()
    init {
        temperatureSensor.temperatureOversampling = Bmx280.OVERSAMPLING_1X
        temperatureSensor.pressureOversampling = Bmx280.OVERSAMPLING_1X
        temperatureSensor.setMode(Bmx280.MODE_NORMAL)
    }

    private val buzzer = RainbowHat.openPiezo()

    private val ledStrip = RainbowHat.openLedStrip()
    init {
        ledStrip.direction = Apa102.Direction.REVERSED
        ledStrip.brightness = 31
    }

    private var webView = WebView(context)
    init {
        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebAppInterface(this), "Android")
        webView.addJavascriptInterface(AlphanumericDisplayWebInterface(alphanumericDisplay), "AlphanumericDisplay")
        webView.addJavascriptInterface(Bmx280WebInterface(temperatureSensor), "Bmx280")
        webView.addJavascriptInterface(SpeakerWebInterface(buzzer), "Speaker")
        webView.addJavascriptInterface(Apa102WebInterface(ledStrip), "Apa102")
    }

    init {
        buttonA.setOnButtonEventListener { button: Button, b: Boolean ->
            stateButtonA = b
            if (b) {
                webView.evaluateJavascript("javascript: onButtonAPressed();",null)
            }
            else {
                webView.evaluateJavascript("javascript: onButtonAReleased();",null)
            }
            webView.evaluateJavascript("javascript: onButtonAChanged();",null)
        }
        buttonB.setOnButtonEventListener { button: Button, b: Boolean ->
            stateButtonB = b
            if (b) {
                webView.evaluateJavascript("javascript: onButtonBPressed();",null)
            }
            else {
                webView.evaluateJavascript("javascript: onButtonBReleased();",null)
            }
            webView.evaluateJavascript("javascript: onButtonBChanged();",null)
        }
        buttonC.setOnButtonEventListener { button: Button, b: Boolean ->
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

    fun cleanRainbowHatState() {
        redLed.value = false
        greenLed.value = false
        blueLed.value = false
        alphanumericDisplay.clear()
        ledStrip.write(intArrayOf(
                Color.TRANSPARENT,
                Color.TRANSPARENT,
                Color.TRANSPARENT,
                Color.TRANSPARENT,
                Color.TRANSPARENT,
                Color.TRANSPARENT,
                Color.TRANSPARENT))
    }

    fun close() {
        redLed.close()
        greenLed.close()
        blueLed.close()

        buttonA.close()
        buttonB.close()
        buttonC.close()

        alphanumericDisplay.close()
        temperatureSensor.close()
        buzzer.close()
        ledStrip.close()
    }

    fun executeProgram(program: String) {
        webView.loadUrl("about:blank")
        cleanRainbowHatState()
        val tagProgram = "<script language=\"JavaScript\">\n $program </script>"
        webView.loadData(tagProgram, "text/html", "UTF-8")
    }
}

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