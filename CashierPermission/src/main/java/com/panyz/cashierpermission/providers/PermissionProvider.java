package com.panyz.cashierpermission.providers;

import java.util.Map;

public class PermissionProvider implements IProvider {
    @Override
    public boolean getBooleanValue(String name, String checkStandard, Map<String, String> map) {
        if (map.containsKey(name)) {
            return checkStandard.equals(map.get(name));
        }
        return false;
    }

    @Override
    public String getStringValue(String name, Map<String, String> map) {
        if (map.containsKey(name)) {
            return map.get(name);
        }
        return null;
    }
}
