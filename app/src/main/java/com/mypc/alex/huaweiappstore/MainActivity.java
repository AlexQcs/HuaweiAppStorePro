package com.mypc.alex.huaweiappstore;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String[] PERMISSION_STORAGE = {};
    private static final int PERMISSION_CODE_STORAGE = 1;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences=getSharedPreferences("appStore", Context.MODE_PRIVATE);
        boolean isFirst=mSharedPreferences.getBoolean("isFirst",true);
        if (!isFirst){
            goHome();
        }
        verifyStoragePermission(this);
    }

    /**
     * 申请SD卡读写权限
     * @param activity
     */
    void verifyStoragePermission(Activity activity){
        //1.定义权限
        int permission= PermissionChecker.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission!=PermissionChecker.PERMISSION_GRANTED){
            //2.弹出申请权限框
            ActivityCompat.requestPermissions(activity,PERMISSION_STORAGE,PERMISSION_CODE_STORAGE);
        }
    }

    @OnClick(R.id.btn_gohome)
    void goHome(){
        mSharedPreferences.edit().putBoolean("isFirst",false).commit();
        startActivity(new Intent(MainActivity.this,HomeActivity.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0]==PermissionChecker.PERMISSION_GRANTED){
            Toast.makeText(this,"申请SD卡权限成功",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"申请SD卡权限失败",Toast.LENGTH_SHORT).show();
        }
    }
}
