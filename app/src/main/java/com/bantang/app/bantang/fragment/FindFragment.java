package com.bantang.app.bantang.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.widget.ImageView;
import android.widget.TextView;

import com.bantang.app.bantang.R;
import com.bantang.app.bantang.entity.FindEntity;
import com.bantang.app.bantang.entity.RollViewPagerObj;
import com.bantang.app.bantang.fragment.findfragment.FindGuanZhuFragment;
import com.bantang.app.bantang.fragment.findfragment.FindReMenFragment;
import com.bantang.app.bantang.fragment.findfragment.FindZuiXinFragment;
import com.bantang.app.bantang.json.JsonUtils;
import com.bantang.app.bantang.loader.PicassoImageLoader;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FindFragment extends Fragment {
    private View root = null;
    private Banner banner;
    private List<String> imglist = new ArrayList<>();
    private RecyclerView xiaozurevycleview;
    private RecyclerView huodongrevycleview;
    private int currentType;
    private HashMap<String, String> xiaozuMap;
    private HashMap<String, String> remenMap;
    private ArrayList<HashMap<String, String>> xiaozuarraylist = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> remenarrayList = new ArrayList<HashMap<String, String>>();
    XizoZuAdapter adapter1;
    HuoDongAdapter adapter2;
    private ArrayList<String> tabList = new ArrayList<String>();
    private ArrayList<Fragment> fragmentlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_find, null);
        initFindBannerData();
        //初始化推荐小组
        initTuijianView();
        //初始化TabLayout
        initTabLayout();
        return root;
    }



    //发现轮播图
    private void initFindBannerData() {
        //初始化使用retrofit2联网轮播图图片
        //1.1、创建Retrofit实例, Retrofit2 的baseUlr 必须以/斜线结束,不然会抛出一个IllegalArgumentException
        //发送网络请求，用异步方式，可直接在主线程调用
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://open4.bantangapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonUtils.FindLunbo lunbotu = retrofit.create(JsonUtils.FindLunbo.class);
        Call<FindEntity> callBack = lunbotu.login("yingyongbao",
                "GT-P5210",
                "ffcda7a1c4ff338e05c42e7972ba7b8d",
                "com.jzyd.BanTang",
                "bt_app_android",
                "720",
                "5.9.7",
                "4.2.2",
                "25",
                "1486518593",
                "133524291428760");
        callBack.enqueue(new Callback<FindEntity>() {
            @Override
            public void onResponse(Call<FindEntity> call, Response<FindEntity> response) {
                Log.i("wangdong", "onResponse运行了:");
                FindEntity obj = response.body();
                List<FindEntity.DataBean.BannerBean> beanList = obj.getData().getBanner();
                for (FindEntity.DataBean.BannerBean temp : beanList) {
                    imglist.add(temp.getPhoto().toString());
                }
                //小组数据
                List<FindEntity.DataBean.SubjectListBean> subject_list = obj.getData().getSubject_list();
                for (FindEntity.DataBean.SubjectListBean subtemp : subject_list) {
                    xiaozuMap = new HashMap<String, String>();
                    Log.i("wangdong小组数据", "小组数据" + subtemp.getPhoto());
                    xiaozuMap.put("xiaozuimg", subtemp.getPhoto());
                    xiaozuMap.put("xiaozuimgtitle", subtemp.getTitle());
                    xiaozuarraylist.add(xiaozuMap);
                }
                adapter1.notifyDataSetChanged();

                //热门数据
                List<FindEntity.DataBean.ActivityListBean> activity_list = obj.getData().getActivity_list();
                for (FindEntity.DataBean.ActivityListBean rementemp : activity_list) {
                    remenMap = new HashMap<String, String>();
                    Log.i("wangdong活动数据", "活动数据" + rementemp.getIcon());
                    remenMap.put("huodongimg", rementemp.getIcon());
                    remenMap.put("huodongimgtitle", rementemp.getTitle());
                    remenMap.put("huodongimgcanyu", rementemp.getUsers());
                    remenarrayList.add(remenMap);
                }
                adapter2.notifyDataSetChanged();

                FindFragment.this.banner = (Banner) root.findViewById(R.id.banner);
                //开启轮播图
                FindFragment.this.banner.setImages(imglist)
                        .setImageLoader(new PicassoImageLoader())
                        .start();
            }

            @Override
            public void onFailure(Call<FindEntity> call, Throwable t) {
                Log.i("wangdong", "onFailure运行了");
            }
        });

    }

    /**
     * 推荐小组
     */
    private void initTuijianView() {
        xiaozurevycleview = (RecyclerView) root.findViewById(R.id.xiaozurevycleview);
        //列表
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        xiaozurevycleview.setLayoutManager(llm);
        adapter1 = new XizoZuAdapter();
        xiaozurevycleview.setAdapter(adapter1);


        huodongrevycleview = (RecyclerView) root.findViewById(R.id.huodongrevycleview);
        //网格
        GridLayoutManager glm = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        huodongrevycleview.setLayoutManager(glm);
        adapter2 = new HuoDongAdapter();

        huodongrevycleview.setAdapter(adapter2);
    }

    /**
     * 小组适配器
     */
    class XizoZuAdapter extends RecyclerView.Adapter<XizoZuAdapter.XiaozuViewHolder> {

        @Override
        public XiaozuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root2 = View.inflate(parent.getContext(), R.layout.find_item_xiaozu, null);
            XiaozuViewHolder viewholder = new XiaozuViewHolder(root2);
            return viewholder;
        }

        @Override
        public void onBindViewHolder(XiaozuViewHolder holder, int position) {
            holder.tv_xiaozu.setText(xiaozuarraylist.get(position).get("xiaozuimgtitle").toString());
            Picasso.with(getContext()).load(xiaozuarraylist.get(position).get("xiaozuimg").toString()).resize(150, 150).into(holder.iv_xiaozu);
        }

        @Override
        public int getItemCount() {
            return xiaozuarraylist.size();
        }

        class XiaozuViewHolder extends RecyclerView.ViewHolder {

            ImageView iv_xiaozu;
            TextView tv_xiaozu;
            public XiaozuViewHolder(View itemView) {
                super(itemView);
                iv_xiaozu = (ImageView) itemView.findViewById(R.id.iv_xiaozu);
                tv_xiaozu = (TextView) itemView.findViewById(R.id.tv_xiaozu);
            }
        }
    }

    /**
     * 活动适配器
     */
    class HuoDongAdapter extends RecyclerView.Adapter<HuoDongAdapter.HuoDongViewHolder> {

        @Override
        public HuoDongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root2 = View.inflate(parent.getContext(), R.layout.find_item_huodong, null);
            HuoDongViewHolder viewholder = new HuoDongViewHolder(root2);
            return viewholder;
        }

        @Override
        public void onBindViewHolder(HuoDongViewHolder holder, int position) {
            holder.tvtitle_huodong.setText(remenarrayList.get(position).get("huodongimgtitle").toString());
            holder.tvcanyu_huodong.setText(remenarrayList.get(position).get("huodongimgcanyu").toString());
            Picasso.with(getContext()).load(remenarrayList.get(position).get("huodongimg").toString()).resize(150, 150).into(holder.iv_huodong);
        }

        @Override
        public int getItemCount() {
            return remenarrayList.size();
        }

        class HuoDongViewHolder extends RecyclerView.ViewHolder {

            ImageView iv_huodong;
            TextView tvtitle_huodong;
            TextView tvcanyu_huodong;
            public HuoDongViewHolder(View itemView) {
                super(itemView);
                iv_huodong = (ImageView) itemView.findViewById(R.id.iv_huodong);
                tvtitle_huodong = (TextView) itemView.findViewById(R.id.tvtitle_huodong);
                tvcanyu_huodong = (TextView) itemView.findViewById(R.id.tvcanyu_huodong);
            }
        }


    }


    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        TabLayout tablayout = (TabLayout) root.findViewById(R.id.tablayout1);
        ViewPager findviewpager = (ViewPager) root.findViewById(R.id.findviewpager);

        tabList.add("最新");
        tabList.add("热门");
        tabList.add("关注");

        fragmentlist = new ArrayList<Fragment>();
        fragmentlist.add(new FindZuiXinFragment());
        fragmentlist.add(new FindReMenFragment());
        fragmentlist.add(new FindGuanZhuFragment());

        FindTabLayoutAdapter adapter1 = new FindTabLayoutAdapter(getChildFragmentManager());
        findviewpager.setAdapter(adapter1);
        tablayout.setTabMode(TabLayout.MODE_FIXED);

        tablayout.setupWithViewPager(findviewpager);

    }
    class FindTabLayoutAdapter extends FragmentPagerAdapter{

        public FindTabLayoutAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentlist.get(position);
        }

        @Override
        public int getCount() {
            return fragmentlist.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabList.get(position);
        }
    }
}
