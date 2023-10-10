package com.example.shoppinglist.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.api.ShoppingApi
import com.example.shoppinglist.models.responses.GetAllMyShopListsResponse
import com.example.shoppinglist.models.data.ShoppingList
import com.example.shoppinglist.repositories.MainRepository
import com.example.shoppinglist.util.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopListsViewModel: ViewModel() {
    private val _shoppingLists = MutableLiveData<List<ShoppingList>>()
    val shoppingLists: LiveData<List<ShoppingList>>
        get() = _shoppingLists
    var errorMessage: String by mutableStateOf("List is empty")

    fun getAllMyShopLists() {
        val key = SharedPreferencesManager.getApiKey()
        val call = ShoppingApi.getInstance().getAllMyShopLists(key!!)
        call.enqueue(object : Callback<GetAllMyShopListsResponse> {
            override fun onFailure(call: Call<GetAllMyShopListsResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<GetAllMyShopListsResponse>, response: Response<GetAllMyShopListsResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    _shoppingLists.postValue(response.body()?.shop_list!!)
                }
                else {
                    errorMessage = "Fetching error"
                }
            }
        })
    }

    fun refresh() {
        getAllMyShopLists()
    }

    fun removeItem(item: ShoppingList) {
        MainRepository.removeShoppingList(item)
        refresh()
    }
}