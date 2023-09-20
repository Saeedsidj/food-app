package mobin.shabanifar.foodpart.utils.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobin.shabanifar.foodpart.data.network.FoodDetailAPI
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object FoodDetailModule {
    @Provides
    fun provideFoodDetailApi(retrofit: Retrofit): FoodDetailAPI {
        return retrofit.create(FoodDetailAPI::class.java)
    }
}