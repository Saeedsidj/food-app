package mobin.shabanifar.foodpart.data.database.food

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")
data class FoodEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo("categoryId")
    val categoryId: String,
    val name: String,
    val image: String,
    val cookTime: Int?,
    val readyTime: Int?
)