package com.example.shoppinglist.repositories

import android.util.Log
import com.example.shoppinglist.api.ShoppingApi
import com.example.shoppinglist.models.responses.RemoveShoppingListResponse
import com.example.shoppinglist.models.data.ShoppingList
import com.example.shoppinglist.models.responses.AddToShoppingListResponse
import com.example.shoppinglist.models.responses.CreateShoppingListResponse
import com.example.shoppinglist.models.responses.CrossItOffResponse
import com.example.shoppinglist.models.responses.RemoveFromListResponse
import com.example.shoppinglist.util.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository {
    companion object {
        fun createApiKey() {
            val call = ShoppingApi.getInstance().createTestKey()
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val key = response.body()?.string()
                    if (key != null && key != SharedPreferencesManager.getApiKey()) {
                        SharedPreferencesManager.saveApiKey(key)
                    }
                }
            })
        }

        suspend fun authenticate(key: String): Boolean {
            return withContext(Dispatchers.IO) {
                val call = ShoppingApi.getInstance().authenticate(key)
                val response = call.execute()

                response.isSuccessful
            }
        }

        fun removeShoppingList(item: ShoppingList) {
            val call = ShoppingApi.getInstance().removeShoppingList(item.id)
            call.enqueue(object : Callback<RemoveShoppingListResponse> {
                override fun onFailure(call: Call<RemoveShoppingListResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<RemoveShoppingListResponse>, response: Response<RemoveShoppingListResponse>) {
                    if (response.isSuccessful) {
                        Log.i(null, "Shopping list was removed")
                    }
                }
            })
        }

        fun createShoppingList(key: String, name: String) {
            val call = ShoppingApi.getInstance().createShoppingList(key, name)

            call.enqueue(object: Callback<CreateShoppingListResponse> {
                override fun onFailure(call: Call<CreateShoppingListResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<CreateShoppingListResponse>,
                    response: Response<CreateShoppingListResponse>
                ) {
                }
            })
        }

        fun addToShoppingList(listId: Int, name: String, value: Int) {
            val call = ShoppingApi.getInstance().addToShoppingList(listId, name, value)

            call.enqueue(object: Callback<AddToShoppingListResponse> {
                override fun onFailure(call: Call<AddToShoppingListResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<AddToShoppingListResponse>,
                    response: Response<AddToShoppingListResponse>
                ) {
                    response.body()
                }
            })
        }

        fun crossItOff(itemId: Int) {
            val call = ShoppingApi.getInstance().crossItOff(itemId)

            call.enqueue(object: Callback<CrossItOffResponse> {
                override fun onFailure(call: Call<CrossItOffResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<CrossItOffResponse>,
                    response: Response<CrossItOffResponse>
                ) {
                    response.body()
                }
            })
        }

        fun removeFromList(listId: Int, itemId: Int) {
            val call = ShoppingApi.getInstance().removeFromList(listId, itemId)

            call.enqueue(object: Callback<RemoveFromListResponse> {
                override fun onFailure(call: Call<RemoveFromListResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<RemoveFromListResponse>,
                    response: Response<RemoveFromListResponse>
                ) {
                    response.body()
                }
            })
        }
    }
}