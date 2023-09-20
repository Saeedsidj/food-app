package mobin.shabanifar.foodpart.data.database.category.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mobin.shabanifar.foodpart.data.database.category.SubCategoryEntity

@Dao
interface SubCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSubCategory(subCategoryList: List<SubCategoryEntity>)

    @Query("SELECT * FROM sub_category WHERE category_id = :categoryId")
    fun getSubCategoriesByCategoryId(categoryId: String): Flow<List<SubCategoryEntity>>

}