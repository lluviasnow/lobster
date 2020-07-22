package com.njuse.jvmfinal.instructions.references;

import com.njuse.jvmfinal.instructions.base.Index16Instruction;
import com.njuse.jvmfinal.memory.jclass.Field;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.InitState;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref.FieldRef;
import com.njuse.jvmfinal.runtime.OperandStack;
import com.njuse.jvmfinal.runtime.StackFrame;
import com.njuse.jvmfinal.runtime.Vars;

public class PUTSTATIC extends Index16Instruction {
    //TODO
    @Override
    public void execute(StackFrame frame) {
        Method currentMethod = frame.getMethod();
        JClass currentClazz = currentMethod.getClazz();
        RuntimeConstantPool currentRuntimeConstantPool = currentClazz.getRuntimeConstantPool();
        FieldRef fieldRef = (FieldRef)currentRuntimeConstantPool.getConstant(this.index);
        try {
            Field field = fieldRef.getResolvedFieldRef();
            JClass targetClazz = field.getClazz();
            if (targetClazz.getInitState() == InitState.PREPARED) {
                frame.setNextPC(frame.getNextPC() - 3);
                targetClazz.initClass(frame.getThread(), targetClazz);
                return;
            }
            char descriptor = field.getDescriptor().charAt(0);
            int slotID = field.getSlotID();
            Vars staticVars = targetClazz.getStaticVars();
            OperandStack stack = frame.getOperandStack();
            switch(descriptor) {
                case 'B':
                case 'C':
                case 'I':
                case 'S':
                case 'Z':
                    staticVars.setInt(slotID, stack.popInt());
                    break;
                case 'D':
                    staticVars.setDouble(slotID, stack.popDouble());
                case 'F':
                    staticVars.setFloat(slotID, stack.popFloat());
                    break;
                case 'J':
                    staticVars.setLong(slotID, stack.popLong());
                    break;
                case 'L':
                case '[':
                    staticVars.setObjectRef(slotID, stack.popObjectRef());
            }
        } catch (ClassNotFoundException var12) {
            var12.printStackTrace();
        }
    }
}
