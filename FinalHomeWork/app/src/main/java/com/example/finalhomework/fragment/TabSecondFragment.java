package com.example.finalhomework.fragment;

import com.example.finalhomework.MainApplication;
import com.example.finalhomework.R;
import com.example.finalhomework.ShoppingCartProActivity;
import com.example.finalhomework.adapter.GoodsAdapter;
import com.example.finalhomework.bean.CartInfo;
import com.example.finalhomework.bean.GoodsInfo;
import com.example.finalhomework.database.CartDBHelper;
import com.example.finalhomework.database.GoodsDBHelper;
import com.example.finalhomework.util.DateUtil;
import com.example.finalhomework.util.FileUtil;
import com.example.finalhomework.util.SharedUtil;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class TabSecondFragment extends Fragment implements GoodsAdapter.addCartListener {
    private static final String TAG = "TabSecondFragment";
    protected View mView; // 声明一个视图对象
    protected Context mContext; // 声明一个上下文对象
    private TextView tv_count;
    private GridView gv_channel; // 声明一个网格视图对象
    private int mCount; // 购物车中的商品数量
    private GoodsDBHelper mGoodsHelper; // 声明一个商品数据库的帮助器对象
    private CartDBHelper mCartHelper; // 声明一个购物车数据库的帮助器对象

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity(); // 获取活动页面的上下文
        // 根据布局文件fragment_tab_second.xml生成视图对象
        mView = inflater.inflate(R.layout.activity_shopping_channel, container, false);

        TextView tv_title = mView.findViewById(R.id.tv_title);
        tv_count = mView.findViewById(R.id.tv_count);
        // 从布局视图中获取名叫gv_channel的网格视图
        gv_channel = mView.findViewById(R.id.gv_channel);
      //  mView.findViewById(R.id.iv_cart).setOnClickListener(this);
        tv_title.setText("温作豪的商城");

        return mView;
    }

    // 把指定编号的商品添加到购物车
    public void addToCart(long goods_id) {
        mCount++;
        tv_count.setText("" + mCount);
        // 把购物车中的商品数量写入共享参数
        SharedUtil.getIntance(mContext).writeShared("count", "" + mCount);
        // 根据商品编号查询购物车数据库中的商品记录
        CartInfo info = mCartHelper.queryByGoodsId(goods_id);
        if (info != null) { // 购物车已存在该商品记录
            info.count++; // 该商品的数量加一
            info.update_time = DateUtil.getNowDateTime("");
            // 更新购物车数据库中的商品记录信息
            mCartHelper.update(info);
        } else { // 购物车不存在该商品记录
            info = new CartInfo();
            info.goods_id = goods_id;
            info.count = 1;
            info.update_time = DateUtil.getNowDateTime("");
            // 往购物车数据库中添加一条新的商品记录
            mCartHelper.insert(info);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        // 获取共享参数保存的购物车中的商品数量
        mCount = Integer.parseInt(SharedUtil.getIntance(mContext).readShared("count", "0"));
        tv_count.setText("" + mCount);
        // 获取商品数据库的帮助器对象
        mGoodsHelper = GoodsDBHelper.getInstance(mContext, 1);
        // 打开商品数据库的读连接
        mGoodsHelper.openReadLink();
        // 获取购物车数据库的帮助器对象
        mCartHelper = CartDBHelper.getInstance(mContext, 1);
        // 打开购物车数据库的写连接
        mCartHelper.openWriteLink();

        // 获取共享参数保存的是否首次打开参数
        mFirst = SharedUtil.getIntance(mContext).readShared("first", "true");
        // 模拟从网络下载商品图片
        downloadGoods();
        // 把是否首次打开写入共享参数
        SharedUtil.getIntance(mContext).writeShared("first", "false");

        // 展示商品列表
        showGoods();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 关闭商品数据库的数据库连接
        mGoodsHelper.closeLink();
        // 关闭购物车数据库的数据库连接
        mCartHelper.closeLink();
    }

    public void showGoods() {
        // 查询商品数据库中的所有商品记录
        ArrayList<GoodsInfo> goodsArray = mGoodsHelper.query("1=1");
        // 构建商场中商品网格的适配器对象
        GoodsAdapter adapter = new GoodsAdapter(mContext, goodsArray, this);
        // 给gv_channel设置商品网格适配器
        gv_channel.setAdapter(adapter);
        // 给gv_channel设置网格项点击监听器
        gv_channel.setOnItemClickListener(adapter);
    }

    private String mFirst = "true"; // 是否首次打开

    //模拟网络数据，初始化数据库中的商品信息
    private void downloadGoods() {
        // 获取共享参数保存的是否首次打开参数
        mFirst = SharedUtil.getIntance(mContext).readShared("first", "true");
        // 获取当前App的私有存储路径
        String path = MainApplication.getInstance().getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS).toString() + "/";
        if (mFirst.equals("true")) { // 如果是首次打开
            ArrayList<GoodsInfo> goodsList = GoodsInfo.getDefaultList();
            for (int i = 0; i < goodsList.size(); i++) {
                GoodsInfo info = goodsList.get(i);
                // 往商品数据库插入一条该商品的记录
                long rowid = mGoodsHelper.insert(info);
                info.rowid = rowid;
                // 往全局内存写入商品小图
                Bitmap thumb = BitmapFactory.decodeResource(getResources(), info.thumb);
                MainApplication.getInstance().mIconMap.put(rowid, thumb);
                String thumb_path = path + rowid + "_s.jpg";
                FileUtil.saveImage(thumb_path, thumb);
                info.thumb_path = thumb_path;
                // 往SD卡保存商品大图
                Bitmap pic = BitmapFactory.decodeResource(getResources(), info.pic);
                String pic_path = path + rowid + ".jpg";
                FileUtil.saveImage(pic_path, pic);
                pic.recycle();
                info.pic_path = pic_path;
                // 更新商品数据库中该商品记录的图片路径
                mGoodsHelper.update(info);
            }
        } else { // 不是首次打开
            // 查询商品数据库中所有商品记录
            ArrayList<GoodsInfo> goodsArray = mGoodsHelper.query("1=1");
            for (int i = 0; i < goodsArray.size(); i++) {
                GoodsInfo info = goodsArray.get(i);
                // 从指定路径读取图片文件的位图数据
                Bitmap thumb = BitmapFactory.decodeFile(info.thumb_path);
                // 把该位图对象保存到应用实例的全局变量中
                MainApplication.getInstance().mIconMap.put(info.rowid, thumb);
            }
        }
        // 把是否首次打开写入共享参数
        SharedUtil.getIntance(mContext).writeShared("first", "false");
    }
}

