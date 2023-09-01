package mobin.shabanifar.foodpart.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import mobin.shabanifar.foodpart.NavigationBottom

@Composable
fun WhatToCook() {
    Column {
        Text(text = NavigationBottom.Cook.name.toString())

    }

}