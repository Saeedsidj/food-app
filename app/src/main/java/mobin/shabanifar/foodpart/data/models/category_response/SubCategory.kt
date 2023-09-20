package mobin.shabanifar.foodpart.data.models.category_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import mobin.shabanifar.foodpart.data.database.category.CategoryEntity
import mobin.shabanifar.foodpart.data.database.category.SubCategoryEntity

@JsonClass(generateAdapter = true)
data class SubCategory(
    @Json(name = "id")
    val id: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "name")
    val name: String
){
    fun toSubCategoryEntity(categoryId: String) = SubCategoryEntity(
        id = id,
        name = name,
        image = image,
        categoryId = categoryId
    )
}