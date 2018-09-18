package com.plattysoft.blocklyrainbowhat

import android.os.Bundle
import android.util.Log
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