package com.plattysoft.blocklyrainbowhat

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.google.android.things.contrib.driver.apa102.Apa102
import com.google.android.things.contrib.driver.bmx280.Bmx280
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.google.android.things.contrib.driver.pwmspeaker.Speaker
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio
import com.google.blockly.android.codegen.CodeGenerationRequest

class MainActivity : RainbowHatBlocklyBaseActivity(), NearbyIotWrapper.RemoteEditorListener {

    lateinit var redLed: Gpio
    lateinit var greenLed: Gpio
    lateinit var blueLed: Gpio

    lateinit var buttonA: Button
    lateinit var buttonB: Button
    lateinit var buttonC: Button

    var stateButtonA = false
    var stateButtonB = false
    var stateButtonC = false

    lateinit var alphanumericDisplay: AlphanumericDisplay
    lateinit var temperatureSensor: Bmx280
    lateinit var buzzer: Speaker
    lateinit var ledStrip: Apa102

    lateinit var webView: WebView

    lateinit var nearbyIotWrapper: NearbyIotWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nearbyIotWrapper = NearbyIotWrapper(this, this)

        initRainbowHat()

        webView = WebView(this)
        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebAppInterface(this), "Android")
        webView.addJavascriptInterface(AlphanumericDisplayWebInterface(alphanumericDisplay), "AlphanumericDisplay")
        webView.addJavascriptInterface(Bmx280WebInterface(temperatureSensor), "Bmx280")
        webView.addJavascriptInterface(SpeakerWebInterface(buzzer), "Speaker")
        webView.addJavascriptInterface(Apa102WebInterface(ledStrip), "Apa102")

        /*
         * The communication from the app towards the WebApp includes the information based on events
         * - Button A, B or C pressed
         */
        // TODO: This code should be run on JavaScript
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

    private fun loadProgram(program: String) {
        Log.d("Program:", program)
        val tagProgram = "<script language=\"JavaScript\">\n $program </script>"
        runOnUiThread{
            // Clean state
            webView.loadUrl("about:blank")
            cleanRainbowHatState()
            webView.loadData(tagProgram, "text/html", "UTF-8")
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
        alphanumericDisplay.setEnabled(true)
        alphanumericDisplay.setBrightness(AlphanumericDisplay.HT16K33_BRIGHTNESS_MAX)

        temperatureSensor = RainbowHat.openSensor()
        temperatureSensor.temperatureOversampling = Bmx280.OVERSAMPLING_1X
        temperatureSensor.pressureOversampling = Bmx280.OVERSAMPLING_1X
        temperatureSensor.setMode(Bmx280.MODE_NORMAL)

        buzzer = RainbowHat.openPiezo()

        ledStrip = RainbowHat.openLedStrip()
        ledStrip.direction = Apa102.Direction.REVERSED
        ledStrip.brightness = 31

        cleanRainbowHatState()
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
        temperatureSensor.close()
        buzzer.close()
        ledStrip.close()

        nearbyIotWrapper.stop()
    }

    override fun onProgramReceived(program: String) {
        loadProgram(program)
    }

    override fun getCodeGenerationCallback(): CodeGenerationRequest.CodeGeneratorCallback {
        return CodeGenerationRequest.CodeGeneratorCallback {
            loadProgram(it)
        }
    }

    override fun onInitBlankWorkspace() {
        // Initialize available variable names.
        controller.addVariable("item")
    }

    override fun getActionBarMenuResId(): Int {
        return R.menu.blockly_default_actionbar
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.getItemId()

        if (id == R.id.action_run) {
            if (controller.workspace.hasBlocks()) {
                onRunCode()
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun cleanRainbowHatState() {
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


}

/**
 * Class that defines all the communication from the WebApp to the LEDs and buttons
 */
class WebAppInterface(val mainActivity: MainActivity) {
    @JavascriptInterface
    fun setRedLed(state: Boolean) {
        mainActivity.redLed.value = state
    }

    @JavascriptInterface
    fun getStateLedRed(): Boolean{
        return mainActivity.redLed.value
    }

    @JavascriptInterface
    fun getStateLedGreen(): Boolean{
        return mainActivity.greenLed.value
    }

    @JavascriptInterface
    fun getStateLedBlue(): Boolean{
        return mainActivity.blueLed.value
    }

    @JavascriptInterface
    fun getStateButtonA(): Boolean {
        return mainActivity.stateButtonA
    }

    @JavascriptInterface
    fun getStateButtonB(): Boolean {
        return mainActivity.stateButtonB
    }

    @JavascriptInterface
    fun getStateButtonC(): Boolean {
        return mainActivity.stateButtonC
    }

    @JavascriptInterface
    fun setGreenLed(state: Boolean) {
        mainActivity.greenLed.value = state
    }

    @JavascriptInterface
    fun setBlueLed(state: Boolean) {
        mainActivity.blueLed.value = state
    }

}
