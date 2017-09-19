package tech.babashnik.olimp.inventory.data.components.olimp

import retrofit2.Call
import retrofit2.http.*
import tech.babashnik.olimp.inventory.data.components.olimp.inventory.InventoryCodeResponse
import tech.babashnik.olimp.inventory.data.components.olimp.inventory.InventoryItemResponse

interface OlimpApi {
    @GET("olimp_inventory.code")
    fun getInventoryCode(@Query("name") name: String): Call<InventoryCodeResponse>

    @GET("olimp_inventory.item")
    fun getInventoryItem(@Query("name") name: String): Call<InventoryItemResponse>

    @POST("olimp_inventory.item")
    fun updateInventoryItem(@Query("name") name: String,
                            @Query("item[title]") title: String,
                            @Query("item[href]") href: String,
                            @Query("item[description]") description: String): Call<InventoryItemResponse>
    @PUT("olimp_inventory.item")
    fun insertInventoryItem(@Query("name") name: String,
                            @Query("item[title]") title: String,
                            @Query("item[href]") href: String,
                            @Query("item[description]") description: String): Call<InventoryItemResponse>
    @DELETE("olimp_inventory.item")
    fun deleteInventoryItem(@Query("name") name: String): Call<InventoryItemResponse>

}
