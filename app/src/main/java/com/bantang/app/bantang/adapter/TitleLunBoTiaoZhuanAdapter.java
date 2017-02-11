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

public class TitleLunBoTiaoZhuanAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> datas;

    public TitleLunBoTiaoZhuanAdapter(Context context, ArrayList<HashMap<String, String>> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        Log.i("wangdong666","换过来的数据："+datas.size());
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView= View.inflate(context, R.layout.title_lunbo_item, null);
            holder.title_pic = (ImageView) convertView.findViewById(R.id.title_pic);
            holder.title_title = (TextView) convertView.findViewById(R.id.title_title);
            holder.LiuLanshu = (TextView) convertView.findViewById(R.id.LiuLanshu);
            holder.Like = (TextView) convertView.findViewById(R.id.Like);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.riqi = (TextView) convertView.findViewById(R.id.riqi);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title_title.setText(datas.get(position).get("imgtitle"));
        holder.LiuLanshu.setText(datas.get(position).get("imgviews"));
        holder.Like.setText(datas.get(position).get("imgpraises"));
        holder.name.setText(datas.get(position).get("imgname"));
        holder.riqi.setText(datas.get(position).get("imgtime"));

        Picasso.with(context).load(datas.get(position).get("imgpic")
                .toString()).placeholder(R.mipmap.a1).into(holder.title_pic);

        return convertView;
    }
    class ViewHolder{
        ImageView title_pic;
        TextView title_title;
        TextView LiuLanshu;
        TextView Like;
        TextView name;
        TextView riqi;

    }
}
