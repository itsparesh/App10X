package com.example.app10x.dataModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Rain {
    @SerializedName("1h")
    @Expose
    var _1h: Double? = null

    @SerializedName("3h")
    @Expose
    var _3h: Double? = null
}