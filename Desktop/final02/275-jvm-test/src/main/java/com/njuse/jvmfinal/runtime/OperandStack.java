package com.njuse.jvmfinal.runtime;

import lombok.Getter;
import lombok.Setter;

import java.util.EmptyStackException;

@Getter
@Setter
public class OperandStack {
    private int top;
    private int maxStackSize;
    private Slot[] slots;

    public OperandStack(int maxStackSize) {
        assert maxStackSize >= 0;
        this.maxStackSize = maxStackSize;
        slots = new Slot[maxStackSize];
        for (int i = 0; i < maxStackSize; i++) slots[i] = new Slot();
        top = 0;
    }

    /**
     * TODO：向操作数栈顶端push一个int型变量
     *
     * @param value 变量的值
     */
    public void pushInt(int value) {
        if(top>=maxStackSize)throw new StackOverflowError();
        slots[top].setValue(value);
        top++;
    }

    /**
     * TODO：从操作数栈顶端pop一个int型变量
     *
     * @return 返回这个int的值
     */
    public int popInt() {
        top--;
        if (top < 0) throw new EmptyStackException();
        int ret=slots[top].getValue();
        slots[top]=new Slot();
        return ret;
    }

    public void pushFloat(float value) {
        if (top >= maxStackSize) throw new StackOverflowError();
        slots[top].setValue(Float.floatToIntBits(value));
        top++;
    }

    public float popFloat() {
        top--;
        if (top < 0) throw new EmptyStackException();
        float ret = Float.intBitsToFloat(slots[top].getValue());
        slots[top] = new Slot();
        return ret;
    }

    /**
     * TODO：向操作数栈顶push一个 long 类型的变量
     *
     * @param value 变量的值
     */
    public void pushLong(long value) { //注意处理溢出的情况
        if(value<Integer.MAX_VALUE)value=value&0xffffffffL;
        //if(value<0&&-value>Integer.MAX_VALUE)value=-value;
        long upperNum=(value>>32);
        long lowerNum=value-(upperNum<<32);
        pushInt((int)lowerNum);
        pushInt((int)upperNum);
    }

    /**
     * TODO：从操作数栈顶端pop一个long型变量
     *
     * @return 返回这个long的值
     */
    public long popLong() {
        long upperNum=this.popInt();
        //upperNum&=0xFFFFFFFF;
        upperNum<<=32;
        long lowerNum=this.popInt();
        //lowerNum&=0xFFFFFFFF;
        return upperNum+lowerNum;
    }

    public void pushDouble(double value) {
        pushLong(Double.doubleToLongBits(value));
    }


    /**
     * TODO：从操作数栈顶端pop一个double型变量
     *
     * @return 返回这个double的值
     */
    public double popDouble() {
        return Double.longBitsToDouble(popLong());
    }

    public void pushObjectRef(JObject ref) {
        if (top >= maxStackSize) throw new StackOverflowError();
        slots[top].setObject(ref);
        top++;
    }

    public JObject popObjectRef() {
        top--;
        if (top < 0) throw new EmptyStackException();
        JObject ret = slots[top].getObject();
        slots[top] = new Slot();
        return ret;
    }

    public void pushSlot(Slot slot) {
        if (top >= maxStackSize) throw new StackOverflowError();
        slots[top] = slot;
        top++;
    }

    public Slot popSlot() {
        top--;
        if (top < 0) throw new EmptyStackException();
        Slot ret = slots[top];
        slots[top] = new Slot();
        return ret;
    }

}
