package com.njuse.jvmfinal.instructions.comparison;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class FCMPG extends NoOperandsInstruction {

    /**
     * TODO：实现这条指令
     */
    @Override
    public void execute(StackFrame frame) { // top double greater
        float v2=frame.getOperandStack().popFloat();
        float v1=frame.getOperandStack().popFloat();
        int ans=0;
        if(Float.isNaN(v1)||Float.isNaN(v2))ans=1;
        if(v1<v2)ans=-1;
        if(v1>v2)ans=1;
        frame.getOperandStack().pushInt(ans);
    }
}
