package com.example.shoppinglist.models.responses

import com.example.shoppinglist.models.data.ShoppingList

data class GetAllMyShopListsResponse(
    val shop_list: List<ShoppingList>,
    val success: Boolean
)