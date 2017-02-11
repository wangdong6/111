package com.bantang.app.bantang.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bantang.app.bantang.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wd on 2017/2/9.
 */

public class TitleFragmentadapter extends BaseAdapter{
    private Context context;
    private ArrayList<HashMap<String,String>> datas;

    public TitleFragmentadapter(Context context, ArrayList<HashMap<String, String>> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        Log.i("wangdong","count:"+datas);
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder v = null;
        if(convertView==null){
            v = new ViewHolder();
            convertView = View.inflate(context, R.layout.title_item,null);
            v.iv = (ImageView) convertView.findViewById(R.id.imgview);
            v.tv_title = (TextView) convertView.findViewById(R.id.titletext);
            v.tv_user = (TextView) convertView.findViewById(R.id.user);
            v.tv_heart = (TextView) convertView.findViewById(R.id.yanheart);
            v.tv_yan = (TextView) convertView.findViewById(R.id.hearttext);
            convertView.setTag(v);
        }else{
            v = (ViewHolder) convertView.getTag();
        }

        v.tv_title.setText(datas.get(position).get("imgtitle").toString());
        v.tv_user.setText(datas.get(position).get("user".toString()));
        v.tv_heart.setText(datas.get(position).get("heart").toString());
        v.tv_yan.setText(datas.get(position).get("yan").toString());

        Picasso.with(context).load(datas.get(position).get("imgpic")
                .toString()).placeholder(R.mipmap.a1).into(v.iv);


        return convertView;



    }

    class ViewHolder{
        ImageView iv;
        TextView tv_title;
        TextView tv_user;
        TextView tv_heart;
        TextView tv_yan;
    }
}
