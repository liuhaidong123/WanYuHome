package com.home.wanyu.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/5/19.
 */

public class Bean_MyHouseInf {
    /**
     * result : success
     * code : 0
     * Family : {"personalId":0,"roomNumber":"","city":"","residentialQuartersId":0,"unitNumber":"","residentialQuartersName":"","ownerName":"","areaName":"","familyName":"17743516301","buildingNumber":"","familyState":3,"id":15,"floor":"","lat":0,"address":"","lng":0,"ownerTelephone":0,"areaCode":0,"userType":0,"items":[]}
     */

    private String result;
    private String code;
    private FamilyBean Family;

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

    public FamilyBean getFamily() {
        return Family;
    }

    public void setFamily(FamilyBean Family) {
        this.Family = Family;
    }

    public static class FamilyBean {
        /**
         * personalId : 0
         * roomNumber :
         * city :
         * residentialQuartersId : 0
         * unitNumber :
         * residentialQuartersName :
         * ownerName :
         * areaName :
         * familyName : 17743516301
         * buildingNumber :
         * familyState : 3
         * id : 15
         * floor :
         * lat : 0
         * address :
         * lng : 0
         * ownerTelephone : 0
         * areaCode : 0
         * userType : 0
         * items : []
         */

        private int personalId;
        private String roomNumber;
        private String city;
        private int residentialQuartersId;
        private String unitNumber;
        private String residentialQuartersName;
        private String ownerName;
        private String areaName;
        private String familyName;
        private String buildingNumber;
        private int familyState;
        private int id;
        private String floor;
        private int lat;
        private String address;
        private int lng;
        private int ownerTelephone;
        private int areaCode;
        private int userType;
        private List<?> items;

        public int getPersonalId() {
            return personalId;
        }

