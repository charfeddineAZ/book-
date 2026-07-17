package com.flowauto.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flowauto.app.screens.home.HomeScreen
import com.flowauto.app.screens.search.SearchScreen
import com.flowauto.app.screens.profile.ProfileScreen
import com.flowauto.app.screens.mybooks.MyBooksScreen

sealed class Screen(val route: String, val label: String) {
    object Home : Screen("home", "الرئيسية")
    object Search : Screen("search", "البحث")
    object MyBooks : Screen("mybooks", "مكتبتي")
    object Profile : Screen("profile", "الملف الشخصي")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController)
            }
            composable(Screen.Search.route) {
                SearchScreen(navController)
            }
            composable(Screen.MyBooks.route) {
                MyBooksScreen(navController)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val screens = listOf(
        Screen.Home,
        Screen.Search,
        Screen.MyBooks,
        Screen.Profile
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    NavigationBar {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (screen) {
                            Screen.Home -> Icons.Default.Home
                            Screen.Search -> Icons.Default.Search
                            Screen.MyBooks -> Icons.Default.MenuBook
                            Screen.Profile -> Icons.Default.Person
                        },
                        contentDescription = screen.label
                    )
                },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        lazyRestoreState = true
                    }
                }
            )
        }
    }
}
