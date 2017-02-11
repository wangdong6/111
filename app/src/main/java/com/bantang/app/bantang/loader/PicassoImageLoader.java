package com.bantang.app.bantang.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bantang.app.bantang.R;
import com.squareup.picasso.Picasso;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by wd on 2017/2/8.
 */
//老师给的例子，这里相当于处理图片弄的毕加索三级缓存。
public class PicassoImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
    /*    if(path instanceof String){
            Picasso.with(context)
                    .load((String)path)
                    //没有加载下来显示的
                    //.placeholder(R.mipmap.a1)
                    //加载失败显示的
                    //.error(R.mipmap.a2)
                    .into(imageView);
        }else if (path instanceof Integer){
            Picasso.with(context)
                    .load((int) path)
                    //.placeholder()
                    //.error()
                    .into(imageView);
        }*/
        Picasso.with(context)
                .load((String)path)
                //没有加载下来显示的
                //.placeholder(R.mipmap.a1)
                //加载失败显示的
                //.error(R.mipmap.a2)
                .into(imageView);
    }
}
