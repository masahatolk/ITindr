package com.hits.itindr.mainflow

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hits.api.model.Chat
import com.hits.core_ui.GradientBackground
import com.hits.impl.data.MatchStore
import com.hits.impl.ui.ChatListRoute
import com.hits.impl.ui.ConversationRoute
import com.hits.impl.ui.EditProfileRoute
import com.hits.impl.ui.FeedScreen
import com.hits.impl.ui.PeopleProfileRoute
import com.hits.impl.ui.PeopleRoute
import com.hits.impl.ui.ProfileRoute
import com.hits.impl.ui.match.MatchOverlay
import org.koin.compose.koinInject

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val matchStore: MatchStore = koinInject()

    val matchData by matchStore.matchData.collectAsState()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = currentBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.Feed.route,
        Screen.People.route,
        Screen.ChatList.route,
        Screen.Profile.route,
    )

    GradientBackground(
        alpha = 1f
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            NavHost(
                navController = navController, startDestination = Screen.Feed.route,

                enterTransition = {
                    val from = tabOrder[initialState.destination.route]

                    val to = tabOrder[targetState.destination.route]

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
                    val from = tabOrder[initialState.destination.route]

                    val to = tabOrder[targetState.destination.route]

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
                }) {

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
                        })
                }

                composable(
                    route = Screen.Conversation.route,

                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(600)
                        )
                    },

                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(600)
                        )
                    },
                ) { backStackEntry ->

                    val chatId = backStackEntry.arguments?.getString("chatId").orEmpty()

                    val title = backStackEntry.arguments?.getString("chatTitle").orEmpty()

                    ConversationRoute(
                        chatId = chatId, title = title, onBack = {
                            navController.popBackStack()
                        })
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
                            animationSpec = tween(600)
                        )
                    },

                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(600)
                        )
                    },
                ) {
                    EditProfileRoute(
                        navController = navController
                    )
                }
            }


            val currentDestination = navController.currentBackStackEntry?.destination

            AnimatedVisibility(
                visible = showBottomBar,
                modifier = Modifier.align(Alignment.BottomCenter),
                enter = slideInVertically(
                    animationSpec = tween(600), initialOffsetY = { it / 2 }),
                exit = slideOutVertically(
                    animationSpec = tween(600), targetOffsetY = { it / 2 })) {
                BottomNavigation(
                    selectedRoute = currentRoute, onNavigate = { route ->
                        val alreadySelected =
                            currentDestination?.hierarchy?.any { it.route == route } == true

                        if (alreadySelected) return@BottomNavigation

                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
            }


            matchData?.let { match ->

                MatchOverlay(
                    matchData = match,

                    onDismiss = {
                        matchStore.dismissMatch()
                    },

                    onMessageClick = {

                        navController.navigate(
                            Screen.Conversation.createRoute(
                                Chat(
                                    id = match.chatId,
                                    title = match.chatTitle,
                                    avatar = null,
                                    lastMessage = null,
                                    updatedAt = 0L
                                )
                            )
                        )

                        matchStore.dismissMatch()
                    })
            }
        }
    }
}
