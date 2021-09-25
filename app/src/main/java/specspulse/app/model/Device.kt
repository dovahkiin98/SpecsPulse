package specspulse.app.model

data class Device(
    val name: String,
    val image: String,
    val link: String,
) {
    val details = mutableListOf<DeviceDetail>()

    val versions = mutableListOf<Pair<String, String>>()

    override fun toString() = name.replace("<br>", " ")
}