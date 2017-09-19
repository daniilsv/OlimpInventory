package tech.babashnik.olimp.inventory.data.components.olimp.inventory

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class InventoryCodeResponse {

    @SerializedName("href")
    @Expose
    var href: String? = null

    override fun toString(): String {
        if (href != null)
            return href!!
        else
            return ""
    }

}
