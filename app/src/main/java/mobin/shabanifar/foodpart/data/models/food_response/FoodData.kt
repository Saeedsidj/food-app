package mobin.shabanifar.foodpart.data.models.food_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import mobin.shabanifar.foodpart.data.database.food.FoodEntity

@JsonClass(generateAdapter = true)
data class FoodData(

    @Json(name = "id")
    val id: String,
    @Json(name = "categoryId")
    val categoryId: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "cookTime")
    val cookTime: Int?,
    @Json(name = "readyTime")
    val readyTime: Int?
){
    fun toFoodEntity() = FoodEntity(
        id = id,
        categoryId = categoryId,
        name = name,
        image = image,
        cookTime = cookTime,
        readyTime = readyTime
    )
}