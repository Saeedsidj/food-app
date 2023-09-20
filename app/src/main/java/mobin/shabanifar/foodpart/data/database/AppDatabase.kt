package mobin.shabanifar.foodpart.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import mobin.shabanifar.foodpart.data.database.category.CategoryEntity
import mobin.shabanifar.foodpart.data.database.category.SubCategoryEntity
import mobin.shabanifar.foodpart.data.database.category.dao.CategoryDao
import mobin.shabanifar.foodpart.data.database.food.FoodDao
import mobin.shabanifar.foodpart.data.database.category.dao.SubCategoryDao
import mobin.shabanifar.foodpart.data.database.food.FoodEntity

@Database(
    entities = [CategoryEntity::class, SubCategoryEntity::class, FoodEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun subCategoryDao(): SubCategoryDao
    abstract fun foodDao(): FoodDao
}

