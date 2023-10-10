package com.example.shoppinglist.models.responses

import com.example.shoppinglist.models.data.Item

data class GetShoppingListResponse(
    val success: Boolean,
    val item_list: List<Item>
)