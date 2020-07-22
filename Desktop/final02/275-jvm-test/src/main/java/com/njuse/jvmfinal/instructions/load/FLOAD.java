package com.njuse.jvmfinal.instructions.load;

import com.njuse.jvmfinal.instructions.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class FLOAD extends Index8Instruction {

    @Override
    public void execute(StackFrame frame) {
        float val = frame.getLocalVars().getFloat(index);
        frame.getOperandStack().pushFloat(val);
    }
}
