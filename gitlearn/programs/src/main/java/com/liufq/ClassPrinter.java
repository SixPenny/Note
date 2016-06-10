package com.liufq;

import org.objectweb.asm.*;

/**
 * Created by dylanliu on 16/6/7.
 */
public class ClassPrinter implements ClassVisitor{

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println(name+" extends  "+superName+"{");
    }

    public void visitSource(String source, String debug) {
        System.out.println("visit source");
        System.out.println(source);
        System.out.println(debug);
        System.out.println("visit source end");
    }

    public void visitOuterClass(String owner, String name, String desc) {
        System.out.println(owner);
        System.out.println(name);
        System.out.println(desc);
    }

    public AnnotationVisitor visitAnnotation(String s, boolean b) {
        return null;
    }

    public void visitAttribute(Attribute attribute) {

    }

    public void visitInnerClass(String s, String s1, String s2, int i) {

    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        System.out.println("visit field enter");
        System.out.println("    "+desc+"  "+name);
        System.out.println("visit field leave");
        return null;
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("visit method enter");
        System.out.println("    "+name +"  "+ desc);//desc is method descriptor
        System.out.println(signature);
        System.out.println("visit method leave");
        return null;
    }

    public void visitEnd() {
        System.out.println("}");
    }
}
