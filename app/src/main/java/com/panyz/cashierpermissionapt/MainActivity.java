package com.panyz.cashierpermissionapt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.panyz.cashierpermission.CashierPermission;
import com.panyz.cashierpermission_annotation.Permission;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Permission(value = "123")
    boolean channelPermissionValue1;

    @Permission(value = "211",isReturnBooleanType = false)
    String channelPermissionValue2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Map<String, String> map = new HashMap<>();
        map.put("123", "1");
        map.put("211", "5");
        CashierPermission.inject(this, map);
        System.out.println("channelPermissionValue1 = " + channelPermissionValue1);
        System.out.println("channelPermissionValue2 = " + channelPermissionValue2);

    }
}