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
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }

                                        },
                                        icon = {
                                            Icon(
                                                painterResource(id = screen.icon),
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
                        composable("photoScreen") {
                            ShowPhoto(navController = navController)
                        }
                        composable("foodDetail") {
                            FoodDetail(navController, isLogin)
                        }
                        composable(NavigationBottom.Category.route) {
                            Category(navController)
                        }
                        composable(NavigationBottom.Cook.route) {
                            WhatToCook(
                                navigateToWTCList = { whatDoYouHave, howMuchTimeHave, level ->
                                    navController.navigate("whatToCookList?whatDoYouHave=$whatDoYouHave&howMuchTimeHave=$howMuchTimeHave&level=$level") {
                                        popUpTo(NavigationBottom.Cook.route) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    }

                                }
                            )
                        }
                        composable(NavigationBottom.Search.route) {
                            Search()
                        }
                        composable(NavigationBottom.Profile.route) {
                            ProfileScreen(
                                usernameSave = userName,
                                isLogin = isLogin,
                                navigateToProfileSignIn = {
                                    navController.navigate("sign_up") {
                                        popUpTo(NavigationBottom.Profile.route) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                        composable("sign_up") {
                            signUpScreen(
                                isLogin = { result ->
                                    isLogin = result
                                },
                                saveUserName = { username ->
                                    userName = username
                                },
                                navigateToProfile = {
                                    navController.navigate(NavigationBottom.Profile.route) {
                                        popUpTo(NavigationBottom.Profile.route) {
                                            inclusive = true
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    }

                                },
                                navigateToProfileLogin = {
                                    navController.navigate("login") {
                                        popUpTo(NavigationBottom.Profile.route)
                                    }
                                }

                            )
                        }
                        composable("login") {
                            LoginScreen(
                                navigateToProfileSignIn = {
                                    navController.navigate("sign_up") {
                                        popUpTo(NavigationBottom.Profile.route)
                                    }
                                },
                                navigateToProfile = {
                                    navController.navigate(NavigationBottom.Profile.route) {
                                        popUpTo(NavigationBottom.Profile.route) {
                                            inclusive = true
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    }
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
                            route = "whatToCookList?whatDoYouHave={whatDoYouHave}&howMuchTimeHave={howMuchTimeHave}&level={level}",
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
                                whatDoYouHave,
                                howMuchTimeHave,
                                level,
                                navigateToWTCForm = {
                                    navController.navigate(NavigationBottom.Cook.route) {
                                        popUpTo(NavigationBottom.Cook.route) {
                                            inclusive = true
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    }
                                },
                                navigateToDetailScreen = {
                                    navController.navigate("foodDetail") {
                                        popUpTo("whatToCookList?whatDoYouHave={whatDoYouHave}&howMuchTimeHave={howMuchTimeHave}&level={level}") {
                                            inclusive = false
                                            saveState = true
                                        }
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

