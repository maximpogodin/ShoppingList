package com.example.shoppinglist.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.shoppinglist.ui.screens.AddToShoppingListScreen
import com.example.shoppinglist.ui.screens.CreateShoppingListScreen
import com.example.shoppinglist.ui.screens.GenerateKeyScreen
import com.example.shoppinglist.ui.screens.HomeScreen
import com.example.shoppinglist.ui.screens.ShoppingListScreen
import com.example.shoppinglist.ui.screens.ShoppingListsScreen

const val TRANSITION_DURATION = 100

@Composable
fun NavigationGraph (navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route)
    {
        composable(route = Screens.GenerateKey.route){
            GenerateKeyScreen(navController)
        }
        composable(route = Screens.Home.route){
            HomeScreen(navController)
        }
        composable(
            route = Screens.ShoppingLists.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(TRANSITION_DURATION)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(TRANSITION_DURATION)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(TRANSITION_DURATION)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(TRANSITION_DURATION)
                )
            }){
            ShoppingListsScreen(navController)
        }
        composable(route = Screens.CreateShoppingList.route){
            CreateShoppingListScreen(navController)
        }
        composable(
            route = Screens.ShoppingList.route,
            arguments = listOf(navArgument("listId") {
                type = NavType.IntType
            })
        ) {
            ShoppingListScreen(navController)
        }
        composable(
            route = Screens.AddToShoppingList.route,
            arguments = listOf(navArgument("listId") {
                type = NavType.IntType
            })
        ) {
            AddToShoppingListScreen(navController)
        }
    }
}