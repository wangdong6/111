package com.bantang.app.bantang.activity;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.bantang.app.bantang.R;
import com.bantang.app.bantang.adapter.TitleLunBoTiaoZhuanAdapter;
import com.bantang.app.bantang.entity.RollViewPagerObj;
import com.bantang.app.bantang.entity.TitleBannerEntity;
import com.bantang.app.bantang.json.JsonUtils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TitleLunBoActivity extends AppCompatActivity {
    private Toolbar toolbar1;

    private String title1;
    private String id;
    private String ids;
    private ArrayList<HashMap<String,String>> datas;

    private HashMap<String,String> retrofit2map;
    private ArrayList<HashMap<String,String>> listdatas = new ArrayList<HashMap<String, String>>();
    private ListView listview1;
    TitleLunBoTiaoZhuanAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_lun_bo);
        //注册EventBus
        EventBus.getDefault().register(this);
        toolbar1 = (Toolbar) this.findViewById(R.id.toolbar1);
        toolbar1.setNavigationIcon(R.mipmap.b1);

        toolbar1.setTitle(title1);

        setSupportActionBar(toolbar1);
        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("wangdong777","点击了");
                finish();
            }
        });
        //数据
        lunbotiaozhuanRetrofit2();
        listview1 = (ListView) this.findViewById(R.id.listview1);
        adapter1 = new TitleLunBoTiaoZhuanAdapter(this,listdatas);
        listview1.setAdapter(adapter1);
    }

    @Subscribe(sticky = true)
    public void receiverLuobo(Message msg) {
        switch (msg.what){
            case 1:
                datas = (ArrayList<HashMap<String, String>>) msg.obj;
                Log.i("wangdong666","走这里了2:"+datas.get(0).get("lunbotitle"));
                title1 = datas.get(0).get("lunbotitle");
                id = datas.get(1).get("luoboid");
                ids = datas.get(0).get("luoboextends");

                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    /**
     * 请求数据
     */
    private void lunbotiaozhuanRetrofit2(){

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://open4.bantangapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonUtils.LunboClick luoboclick = retrofit.create(JsonUtils.LunboClick.class);

        Call<TitleBannerEntity> callBack = luoboclick.login("com.jzyd.BanTang",
                "bt_app_android",
                "ffcda7a1c4ff338e05c42e7972ba7b8d",
                "133524291428760",
                "yingyongbao",
                "1486518593",
                "5.9.7",
                "4.2.2",
                "720",
                "25",
                "GT-P5210",
                ids);
        callBack.enqueue(new Callback<TitleBannerEntity>() {
            @Override
            public void onResponse(Call<TitleBannerEntity> call, Response<TitleBannerEntity> response) {
                TitleBannerEntity body = response.body();
                List<TitleBannerEntity.DataBean.TopicBean> topic = body.getData().getTopic();
                for (TitleBannerEntity.DataBean.TopicBean temp:topic) {
                    retrofit2map = new HashMap<String,String>();
                    Log.i("wangdong666","走这里了1");

                    String imgpic = temp.getPic().toString();
                    retrofit2map.put("imgpic",imgpic);

                    String imgtitle = temp.getTitle().toString();
                    retrofit2map.put("imgtitle",imgtitle);

                    String imgviews = temp.getViews().toString();
                    retrofit2map.put("imgviews",imgviews);

                    String imgpraises = temp.getPraises().toString();
                    retrofit2map.put("imgpraises",imgpraises);


                    String imgname = temp.getUser().getNickname().toString();
                    retrofit2map.put("imgname",imgname);

                    String imgtime = temp.getCreate_time_str().toString();
                    retrofit2map.put("imgtime",imgtime);

                    listdatas.add(retrofit2map);
                    Log.i("wangdong666","listdatas:"+listdatas.size());
                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TitleBannerEntity> call, Throwable t) {
                Log.i("wangdong666","走这里了2");
            }
        });

    }
}
