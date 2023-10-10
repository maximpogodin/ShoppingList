package com.example.shoppinglist.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.api.ShoppingApi
import com.example.shoppinglist.models.data.Item
import com.example.shoppinglist.models.responses.GetShoppingListResponse
import com.example.shoppinglist.repositories.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShoppingListViewModel(private var listId: Int): ViewModel() {
    var itemsList = MutableLiveData<List<Item>>()
    var errorMessage: String by mutableStateOf("List is empty")

    fun getShoppingList() {
        val call = ShoppingApi.getInstance().getShoppingList(listId)
        call.enqueue(object : Callback<GetShoppingListResponse> {
            override fun onFailure(call: Call<GetShoppingListResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<GetShoppingListResponse>, response: Response<GetShoppingListResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    itemsList.postValue(response.body()?.item_list!!)
                }
                else {
                    errorMessage = "Fetching error"
                }
            }
        })
    }

    private fun refresh() {
        getShoppingList()
    }

    fun removeItem(item: Item) {
        MainRepository.removeFromList(listId, item.id)
        refresh()
    }
}