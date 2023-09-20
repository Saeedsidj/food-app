package mobin.shabanifar.foodpart.data.database.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey
    //@ColumnInfo(name = "id")
    val id: String,
    val image: String,
    val name: String
)

