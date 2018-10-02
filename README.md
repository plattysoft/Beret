# Beret (Formerly BlocklyRainbowHAT)

Beret is an educational project to program the Rainbow HAT on Android Things direcly on the device built on Blockly (which is a library from Google for building beginner-friendly block-based programming languages)

You can write the code directly on the Android Things device, or you can write it on an Android phone or tablet and send it over using Nearby

There are a few similarities between Beret and Micro:bit, both are great tools to introduce coding that interacts with hardware. Beret is intended to be an introduction to Android Things, but you can just use it as it is.

# Installing

You need to have an Android Things developer kit with at least version 1.0 of Android Things installed.
You can use a monitor, keyboard and mouse connected to your dev kit, or you can use the companion app on a tablet to create your programs.
You have a few options, I suggest you take the easy way.

## The easy way
- Install the [Beret things apk](https://github.com/plattysoft/Beret/blob/master/releases/beret-things-1.0.apk) into your Android Things Developer Kit usig adb
- Optional: Install the mobile app in yout tablet or phone via [Google Play](https://play.google.com/store/apps/details?id=com.plattysoft.beret)
- You are good to go

## The slightly harder way
- Clone the repo
- Open it on Android Studio
- Compile and install the things module to your Dev Kit
- Optional: Compile and install the mobile module into a phone or tablet to be used as editor

There is a plan to include a system image of Android Things as well so you just need to flash it.

# Available Blocks

The list of currently available blocks for the Rainbow HAT is:

## Buttons

![Button blocks](https://github.com/plattysoft/BlocklyRainbowHat/blob/master/button_blocks.png "Button blocks")

- when [Button] is [pressed | released | changed]
- state of [Button] : Boolean

## LEDs

![LED blocks](https://github.com/plattysoft/BlocklyRainbowHat/blob/master/led_blocks.png "LED blocks")

- set [Red | Green | Blue] LED to [Boolean]
- state of [Red | Green | Blue] LED : Boolean

## LCD display

![LCD display blocks](https://github.com/plattysoft/BlocklyRainbowHat/blob/master/lcd_blocks.png "LCD display blocks")

- display text [text]
- display number [number]

## Sensor

![Sensor blocks](https://github.com/plattysoft/BlocklyRainbowHat/blob/master/sensor_blocks.png "Sensor blocks")

- temperature : Float
- pressure : Float

## Piezo buzzer

![Piezo buzzer blocks](https://github.com/plattysoft/BlocklyRainbowHat/blob/master/buzzer_blocks.png "Piezo buzzer blocks")

- play frequency [Number] Hz
- stop playing 

## RGB LED Strip

![RGB LED Strip blocks](https://github.com/plattysoft/BlocklyRainbowHat/blob/master/rgb_led_strip_blocks.png "RGB LED Strip blocks")

- display colors [list]
- display colors [array of colors]
- display colors [array of colors as insets]

## Functional blocks (hooks)

- when program starts
- repeat every [Number] milliseconds

Temporary removed due to misbehavior

- wait [Number] milliseconds

# Code Samples

This is a list of suggested exercises for beginners. Proper project descriptions in the style of Code Club will be provided soon.

- Blink a LED
- Synchronize the LEDs with the buttons
- Three Tone Piano
- Display current temperature each second
- Marquee mode on the Alphanumeric display
- Display random array of colors on the LED Strip
- Knight Rider
- Control servo via Rainbow HAT (future example)
- Simon says game?

# Future Work (soon to be moved into the issue tracker)
- Support for Servos via the PWM output
- Compile only completed blocks, ignore "headless"
- Allow multiple entries to an event
- Support for multiple save files
- Add a reset key combo to the app, to stop the JS and restart advertising
- Add animation to "compile" or "load" using the Rainbow HAT
- Add Rainbow HAT simulator to the mobile app to "test"
- All the API methods from the components as blocks
- Support for selected I2C or SPI peripherals (i.e. LED Matrix)
- Consider using Rhino instead of a WebView to execute the generated JavaScript
- Only allow remote code deploy on Android Things if there is no screen attached

# Known issues
- Button drivers are not processing 2 buttons pressed at the same time: Move to Button Input Drivers
- Events are executed in the main thread, if an event takes long to run, new events get queued (button presses)
- Only one event of each type is allowed at the moment

# References
- [Official Android Things documentation](https://developer.android.com/things/)
- [Android Things driver for the Rainbow HAT](https://github.com/androidthings/contrib-drivers/tree/master/rainbowhat)
- [Blockly homepage](https://developers.google.com/blockly/)

If you want to get started on Android Things and the Rainbow HAT, I suggest to take a look at the book [Android Things Quick Start Guide](https://www.packtpub.com/hardware-and-creative/android-things-quick-start-guide)
