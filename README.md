# BlocklyRainbowHat

A project to program the Rainbow HAT on Android Things on the device using Blockly (visual language that is the base of scratch)

# Available Blocks

The list of currently available blocks for the Rainbow HAT is:

- when [Button] is [pressed | released | changed]
- state of [Button] : Boolean
- Set [Red | Green | Blue] led to [Boolean]
- display text [text]
- display number [number]
- temperature : Float
- pressure : Float
- play frequency [Number]
- stop playing 
- display colors [array of colors]
- display colors [array of colors as insets]

Missing control for the RGB LED Strip (WIP)

# Code Samples

TODO: Add a few images with code examples

# Future Work

- Allow multiple entries to an event
- Add a companion app and allow to deploy code from the companion app (i.e. running on a tablet)
- Support for Servos via the PWM output
- Support for selected I2C or SPI peripherals (i.e. LED Matrix)

# References

- [Official Android Things documentation](https://developer.android.com/things/)
- [Android Things driver for the Rainbow HAT](https://github.com/androidthings/contrib-drivers/tree/master/rainbowhat)
- [Blockly homepage](https://developers.google.com/blockly/)

If you want to get started on Android Things and the Rainbow HAT, I suggest to take a look at the book [Android Things Quick Start Guide](https://www.packtpub.com/hardware-and-creative/android-things-quick-start-guide)