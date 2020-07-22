package com.njuse.jvmfinal.instructions.load;

import com.njuse.jvmfinal.runtime.JObject;
import com.njuse.jvmfinal.runtime.StackFrame;


public class ALOAD_N extends LOAD_N {
    public ALOAD_N(int index) {
        checkIndex(index);
        this.index = index;
    }
    /**
     * TODO：实现这条指令
     * 其中成员index是这条指令的参数，已经读取好了
     */
    @Override
    public void execute(StackFrame frame) {
        JObject val = frame.getLocalVars().getObjectRef(this.index);
        //将这个元素压入操作数栈
        frame.getOperandStack().pushObjectRef(val);
    }
}