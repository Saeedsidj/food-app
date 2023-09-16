package mobin.shabanifar.foodpart.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mobin.shabanifar.foodpart.data.network.CategoryApi
import javax.inject.Inject
@HiltViewModel
class WhatToCookFormViewModel @Inject constructor(
    private val categoryApi: CategoryApi,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

}