package com.plattysoft.blocklyrainbowhat

import android.util.Log
import com.google.blockly.android.codegen.CodeGenerationRequest

class MainActivity : RainbowHatBlocklyBaseActivity() {

    private fun loadProgram(program: String) {
        Log.d("Program", program)
    }

    override fun getCodeGenerationCallback(): CodeGenerationRequest.CodeGeneratorCallback {
        return CodeGenerationRequest.CodeGeneratorCallback {
            loadProgram(it)
        }
    }

}