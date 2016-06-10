package com.liufq;

import org.objectweb.asm.*;

/**
 * Created by dylanliu on 16/6/8.
 */
public class AddClassMember extends ClassAdapter {
    int fAcc;
    String funName;
    String funDesc;
    boolean isFunPresent;
    public AddClassMember(int fcc,String funName,String funDesc,ClassVisitor classVisitor) {
        super(classVisitor);
        this.fAcc=fcc;
        this.funDesc=funDesc;
        this.funName = funName;
    }

    @Override
    public void visit(int i, int i1, String s, String s1, String s2, String[] strings) {
        super.visit(i, i1, s, s1, s2, strings);
    }

    @Override
    public void visitSource(String s, String s1) {
        super.visitSource(s, s1);
    }

    @Override
    public void visitOuterClass(String s, String s1, String s2) {
        super.visitOuterClass(s, s1, s2);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String s, boolean b) {
        return super.visitAnnotation(s, b);
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        super.visitAttribute(attribute);
    }

    @Override
    public void visitInnerClass(String s, String s1, String s2, int i) {
        super.visitInnerClass(s, s1, s2, i);
    }

    @Override
    public FieldVisitor visitField(int i, String s, String s1, String s2, Object o) {
        if(s.equals(funName)) {
            isFunPresent = true;
        }
        return super.visitField(i, s, s1, s2, o);
    }

    @Override
    public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
        if(s.equals(funName)) {
            isFunPresent = true;
        }
        return super.visitMethod(i, s, s1, s2, strings);
    }

    @Override
    public void visitEnd() {
        if (!isFunPresent){
            MethodVisitor mv = super.visitMethod(fAcc,funName,funDesc,null,null);
//            mv.visitEnd();
        }
        super.visitEnd();
    }
}
