# BlocklyRainbowHat

A project to program the Rainbow HAT on Android Things on the device using Blockly (visual language that is the base of scratch)

- [Official Android Things documentation](https://developer.android.com/things/)
- [Android Things driver for the Rainbow HAT](https://github.com/androidthings/contrib-drivers/tree/master/rainbowhat)
- [Blockly homepage](https://developers.google.com/blockly/)

If you want to get started on Android Things and the Rainbow HAT, I suggest to take a look at the book [Android Things Quick Start Guide](https://www.packtpub.com/hardware-and-creative/android-things-quick-start-guide)


# Steps of the project

- Configure an Activity that exposes the Rainbow HAT to a WebView
- Create and run custom code on that WebView
- Include Blockly as the default view on the app
- Create special blocks to interact with the Rainbow HAT components
- Generate code from blockly into javascript and execute it on the webview

That should be enough to allow the creation of Blockly projects on the device that run on the device.
