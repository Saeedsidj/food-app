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
import mobin.shabanifar.foodpart.data.models.search.SearchResponse
import mobin.shabanifar.foodpart.data.network.SearchApi
import mobin.shabanifar.foodpart.utils.safeApi
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchApi: SearchApi,
) : ViewModel() {

    private val _searchResult = MutableStateFlow<Result>(Result.Idle)
    val searchResult: SharedFlow<Result> = _searchResult.asSharedFlow()

    private val _searchedFood = MutableStateFlow<SearchResponse?>(null)
    val searchedFood: StateFlow<SearchResponse?> = _searchedFood.asStateFlow()

    private val _isFoodFound = MutableStateFlow<Boolean?>(null)
    val isFoodFound: StateFlow<Boolean?> = _isFoodFound.asStateFlow()

    fun doSearch(searchedText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = { searchApi.getSearchedFoods(searchedText) },
                onDataReady = { _searchedFood.value = it }
            ).collect(_searchResult)
        }
    }

    fun isFoodFound(searchedFood: SearchResponse?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (searchedFood?.data == null) {
                _isFoodFound.emit(null)
            } else _isFoodFound.emit(searchedFood.data.isNotEmpty())
        }
    }

    fun resetIsFoodFound() {
        viewModelScope.launch(Dispatchers.IO) {
            _isFoodFound.emit(null)
        }
    }

}