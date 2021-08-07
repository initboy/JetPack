package com.abala.rx.api

import com.abala.rx.bean.ProjectList
import com.abala.rx.bean.ProjectTree
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetApi {

    // IO 耗时操作
    @GET("project/tree/json")
    fun getProjectTree(): Observable<ProjectTree>

    // IO 耗时操作
    /**
     * 请求接口 project/list/1/json/?cid=294
     * 两个变量 1 和 294
     * 第一个变量定义{pageIndex} 接口参数 @Path("pageIndex") pageIndex: Int
     * 第二个变量接口参数 @Query("cid") cid: Int
     */
    @GET("project/list/{pageIndex}/json")
    fun getProjectList(
        @Path("pageIndex") pageIndex: Int,
        @Query("cid") cid: Int
    ): Observable<ProjectList>
}