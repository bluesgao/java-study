package com.bluesgao.demo.agent.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.List;

public class LogTransformer implements ClassFileTransformer {
    private final List<String> needInjectedClass = Arrays.asList("com/bluesgao/demo/agent/Test");

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] retVal = null;
        System.out.println("className:" + className);
        for (String cn : needInjectedClass) {
            if (cn.equals(className)) {
                System.out.println("needInjectedClass:" + className);
                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
                MyClassVisitor myClassVisitor = new MyClassVisitor(cw);
                ClassReader cr = new ClassReader(classfileBuffer);
                cr.accept(myClassVisitor, 0);
                retVal = cw.toByteArray();
            } else {
                retVal = classfileBuffer;
            }
        }

        return retVal;
    }
}
