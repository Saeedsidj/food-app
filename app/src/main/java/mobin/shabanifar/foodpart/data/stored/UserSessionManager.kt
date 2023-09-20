package mobin.shabanifar.foodpart.data.stored

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import mobin.shabanifar.foodpart.utils.SESSION_AUTH_DATA
import mobin.shabanifar.foodpart.utils.USER_ID_DATA
import mobin.shabanifar.foodpart.utils.USER_IMAGE_DATA
import mobin.shabanifar.foodpart.utils.USER_NAME_DATA
import mobin.shabanifar.foodpart.utils.USER_TOKEN_DATA
import javax.inject.Inject

class UserSessionManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SESSION_AUTH_DATA, Context.MODE_PRIVATE)
    }
    private val _tokenFlow: MutableStateFlow<String?> = MutableStateFlow(getToken())
    val tokenFlow: Flow<String?> = _tokenFlow

    private val _userNameFlow: MutableStateFlow<String?> = MutableStateFlow(getUserName())
    val userNameFlow: Flow<String?> = _userNameFlow

    private val _userImageFlow: MutableStateFlow<String?> = MutableStateFlow(getUserImage())
    val userImageFlow: Flow<String?> = _userImageFlow

    private val _userIdFlow: MutableStateFlow<String?> = MutableStateFlow(getUserID())
    val userIdFlow: Flow<String?> = _userIdFlow

    companion object {
        private const val USER_TOKEN_KEY = USER_TOKEN_DATA
        private const val USER_NAME_KEY = USER_NAME_DATA
        private const val USER_IMAGE_KEY = USER_IMAGE_DATA
        private const val USER_ID_KEY = USER_ID_DATA
    }
    //Save User Information
    fun saveUserInfo(token: String, userName: String, userImage: String,userId: String) {
        sharedPreferences.edit().putString(USER_TOKEN_KEY, token).apply()
        sharedPreferences.edit().putString(USER_NAME_KEY, userName).apply()
        sharedPreferences.edit().putString(USER_IMAGE_KEY, userImage).apply()
        sharedPreferences.edit().putString(USER_ID_KEY, userId).apply()
        _userNameFlow.value = userName
        _tokenFlow.value = token
        _userImageFlow.value = userImage
        _userIdFlow.value = userId
    }
    //Edit User Information
    fun editUserToken(token: String) {
        sharedPreferences.edit().putString(USER_TOKEN_KEY, token).apply()
        _tokenFlow.value = token
    }
    fun editUsername(userName: String) {
        sharedPreferences.edit().putString(USER_NAME_KEY, userName).apply()
        _userNameFlow.value = userName
    }

    fun getToken(): String? {
        return sharedPreferences.getString(USER_TOKEN_KEY, null)
    }

    private fun getUserName(): String? {
        return sharedPreferences.getString(USER_NAME_KEY, null)
    }

    private fun getUserImage(): String? {
        return sharedPreferences.getString(USER_IMAGE_KEY, null)
    }
    private fun getUserID(): String? {
        return sharedPreferences.getString(USER_ID_KEY, null)
    }
    //Clear User Information when logged out
    fun clearUserInfo() {
        sharedPreferences.edit().remove(USER_TOKEN_KEY).apply()
        sharedPreferences.edit().remove(USER_NAME_KEY).apply()
        sharedPreferences.edit().remove(USER_IMAGE_KEY).apply()
        sharedPreferences.edit().remove(USER_ID_KEY).apply()
        _userNameFlow.value = null
        _tokenFlow.value = null
        _userImageFlow.value = null
        _userIdFlow.value = null
    }
}
