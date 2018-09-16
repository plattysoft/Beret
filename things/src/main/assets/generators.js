'use strict';

Blockly.JavaScript['rainbow_hat_button_a_clicked'] = function(block) {
    var nextCode = Blockly.JavaScript.statementToCode(block, 'DO')
    return 'function onButtonAPressed(pressed) {\n' +
              nextCode+'\n' +
              '}\n';
};

Blockly.JavaScript['rainbow_hat_set_led_value'] = function(block) {
    return 'Android.setRedLed(pressed);\n';
};