package com.njuse.jvmfinal.instructions.math.algorithm;

import com.njuse.jvmfinal.instructions.base.Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;

import java.nio.ByteBuffer;

public class IINC extends Instruction {
    private int index;
    private int val;
    public void fetchOperands(ByteBuffer reader) {
        this.index=reader.get()&0xf;
        this.val=reader.get();
    }
    public void execute(StackFrame frame) {
        int now=frame.getLocalVars().getInt(this.index);
        frame.getLocalVars().setInt(this.index,now+this.val);
    }
}
