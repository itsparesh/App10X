package com.example.app10x.dataModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ListClass {
    @SerializedName("dt")
    @Expose
    var dt: Int? = null

    @SerializedName("main")
    @Expose
    var main: Main? = null

    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null

    @SerializedName("clouds")
    @Expose
    var clouds: Clouds? = null

    @SerializedName("wind")
    @Expose
    var wind: Wind? = null

    @SerializedName("visibility")
    @Expose
    var visibility: Int? = null

    @SerializedName("pop")
    @Expose
    var pop: Double? = null

    @SerializedName("rain")
    @Expose
    var rain: Rain? = null

    @SerializedName("sys")
    @Expose
    var sys: Sys? = null

    @SerializedName("dt_txt")
    @Expose
    var dtTxt: String? = null
}