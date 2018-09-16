'use strict';

Blockly.JavaScript['rainbow_hat_button_action'] = function(block) {
    var button = Blockly.JavaScript.variableDB_.getName(block.getFieldValue('BUTTON'), Blockly.Variables.NAME_TYPE);
    var nextCode = Blockly.JavaScript.statementToCode(block, 'DO')

    if (button == "BUTTON_A") {
        return 'function onButtonAPressed(pressed) {\n' +
                  nextCode+'\n' +
                  '}\n';
    }
    else if (button == "BUTTON_B") {
        return 'function onButtonBPressed(pressed) {\n' +
                      nextCode+'\n' +
                      '}\n';
    }
    else {
        return 'function onButtonCPressed(pressed) {\n' +
                      nextCode+'\n' +
                      '}\n';
    }
};

Blockly.JavaScript['rainbow_hat_read_button'] = function(block) {
    return "Android.getStateButtonA()";
};

Blockly.JavaScript['rainbow_hat_set_led_value'] = function(block) {
    var led = Blockly.JavaScript.variableDB_.getName(block.getFieldValue('LED'), Blockly.Variables.NAME_TYPE);
    var pressed = "pressed";
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