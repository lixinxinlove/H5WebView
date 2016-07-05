package com.lixinxin.h5webview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.net.Uri;

import com.lixinxin.h5webview.receiver.CompleteReceiver;
import com.lixinxin.h5webview.utils.PrefUtils;
import com.lixinxin.h5webview.utils.ToastUtils;

/**
 * Created by lixinxin on 2016/7/5.
 */
public class UpdateManager {

    private String TAG = "UpdateManager";
    private Context mContext;
    private String apkUrl="https://raw.githubusercontent.com/CymChad/BaseRecyclerViewAdapterHelper/master/demo_res/demo.apk";
    public UpdateManager(Context context) {
        this.mContext = context;
    }

    /**
     * 检测软件更新，这里主要是进行网络请求获取当前版本的信息，然后根据返回的versioncode判断是否跟新
     * 这个方法的参数：isToast指的是否需要弹出“已经是最新版本”弹框，在首页肯定不需要弹出，设置里面需
     * 要弹出，看上面功能集成的代码也能看出来
     */
    public void checkUpdate(final boolean isToast) {
        // 这里面就是单纯的网络请求，请换成自己的网络框架和URL就行
//        RequestParams params = new RequestParams();
//        // URLUtils.GET_UPDATE_INFO：获取当前版本信息的接口
//        NetUtils.doGet(URLUtils.GET_UPDATE_INFO, params, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(“网络请求失败了”) {
//                LogUtils.e(TAG, "获取系统版本号失败" + responseString);
//            }
//
//            @Override
//            public void onSuccess(“网络请求成功了”) {
//                LogUtils.e(TAG, "获取系统版本号成功" + responseString);
//                int mVersion_code = DeviceUtil.getVersionCode(mContext);// 当前的版本号
//                int nVersion_code = JsonParseUtil.getInt(responseString, "code", 0);// 最新的版本号
//                String version_info = JsonParseUtil.getString(responseString, "description");
//                if (mVersion_code < nVersion_code) {// 需要更新
//                    showNoticeDialog(version_info);// 显示更新提示对话框
//                } else {// 已经是最新的版本
//                    if (isToast) {
//                        ToastUtil.show(mContext, "已经是最新版本");
//                    }
//                }
//            }
//        });

        showNoticeDialog("有新版本立即更新",apkUrl);// 显示更新提示对话框
    }
    /**
     * 显示软件更新对话框
     */
    public void showNoticeDialog(String version_info, final String apkUrl) {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("更新");
        builder.setMessage(version_info);
        // 更新
        builder.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ToastUtils.showToast(mContext, "程序正在后台下载，可在通知栏查看下载进度");
                // 首先注册一个广播接收者，用于在下载完成后安装apk
                IntentFilter filter = new IntentFilter();
                filter.addAction("android.intent.action.DOWNLOAD_COMPLETE");
                filter.addAction("android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED");
                BroadcastReceiver receiver = new CompleteReceiver();
                mContext.registerReceiver(receiver, filter);
                // 调用系统的下载功能去下载应用
                DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(mContext.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(apkUrl);//apkUrl：apk下载的接口
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                request.setVisibleInDownloadsUi(false);
                request.setTitle("应用更新");
                // 这个id要保存起来，在广播接收者里面会根据它判断是否成功下载
                long id = downloadManager.enqueue(request);
                PrefUtils.setString(mContext,"UPDATE_ID", String.valueOf(id));
            }
        });

        // 稍后更新
        builder.setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }
}