        public void setPersonalId(int personalId) {
            this.personalId = personalId;
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        public void setRoomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getResidentialQuartersId() {
            return residentialQuartersId;
        }

        public void setResidentialQuartersId(int residentialQuartersId) {
            this.residentialQuartersId = residentialQuartersId;
        }

        public String getUnitNumber() {
            return unitNumber;
        }

        public void setUnitNumber(String unitNumber) {
            this.unitNumber = unitNumber;
        }

        public String getResidentialQuartersName() {
            return residentialQuartersName;
        }

        public void setResidentialQuartersName(String residentialQuartersName) {
            this.residentialQuartersName = residentialQuartersName;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }

        public String getBuildingNumber() {
            return buildingNumber;
        }

        public void setBuildingNumber(String buildingNumber) {
            this.buildingNumber = buildingNumber;
        }

        public int getFamilyState() {
            return familyState;
        }

        public void setFamilyState(int familyState) {
            this.familyState = familyState;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public int getLat() {
            return lat;
        }

        public void setLat(int lat) {
            this.lat = lat;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getLng() {
            return lng;
        }

        public void setLng(int lng) {
            this.lng = lng;
        }

        public int getOwnerTelephone() {
            return ownerTelephone;
        }

        public void setOwnerTelephone(int ownerTelephone) {
            this.ownerTelephone = ownerTelephone;
        }

        public int getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(int areaCode) {
            this.areaCode = areaCode;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public List<?> getItems() {
            return items;
        }

        public void setItems(List<?> items) {
            this.items = items;
        }
    }
//
//    /**
//     * result : success
//     * code : 0
//     * Family : {"personalId":0,"roomNumber":"102","city":"涿州市","residentialQuartersId":1,"unitNumber":"5","residentialQuartersName":"","ownerName":"刘文","areaName":"黑龙江 哈尔滨市 阿城市","familyName":"13717883005","buildingNumber":"12","familyState":0,"id":1,"floor":"","lat":0,"address":"名流一品小区1号楼一单元1楼355号","lng":0,"ownerTelephone":17743516301,"areaCode":110101,"userType":0,"items":[]}
//     */
//
//    private String result;
//    private String code;
//    private FamilyBean Family;
//
//    public String getResult() {
//        return result;
//    }
//
//    public void setResult(String result) {
//        this.result = result;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public FamilyBean getFamily() {
//        return Family;
//    }
//
//    public void setFamily(FamilyBean Family) {
//        this.Family = Family;
//    }
//
//    public static class FamilyBean {
//        /**
//         * personalId : 0
//         * roomNumber : 102
//         * city : 涿州市
//         * residentialQuartersId : 1
//         * unitNumber : 5
//         * residentialQuartersName :
//         * ownerName : 刘文
//         * areaName : 黑龙江 哈尔滨市 阿城市
//         * familyName : 13717883005
//         * buildingNumber : 12
//         * familyState : 0
//         * id : 1
//         * floor :
//         * lat : 0
//         * address : 名流一品小区1号楼一单元1楼355号
//         * lng : 0
//         * ownerTelephone : 17743516301
//         * areaCode : 110101
//         * userType : 0
//         * items : []
//         */
//
//        private int personalId;
//        private String roomNumber;
//        private String city;
//        private int residentialQuartersId;
//        private String unitNumber;
//        private String residentialQuartersName;
//        private String ownerName;
//        private String areaName;
//        private String familyName;
//        private String buildingNumber;
//        private int familyState;
//        private int id;
//        private String floor;
//        private int lat;
//        private String address;
//        private int lng;
//        private long ownerTelephone;
//        private int areaCode;
//        private int userType;
//        private List<?> items;
//
//        public int getPersonalId() {
//            return personalId;
//        }
//
//        public void setPersonalId(int personalId) {
//            this.personalId = personalId;
//        }
//
//        public String getRoomNumber() {
//            return roomNumber;
//        }
//
//        public void setRoomNumber(String roomNumber) {
//            this.roomNumber = roomNumber;
//        }
//
//        public String getCity() {
//            return city;
//        }
//
//        public void setCity(String city) {
//            this.city = city;
//        }
//
//        public int getResidentialQuartersId() {
//            return residentialQuartersId;
//        }
//
//        public void setResidentialQuartersId(int residentialQuartersId) {
//            this.residentialQuartersId = residentialQuartersId;
//        }
//
//        public String getUnitNumber() {
//            return unitNumber;
//        }
//
//        public void setUnitNumber(String unitNumber) {
//            this.unitNumber = unitNumber;
//        }
//
//        public String getResidentialQuartersName() {
//            return residentialQuartersName;
//        }
//
//        public void setResidentialQuartersName(String residentialQuartersName) {
//            this.residentialQuartersName = residentialQuartersName;
//        }
//
//        public String getOwnerName() {
//            return ownerName;
//        }
//
//        public void setOwnerName(String ownerName) {
//            this.ownerName = ownerName;
//        }
//
//        public String getAreaName() {
//            return areaName;
//        }
//
//        public void setAreaName(String areaName) {
//            this.areaName = areaName;
//        }
//
//        public String getFamilyName() {
//            return familyName;
//        }
//
//        public void setFamilyName(String familyName) {
//            this.familyName = familyName;
//        }
//
//        public String getBuildingNumber() {
//            return buildingNumber;
//        }
//
//        public void setBuildingNumber(String buildingNumber) {
//            this.buildingNumber = buildingNumber;
//        }
//
//        public int getFamilyState() {
//            return familyState;
//        }
//
//        public void setFamilyState(int familyState) {
//            this.familyState = familyState;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getFloor() {
//            return floor;
//        }
//
//        public void setFloor(String floor) {
//            this.floor = floor;
//        }
//
//        public int getLat() {
//            return lat;
//        }
//
//        public void setLat(int lat) {
//            this.lat = lat;
//        }
//
//        public String getAddress() {
//            return address;
//        }
//
//        public void setAddress(String address) {
//            this.address = address;
//        }
//
//        public int getLng() {
//            return lng;
//        }
//
//        public void setLng(int lng) {
//            this.lng = lng;
//        }
//
//        public long getOwnerTelephone() {
//            return ownerTelephone;
//        }
//
//        public void setOwnerTelephone(long ownerTelephone) {
//            this.ownerTelephone = ownerTelephone;
//        }
//
//        public int getAreaCode() {
//            return areaCode;
//        }
//
//        public void setAreaCode(int areaCode) {
//            this.areaCode = areaCode;
//        }
//
//        public int getUserType() {
//            return userType;
//        }
//
//        public void setUserType(int userType) {
//            this.userType = userType;
//        }
//
//        public List<?> getItems() {
//            return items;
//        }
//
//        public void setItems(List<?> items) {
//            this.items = items;
//        }
//    }
}
