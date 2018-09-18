'use strict';

Blockly.JavaScript['event_program_starts'] = function(block) {
    var period = block.getFieldValue('PERIOD')
    var nextCode = Blockly.JavaScript.statementToCode(block, 'DO');
    return 'function init() {\n' +
                nextCode+'\n' +
            '}\n'+
        "init();";
};

Blockly.JavaScript['event_repeat_timer'] = function(block) {
    var period = block.getFieldValue('PERIOD')
    var nextCode = Blockly.JavaScript.statementToCode(block, 'DO');
    return "function myFunction() {"+
                nextCode+
            "}"+
            "setInterval(myFunction, "+period+");"
};