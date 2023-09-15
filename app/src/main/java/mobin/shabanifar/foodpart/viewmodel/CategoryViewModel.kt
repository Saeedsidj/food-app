package mobin.shabanifar.foodpart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mobin.shabanifar.foodpart.data.models.Result
import mobin.shabanifar.foodpart.data.models.category_response.CategoryData
import mobin.shabanifar.foodpart.data.models.category_response.CategoryResponse
import mobin.shabanifar.foodpart.data.models.category_response.SubCategory
import mobin.shabanifar.foodpart.data.models.food_response.FoodData
import mobin.shabanifar.foodpart.data.models.food_response.FoodResponse
import mobin.shabanifar.foodpart.data.network.CategoryApi
import mobin.shabanifar.foodpart.utils.safeApi
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryApi: CategoryApi,
) : ViewModel() {

    private val _categoryResult = MutableStateFlow<Result>(Result.Idle)
    val categoryResult: SharedFlow<Result> = _categoryResult.asSharedFlow()

    private val _foodResult = MutableStateFlow<Result>(Result.Idle)
    val foodResult: SharedFlow<Result> = _foodResult.asSharedFlow()

    private val _foodData = MutableStateFlow<FoodResponse?>(null)
    val foodData: StateFlow<FoodResponse?> = _foodData.asStateFlow()

    private val _categoryData = MutableStateFlow<CategoryResponse?>(null)
    val categoryData: StateFlow<CategoryResponse?> = _categoryData.asStateFlow()

    private val _subCategoryList = MutableStateFlow<List<SubCategory>?>(emptyList())
    val subCategoryList: StateFlow<List<SubCategory>?> = _subCategoryList.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow("")
    val selectedCategoryId: StateFlow<String> = _selectedCategoryId.asStateFlow()

    private val _selectedSubCategoryId = MutableStateFlow("")
    val selectedSubCategoryId: StateFlow<String> = _selectedSubCategoryId.asStateFlow()

    init {
        getCategoryApi()
    }

    private fun getCategoryApi() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = { categoryApi.getCategory() },
                onDataReady = { _categoryData.value = it }
            ).collect(_categoryResult)
        }
    }


    fun getCategoryItems(categoryData: CategoryResponse?): List<CategoryData> {
        return categoryData?.data ?: emptyList()
    }

    fun getSubCategoryItems(subCategoryList: List<SubCategory>?): List<SubCategory> {
        return subCategoryList ?: emptyList()
    }

    fun getFoodApi(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = { categoryApi.getFood(subOrCategoryId = id) },
                onDataReady = { _foodData.value = it }
            ).collect(_foodResult)
        }
    }

    fun getFoodItems(foodData: FoodResponse?): List<FoodData> {
        return foodData?.data ?: emptyList()
    }

    fun isFoodFound(foodData: FoodResponse?): Boolean {
        return getFoodItems(foodData).isNotEmpty()
    }


    fun setDataOfSelectedCategory(categoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val categoryById = _categoryData.value?.data?.find {
                it.id == categoryId
            }
            _subCategoryList.emit(categoryById?.subCategories)
        }
    }

    fun updateCategorySelectedId(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedCategoryId.emit(id)
        }
    }

    fun updateSubCategorySelectedId(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedSubCategoryId.emit(id)
        }
    }

}