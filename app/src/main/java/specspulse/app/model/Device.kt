package specspulse.app.model

import android.text.format.DateFormat
import com.google.gson.annotations.SerializedName
import specspulse.app.R
import specspulse.app.data.SpecsUtils

class Device(
    @SerializedName("Name") var name: String = "",
    @SerializedName("Screen") val screen: Double = 0.0,
    @SerializedName("Manu") val manu: String = "",
    @SerializedName("Type") val type: String = "",
    @SerializedName("Date") val date: Long = 0,
    @SerializedName("OS") val os: String = ""
) {

    val dateString get() = DateFormat.format("dd/MM/yyyy", date).toString()
    val oSInt get() = os.toInt(8)
    val imageUrl get() = SpecsUtils.imageUri(name)
    val iconRes
        get() = when (manu) {
            "Samsung" -> R.drawable.manu_samsung
            "Sony" -> R.drawable.manu_sony
            "Motorola" -> R.drawable.manu_motorola
            "LG" -> R.drawable.manu_lg
            "Asus" -> R.drawable.manu_asus
            "Nokia" -> R.drawable.manu_nokia
            "HTC" -> R.drawable.manu_htc
            "Apple" -> R.drawable.manu_apple
            "Google" -> R.drawable.manu_google
            "Huawei" -> R.drawable.manu_huawei
            "Xiaomi" -> R.drawable.manu_xiaomi
            else -> 0
        }
}