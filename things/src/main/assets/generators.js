'use strict';

Blockly.JavaScript['rainbow_hat_button_action'] = function(block) {
    var button = Blockly.JavaScript.variableDB_.getName(block.getFieldValue('BUTTON'), Blockly.Variables.NAME_TYPE);
    var buttonName = 'A';
    if (button == "BUTTON_A") {
        buttonName = 'A';
    }
    else if (button == "BUTTON_B") {
        buttonName = 'B';
    }
    else if (button == "BUTTON_C") {
        buttonName = 'C';
    }

    var state = Blockly.JavaScript.variableDB_.getName(block.getFieldValue('STATE'), Blockly.Variables.NAME_TYPE);
    var buttonState = 'Pressed';
    if (state == "BUTTON_PRESSED") {
        buttonState = 'Pressed';
    }
    else if (state == "BUTTON_RELEASED") {
        buttonState = 'Released';
    }
    else if (state == "BUTTON_CHANGED") {
        buttonState = 'Changed';
    }

    var nextCode = Blockly.JavaScript.statementToCode(block, 'DO');
    return 'function onButton'+buttonName+buttonState+'() {\n' +
                          nextCode+'\n' +
                          '}\n';
};

Blockly.JavaScript['rainbow_hat_read_button'] = function(block) {
    return ["Android.getStateButtonA()", Blockly.JavaScript.ORDER_NONE];
};

Blockly.JavaScript['rainbow_hat_set_led_value'] = function(block) {
    var led = Blockly.JavaScript.variableDB_.getName(block.getFieldValue('LED'), Blockly.Variables.NAME_TYPE);
    var pressed = Blockly.JavaScript.valueToCode(block, 'VALUE', Blockly.JavaScript.ORDER_ADDITION) || 'true'
    if (led == "LED_RED") {
        return 'Android.setRedLed('+pressed+');\n';
    }
    else if (led == "LED_GREEN") {
        return 'Android.setGreenLed('+pressed+');\n';
    }
    else {
        return 'Android.setBlueLed('+pressed+');\n';
    }
};