package com.leichao.studyforit.common.net.retrofit;

import com.leichao.studyforit.common.net.okhttp.ProgressListener;
import com.leichao.studyforit.common.net.okhttp.ProgressRequestBody;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 *
 * Created by leichao on 2016/4/26.
 */
public class RetrofitUpload {

    public static Map<String, RequestBody> getRequestBodyMap(String key, List<UploadBean> listbean) {
        // HashMap是无序的，LinkedHashMap是有序的
        Map<String, RequestBody> params = new LinkedHashMap<>();
        for(UploadBean bean : listbean) {
            File file = bean.getFile();
            final ProgressListener listener = bean.getListener();
            RequestBody requestBody = RequestBody
                    .create(MediaType.parse("multipart/form-data"), file);
            if (listener != null) {
                requestBody = new ProgressRequestBody(requestBody, new ProgressListener() {
                    @Override
                    public void onProgress(long progress, long total, boolean done) {
                        listener.onProgress(progress, total, done);
                    }
                });
            }
            params.put( key + "[]\"; filename=\"" + file.getName(), requestBody);
        }
        return params;
    }

    public static class UploadBean {
        private File file;
        private ProgressListener listener;

        public UploadBean(File file, ProgressListener listener) {
            this.file = file;
            this.listener = listener;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public ProgressListener getListener() {
            return listener;
        }

        public void setListener(ProgressListener listener) {
            this.listener = listener;
        }
    }

}
