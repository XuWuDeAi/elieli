package main.zm.elieli.service;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by shaofa on 2017/11/14.
 */

public class Permissions
{
    // 检查和申请权限
    static final int PERMISSION_REQ_CODE = 1;
    public static void check(Activity activity)
    {
        // 要申请的权限列表
        final String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA
        };

        // 检查本应用是否有了 WRITE_EXTERNAL_STORAGE 权限
        if (ContextCompat.checkSelfPermission(activity, permissions[0])
                != PackageManager.PERMISSION_GRANTED)
        {
            // 系统将弹出一个对话框，询问用户是否授权
            ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQ_CODE);
        }
    }

    // 权限申请的结果
    // requestCode：请求码
    // permissions: 申请的N个权限
    // grantResults: 每个权限是否被授权
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
//    {
//
//        if(requestCode == PERMISSION_REQ_CODE)
//        {
//            for(int i=0; i<permissions.length;i++)
//            {
//                if(grantResults[i] != PackageManager.PERMISSION_GRANTED)
//                {
//                    // 惨,用户没给我们授权...这意味着有此功能就不能用了
//                }
//            }
//        }
//    }
}
