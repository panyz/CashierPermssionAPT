package com.panyz.cashierpermssion_compiler;

import com.panyz.cashierpermission_annotation.Permission;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

public class PermissionField extends BasePermissionField {
    private VariableElement mFieldElement;

    public PermissionField(Element element) throws IllegalArgumentException{
        if (element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException(String.format("only fields can be annotated with @%s", Permission.class.getSimpleName()));
        }
        mFieldElement = (VariableElement) element;

        Permission channelPermission = mFieldElement.getAnnotation(Permission.class);
        String permissionName = channelPermission.value();
        if ("".equals(permissionName)) {
            throw new IllegalArgumentException("权限名不能为空");
        }
        setPermissionName(permissionName);
        setCheckPermissionStandard(channelPermission.checkPermissionStandard());
        setReturnBooleanType(channelPermission.isReturnBooleanType());
    }

    public TypeMirror getFieldType() {
        return mFieldElement.asType();
    }

    public VariableElement getFieldElement() {
        return mFieldElement;
    }

    public void setFieldElement(VariableElement mFieldElement) {
        this.mFieldElement = mFieldElement;
    }
}
