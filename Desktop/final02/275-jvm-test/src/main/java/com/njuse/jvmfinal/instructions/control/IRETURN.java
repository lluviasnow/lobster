package com.njuse.jvmfinal.instructions.control;

import com.njuse.jvmfinal.instructions.base.NoOperandsInstruction;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.StackFrame;

public class IRETURN extends NoOperandsInstruction {

    /**
     * TODO： 实现这条指令
     */
    @Override
    public void execute(StackFrame frame) {

        JThread thread=frame.getThread();
        int ret=thread.getTopFrame().getOperandStack().popInt();
        //if(ret==0)ret++;
        thread.popFrame();
        thread.getTopFrame().getOperandStack().pushInt(ret);
    }
}
