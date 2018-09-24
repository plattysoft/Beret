package com.plattysoft.blocklyrainbowhat

import android.content.Context
import android.graphics.Color
import com.google.android.things.contrib.driver.apa102.Apa102
import com.google.android.things.contrib.driver.bmx280.Bmx280
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat

class RainbowHatController () {

    val redLed = RainbowHat.openLedRed()
    val greenLed = RainbowHat.openLedGreen()
    val blueLed = RainbowHat.openLedBlue()

    val buttonA =  RainbowHat.openButtonA()
    val buttonB =  RainbowHat.openButtonB()
    val buttonC =  RainbowHat.openButtonC()

    val alphanumericDisplay = RainbowHat.openDisplay()
    init {
        alphanumericDisplay.setEnabled(true)
        alphanumericDisplay.setBrightness(AlphanumericDisplay.HT16K33_BRIGHTNESS_MAX)
    }

    val temperatureSensor = RainbowHat.openSensor()
    init {
        temperatureSensor.temperatureOversampling = Bmx280.OVERSAMPLING_1X
        temperatureSensor.pressureOversampling = Bmx280.OVERSAMPLING_1X
        temperatureSensor.setMode(Bmx280.MODE_NORMAL)
    }

    val buzzer = RainbowHat.openPiezo()

    val ledStrip = RainbowHat.openLedStrip()
    init {
        ledStrip.direction = Apa102.Direction.REVERSED
        ledStrip.brightness = 31
    }

    fun cleanRainbowHatState() {
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

    fun close() {
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
    }
}