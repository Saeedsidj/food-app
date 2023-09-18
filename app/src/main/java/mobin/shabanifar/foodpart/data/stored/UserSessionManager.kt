package mobin.shabanifar.foodpart.data.stored

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import mobin.shabanifar.foodpart.utils.SESSION_AUTH_DATA
import mobin.shabanifar.foodpart.utils.USER_IMAGE_DATA
import mobin.shabanifar.foodpart.utils.USER_NAME_DATA
import mobin.shabanifar.foodpart.utils.USER_TOKEN_DATA
import javax.inject.Inject

class UserSessionManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SESSION_AUTH_DATA, Context.MODE_PRIVATE)
    }

    companion object {
        private const val USER_TOKEN_KEY = USER_TOKEN_DATA
        private const val USER_NAME_KEY = USER_NAME_DATA
        private const val USER_IMAGE_KEY = USER_IMAGE_DATA
    }
    //Save User Information
    fun saveUserInfo(token: String, userName: String, userImage: String) {
        sharedPreferences.edit().putString(USER_TOKEN_KEY, token).apply()
        sharedPreferences.edit().putString(USER_NAME_KEY, userName).apply()
        sharedPreferences.edit().putString(USER_IMAGE_KEY, userImage).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(USER_TOKEN_KEY, null)
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(USER_NAME_KEY, null)
    }

    fun getUserImage(): String? {
        return sharedPreferences.getString(USER_IMAGE_KEY, null)
    }
    //Clear User Information when logged out
    fun clearUserInfo() {
        sharedPreferences.edit().remove(USER_TOKEN_KEY).apply()
        sharedPreferences.edit().remove(USER_NAME_KEY).apply()
        sharedPreferences.edit().remove(USER_IMAGE_KEY).apply()
    }
}
