package mobin.shabanifar.foodpart

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mobin.shabanifar.foodpart.screens.Category
import mobin.shabanifar.foodpart.screens.Category
import mobin.shabanifar.foodpart.screens.Profile
import mobin.shabanifar.foodpart.screens.Search
import mobin.shabanifar.foodpart.screens.WhatToCook
import mobin.shabanifar.foodpart.ui.theme.FoodPartTheme
import mobin.shabanifar.foodpart.ui.theme.background


class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            FoodPartTheme {
                val scaffoldState = rememberScaffoldState()
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                Scaffold(

                    bottomBar = {
                        if (currentDestination?.route in mainRoute){
                            BottomNavigation(
                                backgroundColor = MaterialTheme.colors.secondary, modifier = Modifier
                                    .clip(
                                        shape = RoundedCornerShape(
                                            topStart = 28.dp, topEnd = 28.dp
                                        )
                                    )
                                    .height(80.dp)
                            ) {
                                items.forEach { B ->
                                    BottomNavigationItem(
                                        modifier = Modifier.padding(8.dp),
                                        label = {
                                            Text(
                                                text = getString(B.name),
                                                style = MaterialTheme.typography.subtitle1,
                                            )
                                        },
                                        selected = currentDestination?.hierarchy?.any {
                                            it.route == B.route
                                        } == true,
                                        onClick = {
                                            navController.navigate(B.route) {
                                                launchSingleTop = true
                                                popUpTo(NavigationBottom.Category.route){
                                                    saveState=true
                                                }
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                painterResource(id = B.icon),
                                                contentDescription = "",
                                                //  tint = MaterialTheme.colors.onBackground
                                            )
                                        },
                                        selectedContentColor = Color.Red,
                                        unselectedContentColor = MaterialTheme.colors.onBackground,
                                    )
                                }
                            }

                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = NavigationBottom.Category.route,
                        Modifier.padding(it)
                    ) {
                        composable(NavigationBottom.Category.route) {
                            Category()
                        }
                        composable(NavigationBottom.Cook.route) {
                            WhatToCook()
                        }
                        composable(NavigationBottom.Search.route) {
                            Search()
                        }
                        composable(NavigationBottom.profile.route) {
                            Profile()
                        }
                    }
                }
            }
        }
    }
}

