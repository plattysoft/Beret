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
    return "setInterval(() => {\n"+nextCode+"\n}, "+period+");"
};

Blockly.JavaScript['event_wait'] = function(block) {
    // Terrible implementation of wait for the moment
    var timeout = Blockly.JavaScript.valueToCode(block, 'TIMEOUT', Blockly.JavaScript.ORDER_FUNCTION_CALL) || 500
    return  "var d = Date.now();\n"+
            "var d2 = Date.now();\n"+
            "do {\n"+
                "\td2 = Date.now();\n"+
             "}\n"+
            "while(d2-d < "+timeout+");"
};

Blockly.JavaScript['logic_boolean_workaround'] = function(block) {
    var code = (block.getFieldValue('BOOL') == 'TRUE') ? 'true' : 'false';
    return [code, Blockly.JavaScript.ORDER_ATOMIC];
};