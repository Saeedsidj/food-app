package mobin.shabanifar.foodpart.data.database.category.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import mobin.shabanifar.foodpart.data.database.food.FoodEntity

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFood(subCategoryList: List<FoodEntity>)
    @Transaction
    @Query("SELECT * FROM food WHERE categoryId=:categoryId")
    fun observeFoodsWithCategory(categoryId: String): Flow<List<FoodEntity>>
}
