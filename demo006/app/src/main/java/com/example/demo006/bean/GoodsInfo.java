package com.example.demo006.bean;

import com.example.demo006.R;

import java.util.ArrayList;

public class GoodsInfo {
    public long rowid; // 行号
    public int sn; // 序号
    public String name; // 名称
    public String desc; // 描述
    public float price; // 价格
    public String thumb_path; // 小图的保存路径
    public String pic_path; // 大图的保存路径
    public int thumb; // 小图的资源编号
    public int pic; // 大图的资源编号

    public GoodsInfo() {
        rowid = 0L;
        sn = 0;
        name = "";
        desc = "";
        price = 0;
        thumb_path = "";
        pic_path = "";
        thumb = 0;
        pic = 0;
    }

    // 声明一个手机商品的名称数组
    private static String[] mNameArray = {
            "小熊维尼", "电风扇", "耳机", "榴莲干", "百奇", "刷牙套装","刷子","棉花糖"
    };
    // 声明一个手机商品的描述数组
    private static String[] mDescArray = {
            "啥也没用但是很贵的小熊维尼",
            "夏日大风扇，吹吹吹",
            "戴了听不见室友打呼噜的耳机",
            "贼好吃的榴莲干",
            "百奇，百奇，百奇，超奇怪，超好吃",
            "刷牙刷牙，早晚都得刷牙",
            "啥都能刷的刷子，买不了上当",
            "超级甜的棉花糖"
    };
    // 声明一个手机商品的价格数组
    private static float[] mPriceArray = {30, 100, 299, 50, 8, 30,40,10};
    // 声明一个手机商品的小图数组
    private static int[] mThumbArray = {
            R.drawable.a001_s, R.drawable.a002_s, R.drawable.a003_s,
            R.drawable.a004_s, R.drawable.a005_s, R.drawable.a006_s,
            R.drawable.a007_s, R.drawable.a008_s
    };
    // 声明一个手机商品的大图数组
    private static int[] mPicArray = {
            R.drawable.a001, R.drawable.a002, R.drawable.a003,
            R.drawable.a004, R.drawable.a005, R.drawable.a006,
            R.drawable.a007, R.drawable.a008
    };

    // 获取默认的手机信息列表
    public static ArrayList<GoodsInfo> getDefaultList() {
        ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
        for (int i = 0; i < mNameArray.length; i++) {
            GoodsInfo info = new GoodsInfo();
            info.name = mNameArray[i];
            info.desc = mDescArray[i];
            info.price = mPriceArray[i];
            info.thumb = mThumbArray[i];
            info.pic = mPicArray[i];
            goodsList.add(info);
        }
        return goodsList;
    }
}