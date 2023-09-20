package mobin.shabanifar.foodpart.data.database.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sub_category")
data class SubCategoryEntity(
    @PrimaryKey val id: String,
    val image: String,
    val name: String,
    @ColumnInfo(name = "category_id")
    val categoryId: String
)
