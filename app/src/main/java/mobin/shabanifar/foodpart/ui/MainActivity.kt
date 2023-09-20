package mobin.shabanifar.foodpart.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import mobin.shabanifar.foodpart.ui.screens.CategoryScreen
import mobin.shabanifar.foodpart.ui.screens.SearchScreen
import mobin.shabanifar.foodpart.ui.screens.ShowFoodByAttributesScreen
import mobin.shabanifar.foodpart.ui.screens.foodDetail.FoodDetail
import mobin.shabanifar.foodpart.ui.screens.foodDetail.ShowPhoto
import mobin.shabanifar.foodpart.ui.screens.register.LoginScreen
import mobin.shabanifar.foodpart.ui.screens.register.ProfileScreen
import mobin.shabanifar.foodpart.ui.screens.register.SignUpScreen
import mobin.shabanifar.foodpart.ui.screens.what_to_cook.WhatToCookFormScreen
import mobin.shabanifar.foodpart.ui.screens.what_to_cook.WhatToCookListScreen
import mobin.shabanifar.foodpart.ui.theme.FoodPartTheme
import mobin.shabanifar.foodpart.utils.HOW_MUCH_TIME_HAVE
import mobin.shabanifar.foodpart.utils.LEVEL
import mobin.shabanifar.foodpart.utils.NavigationBottom
import mobin.shabanifar.foodpart.utils.WHAT_DO_YOU_HAVE
import mobin.shabanifar.foodpart.utils.items
import mobin.shabanifar.foodpart.utils.mainRoute

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            FoodPartTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                var isLogin by rememberSaveable { mutableStateOf(false) }
                Scaffold(bottomBar = {
                    if (currentDestination?.route in mainRoute) {
                        BottomNavigation(
                            backgroundColor = MaterialTheme.colors.secondary,
                            modifier = Modifier
                                .clip(
                                    shape = RoundedCornerShape(
                                        topStart = 28.dp, topEnd = 28.dp
                                    )
                                )
                                .height(80.dp)
                        ) {
                            items.forEach { screen ->
                                BottomNavigationItem(
                                    modifier = Modifier.padding(8.dp),
                                    label = {
                                        Text(
                                            text = getString(screen.name),
                                            style = MaterialTheme.typography.subtitle1,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    },
                                    selected = currentDestination?.hierarchy?.any {
                                        it.route == screen.route
                                    } == true,
                                    onClick = {
                                        val currentRoute = currentDestination?.route
                                        if (currentRoute != screen.route) {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                restoreState = true
                                            }
                                        }

                                    },
                                    icon = {
                                        Icon(
                                            painterResource(id = screen.icon!!),
                                            contentDescription = ""
                                        )
                                    },
                                    selectedContentColor = MaterialTheme.colors.primary,
                                    unselectedContentColor = MaterialTheme.colors.onBackground,
                                )
                            }
                        }
                    }
                }) {
                    NavHost(
                        navController = navController,
                        startDestination = NavigationBottom.Category.route,
                        Modifier.padding(it)
                    ) {
                        composable(
                            route = NavigationBottom.FoodPhoto.route,
                            arguments = listOf(
                                navArgument("imageUrl"){
                                    type= NavType.StringType
                                }
                            )
                        ) {navBackStackEntry->
                           val imageUrl= navBackStackEntry.arguments?.getString("imageUrl")
                            ShowPhoto(imageUrl?:""){
                                navController.navigateUp()
                            }
                            Log.d("APi",imageUrl?:"")
                        }
                        composable(
                            NavigationBottom.Category.route
                        ) {
                            CategoryScreen(navToDetail = { id:String ->
                                navController.navigate(
                                    NavigationBottom.FoodDetail.creteRout(id)
                                )
                            })
                        }
                        composable(
                            route = NavigationBottom.FoodDetail.route,
                            arguments = listOf(
                                navArgument("foodId"){
                                    type= NavType.StringType
                                    defaultValue=""
                                    nullable=false
                                }
                            )
                            ){
                            FoodDetail(){imageUrl->
                                navController.navigate(
                                    NavigationBottom.FoodPhoto.creteRout(imageUrl)
                                )
                                NavigationBottom.FoodPhoto.creteRout(imageUrl)
                                Log.d("API",imageUrl)
                            }
                        }
                        composable(NavigationBottom.Cook.route) {
                            WhatToCookFormScreen(
                                navigateToWTCList = { whatDoYouHave, howMuchTimeHave, level ->
                                navController.navigate(
                                    NavigationBottom.WhatToCook.createRoute(
                                        whatDoYouHave, howMuchTimeHave, level
                                    )
                                )
                            })
                        }
                        composable(NavigationBottom.Search.route) {
                            SearchScreen(navToDetail = { degree: Int, name: String, time: Int, image: Int ->
                                navController.navigate("foodDetail/$degree/$name/$time/$image") {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            })
                        }
                        composable(NavigationBottom.Profile.route) {
                            ProfileScreen(
                                navigateToProfileSignIn = {
                                    navController.navigate(NavigationBottom.SignUp.route)
                                }
                            )
                        }
                        composable(NavigationBottom.SignUp.route) {
                            SignUpScreen(navigateToProfile = {
                                navController.popBackStack()
                            }, navigateToProfileLogin = {
                                navController.navigate(NavigationBottom.Login.route)
                            })
                        }
                        composable(NavigationBottom.Login.route) {
                            LoginScreen(navigateToProfileSignIn = {
                                navController.navigate(NavigationBottom.SignUp.route) {
                                    popUpTo(NavigationBottom.Profile.route)
                                }
                            }, navigateToProfile = {
                                navController.navigate(NavigationBottom.Profile.route)
                            })
                        }
                        composable(route = NavigationBottom.WhatToCook.route,
                            arguments = listOf(navArgument(WHAT_DO_YOU_HAVE) {
                                type = NavType.StringType
                                nullable = true
                            }, navArgument(HOW_MUCH_TIME_HAVE) {
                                type = NavType.StringType
                                nullable = true
                            }, navArgument(LEVEL) {
                                type = NavType.StringType
                                nullable = true
                            })
                        ) { backStackEntry ->
                            val whatDoYouHave =
                                backStackEntry.arguments?.getString(WHAT_DO_YOU_HAVE) ?: ""

                            val howMuchTimeHave =
                                backStackEntry.arguments?.getString(HOW_MUCH_TIME_HAVE) ?: ""

                            val level = backStackEntry.arguments?.getString(LEVEL) ?: ""

                            WhatToCookListScreen(
                                whatDoYouHave,
                                howMuchTimeHave,
                                level,
                                navToDetail = { degree: Int, name: String, time: Int, image: Int ->
                                    navController.navigate("foodDetail/$degree/$name/$time/$image") {
                                        popUpTo(NavigationBottom.Profile.route) {
                                            inclusive = true
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                navigateToWTCForm = {
                                    navController.popBackStack()
                                })
                        }
                        composable(
                            route = NavigationBottom.ShowFoodByAttributes.route,
                            arguments = listOf(navArgument("title") {
                                type = NavType.StringType
                            })
                        ) { entry ->
                            val topTitle = entry.arguments?.getString("title")!!
                            ShowFoodByAttributesScreen(topTitle = topTitle,
                                navController = navController,
                                navToDetail = { degree: Int, name: String, time: Int, image: Int ->
                                    navController.navigate("foodDetail/$degree/$name/$time/$image") {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}

