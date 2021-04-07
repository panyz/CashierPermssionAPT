package com.panyz.cashierpermssion_compiler;

import com.google.auto.service.AutoService;
import com.panyz.cashierpermission_annotation.Permission;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class CashierPermissionProcessor extends AbstractProcessor {

    private Filer mFiler;//文件相关的辅助类
    private Elements mElementUtils;//元素相关的辅助类
    private Messager mMessager;//日志相关的辅助类

    private Map<String, AnnotatedClass> mAnnotatedClassMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Permission.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // process() will be called several times
        mAnnotatedClassMap.clear();
        try {
            //解析注解元素
            processPermission(roundEnvironment);
        } catch (Exception e) {
            mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(e.getMessage()));
            return true;//stop process
        }

        for (AnnotatedClass annotatedClass : mAnnotatedClassMap.values()) {
            try {
                mMessager.printMessage(Diagnostic.Kind.NOTE, String.format("Generating file for %s", annotatedClass.getFullClassName()));
                annotatedClass.generatePermission().writeTo(mFiler);
            } catch (IOException e) {
                mMessager.printMessage(Diagnostic.Kind.ERROR, String.format("Generate file failed, reason: %s",e.getMessage()));
            }
        }

        return true;
    }

    private void processPermission(RoundEnvironment roundEnvironment) throws IllegalArgumentException {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Permission.class)) {
            AnnotatedClass annotatedClass = getAnnotatedClass(element);
            PermissionField field = new PermissionField(element);
            annotatedClass.addPermissionField(field);
        }
    }

    private AnnotatedClass getAnnotatedClass(Element element) {
        TypeElement classElement = (TypeElement) element.getEnclosingElement();// 获取父元素
        String fullClassName = classElement.getQualifiedName().toString();
        AnnotatedClass annotatedClass = mAnnotatedClassMap.get(fullClassName);
        if (annotatedClass == null) {
            annotatedClass = new AnnotatedClass(mElementUtils,classElement);
            mAnnotatedClassMap.put(fullClassName, annotatedClass);
        }
        return annotatedClass;
    }

}