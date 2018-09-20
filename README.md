# BlocklyRainbowHat

A project to program the Rainbow HAT on Android Things on the device using Blockly (visual language that is the base of scratch)

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

Considered for the future:

- Servo controls
- All the API methods from the components

# Code Samples

TODO: Add a few images with code examples

# Future Work

- Add animation to "compile" or "load" using the Rainbow HAT
- Tweak Android UI icons and feedback
- Allow multiple entries to an event
- Add a companion app and allow to deploy code from the companion app (i.e. running on a tablet)
- Add Rainbow HAT simulator to the mobile app to "test"
- Support for Servos via the PWM output
- Support for selected I2C or SPI peripherals (i.e. LED Matrix)
- Consider using Rhino instead of a WebView to execute the generated JavaScript

# References

- [Official Android Things documentation](https://developer.android.com/things/)
- [Android Things driver for the Rainbow HAT](https://github.com/androidthings/contrib-drivers/tree/master/rainbowhat)
- [Blockly homepage](https://developers.google.com/blockly/)

If you want to get started on Android Things and the Rainbow HAT, I suggest to take a look at the book [Android Things Quick Start Guide](https://www.packtpub.com/hardware-and-creative/android-things-quick-start-guide)