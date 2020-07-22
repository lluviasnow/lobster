package com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.constant.ref;

import com.njuse.jvmfinal.classloader.classfileparser.constantpool.info.MethodrefInfo;
import com.njuse.jvmfinal.memory.jclass.JClass;
import com.njuse.jvmfinal.memory.jclass.Method;
import com.njuse.jvmfinal.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class MethodRef extends MemberRef {
    private Method method;

    public MethodRef(RuntimeConstantPool runtimeConstantPool, MethodrefInfo methodrefInfo) {
        super(runtimeConstantPool, methodrefInfo);
    }
    public Method resolveMethodRef(JClass clazz) {
        resolve(clazz);
        return method;
    }

    /**
     * TODO: 实现这个方法
     * 这个方法用来解析methodRef对应的方法
     * 与上面的动态查找相比，这里的查找始终是从这个Ref对应的class开始查找的
     */
    public Method resolveMethodRef() {
        if(method!=null)return method;
        try {
            resolveClassRef();
            resolve(clazz);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return method;
    }


    public void resolve(JClass clazz) {
        if(clazz==null)return;
        Optional optionalMethod;
        for (JClass currentClazz = clazz;currentClazz != null;currentClazz = currentClazz.getSuperClass()){
            optionalMethod = currentClazz.resolveMethod(name, descriptor);
            if (optionalMethod.isPresent()) {
                method =(Method)optionalMethod.get();
                return;
            }
        }

    }

}
