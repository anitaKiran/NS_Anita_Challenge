package com.nordsecurity.test

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    // for login token
    @Headers("Content-Type:application/json")
    @POST("v1/tokens")
    suspend fun getLoginToken(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>


    // get list for server
    @GET("v1/servers")
    suspend fun getServersList() : Response<ArrayList<ServersModelItem>?>
}
