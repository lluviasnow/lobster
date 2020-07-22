package com.njuse.jvmfinal.instructions.comparison;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class DCMPG extends NoOperandsInstruction {

    /**
     * TODO：实现这条指令
     */
    @Override
    public void execute(StackFrame frame) { // top double greater
        double v2=frame.getOperandStack().popDouble();
        double v1=frame.getOperandStack().popDouble();
        int ans=0;
        if(Double.isNaN(v1)||Double.isNaN(v2))ans=1;
        if(v1<v2)ans=-1;
        if(v1>v2)ans=1;
        frame.getOperandStack().pushInt(ans);
    }
}
