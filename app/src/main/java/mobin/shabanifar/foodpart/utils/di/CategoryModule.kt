package mobin.shabanifar.foodpart.utils.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobin.shabanifar.foodpart.data.network.CategoryApi
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object CategoryModule {
    @Provides
    fun provideCategoryApi(retrofit: Retrofit): CategoryApi {
        return retrofit.create(CategoryApi::class.java)
    }
}