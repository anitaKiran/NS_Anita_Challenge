package com.nordsecurity.test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ServerListViewModel @Inject constructor(
    private val repo: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private var _serverList = MutableLiveData<Resource<ArrayList<ServersModelItem>>>()
    val serverList: LiveData<Resource<ArrayList<ServersModelItem>>> get() = _serverList

    init {
         getList()
    }

    fun getList() {
        // if internet is available then fetch the data from api else check if data is stored in db
        if (networkHelper.isNetworkAvailable()) {
            getServerListFromApi()
        } else {
            viewModelScope.launch() {
                _serverList.postValue(Resource.success(repo.getServerListFromRoom()) as Resource<ArrayList<ServersModelItem>>?)
            }
        }
    }


    fun getServerListFromApi() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repo.getServerList().let { response ->
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _serverList.postValue(Resource.success(response.body()))
                            response.body()?.let {
                                // delete previous data and insert new records
                                repo.deletePrevious()
                                repo.insert(it)
                            }
                        } else
                            _serverList.postValue(Resource.error(response.errorBody().toString(), null))
                    }
                }
            }
        } catch (e: Exception) {
            _serverList.postValue(Resource.error(AppConstants.SERVER_ERROR, null))
        }
    }
}