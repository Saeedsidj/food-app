package mobin.shabanifar.foodpart

import android.annotation.SuppressLint
import android.os.Bundle
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import mobin.shabanifar.foodpart.screens.Category
import mobin.shabanifar.foodpart.screens.LoginScreen
import mobin.shabanifar.foodpart.screens.ProfileScreen
import mobin.shabanifar.foodpart.screens.Search
import mobin.shabanifar.foodpart.screens.ShowFoodByAttributes
import mobin.shabanifar.foodpart.screens.WhatToCook
import mobin.shabanifar.foodpart.screens.WhatToCookListScreen
import mobin.shabanifar.foodpart.screens.foodDetail.FoodDetail
import mobin.shabanifar.foodpart.screens.foodDetail.ShowPhoto
import mobin.shabanifar.foodpart.screens.signUpScreen
import mobin.shabanifar.foodpart.ui.theme.FoodPartTheme
import mobin.shabanifar.foodpart.utils.HOW_MUCH_TIME_HAVE
import mobin.shabanifar.foodpart.utils.LEVEL
import mobin.shabanifar.foodpart.utils.WHAT_DO_YOU_HAVE


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
                var userName by rememberSaveable { mutableStateOf("مهمان") }
                var isLogin by rememberSaveable { mutableStateOf(false) }
                Scaffold(
                    bottomBar = {
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
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination

                                items.forEach { screen ->
                                    BottomNavigationItem(
                                        modifier = Modifier.padding(8.dp),
                                        label = {
                                            Text(
                                                text = getString(screen.name),
                                                style = MaterialTheme.typography.subtitle1,
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
                        composable(NavigationBottom.FoodPhoto.route) {
                            ShowPhoto(navController = navController)
                        }
                        composable(
                            NavigationBottom.FoodDetail.route,
                            arguments = listOf(
                                navArgument("degree") {
                                    type = NavType.IntType
                                },
                                navArgument("name") {
                                    type = NavType.StringType
                                },
                                navArgument("time") {
                                    type = NavType.IntType
                                },
                                navArgument("image") {
                                    type = NavType.IntType
                                }
                            )
                        ) { entry ->
                            val degree = entry.arguments?.getInt("degree")!!
                            val name = entry.arguments?.getString("name")!!
                            val time = entry.arguments?.getInt("time")!!
                            val image = entry.arguments?.getInt("image")!!

                            FoodDetail(
                                degree,
                                name,
                                time,
                                image,
                                navController,
                                isLogin,
                                toAttributesScreen = { title: String ->
                                    navController.navigate("showFoodByAttributes/$title") {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                })
                        }
                        composable(
                            NavigationBottom.Category.route
                        ) {
                            Category(
                                navToDetail = { degree: Int, name: String, time: Int, image: Int ->
                                    navController.navigate("foodDetail/$degree/$name/$time/$image") {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                        composable(NavigationBottom.Cook.route) {
                            WhatToCook(
                                navigateToWTCList = { whatDoYouHave, howMuchTimeHave, level ->
                                    navController.navigate("whatToCookList?whatDoYouHave=$whatDoYouHave&howMuchTimeHave=$howMuchTimeHave&level=$level")
                                }
                            )
                        }
                        composable(NavigationBottom.Search.route) {
                            Search(
                                navToDetail = { degree: Int, name: String, time: Int, image: Int ->
                                    navController.navigate("foodDetail/$degree/$name/$time/$image") {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                        composable(NavigationBottom.Profile.route) {
                            ProfileScreen(
                                usernameSave = userName,
                                isLogin = isLogin,
                                navigateToProfileSignIn = {
                                    navController.navigate(NavigationBottom.SignUp.route)
                                },
                                changeLoginState = {
                                    isLogin = !isLogin
                                }
                            )
                        }
                        composable(NavigationBottom.SignUp.route) {
                            signUpScreen(
                                isLogin = { result ->
                                    isLogin = result
                                },
                                saveUserName = { username ->
                                    userName = username
                                },
                                navigateToProfile = {
                                    navController.popBackStack()
                                },
                                navigateToProfileLogin = {
                                    navController.navigate(NavigationBottom.Login.route) {
                                        popUpTo(NavigationBottom.Profile.route)
                                    }
                                }

                            )
                        }
                        composable(NavigationBottom.Login.route) {
                            LoginScreen(
                                navigateToProfileSignIn = {
                                    navController.navigate(NavigationBottom.SignUp.route) {
                                        popUpTo(NavigationBottom.Profile.route)
                                    }
                                },
                                navigateToProfile = {
                                    navController.popBackStack()
                                },
                                saveUserName = { username ->
                                    userName = username
                                },
                                isLogin = { result ->
                                    isLogin = result
                                }
                            )
                        }
                        composable(
                            route = NavigationBottom.WhatToCook.route,
                            arguments = listOf(
                                navArgument(WHAT_DO_YOU_HAVE) {
                                    type = NavType.StringType
                                    nullable = true
                                },
                                navArgument(HOW_MUCH_TIME_HAVE) {
                                    type = NavType.StringType
                                    nullable = true
                                },
                                navArgument(LEVEL) {
                                    type = NavType.StringType
                                    nullable = true
                                }
                            )
                        ) { getData ->
                            val whatDoYouHave = getData.arguments?.getString(WHAT_DO_YOU_HAVE)
                            val howMuchTimeHave = getData.arguments?.getString(HOW_MUCH_TIME_HAVE)
                            val level = getData.arguments?.getString(LEVEL)
                            WhatToCookListScreen(
                                whatDoYouHave.toString(),
                                howMuchTimeHave.toString(),
                                level.toString(),
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
                                }
                            )
                        }
                        composable(
                            route = NavigationBottom.ShowFoodByAttributes.route,
                            arguments = listOf(
                                navArgument("title") {
                                    type = NavType.StringType
                                }
                            )
                        ) { entry ->
                            val topTitle = entry.arguments?.getString("title")!!
                            ShowFoodByAttributes(
                                topTitle = topTitle,
                                navController = navController,
                                navToDetail = { degree: Int, name: String, time: Int, image: Int ->
                                    navController.navigate("foodDetail/$degree/$name/$time/$image") {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

