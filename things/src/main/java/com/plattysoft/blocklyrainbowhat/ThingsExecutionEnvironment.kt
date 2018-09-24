package com.plattysoft.blocklyrainbowhat

import android.content.Context
import android.webkit.WebView
import com.google.android.things.contrib.driver.button.Button


class ThingsExecutionEnvironment(private val context: Context) {

    companion object {
        private const val LEDS_JS_OBJECT = "Android"
        private const val LCD_JS_OBJECT = "AlphanumericDisplay"
        private const val SENSOR_JS_OBJECT = "Bmx280"
        private const val BUZZER_JS_OBJECT = "Speaker"
        private const val LED_STRIP_JS_OBJECT = "Apa102"
    }

    private val rainbowHatController = RainbowHatController()

    private var stateButtonA = false
    private var stateButtonB = false
    private var stateButtonC = false

    private var webView: WebView = WebView(context)

    init {
        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    // This should be part of the JS Code inside the generated program
    init {
        rainbowHatController.buttonA.setOnButtonEventListener { button: Button, b: Boolean ->
            stateButtonA = b
            if (b) {
                webView.evaluateJavascript("javascript: onButtonAPressed();",null)
            }
            else {
                webView.evaluateJavascript("javascript: onButtonAReleased();",null)
            }
            webView.evaluateJavascript("javascript: onButtonAChanged();",null)
        }
        rainbowHatController.buttonB.setOnButtonEventListener { button: Button, b: Boolean ->
            stateButtonB = b
            if (b) {
                webView.evaluateJavascript("javascript: onButtonBPressed();",null)
            }
            else {
                webView.evaluateJavascript("javascript: onButtonBReleased();",null)
            }
            webView.evaluateJavascript("javascript: onButtonBChanged();",null)
        }
        rainbowHatController.buttonC.setOnButtonEventListener { button: Button, b: Boolean ->
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

    fun executeProgram(program: String) {
        tearDownWebView()
        rainbowHatController.cleanRainbowHatState()

        setupWebView()
        val tagProgram = "<script language=\"JavaScript\">\n $program </script>"
        webView.loadData(tagProgram, "text/html", "UTF-8")
    }

    private fun tearDownWebView() {
        webView.apply {
            removeJavascriptInterface(LEDS_JS_OBJECT)
            removeJavascriptInterface(LCD_JS_OBJECT)
            removeJavascriptInterface(SENSOR_JS_OBJECT)
            removeJavascriptInterface(BUZZER_JS_OBJECT)
            removeJavascriptInterface(LED_STRIP_JS_OBJECT)
            loadUrl("about:blank")
        }
    }

    private fun setupWebView() {
        webView = WebView(context).apply {
            settings.javaScriptEnabled = true
            addJavascriptInterface(WebAppInterface(rainbowHatController), LEDS_JS_OBJECT)
            addJavascriptInterface(AlphanumericDisplayWebInterface(rainbowHatController.alphanumericDisplay), LCD_JS_OBJECT)
            addJavascriptInterface(Bmx280WebInterface(rainbowHatController.temperatureSensor), SENSOR_JS_OBJECT)
            addJavascriptInterface(SpeakerWebInterface(rainbowHatController.buzzer), BUZZER_JS_OBJECT)
            addJavascriptInterface(Apa102WebInterface(rainbowHatController.ledStrip), LED_STRIP_JS_OBJECT)
        }
    }

    fun close() {
        tearDownWebView()
        rainbowHatController.close()
    }

}
