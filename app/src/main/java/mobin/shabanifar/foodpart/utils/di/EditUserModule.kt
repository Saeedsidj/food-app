package mobin.shabanifar.foodpart.utils.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobin.shabanifar.foodpart.data.network.EditUserApi
import mobin.shabanifar.foodpart.data.network.LoginApi
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object EditUserModule {
    @Provides
    fun provideEditUserApi(retrofit: Retrofit): EditUserApi {
        return retrofit.create(EditUserApi::class.java)
    }

}