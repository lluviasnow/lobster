package com.njuse.jvmfinal.instructions.constant;

import com.njuse.jvmfinal.instructions.base.Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;
import lombok.val;

import java.nio.ByteBuffer;

public class BIPUSH extends Instruction {

    private byte val;
    @Override
    public void execute(StackFrame frame) {
        int res=val;
        frame.getOperandStack().pushInt(res);
    }

    @Override
    public void fetchOperands(ByteBuffer reader) {
        this.val=reader.get();
    }
}
