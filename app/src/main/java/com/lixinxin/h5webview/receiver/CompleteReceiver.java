package com.lixinxin.h5webview.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.lixinxin.h5webview.utils.PrefUtils;

import java.io.File;

/**
 * Created by lixinxin on 2016/7/5.
 */
public class CompleteReceiver extends BroadcastReceiver {
    // 接收到下载完成的广播后，检查是否是一个apk，然后安装
    @Override
    public void onReceive(Context context, Intent intent) {
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        long did = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
        //获取调用DownloadManager时保存的id
        long myid = Long.parseLong(PrefUtils.getString(context, "UPDATE_ID", ""));
        if (myid != did) {// 用于验证是否是下载的同一个apk
            return;
        }
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(did);
        Cursor c = manager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_SUCCESSFUL:
                    //如果文件名不为空，说明已经存在了，然后获取uri，进行安装
                    File path = new File(c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME)));
                    if (!path.exists()) {
                        return;
                    }
                    Uri uri = Uri.fromFile(path);
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    install.setDataAndType(uri, "application/vnd.android.package-archive");
                    // 执行意图进行安装
                    context.startActivity(install);
                    break;
                default:
                    manager.remove(did);
                    break;
            }
        }
    }
}
