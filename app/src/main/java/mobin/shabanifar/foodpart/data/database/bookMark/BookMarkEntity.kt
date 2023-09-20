package mobin.shabanifar.foodpart.data.database.bookMark

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookMarkEntity")
data class BookMarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int =0,
    val foodId:String,
    val token:String
)
