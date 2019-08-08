package com.chengfan.xiyou.utils;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-28/19:39
 * @Description:
 */
public class BaiDuEntity {
    /**
     * address : CN|新疆|五家渠|None|CHINANET|0|0
     * content : {"address_detail":{"province":"新疆维吾尔自治区","city":"五家渠市","district":"","street":"","street_number":"","city_code":789},"address":"新疆维吾尔自治区五家渠市","point":{"y":"5492546.5092417","x":"9747847.25545653"}}
     * status : 0
     */

    private String address;
    private ContentBean content;
    private int status;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class ContentBean {
        /**
         * address_detail : {"province":"新疆维吾尔自治区","city":"五家渠市","district":"","street":"","street_number":"","city_code":789}
         * address : 新疆维吾尔自治区五家渠市
         * point : {"y":"5492546.5092417","x":"9747847.25545653"}
         */

        private AddressDetailBean address_detail;
        private String address;
        private PointBean point;

        public AddressDetailBean getAddress_detail() {
            return address_detail;
        }

        public void setAddress_detail(AddressDetailBean address_detail) {
            this.address_detail = address_detail;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public PointBean getPoint() {
            return point;
        }

        public void setPoint(PointBean point) {
            this.point = point;
        }

        public static class AddressDetailBean {
            /**
             * province : 新疆维吾尔自治区
             * city : 五家渠市
             * district :
             * street :
             * street_number :
             * city_code : 789
             */

            private String province;
            private String city;
            private String district;
            private String street;
            private String street_number;
            private int city_code;

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getStreet_number() {
                return street_number;
            }

            public void setStreet_number(String street_number) {
                this.street_number = street_number;
            }

            public int getCity_code() {
                return city_code;
            }

            public void setCity_code(int city_code) {
                this.city_code = city_code;
            }
        }

        public static class PointBean {
            /**
             * y : 5492546.5092417
             * x : 9747847.25545653
             */

            private String y;
            private String x;

            public String getY() {
                return y;
            }

            public void setY(String y) {
                this.y = y;
            }

            public String getX() {
                return x;
            }

            public void setX(String x) {
                this.x = x;
            }
        }
    }
}
