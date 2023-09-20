package mobin.shabanifar.foodpart.utils.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobin.shabanifar.foodpart.data.database.AppDatabase
import mobin.shabanifar.foodpart.data.database.category.dao.CategoryDao
import mobin.shabanifar.foodpart.data.database.category.dao.SubCategoryDao
import mobin.shabanifar.foodpart.data.network.CategoryApi
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object CategoryModule {
    @Provides
    fun provideCategoryApi(retrofit: Retrofit): CategoryApi {
        return retrofit.create(CategoryApi::class.java)
    }

    @Provides
    fun provideCategoryDaoDao(appDatabase: AppDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }

    @Provides
    fun provideSubCategoryDaoDao(appDatabase: AppDatabase): SubCategoryDao {
        return appDatabase.subCategoryDao()
    }

    /*@Provides
    fun provideCatSubTableDaoDao(appDatabase: AppDatabase): CatSubTableDao {
        return appDatabase.catSubTableDao()
    }*/
}