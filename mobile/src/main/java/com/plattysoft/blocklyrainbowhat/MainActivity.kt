package com.plattysoft.blocklyrainbowhat

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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

class MainActivity : RainbowHatBlocklyBaseActivity() {

//    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        webView = WebView(this)
//
//        webView.settings.javaScriptEnabled = true
//        webView.addJavascriptInterface(WebAppInterface(this), "Android")
//        webView.addJavascriptInterface(AlphanumericDisplayWebInterface(alphanumericDisplay), "AlphanumericDisplay")
//        webView.addJavascriptInterface(Bmx280WebInterface(temperatureSensor), "Bmx280")
//        webView.addJavascriptInterface(SpeakerWebInterface(buzzer), "Speaker")
//        webView.addJavascriptInterface(Apa102WebInterface(ledStrip), "Apa102")
    }

    private fun loadProgram(program: String) {
        Log.d("Program", program)
//        val tagProgram = "<script language=\"JavaScript\">\n $program </script>"
//        runOnUiThread{
//            webView.loadData(tagProgram, "text/html", "UTF-8")
//        }
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
}