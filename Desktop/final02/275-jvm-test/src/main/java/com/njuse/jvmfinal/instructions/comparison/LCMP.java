package com.njuse.jvmfinal.instructions.comparison;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class LCMP extends NoOperandsInstruction {
    public void execute(StackFrame frame) {
        long val2 = frame.getOperandStack().popLong();
        long val1 = frame.getOperandStack().popLong();
        int res=0;
        if(val1>val2)res=1;
        if(val1<val2)res=-1;
        frame.getOperandStack().pushInt(res);
    }
}