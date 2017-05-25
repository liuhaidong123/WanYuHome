package com.home.wanyu.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/5/24.
 */

public class Bean_otherActivity {

    /**
     * total : 1
     * rows : [{"createTimeString":"","personalId":1,"residentialQuartersId":0,"user_name":"","activityAddress":"网吧","starttime":{"date":19,"hours":10,"seconds":59,"month":4,"nanos":0,"timezoneOffset":-480,"year":117,"minutes":49,"time":1495162199000,"day":5},"likeNum":0,"activityTelephone":18783983939,"starttimeString":"2017-05-19 10:49:59","id":14,"activityContent":"约好基友一起撸","joined":false,"activityTheme":"LOL五黑","avatar":"","participateNumber":0,"islike":false,"visibleRange":0,"activitypictureId":0,"comment":0,"time":null,"activityNumber":4,"activityType":0,"endtimeString":"2017-05-23 10:50:13"}]
     * colmodel : []
     */

    private int total;
    private List<RowsBean> rows;
    private List<?> colmodel;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public List<?> getColmodel() {
        return colmodel;
    }

    public void setColmodel(List<?> colmodel) {
        this.colmodel = colmodel;
    }

    public static class RowsBean {
        /**
         * createTimeString :
         * personalId : 1
         * residentialQuartersId : 0
         * user_name :
         * activityAddress : 网吧
         * starttime : {"date":19,"hours":10,"seconds":59,"month":4,"nanos":0,"timezoneOffset":-480,"year":117,"minutes":49,"time":1495162199000,"day":5}
         * likeNum : 0
         * activityTelephone : 18783983939
         * starttimeString : 2017-05-19 10:49:59
         * id : 14
         * activityContent : 约好基友一起撸
         * joined : false
         * activityTheme : LOL五黑
         * avatar :
         * participateNumber : 0
         * islike : false
         * visibleRange : 0
         * activitypictureId : 0
         * comment : 0
         * time : null
         * activityNumber : 4
         * activityType : 0
         * endtimeString : 2017-05-23 10:50:13
         */

        private String createTimeString;
        private int personalId;
        private int residentialQuartersId;
        private String user_name;
        private String activityAddress;
        private StarttimeBean starttime;
        private int likeNum;
        private long activityTelephone;
        private String starttimeString;
        private int id;
        private String activityContent;
        private boolean joined;
        private String activityTheme;
        private String avatar;
        private int participateNumber;
        private boolean islike;
        private int visibleRange;
        private int activitypictureId;
        private int comment;
        private Object time;
        private int activityNumber;
        private int activityType;
        private String endtimeString;

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public int getPersonalId() {
            return personalId;
        }

        public void setPersonalId(int personalId) {
            this.personalId = personalId;
        }

        public int getResidentialQuartersId() {
            return residentialQuartersId;
        }

        public void setResidentialQuartersId(int residentialQuartersId) {
            this.residentialQuartersId = residentialQuartersId;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getActivityAddress() {
            return activityAddress;
        }

        public void setActivityAddress(String activityAddress) {
            this.activityAddress = activityAddress;
        }

        public StarttimeBean getStarttime() {
            return starttime;
        }

        public void setStarttime(StarttimeBean starttime) {
            this.starttime = starttime;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public long getActivityTelephone() {
            return activityTelephone;
        }

        public void setActivityTelephone(long activityTelephone) {
            this.activityTelephone = activityTelephone;
        }

        public String getStarttimeString() {
            return starttimeString;
        }

        public void setStarttimeString(String starttimeString) {
            this.starttimeString = starttimeString;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getActivityContent() {
            return activityContent;
        }

        public void setActivityContent(String activityContent) {
            this.activityContent = activityContent;
        }

        public boolean isJoined() {
            return joined;
        }

        public void setJoined(boolean joined) {
            this.joined = joined;
        }

        public String getActivityTheme() {
            return activityTheme;
        }

        public void setActivityTheme(String activityTheme) {
            this.activityTheme = activityTheme;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getParticipateNumber() {
            return participateNumber;
        }

        public void setParticipateNumber(int participateNumber) {
            this.participateNumber = participateNumber;
        }

        public boolean isIslike() {
            return islike;
        }

        public void setIslike(boolean islike) {
            this.islike = islike;
        }

        public int getVisibleRange() {
            return visibleRange;
        }

        public void setVisibleRange(int visibleRange) {
            this.visibleRange = visibleRange;
        }

        public int getActivitypictureId() {
            return activitypictureId;
        }

        public void setActivitypictureId(int activitypictureId) {
            this.activitypictureId = activitypictureId;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public Object getTime() {
            return time;
        }

        public void setTime(Object time) {
            this.time = time;
        }

        public int getActivityNumber() {
            return activityNumber;
        }

        public void setActivityNumber(int activityNumber) {
            this.activityNumber = activityNumber;
        }

        public int getActivityType() {
            return activityType;
        }

        public void setActivityType(int activityType) {
            this.activityType = activityType;
        }

        public String getEndtimeString() {
            return endtimeString;
        }

        public void setEndtimeString(String endtimeString) {
            this.endtimeString = endtimeString;
        }

        public static class StarttimeBean {
            /**
             * date : 19
             * hours : 10
             * seconds : 59
             * month : 4
             * nanos : 0
             * timezoneOffset : -480
             * year : 117
             * minutes : 49
             * time : 1495162199000
             * day : 5
             */

            private int date;
            private int hours;
            private int seconds;
            private int month;
            private int nanos;
            private int timezoneOffset;
            private int year;
            private int minutes;
            private long time;
            private int day;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }
        }
    }
}
