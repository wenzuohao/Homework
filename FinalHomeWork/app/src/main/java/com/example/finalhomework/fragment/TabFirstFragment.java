package com.example.finalhomework.fragment;

import com.example.finalhomework.R;
import com.example.finalhomework.adapter.RecyclerStaggeredAdapter;
import com.example.finalhomework.bean.GoodsInfo;
import com.example.finalhomework.constant.ImageList;
import com.example.finalhomework.util.Utils;
import com.example.finalhomework.widget.BannerPager;
import com.example.finalhomework.widget.SpacesItemDecoration;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class TabFirstFragment extends Fragment implements BannerPager.BannerClickListener  {
    private static final String TAG = "TabFirstFragment";
    protected View mView; // 声明一个视图对象
    protected Context mContext; // 声明一个上下文对象
    private TextView tv_pager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity(); // 获取活动页面的上下文
        // 根据布局文件fragment_tab_first.xml生成视图对象
        mView = inflater.inflate(R.layout.fragment_tab_first, container, false);
        initRecyclerStaggered();
        // 根据碎片标签栏传来的参数拼接文本字符串
       // String desc = String.format("我是%s页面，来自%s",
       //         "首页", getArguments().getString("tag"));
       // TextView tv_first = mView.findViewById(R.id.tv_first);
       // tv_first.setText(desc);
        tv_pager =  mView.findViewById(R.id.tv_pager);
        // 从布局文件中获取名叫banner_pager的横幅轮播条
        BannerPager banner = mView.findViewById(R.id.banner_pager);
        // 获取横幅轮播条的布局参数
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) banner.getLayoutParams();
        params.height = 250;
        // 设置横幅轮播条的布局参数
        banner.setLayoutParams(params);
        // 设置横幅轮播条的广告图片队列
        banner.setImage(ImageList.getDefault());
        // 设置横幅轮播条的广告点击监听器
        banner.setOnBannerListener(this);
        // 开始广告图片的轮播滚动
        banner.start();
        return mView;
    }

    private void initRecyclerStaggered() {
        // 从布局文件中获取名叫rv_staggered的循环视图
        RecyclerView rv_staggered = mView.findViewById(R.id.rv_staggered);
        // 创建一个垂直方向的瀑布流布局管理器
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(
                3, RecyclerView.VERTICAL);
        // 设置循环视图的布局管理器
        rv_staggered.setLayoutManager(manager);
        // 构建一个服装列表的瀑布流适配器
        RecyclerStaggeredAdapter adapter = new RecyclerStaggeredAdapter(getActivity(), GoodsInfo.getDefaultStag());
        // 设置瀑布流列表的点击监听器
        adapter.setOnItemClickListener(adapter);
        // 设置瀑布流列表的长按监听器
        adapter.setOnItemLongClickListener(adapter);
        // 给rv_staggered设置服装瀑布流适配器
        rv_staggered.setAdapter(adapter);
        // 设置rv_staggered的默认动画效果
        rv_staggered.setItemAnimator(new DefaultItemAnimator());
        // 给rv_staggered添加列表项之间的空白装饰
        rv_staggered.addItemDecoration(new SpacesItemDecoration(3));
    }

    public void onBannerClick(int position) {
      //  String desc = String.format("您点击了第%d张图片", position + 1);
        //tv_pager.setText(desc);
    }
}
