package com.example.shoppinglist.navigation

const val SHOPPING_LIST_KEY = "listId";

sealed class Screens(val route: String) {
    object GenerateKey: Screens("generate_key_screen")
    object Home: Screens("home_screen")
    object ShoppingLists: Screens("shopping_lists_screen")
    object ShoppingList: Screens("shopping_list_screen/{$SHOPPING_LIST_KEY}") {
        fun passListId(listId: Int): String {
            return this.route.replace("{$SHOPPING_LIST_KEY}", listId.toString())
        }
    }
    object CreateShoppingList: Screens("create_shopping_list_screen")
    object AddToShoppingList: Screens("add_to_shopping_list_screen/{$SHOPPING_LIST_KEY}") {
        fun passListId(listId: Int): String {
            return this.route.replace("{$SHOPPING_LIST_KEY}", listId.toString())
        }
    }
}