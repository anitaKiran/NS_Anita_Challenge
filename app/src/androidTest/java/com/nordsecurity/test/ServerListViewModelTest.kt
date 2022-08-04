package com.nordsecurity.test

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.AndroidJUnitRunner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

/**
 * Created by Anita Kiran on 7/27/2022.
 */

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ServerListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var listViewModel: ServerListViewModel
    private lateinit var mainRepository: MainRepository
    private lateinit var networkHelper: NetworkHelper
    private lateinit var database: NordSecurityDatabase
    private lateinit var nordDao: NordDao

    @Mock
    lateinit var apiService: ApiService

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainRule = TestCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
         val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, NordSecurityDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        nordDao = database.getNordDao()
        networkHelper = NetworkHelper(context)
        mainRepository = MainRepository(apiService,nordDao)
        listViewModel = ServerListViewModel(mainRepository, networkHelper)
    }

    @Test
    fun getServerListSuccess(){
        runBlocking {
            Mockito.`when`(mainRepository.getServerList()).thenReturn(Response.success(ArrayList<ServersModelItem>()))
            listViewModel.getServerListFromApi()
            val result = listViewModel.serverList.getOrAwaitValue()
            assert(result.data != null)
        }
    }

    @Test
    fun getServerListFailure(){
        runBlocking {
            Mockito.`when`(mainRepository.getServerList()).thenReturn(Response.success(ArrayList<ServersModelItem>()))
            listViewModel.getServerListFromApi()
            val result = listViewModel.serverList.getOrAwaitValue()
            assert(result.data?.size ==0)
        }
    }
}