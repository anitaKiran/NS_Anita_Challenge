package com.nordsecurity.test

import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Anita Kiran on 6/26/2022.
 */
class MainRepository @Inject constructor(private val apiService: ApiService,private val nordDao: NordDao) {

    suspend fun getLoginToken(loginRequest: LoginRequest): Response<LoginResponse> =
        apiService.getLoginToken(loginRequest)

    suspend fun getServerList() = apiService.getServersList()

    suspend fun getServerListFromRoom() = nordDao.getList()

    suspend fun deletePrevious() = nordDao.deleteAll()

    suspend fun insert(toDoList: ArrayList<ServersModelItem>) {
        nordDao.insert(toDoList)
    }
}