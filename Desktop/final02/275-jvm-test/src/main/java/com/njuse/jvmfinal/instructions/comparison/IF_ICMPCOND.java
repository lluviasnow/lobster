package com.njuse.jvmfinal.instructions.comparison;

import com.njuse.jvmfinal.instructions.base.BranchInstruction;
import com.njuse.jvmfinal.instructions.control.GOTO;
import com.njuse.jvmfinal.runtime.StackFrame;

public abstract class IF_ICMPCOND extends BranchInstruction {
    /**
     * TODO：实现这条指令
     * 其中，condition 方法是对具体条件的计算，当条件满足时返回true，否则返回false
     */
    @Override
    public void execute(StackFrame frame) {
        int v2=frame.getOperandStack().popInt();//第二个操作数先出栈
        int v1=frame.getOperandStack().popInt();
        if(condition(v1,v2)){
            int branchPC = frame.getNextPC() - 3 + super.offset;// 3 = opcode + signed short offset
            frame.setNextPC(branchPC);
        };
    }

    protected abstract boolean condition(int v1, int v2);
}
