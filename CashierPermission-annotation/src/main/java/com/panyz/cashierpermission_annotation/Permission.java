package com.panyz.cashierpermission_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface Permission {
    /**
     *
     * @return 权限名
     */
    String value();


    /**
     *
     * @return 权限返回类型：true:返回布尔值，false：返回String类型
     */
    boolean isReturnBooleanType() default true;

    /**
     *
     * @return 权限判断基准，默认用1来作判断
     */
    String checkPermissionStandard() default "1";
}
