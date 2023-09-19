package mobin.shabanifar.foodpart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mobin.shabanifar.foodpart.data.models.Result
import mobin.shabanifar.foodpart.data.models.edit_profile.EditUserBody
import mobin.shabanifar.foodpart.data.models.edit_profile.EditUserResponse
import mobin.shabanifar.foodpart.data.network.EditUserApi
import mobin.shabanifar.foodpart.data.stored.UserSessionManager
import mobin.shabanifar.foodpart.utils.safeApi
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val editUserApi: EditUserApi, private val userSessionManager: UserSessionManager
) : ViewModel() {

    private val _editUserResult = MutableStateFlow<Result>(Result.Idle)
    val editUserResult: SharedFlow<Result> = _editUserResult.asSharedFlow()

    private val _editUserResponse = MutableStateFlow(EditUserResponse(EditUserResponse.AdditionalInfo(""),"" ))
    val editUserResponse: StateFlow<EditUserResponse> = _editUserResponse.asStateFlow()

    fun postEditUser(editUserBody: EditUserBody) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(call = {
                editUserApi.postEditUser(editUserBody)
            }, onDataReady = {
                _editUserResponse.value = it
            }).collect(_editUserResult)
        }
    }

    // fun for save data from saveUserInfo
    fun saveUserInfo(token: String, userName: String, userImage: String) {
        viewModelScope.launch {
            userSessionManager.saveUserInfo(token, userName, userImage)
        }
    }

    fun getUserName():String? {
        return userSessionManager.getUserName()
    }

    fun getUserImage():String? {
        return userSessionManager.getUserImage()
    }

    fun clearUserInfo() {
        userSessionManager.clearUserInfo()
    }

}