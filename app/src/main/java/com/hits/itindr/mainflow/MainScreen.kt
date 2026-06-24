package com.hits.itindr.mainflow

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hits.impl.ui.ChatListRoute
import com.hits.impl.ui.ConversationRoute
import com.hits.itindr.GradientBackground
import com.hits.itindr.mainflow.profile.ui.ProfileRoute
import com.hits.itindr.mainflow.feed.FeedScreen
import com.hits.itindr.mainflow.feed.PeopleProfileRoute
import com.hits.itindr.mainflow.feed.PeopleRoute
import com.hits.itindr.mainflow.feed.PeopleScreen
import com.hits.itindr.mainflow.profile.ui.EditProfileRoute

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = currentBackStackEntry
        ?.destination
        ?.route

    val showBottomBar =
        currentRoute in listOf(
            Screen.Feed.route,
            Screen.People.route,
            Screen.ChatList.route,
            Screen.Profile.route,
        )

    GradientBackground {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            NavHost(
                navController = navController,
                startDestination = Screen.Feed.route,

                enterTransition = {
                    val from =
                        tabOrder[initialState.destination.route]

                    val to =
                        tabOrder[targetState.destination.route]

                    (if (from != null && to != null && from != to) {

                        if (to > from) {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(320)
                            )
                        } else {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(320)
                            )
                        }
                    } else {
                        EnterTransition.None
                    })
                },

                exitTransition = {
                    val from =
                        tabOrder[initialState.destination.route]

                    val to =
                        tabOrder[targetState.destination.route]

                    (if (from != null && to != null && from != to) {

                        if (to > from) {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(320)
                            )
                        } else {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(320)
                            )
                        }
                    } else {
                        ExitTransition.None
                    })
                }
            ) {

                composable(Screen.Feed.route) {
                    FeedScreen()
                }

                composable(Screen.People.route) {
                    PeopleRoute(
                        navController = navController
                    )
                }

                composable(Screen.PeopleProfile.route) {
                    PeopleProfileRoute(
                        navController = navController
                    )
                }

                composable(Screen.ChatList.route) {
                    ChatListRoute(
                        onOpenChat = { chat ->
                            navController.navigate(
                                Screen.Conversation.createRoute(chat)
                            )
                        }
                    )
                }

                composable(
                    route = Screen.Conversation.route
                ) { backStackEntry ->

                    val chatId =
                        backStackEntry.arguments
                            ?.getString("chatId")
                            .orEmpty()

                    val title =
                        backStackEntry.arguments
                            ?.getString("chatTitle")
                            .orEmpty()

                    ConversationRoute(
                        chatId = chatId,
                        title = title,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(Screen.Profile.route) {
                    ProfileRoute(
                        navController = navController
                    )
                }

                composable(
                    route = Screen.EditProfile.route,

                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(300)
                        )
                    },

                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(300)
                        )
                    },
                ) {
                    EditProfileRoute(
                        navController = navController
                    )
                }
            }

            if (showBottomBar) {

                BottomNavigation(
                    modifier = Modifier.align(
                        Alignment.BottomCenter
                    ),
                    selectedRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route){

                            popUpTo(
                                navController.graph.startDestinationId
                            )

                            launchSingleTop = true

                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
