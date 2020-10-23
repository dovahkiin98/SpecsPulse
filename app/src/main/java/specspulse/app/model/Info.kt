package specspulse.app.model

import com.squareup.moshi.Json

data class Info(
    @Json(name ="Data") var Data: List<Specification> = emptyList(),
    @Json(name ="Title") var Title: String = ""
)