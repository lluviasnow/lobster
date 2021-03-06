package com.njuse.jvmfinal.instructions.invoke;

import com.njuse.jvmfinal.instructions.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.Constant;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.MethodRef;
import com.njuse.jvmfinal.runtime.JThread;
import com.njuse.jvmfinal.runtime.Slot;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;

public class INVOKE_STATIC extends Index16Instruction {

    /**
     * TODO 实现这条指令，注意其中的非标准部分：
     * 1. TestUtil.equalInt(int a, int b): 如果a和b相等，则跳过这个方法，
     * 否则抛出`RuntimeException`, 其中，这个异常的message为
     * ：${第一个参数的值}!=${第二个参数的值}
     * 例如，TestUtil.equalInt(1, 2)应该抛出
     * RuntimeException("1!=2")
     *
     * 2. TestUtil.fail(): 抛出`RuntimeException`
     *
     * 3. TestUtil.equalFloat(float a, float b): 如果a和b相等，则跳过这个方法，
     * 否则抛出`RuntimeException`. 对于异常的message不作要求
     *
     */
    @Override
    public void execute(StackFrame frame) {
        JClass currentClz = frame.getMethod().getClazz();
        Constant methodRef=currentClz.getRuntimeConstantPool().getConstant(super.index);
        assert methodRef instanceof MethodRef;
        Method toInvoke= ((MethodRef) methodRef).resolveMethodRef();
        if (((MethodRef)methodRef).getClassName().contains("TestUtil")) {
            if (toInvoke.getName().contains("equalInt")) {
                int v1=frame.getOperandStack().popInt();
                int v2=frame.getOperandStack().popInt();
                if (v1!=v2) {
                    throw new RuntimeException(Integer.toString(v2)+"!="+Integer.toString(v1));
                }
                frame.getOperandStack().pushInt(v2);
                frame.getOperandStack().pushInt(v1);
            }

            else if (toInvoke.getName().contains("equalFloat")) {
                float v1=frame.getOperandStack().popFloat();
                float v2=frame.getOperandStack().popFloat();
                float eps=(float)1e-5;
                if ((double)Math.abs(v1-v2)>eps) {
                    throw new RuntimeException("fail");
                }
                frame.getOperandStack().pushFloat(v2);
                frame.getOperandStack().pushFloat(v1);
            }
            else if (toInvoke.getName().contains("reach")){
                int v1=frame.getOperandStack().popInt();
                System.out.println(v1);
                frame.getOperandStack().pushInt(v1);
            }
            else if (toInvoke.getName().equals("fail")) {
                throw new RuntimeException();
            }
        }
        StackFrame newframe = new StackFrame(frame.getThread(),toInvoke,toInvoke.getMaxStack(),toInvoke.getMaxLocal());
        int argc = toInvoke.getArgc();
        Vars localVars = newframe.getLocalVars();
        for(int i = argc - 1; i >= 0; --i) {
            Slot slot = frame.getOperandStack().popSlot();
            localVars.setSlot(i, slot);
        }
        JThread thread = frame.getThread();
        newframe.setMethod(toInvoke);
        thread.pushFrame(newframe);
        if (toInvoke.isNative()) {
            if (toInvoke.getName().equals("registerNatives")) {
                frame.getThread().popFrame();
            } else {
                System.out.println("Native method:"
                        + toInvoke.getClazz().getName()
                        + toInvoke.name
                        + toInvoke.descriptor);
                frame.getThread().popFrame();
            }
        }
    }
}
