package com.plattysoft.blocklyrainbowhat

import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.Strategy
import com.google.blockly.android.AbstractBlocklyActivity
import com.google.blockly.model.DefaultBlocks
import org.json.JSONObject
import java.util.*

/**
 * Created by Raul Portales on 17/09/18.
 */
abstract class RainbowHatBlocklyBaseActivity : AbstractBlocklyActivity() {

    companion object {
        const val SERVICE_ID = "com.plattysoft.nearby"
        val STRATEGY = Strategy.P2P_STAR
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
}

fun Payload.getAsJson(): JSONObject {
    val string = String(this.asBytes()!!)
    return JSONObject(string)
}
