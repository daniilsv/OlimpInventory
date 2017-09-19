package tech.babashnik.olimp.inventory.data.components.olimp.inventory

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class InventoryItem {

    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("href")
    @Expose
    var href: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null

    fun getMap(): HashMap<String, String> {
        val map: HashMap<String, String> = HashMap()
        map["name"] = name!!
        map["href"] = href!!
        map["title"] = title!!
        map["description"] = description!!
        return map
    }

    override fun toString(): String {
        if (title != null)
            return title!!
        else
            return ""
    }

}