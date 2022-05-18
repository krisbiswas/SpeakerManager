package com.tut.lifestyle.constants;

public class Constants {
    public enum Percent {
        PERCENT_50(0.5f), PERCENT_30(0.3f), PERCENT_100(1f);
        Percent(float v) {
        }
    }

    public enum Size{
        SIZE_20DP(20);

        Size(int i) {
        }
    }

    public enum Margin{
        MARGIN_10(10);
        Margin(int i){}
    }
}
