package com.liufq;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import java.io.IOException;

/**
 * Created by dylanliu on 16/6/5.
 */
public class Comment {
    public static void main(String[] args) throws IOException {
        ClassPrinter cp = new ClassPrinter();
        AddClassMember addClassMember= new AddClassMember(Opcodes.ACC_PUBLIC,"run1","(Ljava/lang/Object;)I",cp);
        ClassReader cr = new ClassReader("java.lang.Runnable");
        cr.accept(addClassMember,0);
    }

}
