package com.plattysoft.blocklyrainbowhat

import android.app.Activity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio
import com.plattysoft.blockilyrainbowhat.R
import kotlinx.android.synthetic.main.activity_main.*

private val TAG = MainActivity::class.java.simpleName

class MainActivity : Activity() {

    lateinit var redLed: Gpio
    lateinit var greenLed: Gpio
    lateinit var blueLed: Gpio

    lateinit var buttonA: Button
    lateinit var buttonB: Button
    lateinit var buttonC: Button

    lateinit var alphanumericDisplay: AlphanumericDisplay

    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView: WebView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebAppInterface(this), "Android")

        initRainbowHat()

        webView.loadUrl("file:///android_asset/index.html");

        /*
         * The communication from the app towards the WebApp includes the information based on events
         * - Button A, B or C pressed
         * - Temperature / pressure changed
         */
        buttonA.setOnButtonEventListener { button: Button, b: Boolean ->
            webView.evaluateJavascript("javascript: " +
                    "onButtonAPressed($b);",
                    null)
        }
        buttonB.setOnButtonEventListener { button: Button, b: Boolean ->
            webView.evaluateJavascript("javascript: " +
                    "onButtonBPressed($b);",
                    null)
        }
        buttonC.setOnButtonEventListener { button: Button, b: Boolean ->
            webView.evaluateJavascript("javascript: " +
                    "onButtonCPressed($b);",
                    null)
        }
    }

    private fun initRainbowHat() {
        redLed = RainbowHat.openLedRed()
        greenLed = RainbowHat.openLedGreen()
        blueLed = RainbowHat.openLedBlue()

        buttonA = RainbowHat.openButtonA()
        buttonB = RainbowHat.openButtonB()
        buttonC = RainbowHat.openButtonC()

        alphanumericDisplay = RainbowHat.openDisplay()
    }

    override fun onDestroy() {
        super.onDestroy()

        redLed.close()
        greenLed.close()
        blueLed.close()

        buttonA.close()
        buttonB.close()
        buttonC.close()

        alphanumericDisplay.close()
    }

}

/**
 * Class that defines all the communication from the WebApp to the Rainbow HAT
 * It gives access to:
 * - Red, Green and Blue LEDs
 * - Read button A, B and C states
 * - Read temperature value
 * - Alphanumeric display
 * - RGB LED Strip
 * - Piezo buzzer
 */
class WebAppInterface(val mainActivity: MainActivity) {
    @JavascriptInterface
    fun setRedLed(state: Boolean) {
        mainActivity.redLed.value = state
    }

    @JavascriptInterface
    fun setGreenLed(state: Boolean) {
        mainActivity.greenLed.value = state
    }

    @JavascriptInterface
    fun setBlueLed(state: Boolean) {
        mainActivity.blueLed.value = state
    }

    @JavascriptInterface
    fun setAlphanumericDisplayEnable(state: Boolean) {
        mainActivity.alphanumericDisplay.setEnabled(state)
    }

    @JavascriptInterface
    fun display(text: String) {
        mainActivity.alphanumericDisplay.display(text)
    }

    @JavascriptInterface
    fun display(value: Int) {
        mainActivity.alphanumericDisplay.display(value)
    }
}
