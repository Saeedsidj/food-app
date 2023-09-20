package mobin.shabanifar.foodpart.data.database.category.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mobin.shabanifar.foodpart.data.database.category.CategoryEntity

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategory(dataList: List<CategoryEntity>)

    @Query("SELECT * FROM category")
    fun observeAllCategory(): Flow<List<CategoryEntity>>

}