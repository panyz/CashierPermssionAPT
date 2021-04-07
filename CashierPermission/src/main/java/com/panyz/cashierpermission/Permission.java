package com.panyz.cashierpermission;

import com.panyz.cashierpermission.providers.IProvider;

import java.util.Map;

public interface Permission<T> {
    void inject(T host, Map<String, String> source, IProvider provider);
}
