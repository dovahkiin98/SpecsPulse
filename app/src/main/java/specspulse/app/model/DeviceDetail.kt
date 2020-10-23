package specspulse.app.model

data class DeviceDetail (
    val sectionName: String,
    val title: String,
    val text: String,
) {
    var link: String = ""
    var termLink: String = ""

    override fun toString() = title
}