# BlocklyRainbowHat

A project to program the Rainbow HAT on Android Things on the device using Blockly (visual language that is the base of scratch)

You can write the code directly on the Android Things device, or you can write it on an Android phone or tablet and send it over using Nearby

# Installing

You need to have an Android Things developer kit with at least version 1.0 of Android Things installed.
- Clone the repo
- Open it on Android Studio
- Compile and install the things module to your Dev Kit
- Optionally: Compile and install the mobile module into a phone or tablet to be used as editor

For the version 1.0 onwards I plan to include a compiled .apk and also a system image of Android Things and to publish the editor in Google Play

# Available Blocks

The list of currently available blocks for the Rainbow HAT is:

- when [Button] is [pressed | released | changed]
- state of [Button] : Boolean
- set [Red | Green | Blue] LED to [Boolean]
- state of [Red | Green | Blue] LED : Boolean
- display text [text]
- display number [number]
- temperature : Float
- pressure : Float
- play frequency [Number] Hz
- stop playing 
- display colors [array of colors]
- display colors [array of colors as insets]
- display colors [list]

Other functional blocks:

- when program starts
- repeat every [Number] milliseconds
- wait [Number] milliseconds

# Code Samples

TODO: Add a few images with code examples
- Blink a LED
- Synchronize the LEDs with the buttons
- Three Tone Piano
- Display current temperature
- Knight Rider
- Control servo via Rainbow HAT
- Simon says game?

# Known issues
- Button drivers are not processing 2 buttons pressed at the same time: Move to Button Input Drivers
- Events are executed in the main thread, if an event takes long to run, new events get queued (button presses)
- Only one event of each type is allowed at the moment

# Future Work
- Dynamic permission request on the mobile module
- Deploy progress dialog with the option to cancel instead of Toast
- Add a reset key combo to the app, to stop the JS and restart advertising
- Improve companion app
- Add animation to "compile" or "load" using the Rainbow HAT
- Tweak Android UI icons and feedback
- Allow multiple entries to an event
- Support for multiple save files
- Add Rainbow HAT simulator to the mobile app to "test"
- Support for Servos via the PWM output
- All the API methods from the components as blocks
- Support for selected I2C or SPI peripherals (i.e. LED Matrix)
- Consider using Rhino instead of a WebView to execute the generated JavaScript
- Only allow remote code deploy on Android Things if there is no screen attached

# References
- [Official Android Things documentation](https://developer.android.com/things/)
- [Android Things driver for the Rainbow HAT](https://github.com/androidthings/contrib-drivers/tree/master/rainbowhat)
- [Blockly homepage](https://developers.google.com/blockly/)

If you want to get started on Android Things and the Rainbow HAT, I suggest to take a look at the book [Android Things Quick Start Guide](https://www.packtpub.com/hardware-and-creative/android-things-quick-start-guide)
