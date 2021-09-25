package specspulse.app.data

class Repository private constructor() {

    private val jsoupRepo = JsoupRepo()

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(): Repository {
            return INSTANCE ?: synchronized(this) {
                val instance = Repository()

                INSTANCE = instance

                instance
            }
        }
    }

    suspend fun getMostPopular() = jsoupRepo.search("")

    suspend fun searchDevices(term: String) = jsoupRepo.search(term)

    suspend fun getDeviceDetails(link: String) = jsoupRepo.getDevice(
        "https://m.gsmarena.com/${link}.php"
    )
}