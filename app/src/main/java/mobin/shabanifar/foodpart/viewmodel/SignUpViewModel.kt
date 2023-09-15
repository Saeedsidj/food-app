package mobin.shabanifar.foodpart.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
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
import mobin.shabanifar.foodpart.data.models.sign_up.SignUpBody
import mobin.shabanifar.foodpart.data.models.sign_up.SignUpResponse
import mobin.shabanifar.foodpart.data.network.SignUpApi
import mobin.shabanifar.foodpart.utils.safeApi
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpApi: SignUpApi,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _signUpResult = MutableStateFlow<Result>(Result.Idle)
    val signUpResult: SharedFlow<Result> = _signUpResult.asSharedFlow()

    private val _signUpResponse = MutableStateFlow<SignUpResponse.Data>(SignUpResponse.Data("","",""))
    val signUpResponse: StateFlow<SignUpResponse.Data> =
        _signUpResponse.asStateFlow()

    private val _signUpBody = MutableStateFlow<SignUpBody>(SignUpBody("",""))
    val signUpBody: StateFlow<SignUpBody> =
        _signUpBody.asStateFlow()

    fun postUserSignUp(signUpBody:SignUpBody) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                    Log.d("OKHTTP",_signUpResponse.toString())
                    signUpApi.postUserSignUp(signUpBody)
                },
                onDataReady = {
                    Log.d("OKHTTP",_signUpResponse.toString())
                    _signUpResponse.value = it.data
                }
            ).collect(_signUpResult)
        }
    }

}