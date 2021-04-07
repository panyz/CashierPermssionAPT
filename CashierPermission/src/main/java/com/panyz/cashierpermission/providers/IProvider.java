package com.panyz.cashierpermission.providers;

import java.util.Map;

public interface IProvider {
    /**
     *
     * @return 获取权限布尔值
     */
    boolean getBooleanValue(String name,String checkStandard,Map<String,String> map);

    /**
     *
     * @return 获取权限int类型
     */
    String getStringValue(String name,Map<String,String> map);
}
