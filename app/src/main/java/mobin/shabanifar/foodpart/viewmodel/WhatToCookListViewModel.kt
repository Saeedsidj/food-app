package mobin.shabanifar.foodpart.viewmodel

import androidx.lifecycle.SavedStateHandle
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
import mobin.shabanifar.foodpart.data.models.what_to_cook_response.WhatToCookResponse
import mobin.shabanifar.foodpart.data.network.WhatToCookApi
import mobin.shabanifar.foodpart.utils.DIFFICULTY
import mobin.shabanifar.foodpart.utils.HOW_MUCH_TIME_HAVE
import mobin.shabanifar.foodpart.utils.INGREDIENTS
import mobin.shabanifar.foodpart.utils.LEVEL
import mobin.shabanifar.foodpart.utils.TIME_LIMIT
import mobin.shabanifar.foodpart.utils.WHAT_DO_YOU_HAVE
import mobin.shabanifar.foodpart.utils.safeApi
import javax.inject.Inject

@HiltViewModel
class WhatToCookListViewModel @Inject constructor(
    private val whatToCookApi: WhatToCookApi,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _whatToCookResult = MutableStateFlow<Result>(Result.Idle)
    val whatToCookResult: SharedFlow<Result> = _whatToCookResult.asSharedFlow()

    private val _whatToCookResponse = MutableStateFlow<List<WhatToCookResponse.Data>>(emptyList())
    val whatToCookResponse: StateFlow<List<WhatToCookResponse.Data>> =
        _whatToCookResponse.asStateFlow()

    private val whatDoYouHave: String
        get() = savedStateHandle.get<String>(WHAT_DO_YOU_HAVE).orEmpty()
    private val howMuchTimeHave: String
        get() = savedStateHandle.get<String>(HOW_MUCH_TIME_HAVE).orEmpty()
    private val level: String
        get() = savedStateHandle.get<String>(LEVEL).orEmpty()

    init {
        getWhatToCook(
            whatToCookQueries(
                ingredients = whatDoYouHave,
                timeLimit = howMuchTimeHave,
                difficulty = level
            )
        )
    }


    fun whatToCookQueries(
        ingredients: String,
        difficulty: String,
        timeLimit: String
    ): Map<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[INGREDIENTS] = ingredients
        queries[DIFFICULTY] = difficulty
        queries[TIME_LIMIT] = timeLimit
        return queries
    }

    fun getWhatToCook(queries: Map<String, String>) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApi(
                call = {
                    whatToCookApi.getWhatToCook(queries)
                }
            ) {
                _whatToCookResponse.value = it.data
            }.collect(_whatToCookResult)
        }
    }

}