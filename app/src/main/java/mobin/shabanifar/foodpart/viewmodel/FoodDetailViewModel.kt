package mobin.shabanifar.foodpart.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import mobin.shabanifar.foodpart.R
import mobin.shabanifar.foodpart.data.database.bookMark.BookMarkEntity
import mobin.shabanifar.foodpart.data.database.bookMark.dao.BookMarkDao
import mobin.shabanifar.foodpart.data.models.Result
import mobin.shabanifar.foodpart.data.models.food_Detail.FoodDetailResponse
import mobin.shabanifar.foodpart.data.models.food_Detail.Meal
import mobin.shabanifar.foodpart.data.models.food_Detail.ReportBody
import mobin.shabanifar.foodpart.data.models.food_Detail.moreFoodById.Data
import mobin.shabanifar.foodpart.data.models.food_Detail.moreFoodById.MoreFoodById
import mobin.shabanifar.foodpart.data.models.food_response.FoodData
import mobin.shabanifar.foodpart.data.models.food_response.FoodResponse
import mobin.shabanifar.foodpart.data.network.FoodDetailAPI
import mobin.shabanifar.foodpart.data.stored.UserSessionManager
import mobin.shabanifar.foodpart.ui.theme.green
import mobin.shabanifar.foodpart.ui.theme.red
import mobin.shabanifar.foodpart.ui.theme.yellow
import mobin.shabanifar.foodpart.utils.safeApi
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val foodDetailAPI: FoodDetailAPI,
    private val savedStateHandle: SavedStateHandle,
    private val userSessionManager: UserSessionManager,
    private val bookMarkDao: BookMarkDao
):ViewModel(){

    private val _foodDetailData=MutableStateFlow<FoodDetailResponse?>(null)
    val foodDetailData:StateFlow<FoodDetailResponse?> = _foodDetailData.asStateFlow()

    private val _moreFood= MutableStateFlow<MoreFoodById?>(null)
    val moreFood =_moreFood.asStateFlow()

    private val _foodsByMeal = MutableStateFlow<MoreFoodById?>(null)
    val foodsByMeal= _foodsByMeal.asStateFlow()

    val isLoggedIn=userSessionManager.tokenFlow

    private val _foodDetailResult=MutableStateFlow<Result>(Result.Idle)
    val foodDetailResult : SharedFlow<Result> = _foodDetailResult.asSharedFlow()

    private val _mealsList=MutableStateFlow<Meal?>(null)
    val mealsList:StateFlow<Meal?> = _mealsList.asStateFlow()

   val foodId:String get() = savedStateHandle.get<String>("foodId").orEmpty()
    val mealId:String get()=savedStateHandle.get<String>("mealId").orEmpty()
    init {
        getFoodDetailApi(foodId)
        getTabTitle()
        getFoodsByMeal(mealId)
    }
     private fun getFoodDetailApi(foodId:String){
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                    foodDetailAPI.getFoodDetail(foodId)
                       },
                onDataReady = {
                    _foodDetailData.value=it
                    getMoreFood(it.additionalInfo.similarFoods?.joinToString(",") ?: "")
                }
            ).collect(_foodDetailResult)
        }
    }
    private fun getMoreFood(ids:String){
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = { foodDetailAPI.getMoreFood(ids) },
                onDataReady ={
                    _moreFood.value=it
                }
            ).collect(_foodDetailResult)
        }
    }
    private fun getFoodsByMeal(mealId:String){
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = { foodDetailAPI.getFoodsByMeal(mealId)},
                onDataReady = {
                    _foodsByMeal.value=it
                }
            ).collect(_foodDetailResult)
        }
    }
    fun getMeals(): List<Meal> {
       return _foodDetailData.value?.additionalInfo?.meals ?: emptyList()

    }
    fun getDifficultyColor():Color{
        return when(_foodDetailData.value?.data?.difficulty){
            "xajip1pes5ljs3i" -> red
            "yl9nm3vppgzco0g" -> yellow
            "ppqrincaaop4cpp" -> green
            else -> Color.Transparent
        }
    }
    fun getDifficultyName():String{
        return when(_foodDetailData.value?.data?.difficulty){
            _foodDetailData.value?.additionalInfo?.difficulty?.id -> _foodDetailData.value?.additionalInfo?.difficulty?.name ?: ""
            else -> ""
        }
    }

    fun getTabTitle():List<String>{
        val tabTitle= mutableListOf<String>("مواد اولیه","طرز تهیه")
        if (!foodDetailData.value?.data?.point.isNullOrEmpty())
            tabTitle.add("اطلاعات بیشتر")
        return tabTitle
    }

    fun getCount():String{
        return foodDetailData.value?.data?.count ?: ""
    }
    fun postReport(id:String,txtReport:String){
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                       foodDetailAPI.postReport(id, ReportBody(txtReport))
                },
                onDataReady = {
                    Log.d("API","its Sent")
                }
            ).collect(_foodDetailResult)
        }
    }
    fun addToBookMark(id: String,token: String){
        viewModelScope.launch(Dispatchers.IO) {
            bookMarkDao.insert(BookMarkEntity(foodId = id, token =token))
        }
    }
}
