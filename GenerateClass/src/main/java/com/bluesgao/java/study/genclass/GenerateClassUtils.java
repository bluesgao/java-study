package com.bluesgao.java.study.genclass;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: GenerateClassUtils
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/6/28 20:53
 **/
public class GenerateClassUtils {
    public static byte[] genClass(String className, List<String> fields) throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass(className);

        for (String field : fields) {
            String str = "private " + " String " + field + " ;";
            System.out.println("str:" + str);
            CtField ctField = CtField.make(str, ctClass);
            ctClass.addField(ctField);
        }

        return ctClass.toBytecode();
    }

    public static void main(String[] args) {
        List<String> fields = new ArrayList<String>();
        fields.add("name");
        fields.add("age");
        fields.add("sex");
        String className = "User";

        try {
            byte[] classBytes = GenerateClassUtils.genClass(className, fields);
            MyClassLoader myClassLoader = new MyClassLoader(className, classBytes);
            Class<?> userClass = myClassLoader.findClass(className);
            System.out.println("userClass:" + userClass.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
