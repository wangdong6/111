package com.bantang.app.bantang.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bantang.app.bantang.R;
import com.bantang.app.bantang.activity.TitleLunBoActivity;
import com.bantang.app.bantang.entity.RollViewPagerObj;
import com.bantang.app.bantang.fragment.titlefragment.JiaJuFragment;
import com.bantang.app.bantang.fragment.titlefragment.LiWuFragment;
import com.bantang.app.bantang.fragment.titlefragment.MeiShiFragment;
import com.bantang.app.bantang.fragment.titlefragment.ReMenFragment;
import com.bantang.app.bantang.fragment.titlefragment.ShejiGanFragment;
import com.bantang.app.bantang.fragment.titlefragment.ShengHuoFragment;
import com.bantang.app.bantang.fragment.titlefragment.ShuMaFragment;
import com.bantang.app.bantang.fragment.titlefragment.TuiJianFragment;
import com.bantang.app.bantang.fragment.titlefragment.YueDuFragment;
import com.bantang.app.bantang.fragment.titlefragment.ZuiXinFragment;
import com.bantang.app.bantang.json.JsonUtils;
import com.bantang.app.bantang.loader.PicassoImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TitleFragment extends Fragment {
    private Banner banner;
    private View root;
    private List<Integer> bannerList;
    private List<String> ImgList;
    private TabLayout tablayout;
    private ViewPager vp;
    private ArrayList<Fragment> fl;
    private ArrayList<String> tl;
    private MyPagerAdapter adapter1;
    private HashMap<String, String> lunbotiaozhuanmap;
    private ArrayList<HashMap<String, String>> lunbotiaozhuanlist = new ArrayList<HashMap<String, String>>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_title, null);


        ImgList = new ArrayList<>();
        //initBanner();
        initBannerData();
        initView();
        return root;
    }


    //初始化使用retrofit2联网轮播图图片
    //1.1、创建Retrofit实例, Retrofit2 的baseUlr 必须以/斜线结束,不然会抛出一个IllegalArgumentException
    private void initBannerData() {
        //发送网络请求，用异步方式，可直接在主线程调用
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://open4.bantangapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonUtils.LunBoTuApi lunbotu = retrofit.create(JsonUtils.LunBoTuApi.class);
        Call<RollViewPagerObj> callBack = lunbotu.login("com.jzyd.BanTang",
                "bt_app_android",
                "ffcda7a1c4ff338e05c42e7972ba7b8d",
                "133524291428760",
                "yingyongbao",
                "1486518593",
                "5.9.7",
                "4.2.2",
                "720",
                "25",
                "GT-P5210");
        callBack.enqueue(new Callback<RollViewPagerObj>() {
            @Override
            public void onResponse(Call<RollViewPagerObj> call, Response<RollViewPagerObj> response) {
                Log.i("wangdong", "onResponse运行了:");
                RollViewPagerObj obj = response.body();
                List<RollViewPagerObj.DataBeanX.BannerBean> lunbolist = obj.getData().getBanner();
                for (RollViewPagerObj.DataBeanX.BannerBean lunboitem : lunbolist) {
                    String item = lunboitem.getPhoto();
                    ImgList.add(item);

                    //轮播图跳转
                    lunbotiaozhuanmap = new HashMap<String, String>();
                    //标题
                    lunbotiaozhuanmap.put("lunbotitle", lunboitem.getTitle());
                    //id
                    lunbotiaozhuanmap.put("luoboid", lunboitem.getId());
                    //extends
                    lunbotiaozhuanmap.put("luoboextends", lunboitem.getExtend());
                    lunbotiaozhuanlist.add(lunbotiaozhuanmap);
                }
                banner = (Banner) root.findViewById(R.id.banner);
                //开启轮播图
                banner.setImages(ImgList)
                        .setImageLoader(new PicassoImageLoader())
                        .start();
                initJump();
            }

            @Override
            public void onFailure(Call<RollViewPagerObj> call, Throwable t) {
                Log.i("wangdong", "onFailure运行了");
            }
        });

    }

    /**
     * 点击轮播图跳转
     */
    private void initJump() {
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), TitleLunBoActivity.class);
                Toast.makeText(getActivity(), "点击了" + position, Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 1:
                        Message message = new Message();
                        message.what = 1;
                        message.obj = lunbotiaozhuanlist;
                        //发送粘性
                        EventBus.getDefault().postSticky(message);
                        startActivity(intent);
                        break;

                }
            }
        });
    }

    //初始化视图
    private void initView() {
        //初始化控件
        vp = (ViewPager) root.findViewById(R.id.title_vp);
        tablayout = (TabLayout) root.findViewById(R.id.tablayout);
        //初始化字符串的tablayout
        tl = new ArrayList<String>();
        tl.add("推荐");
        tl.add("最新");
        tl.add("热门");
        tl.add("礼物");
        tl.add("美食");
        tl.add("生活");
        tl.add("设计感");
        tl.add("家居");
        tl.add("数码");
        tl.add("阅读");
        //初始化fragmentlist
        fl = new ArrayList<Fragment>();
        fl.add(new TuiJianFragment());
        fl.add(new TuiJianFragment());
        fl.add(new TuiJianFragment());
        fl.add(new TuiJianFragment());
        fl.add(new TuiJianFragment());
        fl.add(new TuiJianFragment());
        fl.add(new TuiJianFragment());
        fl.add(new TuiJianFragment());
        fl.add(new TuiJianFragment());
        fl.add(new TuiJianFragment());
       /* fl.add(new ZuiXinFragment());
        fl.add(new ReMenFragment());
        fl.add(new LiWuFragment());
        fl.add(new MeiShiFragment());
        fl.add(new ShengHuoFragment());
        fl.add(new ShejiGanFragment());
        fl.add(new JiaJuFragment());
        fl.add(new ShuMaFragment());
        fl.add(new YueDuFragment());
*/
   /*     fragmentlist.add(new LiWuFragment());
        fragmentlist.add(new MeiShiFragment());
        fragmentlist.add(new ShengHuoFragment());
        fragmentlist.add(new ShejiGanFragment());
        fragmentlist.add(new JiaJuFragment());*/
        //初始化适配器
        adapter1 = new MyPagerAdapter(getChildFragmentManager());
        //绑定适配器
        vp.setAdapter(adapter1);
        //设置模式
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //给tabLayout添加选项卡
     /*   tablayout.addTab(tablayout.newTab());
        tablayout.addTab(tablayout.newTab());
        tablayout.addTab(tablayout.newTab());*/

        //设置关联的viwepager
        tablayout.setupWithViewPager(vp, true);


    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fl.get(position);
        }

        @Override
        public int getCount() {
            return fl.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tl.get(position);
        }
    }







 /*   *//**
     * 初始化轮播图
     *//*
    private void initBanner() {
        Log.i("wangdong","第三个运行了");
        banner = (Banner) root.findViewById(R.id.banner);
        //初始化轮播图集合
        bannerList = new ArrayList<Integer>();
        *//*bannerList.add(R.mipmap.a1);
        bannerList.add(R.mipmap.a2);
        bannerList.add(R.mipmap.a3);*//*
        //开启轮播图
        banner.setImages(bannerList)
                .setImageLoader(new PicassoImageLoader())
                .start();
    }*/
}
