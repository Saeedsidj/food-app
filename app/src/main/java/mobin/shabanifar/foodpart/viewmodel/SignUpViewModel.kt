package mobin.shabanifar.foodpart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import mobin.shabanifar.foodpart.data.models.Result
import mobin.shabanifar.foodpart.data.models.sign_up.SignUpBody
import mobin.shabanifar.foodpart.data.network.SignUpApi
import mobin.shabanifar.foodpart.utils.safeApi
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpApi: SignUpApi,
) : ViewModel() {

    private val _signUpResult = MutableStateFlow<Result>(Result.Idle)
    val signUpResult: SharedFlow<Result> = _signUpResult.asSharedFlow()
    fun postUserSignUp(signUpBody: SignUpBody) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                    signUpApi.postUserSignUp(signUpBody)
                }
            ) {
            }.collect(_signUpResult)
        }
    }
}