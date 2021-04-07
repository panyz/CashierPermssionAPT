package com.panyz.cashierpermssion_compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class AnnotatedClass {
    public List<PermissionField> permissionFields;
    public Elements elementsUtils;
    public TypeElement classElement;

    public AnnotatedClass(Elements elementsUtils, TypeElement classElement) {
        this.elementsUtils = elementsUtils;
        this.classElement = classElement;
        permissionFields = new ArrayList<>();
    }

    public void addPermissionField(PermissionField field) {
        permissionFields.add(field);
    }

    public JavaFile generatePermission() {
        MethodSpec.Builder injectMethodBuilder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(classElement.asType()), "host")
                .addParameter(ClassName.get("java.util", "Map"), "source")
                .addParameter(ClassName.get("com.panyz.cashierpermission.providers", "IProvider"), "provider");

        for (PermissionField permissionField : permissionFields) {
            if (permissionField.isReturnBooleanType()) {
                injectMethodBuilder.addStatement("host.$L = provider.getBooleanValue($S,$S,source)", permissionField.getFieldElement().getSimpleName(),
                        permissionField.getPermissionName(), permissionField.getCheckPermissionStandard());
            } else {
                injectMethodBuilder.addStatement("host.$L = provider.getStringValue($S,source)", permissionField.getFieldElement().getSimpleName(),
                        permissionField.getPermissionName());
            }


        }

        //获取泛形类型
        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(ClassName.get("com.panyz.cashierpermission","Permission"), TypeName.get(classElement.asType()));
        TypeSpec typeSpec = TypeSpec.classBuilder(classElement.getSimpleName() + "$$Permission")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(parameterizedTypeName)
                .addMethod(injectMethodBuilder.build())
                .build();

        String pageageName = elementsUtils.getPackageOf(classElement).getQualifiedName().toString();
        return JavaFile.builder(pageageName, typeSpec).build();
    }

    public String getFullClassName() {
        return classElement.getQualifiedName().toString();
    }
}
