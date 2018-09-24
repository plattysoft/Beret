package com.plattysoft.blocklyrainbowhat

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.blockly.android.codegen.CodeGenerationRequest

class MainActivity : RainbowHatBlocklyBaseActivity(), NearbyIotWrapper.RemoteEditorListener {

    lateinit var nearbyIotWrapper: NearbyIotWrapper
    lateinit var executionEnvironment: ThingsExecutionEnvironment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        executionEnvironment = ThingsExecutionEnvironment(this)
    }

    override fun onResume() {
        super.onResume()
        nearbyIotWrapper = NearbyIotWrapper(this, this)
    }

    override fun onPause() {
        super.onPause()
        nearbyIotWrapper.stop()
    }

    private fun loadProgram(program: String) {
        Log.d("Program:", program)
        runOnUiThread{
            // Clean state
            executionEnvironment.executeProgram(program)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        executionEnvironment.close()
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

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        val id = item.getItemId()
//
//        if (id == R.id.action_run) {
//            if (controller.workspace.hasBlocks()) {
//                onRunCode()
//            }
//            return true
//        }
//
//        return super.onOptionsItemSelected(item)
//    }
}