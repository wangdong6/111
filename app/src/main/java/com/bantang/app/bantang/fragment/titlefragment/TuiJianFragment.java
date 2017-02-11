package com.bantang.app.bantang.fragment.titlefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bantang.app.bantang.R;
import com.bantang.app.bantang.adapter.TitleFragmentadapter;
import com.bantang.app.bantang.entity.TitleEntity;
import com.bantang.app.bantang.json.JsonUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 推荐界面
 */
public class TuiJianFragment extends Fragment {
    private View root = null;
    private PullToRefreshListView pulltorefreshlistview;
    private List<String> datas;
    private ArrayAdapter<String> adapter;
    private HashMap<String, String> titlemap;
    private ArrayList<HashMap<String, String>> dataslist;
    private TitleFragmentadapter titleadapter;
    private int page = 0;
    private Call<TitleEntity> callBack;
    JsonUtils.TitleTuiJian titletuijian;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tui_jian, null);
        dataslist = new ArrayList<HashMap<String, String>>();
        initData();
        initView();

        return root;
    }

    private void retrofitshju(int page) {

        callBack = titletuijian.login("com.jzyd.BanTang",
                "bt_app_android",
                "ffcda7a1c4ff338e05c42e7972ba7b8d",
                "133524291428760",
                "yingyongbao",
                "1486518593",
                "app_versions",
                "os_versions",
                "720",
                "25",
                "GT-P5210",
                page + "",
                "20",
                "14",
                "0",
                "1486603964");
    }

    //初始化数据
    private void initData() {
        Log.i("wangdong", "111:");
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://open4.bantangapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.i("wangdong", "retrofit:");
        titletuijian = retrofit.create(JsonUtils.TitleTuiJian.class);
     /*   Retrofit retrofit = new Retrofit.Builder().baseUrl("http://open4.bantangapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.i("wangdong", "retrofit:");
        JsonUtils.TitleTuiJian titletuijian = retrofit.create(JsonUtils.TitleTuiJian.class);
        Call<TitleEntity> callBack = titletuijian.login("com.jzyd.BanTang",
                "bt_app_android",
                "ffcda7a1c4ff338e05c42e7972ba7b8d",
                "133524291428760",
                "yingyongbao",
                "1486518593",
                "app_versions",
                "os_versions",
                "720",
                "25",
                "GT-P5210",
                page,
                "20",
                "14",
                "0",
                "1486603964");*/
        retrofitshju(page);
        Log.i("wangdong", "Call:");
        callBack.enqueue(new Callback<TitleEntity>() {
            @Override
            public void onResponse(Call<TitleEntity> call, Response<TitleEntity> response) {
                Log.i("wangdong", "titleEntituy运行了");


                TitleEntity titleentity = response.body();
                List<TitleEntity.DataBean.TopicBean> topic = titleentity.getData().getTopic();

                for (TitleEntity.DataBean.TopicBean topicdata : topic) {
                    titlemap = new HashMap<String, String>();
                    titlemap.put("imgpic", topicdata.getPic().toString());
                    Log.i("wangdongimg", "hehe:" + topicdata.getPic().toString());
                    titlemap.put("imgtitle", topicdata.getTitle().toString());
                    titlemap.put("user", topicdata.getUser().getNickname().toString());
                    titlemap.put("yan", topicdata.getViews().toString());
                    titlemap.put("heart", topicdata.getPraises().toString());
                    dataslist.add(titlemap);
                }
                Log.i("wangdong", "dataslist:" + dataslist.size());
                if (pulltorefreshlistview != null) {
                    pulltorefreshlistview.onRefreshComplete();
                }

                titleadapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<TitleEntity> call, Throwable t) {
                Log.i("wangdong", "onFailure执行了");
            }
        });


    }

    int intpage;

    //初始化视图
    private void initView() {
        pulltorefreshlistview = (PullToRefreshListView) root.findViewById(R.id.pulltorefreshlistview);
        //允许上拉或者下拉
        pulltorefreshlistview.setMode(PullToRefreshBase.Mode.BOTH);
        Log.i("wangdong222", "走到这1了:");
        //注册滑动监听器
        pulltorefreshlistview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉  加载最新
                Log.i("wangdong222", "走到这2了");
                dataslist.clear();
                page = 0;
                initData();
                Log.i("wangdong222", "走到这5了:" + page);
                // pulltorefreshlistview.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.i("wangdong222", "走到这3了:" + page);
                //上滑 加载更多
                page++;
                Log.i("wangdong222", "走到这4了:" + page);
                initData();
                //pulltorefreshlistview.onRefreshComplete();
            }
        });

        // adapter = new ArrayAdapter<String>(getActivity(),R.layout.title_item,datas);
        titleadapter = new TitleFragmentadapter(getContext(), dataslist);
        pulltorefreshlistview.setAdapter(titleadapter);
        pulltorefreshlistview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
