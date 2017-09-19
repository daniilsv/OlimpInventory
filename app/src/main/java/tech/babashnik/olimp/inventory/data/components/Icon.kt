package ru.dvs.eshop.data.components

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Icon {

    @SerializedName("original")
    @Expose
    var original: String? = null
    @SerializedName("big")
    @Expose
    var big: String? = null
    @SerializedName("normal")
    @Expose
    var normal: String? = null
    @SerializedName("small")
    @Expose
    var small: String? = null
    @SerializedName("micro")
    @Expose
    var micro: String? = null

    override fun toString(): String {
        return big!!
    }

}