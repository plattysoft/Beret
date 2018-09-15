package com.plattysoft.blocklyrainbowhat

import android.app.Activity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio

private val TAG = MainActivity::class.java.simpleName

class MainActivity : Activity() {

    lateinit var redLed: Gpio
    lateinit var greenLed: Gpio
    lateinit var blueLed: Gpio

    lateinit var buttonA: Button
    lateinit var buttonB: Button
    lateinit var buttonC: Button

    lateinit var alphanumericDisplay: AlphanumericDisplay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRainbowHat()

        val webView: WebView = findViewById(R.id.webView) as WebView
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebAppInterface(this), "Android")
        webView.addJavascriptInterface(AlphanumericDisplayWebInterface(alphanumericDisplay), "AlphanumericDisplay")


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

        loadSampleProgram(webView)
    }

    private fun loadSampleProgram(webView: WebView) {
        val program =
                "<script language=\"JavaScript\">\n"+
                    "function onButtonAPressed(pressed) {\n" +
                    "    Android.setRedLed(pressed)\n" +
                    "}\n" +
                    "\n" +
                    "function onButtonBPressed(pressed) {\n" +
                    "    Android.setGreenLed(pressed)\n" +
                    "}\n" +
                    "\n" +
                    "function onButtonCPressed(pressed) {\n" +
                    "    Android.setBlueLed(pressed)\n" +
                    "}\n"+
                "</script>"
        webView.loadData(program, "text/html", "UTF-8")
    }

    private fun initRainbowHat() {
        redLed = RainbowHat.openLedRed()
        greenLed = RainbowHat.openLedGreen()
        blueLed = RainbowHat.openLedBlue()

        buttonA = RainbowHat.openButtonA()
        buttonB = RainbowHat.openButtonB()
        buttonC = RainbowHat.openButtonC()

        alphanumericDisplay = RainbowHat.openDisplay()
        alphanumericDisplay.setEnabled(true)
        alphanumericDisplay.setBrightness(AlphanumericDisplay.HT16K33_BRIGHTNESS_MAX)
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
    fun setAlphanumericDisplayText(text: String) {
        mainActivity.alphanumericDisplay.display(text)
    }
}
