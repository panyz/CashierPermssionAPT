package com.panyz.cashierpermission;

import com.panyz.cashierpermission.providers.PermissionProvider;
import com.panyz.cashierpermission.providers.IProvider;

import java.util.HashMap;
import java.util.Map;

public class CashierPermission {
    private static final int TYPE_CHANNEL = 1;//渠道配置权限
    private static final int TYPE_CONFIG = 2;//系统配置权限
    private static final int TYPE_FPOS = 3;//FPOS权限

    private static final Map<String, Permission> PERMISSION_MAP = new HashMap<>();

    private static final PermissionProvider PERMISSION_PROVIDER = new PermissionProvider();

    public static void inject(Object host,Map<String, String> permissionMap) {
        inject(host,permissionMap,PERMISSION_PROVIDER);
    }

    public static void inject(Object host,Map<String, String> permissionMap, IProvider provider) {
        String className = host.getClass().getName();
        try {
            Permission permission = PERMISSION_MAP.get(className);
            if (permission == null) {
                //通过注解类的类名，得到其内部类的class对象
                Class<?> permissionClass = Class.forName(className + "$$Permission");
                //实例化拿到的具体对象，调用注入方法
                permission = (Permission) permissionClass.newInstance();
                //避免每一次注入都去找Permission对象，用map缓存起来
                PERMISSION_MAP.put(className, permission);
            }
            permission.inject(host, permissionMap, provider);
        } catch (Exception e) {
            throw new RuntimeException("unable to inject for" + className, e);
        }
    }
}
