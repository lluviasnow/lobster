package com.njuse.jvmfinal.instructions.load;

import com.njuse.jvmfinal.instructions.base.Index8Instruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public class DLOAD extends Index8Instruction {

    /**
     * TODO：实现这条指令
     * 其中成员index是这条指令的参数，已经读取好了
     */
    @Override
    public void execute(StackFrame frame) {
        double val = frame.getLocalVars().getDouble(index);
        //将这个元素压入操作数栈
        frame.getOperandStack().pushDouble(val);
    }
}
