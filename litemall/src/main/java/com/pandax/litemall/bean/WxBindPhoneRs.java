package com.pandax.litemall.bean;

/**
 * @author Da
 * @version 1.0
 * @date 2019/11/22
 * @time 13:57
 */

public class WxBindPhoneRs {

    /**
     * phoneNumber : 17858934751
     * watermark : {"appid":"wx848f92fc267fbb3b","timestamp":1574401899}
     * purePhoneNumber : 17858934751
     * countryCode : 86
     */
    private String phoneNumber;
    private WatermarkEntity watermark;
    private String purePhoneNumber;
    private String countryCode;

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setWatermark(WatermarkEntity watermark) {
        this.watermark = watermark;
    }

    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public WatermarkEntity getWatermark() {
        return watermark;
    }

    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public class WatermarkEntity {
        /**
         * appid : wx848f92fc267fbb3b
         * timestamp : 1574401899
         */
        private String appid;
        private int timestamp;

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getAppid() {
            return appid;
        }

        public int getTimestamp() {
            return timestamp;
        }
    }
}
