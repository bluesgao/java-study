package com.bluesgao.jvm.demo;

public class SwitchTest {

    public enum TypeEnum {
        S(0, "成功"),
        F(1, "失败");

        private int code;
        private String desc;

        TypeEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }

    int test1(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return -1;
        }
    }

    int test2(int i) {
        switch (i) {
            case 0:
                return 0;
            case 100:
                return 100;
            case 1000:
                return 1000;
            case 10000:
                return 10000;
            default:
                return -1;
        }
    }

    int test3(TypeEnum typeEnum) {
        switch (typeEnum) {
            case S:
                return 0;
            case F:
                return 1;
            default:
                return -1;
        }
    }

}
