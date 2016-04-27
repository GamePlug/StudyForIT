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
 * 生成上传文件所需的RequestBody与Map<String, RequestBody>
 * Created by leichao on 2016/4/26.
 */
public class RetrofitUpload {

    public static Map<String, RequestBody> getRequestBodyMap(List<UploadBean> listbean) {
        // HashMap是无序的，LinkedHashMap是有序的
        Map<String, RequestBody> params = new LinkedHashMap<>();
        for(UploadBean bean : listbean) {
            File file = bean.getFile();
            params.put( bean.getKey() + "\"; filename=\"" + file.getName(), getRequestBody(bean));
        }
        return params;
    }

    public static RequestBody getRequestBody(UploadBean bean) {
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
        return requestBody;
    }

    public static class UploadBean {
        private String key;
        private File file;
        private ProgressListener listener;

        /**
         * key是否加上"[]"取决于服务器。
         */
        public UploadBean(String key, File file, ProgressListener listener) {
            this.key = key;
            this.file = file;
            this.listener = listener;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
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
