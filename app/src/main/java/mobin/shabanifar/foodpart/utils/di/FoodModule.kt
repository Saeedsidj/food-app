package mobin.shabanifar.foodpart.utils.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobin.shabanifar.foodpart.data.database.AppDatabase
import mobin.shabanifar.foodpart.data.database.category.dao.FoodDao

@Module
@InstallIn(SingletonComponent::class)
class FoodModule {
    @Provides
    fun provideFoodDao(database: AppDatabase): FoodDao {
        return database.foodDao()
    }
}