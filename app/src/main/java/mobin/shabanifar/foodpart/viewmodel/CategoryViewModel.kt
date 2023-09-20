package mobin.shabanifar.foodpart.viewmodel

import android.util.Log
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
import mobin.shabanifar.foodpart.data.database.category.CategoryEntity
import mobin.shabanifar.foodpart.data.database.category.SubCategoryEntity
import mobin.shabanifar.foodpart.data.database.category.dao.CategoryDao
import mobin.shabanifar.foodpart.data.database.category.dao.SubCategoryDao
import mobin.shabanifar.foodpart.data.database.food.FoodDao
import mobin.shabanifar.foodpart.data.database.food.FoodEntity
import mobin.shabanifar.foodpart.data.models.Result
import mobin.shabanifar.foodpart.data.network.CategoryApi
import mobin.shabanifar.foodpart.utils.safeApi
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryApi: CategoryApi,
    private val categoryDao: CategoryDao,
    private val subCategoryDao: SubCategoryDao,
    private val foodDao: FoodDao
) : ViewModel() {

    private val _foodResult = MutableStateFlow<Result>(Result.Idle)
    val foodResult: SharedFlow<Result> = _foodResult.asSharedFlow()

    private val _categoryResult = MutableStateFlow<Result>(Result.Idle)
    val categoryResult: SharedFlow<Result> = _categoryResult.asSharedFlow()

    private val _selectedCategoryId = MutableStateFlow("")
    val selectedCategoryId: StateFlow<String> = _selectedCategoryId.asStateFlow()

    private val _selectedSubCategoryId = MutableStateFlow("")
    val selectedSubCategoryId: StateFlow<String> = _selectedSubCategoryId.asStateFlow()

    //////////////////////////////////////////////////////////////////////
    private val _categoryList = MutableStateFlow<List<CategoryEntity>?>(emptyList())
    val categoryList: StateFlow<List<CategoryEntity>?> = _categoryList.asStateFlow()

    private val _subList = MutableStateFlow<List<SubCategoryEntity>?>(emptyList())
    val subList: StateFlow<List<SubCategoryEntity>?> = _subList.asStateFlow()

    private val _foodList = MutableStateFlow<List<FoodEntity>?>(emptyList())
    val foodList: StateFlow<List<FoodEntity>?> = _foodList.asStateFlow()

    //////////////////////////////////////////////////////////////////////

    init {
        observeCategory()
        getCategoryApi()
        Log.d("checkTimeDo", "init ")
        Log.d("checkTimeDo", "${_categoryResult.value} ")
    }


    private fun observeCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            categoryDao.observeAllCategory().collect {
                _categoryList.emit(it)
                Log.d("debugMode", "observeCategory ")
            }
        }
    }

    fun observeSubCategoryByCategoryId(categoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            subCategoryDao.getSubCategoriesByCategoryId(categoryId).collect {
                _subList.emit(it)
                Log.d("debugMode", "observeSubCategory ")
            }
        }
    }

    fun observeFoodByCategoryId(categoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            foodDao.observeFoodsWithCategory(categoryId).collect {foods->
                _foodList.emit(foods)
                Log.d("debugMode", "${_foodList.value} ")
            }
        }
    }

    fun getCategoryApi() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = { categoryApi.getCategory() },
                onDataReady = { category ->

                    val categoryEntity = category.data.map { categoryData ->
                        categoryData.toCategoryEntity()
                    }
                    storedCategory(categoryEntity)

                    val subCategoryEntity = category.data.flatMap { categoryData ->
                        categoryData.subCategories?.map { subCategory ->
                            subCategory.toSubCategoryEntity(categoryData.id)
                        } ?: emptyList()
                    }
                    storedSubCategory(subCategoryEntity)

                }
            ).collect(_categoryResult)
        }
    }

    fun getFoodApi(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = { categoryApi.getFood(subOrCategoryId = id) },
                onDataReady = { foodResponse ->
                    val foodEntity = foodResponse.data.map { foodData ->
                        foodData.toFoodEntity()
                    }
                    storedFood(foodEntity)
                }
            ).collect(_foodResult)
        }
    }

    private fun storedCategory(categoryList: List<CategoryEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryDao.insertAllCategory(categoryList)
            Log.d("debugMode", "storedCategory ")
        }
    }

    private fun storedSubCategory(subCategoryList: List<SubCategoryEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            subCategoryDao.insertAllSubCategory(subCategoryList)
            Log.d("debugMode", "storedSubCategory ")
        }
    }


    private fun storedFood(foodList: List<FoodEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            foodDao.insertAllFood(foodList)
            Log.d("debugMode", "storedFood ")
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