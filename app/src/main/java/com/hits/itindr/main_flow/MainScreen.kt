package com.hits.itindr.main_flow

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hits.itindr.GradientBackground

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val navController = rememberNavController()

    GradientBackground {
        Column(
            modifier = Modifier
                .padding(WindowInsets.statusBars.asPaddingValues()),
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Feed.route,
                modifier = Modifier.weight(1f)
            ) {
                screens.forEachIndexed { index, screen ->
                    composable(
                        route = screen.route,
                        enterTransition = {
                            if (index > viewModel.previousIndex) {
                                slideInHorizontally { it }
                            } else {
                                slideInHorizontally { -it }
                            }
                        },
                        exitTransition = {
                            if (index > viewModel.previousIndex) {
                                slideOutHorizontally { it }
                            } else {
                                slideOutHorizontally { -it }
                            }
                        }
                    ) {
                        screen.content()
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                BottomNavigation(
                    selectedIndex = viewModel.selectedIndex,
                    onItemSelected = { index ->
                        viewModel.previousIndex = viewModel.selectedIndex
                        viewModel.selectedIndex = index
                        navController.navigate(screens[index].route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}