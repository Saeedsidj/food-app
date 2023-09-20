package mobin.shabanifar.foodpart.data.database.bookMark.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mobin.shabanifar.foodpart.data.database.bookMark.BookMarkEntity

@Dao
interface BookMarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: BookMarkEntity)
    @Delete
    suspend fun delete(item: BookMarkEntity)
    @Query("SELECT * FROM bookMarkEntity")
    fun getAll(): Flow<List<BookMarkEntity>>
}