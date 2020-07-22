package com.njuse.jvmfinal.instructions.conversion;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;

public class I2D extends NoOperandsInstruction {

    /**
     * TODO：（加分项）实现这条指令
     * 这是一条可选测试用例才会涉及的指令
     */
    @Override
    public void execute(StackFrame frame) {
        OperandStack stack=frame.getOperandStack();
        int value=stack.popInt();
        int tmp=0;
        if(value<0)tmp=0xffffffff;
        long ans=(tmp<<32)+value;
        stack.pushDouble(Double.longBitsToDouble(ans));
    }
}
