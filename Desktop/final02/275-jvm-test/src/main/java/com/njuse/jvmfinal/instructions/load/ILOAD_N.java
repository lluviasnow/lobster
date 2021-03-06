package com.njuse.jvmfinal.instructions.load;

import com.njuse.jvmfinal.runtime.StackFrame;

public class ILOAD_N extends LOAD_N {
    public ILOAD_N(int index) {
        checkIndex(index);
        this.index = index;
    }
    /**
     * TODO：实现这条指令
     * 其中成员index是这条指令的参数，已经读取好了
     */
    @Override
    public void execute(StackFrame frame) {
        int val = frame.getLocalVars().getInt(this.index);
        //将这个元素压入操作数栈
        frame.getOperandStack().pushInt(val);
    }
}
