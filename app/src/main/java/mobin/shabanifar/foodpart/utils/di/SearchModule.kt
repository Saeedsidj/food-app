package mobin.shabanifar.foodpart.utils.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobin.shabanifar.foodpart.data.network.SearchApi
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {
    @Provides
    fun provideCategoryApi(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

}