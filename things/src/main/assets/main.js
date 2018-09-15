//var textContainer = document.createElement("p");
//var nativeText = document.createTextNode("Android Text");
//textContainer.appendChild(nativeText);
//nativeText.nodeValue = "Test";

function setRedLed() {
    var checkBox = document.getElementById("redLed");
    Android.setRedLed(checkBox.checked)
}

function setGreenLed() {
    var checkBox = document.getElementById("greenLed");
    Android.setGreenLed(checkBox.checked)
}

function setBlueLed() {
    var checkBox = document.getElementById("blueLed");
    Android.setBlueLed(checkBox.checked)
}

function onButtonPressed(buttonName) {
    document.getElementById('textbox').value = buttonName
}