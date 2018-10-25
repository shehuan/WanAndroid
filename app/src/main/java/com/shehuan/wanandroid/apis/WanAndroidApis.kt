package com.shehuan.wanandroid.apis

import com.shehuan.wanandroid.base.BaseResponse
import com.shehuan.wanandroid.bean.*
import com.shehuan.wanandroid.bean.articleList.ArticleListBean
import com.shehuan.wanandroid.bean.navi.NaviBean
import com.shehuan.wanandroid.bean.newProject.NewProjectBean
import com.shehuan.wanandroid.bean.query.QueryBean
import com.shehuan.wanandroid.bean.tree.TreeBean
import io.reactivex.Observable
import retrofit2.http.*

interface WanAndroidApis {
    /**
     * 首页banner
     */
    @GET("banner/json")
    fun banner(): Observable<BaseResponse<List<BannerBean>>>

    /**
     * 常用网站
     */
    @GET("friend/json")
    fun friend(): Observable<BaseResponse<List<FriendBean>>>

    /**
     * 首页文章列表
     */
    @GET("article/list/{pageNum}/json")
    fun articleList(@Path("pageNum") pageNum: Int): Observable<BaseResponse<ArticleListBean>>

    /**
     * 最新项目
     */
    @GET("article/listproject/{pageNum}/json")
    fun newProject(@Path("pageNum") pageNum: Int): Observable<BaseResponse<NewProjectBean>>

    /**
     * 热词（目前搜索最多的关键词）
     */
    @GET("//hotkey/json")
    fun hotKey(): Observable<BaseResponse<List<HotKeyBean>>>

    /**
     * 搜索
     */
    @POST("article/query/{pageNum}/json")
    fun query(@Path("pageNum") pageNum: Int, @Query("k") k: String): Observable<BaseResponse<QueryBean>>

    /**
     * 体系结构
     */
    @GET("tree/json")
    fun tree(): Observable<BaseResponse<List<TreeBean>>>

    /**
     * 导航
     */
    @GET("navi/json")
    fun navi(): Observable<BaseResponse<List<NaviBean>>>

    /**
     * 登录
     */
    @POST("user/login")
    fun login(@QueryMap param: Map<String, String>): Observable<BaseResponse<LoginBean>>

    /**
     * 注册
     */
    @POST("user/register")
    fun register(@QueryMap param: Map<String, String>): Observable<BaseResponse<RegisterBean>>

//    /**
//     * 收藏文章列表
//     */
//    @GET("/lg/collect/list/{pageNum}/json")
//    fun collectArticleList(@Path("pageNum") pageNum: Int): Observable<BaseResponse<>>
}