package specspulse.app.model

data class Device(
    val id: Int,
    val name: String,
    val image: String,
) {
    private val tag: String = name.replace('\n', ' ')
        .replace(' ', '_')
        .lowercase()

    val detailsLink: String = "$tag-$id"

    override fun toString() = name

    companion object {
        val dummyDevice
            get() = Device(
                id = 12824,
                name = "Samsung\nGalaxy A55",
                image = "https://fdn2.gsmarena.com/vv/bigpic/samsung-galaxy-a55.jpg",
            )
    }
}