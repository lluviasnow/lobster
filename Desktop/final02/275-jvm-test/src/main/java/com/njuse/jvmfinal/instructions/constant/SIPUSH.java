package com.njuse.jvmfinal.instructions.constant;

import com.njuse.jvmfinal.instructions.base.Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;

import java.nio.ByteBuffer;

public class SIPUSH extends Instruction {

    private short val;
    @Override
    public void execute(StackFrame frame) {
        int res=(int)val;
        frame.getOperandStack().pushInt(res);
    }

    @Override
    public void fetchOperands(ByteBuffer reader) {
        this.val=reader.getShort();
    }
}
