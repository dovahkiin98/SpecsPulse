package specspulse.app.model

import com.google.gson.annotations.SerializedName

data class Specification(
    @SerializedName("Icon") var Icon: String = "",
    @SerializedName("Text") var Text: String = "",
    @SerializedName("Title") var Title: String = ""
)