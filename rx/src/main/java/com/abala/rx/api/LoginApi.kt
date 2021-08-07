package com.abala.rx.api

import io.reactivex.Observable
import retrofit2.http.Body

interface LoginApi {
    fun reg(@Body request: RegRequest): Observable<RegResponse>
    fun login(@Body request: LoginRequest): Observable<LoginResponse>
}

class RegRequest {}
class RegResponse {}
class LoginRequest {}
class LoginResponse {}