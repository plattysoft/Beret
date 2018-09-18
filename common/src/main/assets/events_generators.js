'use strict';

Blockly.JavaScript['event_program_starts'] = function(block) {
    var nextCode = Blockly.JavaScript.statementToCode(block, 'DO');
    return 'function init() {\n' +
                nextCode+'\n' +
            '}\n'+
        "init();";
};

Blockly.JavaScript['event_repeat_timer'] = function(block) {
    var period = Blockly.JavaScript.valueToCode(block, 'PERIOD', Blockly.JavaScript.ORDER_FUNCTION_CALL) || 500

    var nextCode = Blockly.JavaScript.statementToCode(block, 'DO');
    return "function myFunction() {\n"+
                nextCode+"\n"+
            "}\n"+
            "setInterval(myFunction, "+period+");"
};

Blockly.JavaScript['logic_boolean_workaround'] = function(block) {
    var code = (block.getFieldValue('BOOL') == 'TRUE') ? 'true' : 'false';
    return [code, Blockly.JavaScript.ORDER_ATOMIC];
};