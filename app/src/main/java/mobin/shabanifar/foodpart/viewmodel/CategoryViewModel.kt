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
import mobin.shabanifar.foodpart.data.network.CategoryApi
import javax.inject.Inject
import mobin.shabanifar.foodpart.data.models.Result
import mobin.shabanifar.foodpart.data.models.category_response.CategoryResponse
import mobin.shabanifar.foodpart.data.models.category_response.SubCategory
import mobin.shabanifar.foodpart.data.models.food_response.FoodResponse
import mobin.shabanifar.foodpart.utils.safeApi

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
    val subCategoryList : StateFlow<List<SubCategory>?> =  _subCategoryList.asStateFlow()

    private val _selectedCategoryIndex = MutableStateFlow<Int>(0)
     val selectedCategoryIndex : StateFlow<Int> = _selectedCategoryIndex.asStateFlow()

    private val _selectedSubCategoryIndex = MutableStateFlow<Int>(0)
    val selectedSubCategoryIndex : StateFlow<Int> = _selectedSubCategoryIndex.asStateFlow()
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

    private fun getFoodApi(id:String) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = { categoryApi.getFood(id) },
                onDataReady = { _foodData.value = it }
            ).collect(_foodResult)
        }
    }

    fun setDataOfSelectedCategory(categoryId:String){
        viewModelScope.launch(Dispatchers.IO){
            val categoryById = _categoryData.value?.data?.find {
                it.id == categoryId
            }
            _subCategoryList.emit(categoryById?.subCategories)

        }
    }

    fun updateCategorySelectedIndex(index:Int){
        viewModelScope.launch(Dispatchers.IO){
            _selectedCategoryIndex.emit(index)
        }
    }
    fun updateSubCategorySelectedIndex(index:Int){
        viewModelScope.launch(Dispatchers.IO){
            _selectedSubCategoryIndex.emit(index)
        }
    }

}