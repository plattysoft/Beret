# BlocklyRainbowHat

A project to program the Rainbow HAT on Android Things on the device using Blockly (visual language that is the base of scratch)

# Installing

You need to have an Android Things developer kit with at least version 1.0 of Android Things installed.
- Clone the repo
- Open it on Android Studio
- Compile and deploy to your Dev Kit

For the version 1.0 onwards I plan to include a compiled .apk and also a system image of Android Things.

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

# Known issues
- Button drivers are not processing 2 buttons pressed at the same time: Move to Button Input Drivers
- Events are executed in the main thread, if an event takes long to run, new events get queued (button presses)
- Only one event of each type is allowed at the moment

# Future Work
- Add a companion app and allow to deploy code from the companion app (i.e. running on a tablet)
- Add animation to "compile" or "load" using the Rainbow HAT
- Tweak Android UI icons and feedback
- Allow multiple entries to an event
- Support for multiple save files
- Add Rainbow HAT simulator to the mobile app to "test"
- Support for Servos via the PWM output
- All the API methods from the components as blocks
- Support for selected I2C or SPI peripherals (i.e. LED Matrix)
- Consider using Rhino instead of a WebView to execute the generated JavaScript

# References
- [Official Android Things documentation](https://developer.android.com/things/)
- [Android Things driver for the Rainbow HAT](https://github.com/androidthings/contrib-drivers/tree/master/rainbowhat)
- [Blockly homepage](https://developers.google.com/blockly/)

If you want to get started on Android Things and the Rainbow HAT, I suggest to take a look at the book [Android Things Quick Start Guide](https://www.packtpub.com/hardware-and-creative/android-things-quick-start-guide)