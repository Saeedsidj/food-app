package mobin.shabanifar.foodpart.utils.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobin.shabanifar.foodpart.data.database.AppDatabase
import mobin.shabanifar.foodpart.data.database.bookMark.dao.BookMarkDao
import mobin.shabanifar.foodpart.data.database.category.dao.CategoryDao

@Module
@InstallIn(SingletonComponent::class)
object BookMarkModule {
    @Provides
    fun provideBookMarkDao(appDatabase: AppDatabase): BookMarkDao {
        return appDatabase.bookMark()
    }
}