package com.nordsecurity.test


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Anita Kiran on 6/26/2022.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: MainRepository,
    private val networkHelper: NetworkHelper) : ViewModel()  {

    private var _loginResponse = MutableLiveData<Resource<LoginResponse>>()
    val loginToken: LiveData<Resource<LoginResponse>> get() = _loginResponse


    fun getLoginToken(loginRequest: LoginRequest) {
        try {
            if(networkHelper.isNetworkAvailable()) {
                viewModelScope.launch(Dispatchers.IO) {
                    repo.getLoginToken(loginRequest).let { response ->
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                _loginResponse.postValue(Resource.success(response.body()))
                            } else
                                _loginResponse.postValue(Resource.error(response.errorBody().toString(), null))
                        }
                    }
                }
            } else
                _loginResponse.postValue(Resource.error(AppConstants.INTERNET_ERROR, null))
        } catch (e: Throwable) {
            _loginResponse.postValue(Resource.error(AppConstants.SERVER_ERROR, null))
        }
    }
}