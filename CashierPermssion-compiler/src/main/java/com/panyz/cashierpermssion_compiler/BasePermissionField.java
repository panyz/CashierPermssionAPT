package com.panyz.cashierpermssion_compiler;

public class BasePermissionField {

    private String permissionName;

    private boolean isReturnBooleanType;

    private String checkPermissionStandard;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public boolean isReturnBooleanType() {
        return isReturnBooleanType;
    }

    public void setReturnBooleanType(boolean returnBooleanType) {
        isReturnBooleanType = returnBooleanType;
    }

    public String getCheckPermissionStandard() {
        return checkPermissionStandard;
    }

    public void setCheckPermissionStandard(String checkPermissionStandard) {
        this.checkPermissionStandard = checkPermissionStandard;
    }
}
