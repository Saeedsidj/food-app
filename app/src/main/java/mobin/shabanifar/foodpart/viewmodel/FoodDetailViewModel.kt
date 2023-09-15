package mobin.shabanifar.foodpart.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mobin.shabanifar.foodpart.data.models.Result
import mobin.shabanifar.foodpart.data.models.food_Detail.FoodDetailResponse
import mobin.shabanifar.foodpart.data.models.food_Detail.Meal
import mobin.shabanifar.foodpart.data.network.FoodDetailAPI
import mobin.shabanifar.foodpart.ui.theme.green
import mobin.shabanifar.foodpart.ui.theme.red
import mobin.shabanifar.foodpart.ui.theme.yellow
import mobin.shabanifar.foodpart.utils.safeApi
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val foodDetailAPI: FoodDetailAPI
):ViewModel(){

    private val _foodDetailData=MutableStateFlow<FoodDetailResponse?>(null)
    val foodDetailData:StateFlow<FoodDetailResponse?> = _foodDetailData.asStateFlow()

    private val _moreFood= MutableStateFlow<List<FoodDetailResponse?>?>(emptyList())
    val moreFood =_moreFood.asStateFlow()

    private val _foodDetailResult=MutableStateFlow<Result>(Result.Idle)
    val foodDetailResult :StateFlow<Result> = _foodDetailResult.asStateFlow()

    private val _mealsList=MutableStateFlow<Meal?>(null)
    val mealsList:StateFlow<Meal?> = _mealsList.asStateFlow()

    init {
        getFoodDetailApi()
    }
    private fun getFoodDetailApi(){
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                    foodDetailAPI.getFoodDetail()
                       },
                onDataReady = {
                    _foodDetailData.value=it
                    getMoreFood(_foodDetailData.value?.additionalInfo?.similarFoods.orEmpty())
                }
            ).collect(_foodDetailResult)
        }
    }
    private fun getMoreFood(id:List<String>){
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = { foodDetailAPI.getMoreFood(id) },
                onDataReady ={
                    _moreFood.value=it
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


}
