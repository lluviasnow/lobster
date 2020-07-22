package com.njuse.jvmfinal.instructions.comparison;

import com.njuse.jvmfinal.instructions.base.BranchInstruction;
import com.njuse.jvmfinal.runtime.StackFrame;

public abstract class IFCOND extends BranchInstruction {

    /**
     * TODO：实现这条指令
     * 其中，condition 方法是对具体条件的计算，当条件满足时返回true，否则返回false
     */
    @Override
    public void execute(StackFrame frame) {
        int value=frame.getOperandStack().popInt();
        if(condition(value)){
            int branchPC = frame.getNextPC() - 3 + super.offset;// 3 = opcode + signed short offset
            //设置PC为跳转后地址
            frame.setNextPC(branchPC);
        }
    }

    protected abstract boolean condition(int value);

}
