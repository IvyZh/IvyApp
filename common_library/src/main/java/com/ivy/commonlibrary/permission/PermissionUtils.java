package com.ivy.commonlibrary.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.ivy.commonlibrary.utils.L;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivy on 2018/5/29.
 */

public class PermissionUtils {

    private PermissionUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static void executeSucceedMethod(Object reflectObject, int requestCode) {
        Method[] declaredMethods = reflectObject.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            PermissionSucceed permissionSucceed = method.getAnnotation(PermissionSucceed.class);
            L.v("executeSucceedMethod ：" + method + ",permissionSucceed:" + permissionSucceed);

            if (permissionSucceed != null) {
                int methodCode = permissionSucceed.requestCode();

                L.v("executeSucceedMethod ：" + methodCode + "," + requestCode + "," + method);
                if (methodCode == requestCode) {
                    L.v("找到了方法：" + method);
                    executeMethod(reflectObject, method);
                }
            }
        }

    }

    private static void executeMethod(Object reflectObject, Method method) {
        method.setAccessible(true);
        try {
            method.invoke(reflectObject, new Object[]{});//反射执行方法  第一个是传该方法是属于哪个类   第二个参数是反射方法的参数
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getDeniedPermissions(Object object, String[] requestPermissions) {
        List<String> deniedPermissions = new ArrayList<>();

        for (String permission : requestPermissions) {
            if (ContextCompat.checkSelfPermission(getActivity(object), permission) == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }

    public static Activity getActivity(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else {
            return null;
        }
    }

    public static void executeFailMethod(Object reflectObject, int requestCode) {
        Method[] declaredMethods = reflectObject.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            PermissionFail permissionFail = method.getAnnotation(PermissionFail.class);
            L.v("executeFailMethod ：" + method + ",permissionSucceed:" + permissionFail);

            if (permissionFail != null) {
                int methodCode = permissionFail.requestCode();
                L.v("executeFailMethod ：" + methodCode + "," + requestCode + "," + method);
                if (methodCode == requestCode) {
                    L.v("找到了方法：" + method);
                    executeMethod(reflectObject, method);
                }
            }
        }
    }
}
