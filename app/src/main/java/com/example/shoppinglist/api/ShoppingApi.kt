package com.example.shoppinglist.api

import com.example.shoppinglist.models.responses.AddToShoppingListResponse
import com.example.shoppinglist.models.responses.CreateShoppingListResponse
import com.example.shoppinglist.models.responses.CrossItOffResponse
import com.example.shoppinglist.models.responses.GetAllMyShopListsResponse
import com.example.shoppinglist.models.responses.GetShoppingListResponse
import com.example.shoppinglist.models.responses.RemoveFromListResponse
import com.example.shoppinglist.models.responses.RemoveShoppingListResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ShoppingApi {
    @POST("CreateTestKey")
    fun createTestKey(): Call<ResponseBody>

    @POST("Authentication")
    fun authenticate(@Query("key") key: String): Call<ResponseBody>

    @POST("CreateShoppingList")
    fun createShoppingList(@Query("key") key: String, @Query("name") name: String): Call<CreateShoppingListResponse>

    @POST("RemoveShoppingList")
    fun removeShoppingList(@Query("list_id") list_id: Int): Call<RemoveShoppingListResponse>

    @POST("AddToShoppingList")
    fun addToShoppingList(@Query("id") id: Int, @Query("value") value: String, @Query("n") n: Int): Call<AddToShoppingListResponse>

    @POST("CrossItOff")
    fun crossItOff(@Query("id") id: Int): Call<CrossItOffResponse>

    @GET("GetAllMyShopLists")
    fun getAllMyShopLists(@Query("key") key: String): Call<GetAllMyShopListsResponse>

    @GET("GetShoppingList")
    fun getShoppingList(@Query("list_id") id: Int): Call<GetShoppingListResponse>

    @POST("RemoveFromList")
    fun removeFromList(@Query("list_id") list_id: Int, @Query("item_id") item_id: Int): Call<RemoveFromListResponse>

    companion object Factory {
        private var api: ShoppingApi? = null

        fun getInstance(): ShoppingApi {
            if (api == null) {
                api = Retrofit.Builder()
                    .baseUrl("https://cyberprot.ru/shopping/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ShoppingApi::class.java)
            }

            return api!!;
        }
    }
}