package com.flowauto.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.flowauto.app.screens.browser.BrowserScreen
import com.flowauto.app.screens.editor.WorkflowEditorScreen
import com.flowauto.app.screens.home.HomeScreen
import com.flowauto.app.screens.libraries.LibrariesScreen
import com.flowauto.app.screens.mybooks.MyBooksScreen
import com.flowauto.app.screens.profile.ProfileScreen
import com.flowauto.app.screens.runner.RunnerScreen
import com.flowauto.app.screens.search.SearchScreen
import com.flowauto.app.screens.settings.SettingsScreen
import com.flowauto.app.screens.workspace.WorkspaceScreen

sealed class Screen(val route: String, val label: String) {
    object Home : Screen("home", "الرئيسية")
    object Search : Screen("search", "البحث")
    object MyBooks : Screen("mybooks", "مكتبتي")
    object Profile : Screen("profile", "الملف الشخصي")
    object Editor : Screen("editor", "المحرر")
    object Runner : Screen("runner", "المشغل")
    object Browser : Screen("browser", "المتصفح")
    object Workspace : Screen("workspace", "مساحة العمل")
    object Libraries : Screen("libraries", "المكتبات")
    object Settings : Screen("settings", "الإعدادات")
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
            composable(Screen.Editor.route) {
                WorkflowEditorScreen(navController)
            }
            composable(Screen.Runner.route) {
                RunnerScreen(navController)
            }
            composable(Screen.Browser.route) {
                BrowserScreen(navController)
            }
            composable(Screen.Workspace.route) {
                WorkspaceScreen(navController)
            }
            composable(Screen.Libraries.route) {
                LibrariesScreen(navController)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(navController)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val screens = listOf(
        Screen.Home,
        Screen.Editor,
        Screen.Runner,
        Screen.Workspace
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
                            Screen.Editor -> Icons.Default.Edit
                            Screen.Runner -> Icons.Default.PlayArrow
                            Screen.Workspace -> Icons.Default.FolderOpen
                            else -> Icons.Default.Help
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
                        restoreState = true
                    }
                }
            )
        }
    }
}
