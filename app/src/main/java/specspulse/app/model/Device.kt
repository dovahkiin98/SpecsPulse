package specspulse.app.model

data class Device(
    val name: String,
    val image: String,
    val link: String,
) {
    override fun toString() = name

    companion object {
        val dummyDevice
            get() = Device(
                name = "Samsung\nGalaxy A55",
                link = "samsung_galaxy_a55-12824",
                image = "https://fdn2.gsmarena.com/vv/bigpic/samsung-galaxy-a55.jpg",
            )
    }
}