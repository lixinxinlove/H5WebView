package com.lixinxin.h5webview.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/1/4.
 */
public class ToastUtils {
    private ToastUtils( ){}

    public static void showToast( Context context, String toast ){
        if( null == mToast ){
            mToast = Toast.makeText(context, toast, Toast.LENGTH_SHORT);
        }else{
            mToast.setText( toast );
        }
        mToast.show( );
    }

    public static void cancel( ){
        if( null != mToast ){
            mToast.cancel( );

        }
    }
    public static Toast mToast = null;

}
