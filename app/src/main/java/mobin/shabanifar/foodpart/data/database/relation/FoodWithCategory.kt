package mobin.shabanifar.foodpart.data.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import mobin.shabanifar.foodpart.data.database.category.CategoryEntity
import mobin.shabanifar.foodpart.data.database.food.FoodEntity

/*data class FoodWithCategory(
    @Embedded val category: CategoryEntity,
    @Relation(parentColumn = "id", entityColumn = "categoryId")
    val foods: List<FoodEntity>
)*/
