'use strict';

Blockly.JavaScript['rainbow_hat_button_a_clicked'] = function(block) {
    return 'function onButtonAPressed(pressed) {\n' +
              '    Android.setRedLed(pressed)\n' +
              '}\n';
};

//Blockly.JavaScript['rainbow_hat_set_led_value'] = function(block) {
//    return 'Android.setRedLed(true);\n';
//};