package hoannt.android.moviedb.data.network.model


import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    var id: Int,
    @SerializedName("logo_path")
    var logoPath: String,
    var name: String,
    @SerializedName("origin_country")
    var originCountry: String
)