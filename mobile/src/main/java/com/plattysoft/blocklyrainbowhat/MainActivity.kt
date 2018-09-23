package com.plattysoft.blocklyrainbowhat

import android.os.Bundle
import android.util.Log
import com.google.blockly.android.codegen.CodeGenerationRequest

class MainActivity : RainbowHatBlocklyBaseActivity() {

    lateinit private var nearbyWrapper: NearbyWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nearbyWrapper = NearbyWrapper(this)
    }

    private fun loadProgram(program: String) {
        nearbyWrapper.sendProgram(program)
        Log.d("Program", program)
    }

    override fun getCodeGenerationCallback(): CodeGenerationRequest.CodeGeneratorCallback {
        return CodeGenerationRequest.CodeGeneratorCallback {
            loadProgram(it)
        }
    }
}