package com.plattysoft.blocklyrainbowhat

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.MenuItem
import com.google.android.gms.nearby.connection.Strategy
import com.google.blockly.android.AbstractBlocklyActivity
import com.google.blockly.model.DefaultBlocks
import com.plattysoft.blocklyrainbowhat.common.R
import java.util.*

/**
 * Created by Raul Portales on 17/09/18.
 */
abstract class RainbowHatBlocklyBaseActivity : AbstractBlocklyActivity() {

    companion object {
        const val SERVICE_ID = "com.plattysoft.nearby"
        val STRATEGY = Strategy.P2P_STAR as Strategy
    }

    override fun getToolboxContentsXmlPath(): String {
        return "toolbox.xml"
    }

    override fun getBlockDefinitionsJsonPaths(): MutableList<String> {
        val assetPaths = ArrayList(DefaultBlocks.getAllBlockDefinitions())
        assetPaths.add("rainbowHat_blocks.json")
        assetPaths.add("events_blocks.json")
        return assetPaths
    }

    override fun getGeneratorsJsPaths(): MutableList<String> {
        return Arrays.asList("rainbowHat_generators.js", "events_generators.js")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_clear) {
            // Ask for confirmation
            if (controller.workspace.hasBlocks()) {
                askForClearConfirmation()
            }
            return true
        }
//        else if (id == R.id.action_save) {
//            onSaveWorkspace()
//            return true
//        } else if (id == R.id.action_load) {
//            onLoadWorkspace()
//            return true
//        }  else if (id == R.id.action_run) {
//            if (controller.workspace.hasBlocks()) {
//                onRunCode()
//            } else {
//                Log.i(TAG, "No blocks in workspace. Skipping run request.")
//            }
//            return true
        return super.onOptionsItemSelected(item)
    }

    private fun askForClearConfirmation() {
        // Consider doing this with a snackbar and an UNDO option instead
        AlertDialog.Builder(this)
                .setMessage(R.string.confirm_clear_workspace_message)
                .setTitle(R.string.confirm_clear_workspace_title)
                .setPositiveButton(R.string.yes, { dialogInterface: DialogInterface, i: Int ->
                    onClearWorkspace()
                    dialogInterface.dismiss()
                })
                .setNegativeButton(R.string.no, { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                })
                .create()
                .show()
    }
}
