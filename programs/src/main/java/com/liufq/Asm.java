package com.liufq;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

/**
 * Created by dylanliu on 16/6/5.
 */
public class Asm {
    public static void main(String[] args) {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(Opcodes.V1_7,Opcodes.ACC_PUBLIC+Opcodes.ACC_INTERFACE,"com.liufq.AsmGenerated",null,null,null);
        System.out.println();

    }

    /**
     * heheheheh
     */
    public void hehe() {
        System.out.println("hel");
    }
}
