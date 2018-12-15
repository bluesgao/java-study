package com.bluesgao.demo.agent.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MyClassVisitor extends ClassVisitor {
    public MyClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM6, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (cv != null) {
            cv.visit(version, access, name, signature, superName, interfaces);
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        //当方法名为changeMethodName时，修改方法名为changeMethodName1
        //从字节码层面修改方法名后，如果老方法名被调用过，会引起NoSuchMethodError，修改存在的方法名是危险操作。
        if (cv != null && "changeMethodName".equals(name)){
            return cv.visitMethod(access, name + "1", descriptor, signature, exceptions);
        }

        //此处的changeMethodContent即为需要修改的方法
        if("changeMethodContent".equals(name)){
            //先得到原始的方法
            MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
            MethodVisitor newMethod = null;
            //访问需要修改的方法
            newMethod = new MyMethodVisitor(mv);
            return newMethod;
        }

        if (cv != null) {
            return cv.visitMethod(access, name, descriptor, signature, exceptions);
        }

        return null;
    }
}
