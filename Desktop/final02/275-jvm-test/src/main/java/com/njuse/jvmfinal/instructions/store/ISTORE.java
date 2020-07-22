package com.njuse.jvmfinal.instructions.store;

import com.njuse.jvmfinal.instructions.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class ISTORE extends Index8Instruction {


    /**
     * TODO：实现这条指令
     */
    @Override
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        int value=stack.popInt();
        frame.getLocalVars().setInt(0,value);
    }
}
