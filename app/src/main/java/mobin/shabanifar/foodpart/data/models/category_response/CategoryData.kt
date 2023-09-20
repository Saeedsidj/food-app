package mobin.shabanifar.foodpart.data.models.category_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import mobin.shabanifar.foodpart.data.database.category.CategoryEntity

@JsonClass(generateAdapter = true)
data class CategoryData(
    @Json(name = "id")
    val id: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "subCategories")
    val subCategories: List<SubCategory>?
){
    fun toCategoryEntity() = CategoryEntity(
        id = id,
        name = name,
        image = image
    )
}