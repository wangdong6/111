package com.bantang.app.bantang.json;

import com.bantang.app.bantang.entity.FindEntity;
import com.bantang.app.bantang.entity.RollViewPagerObj;
import com.bantang.app.bantang.entity.TitleBannerEntity;
import com.bantang.app.bantang.entity.TitleEntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by wd on 2017/2/8.
 */

public class JsonUtils {
    /**
     * 轮播图，定义请求的service接口
     */
    public interface LunBoTuApi{
        @FormUrlEncoded
        @POST("/recommend/operationElement")
        Call<RollViewPagerObj> login(@Field("app_id") String app_id,
                                     @Field("client_id") String client_id,
                                     @Field("client_secret") String client_secret,
                                     @Field("track_deviceid")String track_deviceid,
                                     @Field("channel_name") String channel_name,
                                     @Field("app_installtime") String app_installtime,
                                     @Field("app_versions") String app_versions,
                                     @Field("os_versions") String os_versions,
                                     @Field("screensize") String screensize,
                                     @Field("v") String v,
                                     @Field("track_device_info") String track_device_info
                                );
    }

    /**
     * 推荐数据
     */
    public interface TitleTuiJian{
        @FormUrlEncoded
        @POST("/recommend/index")
        Call<TitleEntity> login(@Field("app_id")String app_id,
                                @Field("client_id") String client_id,
                                @Field("client_secret")String client_secret,
                                @Field("track_deviceid")String track_deviceid,
                                @Field("channel_name")String channel_name,
                                @Field("app_installtime")String app_installtime,
                                @Field("app_versions")String app_versions,
                                @Field("os_versions")String os_versions,
                                @Field("screensize")String screensize,
                                @Field("v")String v,
                                @Field("track_device_info")String track_device_info,
                                @Field("page")String page,
                                @Field("pagesize")String pagesize,
                                @Field("app_open_count")String app_open_count,
                                @Field("update_time")String update_time,
                                @Field("last_get_time")String last_get_time
        );
    }
    /**
     * 发现轮播图
     */
    public interface FindLunbo{
        @FormUrlEncoded
        @POST("/post/index/index")
        Call<FindEntity> login(@Field("channel_name") String channel_name,
                               @Field("track_device_info") String track_device_info,
                               @Field("client_secret") String client_secret,
                               @Field("app_id")String app_id,
                                @Field("client_id") String client_id,
                               @Field("screensize")String screensize,
                               @Field("app_versions")String app_versions,
                               @Field("os_versions") String os_versions,
                               @Field("v")String v,
                               @Field("app_installtime")String app_installtime,
                               @Field("track_deviceid")String track_deviceid
        );
    }

    /**
     * 轮播图点击跳转
     */
    public interface LunboClick{
        @FormUrlEncoded
        @POST("/topics/topic/listByIds")
        Call<TitleBannerEntity> login(@Field("app_id") String app_id,
                                      @Field("client_id") String client_id,
                                      @Field("client_secret") String client_secret,
                                      @Field("track_deviceid")String track_deviceid,
                                      @Field("channel_name") String channel_name,
                                      @Field("app_installtime")String app_installtime,
                                      @Field("app_versions")String app_versions,
                                      @Field("os_versions") String os_versions,
                                      @Field("screensize")String screensize,
                                      @Field("v")String v,
                                      @Field("track_device_info")String track_device_info,
                                      @Field("ids")String ids
        );
    }

}
