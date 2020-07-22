package com.njuse.jvmfinal.instructions.control;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class POP extends NoOperandsInstruction {
    public void execute(StackFrame frame) {
        frame.getOperandStack().popSlot();
    }
}
