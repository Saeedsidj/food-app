package mobin.shabanifar.foodpart.utils.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobin.shabanifar.foodpart.data.network.CategoryApi
import mobin.shabanifar.foodpart.data.network.WhatToCookApi
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object WhatToCookModule {
    @Provides
    fun provideWhatToCookApi(retrofit: Retrofit): WhatToCookApi {
        return retrofit.create(WhatToCookApi::class.java)
    }

}