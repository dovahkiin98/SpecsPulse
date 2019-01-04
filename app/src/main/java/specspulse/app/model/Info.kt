package specspulse.app.model

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("Data") var Data: List<Specification> = emptyList(),
    @SerializedName("Title") var Title: String = ""
)