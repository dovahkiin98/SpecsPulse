package specspulse.app.model

import com.squareup.moshi.Json

data class Specification(
    @Json(name ="Icon") var Icon: String = "",
    @Json(name ="Text") var Text: String = "",
    @Json(name ="Title") var Title: String = ""
)