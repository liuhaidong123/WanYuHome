package com.home.wanyu.bean;

/**
 * Created by wanyu on 2017/5/19.
 */

public class Bean_AboutUs {

    /**
     * result : success
     * code : 0
     * AboutUs : {"title":"宇家1.0","version":"当前版本号：1.0（wanyu2017）","content":"","picture":"/static/image/logo.png","id":1}
     */

    private String result;
    private String code;
    private AboutUsBean AboutUs;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AboutUsBean getAboutUs() {
        return AboutUs;
    }

    public void setAboutUs(AboutUsBean AboutUs) {
        this.AboutUs = AboutUs;
    }

    public static class AboutUsBean {
        /**
         * title : 宇家1.0
         * version : 当前版本号：1.0（wanyu2017）
         * content :
         * picture : /static/image/logo.png
         * id : 1
         */

        private String title;
        private String version;
        private String content;
        private String picture;
        private int id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
