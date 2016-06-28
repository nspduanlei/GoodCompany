package com.apec.android.views.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.android.R;
import com.apec.android.domain.entities.user.Version;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogUtils;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

/**
 * Author: duanlei
 * Date: 2015-06-30
 * 版本更新帮助类
 */
public class UpdateVersionUtil {

    private Activity context;
    private Version.data versionForUpdate;
    private ViewHolderDialog viewHolderDialog;

    public UpdateVersionUtil(Activity context, Version.data versionForUpdate) {
        this.context = context;
        this.versionForUpdate = versionForUpdate;
    }

    public void showDialog() {

        //版本更新的弹窗
//        View header = context.getLayoutInflater().inflate(R.layout.dialog_header, null);
//        TextView title = (TextView) header.findViewById(R.id.tv_title);
//        title.setText("版本更新");

        View dialogView = context.getLayoutInflater().inflate(R.layout.dialog_update_version, null);
        viewHolderDialog = new ViewHolderDialog(dialogView);
        viewHolderDialog.tvUpdateMsg.setText(versionForUpdate.getRemake());
        Holder holder = new ViewHolder(dialogView);
        DialogUtils.showNoHeaderDialog(context, holder, clickListener, DialogPlus.Gravity.CENTER);
    }

    static class ViewHolderDialog {
        DialogPlus dialog;
        File file;
        @BindView(R.id.tv_update_msg)
        TextView tvUpdateMsg;
        @BindView(R.id.pb_download)
        ProgressBar pbDownload;
        @BindView(R.id.fl_dialog)
        FrameLayout flDialog;

        @BindView(R.id.btn_download)
        Button btnDownload;

        ViewHolderDialog(View view) {
            ButterKnife.bind(this, view);
        }
    }

    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(DialogPlus dialog, View view) {
            switch (view.getId()) {
                case R.id.btn_download:
                    viewHolderDialog.tvUpdateMsg.setVisibility(View.GONE);
                    viewHolderDialog.flDialog.setVisibility(View.VISIBLE);
                    viewHolderDialog.btnDownload.setEnabled(false);

                    /**********************下载apk************************/
//                    ThinDownloadManager downloadManager = new ThinDownloadManager(4);
//                    RetryPolicy retryPolicy = new DefaultRetryPolicy();
//
//                    File file = new File(SDCardUtils.getSDCardPath() + Constants.APK_DIR + File.separator
//                            + AppUtils.getAppName(context) + new Date().getTime() + ".apk");
//
//                    if (!file.exists()) {
//                        file.mkdirs();
//                    }
//
//                    Uri downloadUri = Uri.parse(versionForUpdate.getUrl());
//                    Uri destiantionUri = Uri.parse(file.getPath());
//                    DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
//                            .setRetryPolicy(retryPolicy)
//                            .setDestinationURI(destiantionUri)
//                            .setPriority(DownloadRequest.Priority.HIGH)
//                            .setDownloadListener(new APKDownLoadStatusListener());
//                    downloadManager.add(downloadRequest);
//
//                    viewHolderDialog.dialog = dialog;
//                    viewHolderDialog.file = file;
                    //dialog.dismiss();







                    break;
                case R.id.iv_cancel:
                    //SPUtils.put(context, SPUtils.IS_NOT_UPDATE_VERSION, true);
                    dialog.dismiss();
                    break;
            }
        }
    };

//    class APKDownLoadStatusListener implements DownloadStatusListener {
//        @Override
//        public void onDownloadComplete(int id) {
//            viewHolderDialog.dialog.dismiss();
//            //下载完成安装
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(Uri.fromFile(viewHolderDialog.file),
//                    "application/vnd.android.package-archive");
//
//            context.startActivity(intent);
//        }
//
//        @Override
//        public void onDownloadFailed(int id, int errorCode, String errorMessage) {
//        }
//
//        @Override
//        public void onProgress(int id, long totalBytes, long downloadedBytes, int progress) {
//            viewHolderDialog.pbDownload.setProgress(progress);
//        }
//    }



}
