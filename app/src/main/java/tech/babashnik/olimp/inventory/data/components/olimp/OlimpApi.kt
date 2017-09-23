package tech.babashnik.olimp.inventory.data.components.olimp

import retrofit2.Call
import retrofit2.http.*
import tech.babashnik.olimp.inventory.data.components.olimp.inventory.InventoryItem

interface OlimpApi {

    @GET("olimp_inventory.item")
    fun getInventoryItem(@Query("name") name: String): Call<InventoryItem>

    @POST("olimp_inventory.item")
    fun updateInventoryItem(@Query("name") name: String,
                            @Query("item[title]") title: String,
                            @Query("item[href]") href: String,
                            @Query("item[description]") description: String): Call<InventoryItem>

    //update == save for update item
    @PUT("olimp_inventory.item")
    fun insertInventoryItem(@Query("name") name: String,
                            @Query("item[title]") title: String,
                            @Query("item[href]") href: String,
                            @Query("item[description]") description: String): Call<InventoryItem>
//insert == save for new items

    @DELETE("olimp_inventory.item")
    fun deleteInventoryItem(@Query("name") name: String): Call<InventoryItem>

}
//ДЕЙСТВУЙ!
