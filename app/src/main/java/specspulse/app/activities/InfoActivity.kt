package specspulse.app.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.updatePadding
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_info.*
import org.jetbrains.anko.intentFor
import specspulse.app.R
import specspulse.app.adapters.InfoListAdapter
import specspulse.app.data.SpecsUtils
import specspulse.app.utils.consume
import specspulse.app.utils.getStatusBarHeight
import specspulse.app.utils.showAds

class InfoActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        setSupportActionBar(toolBar)
        var name = ""
        if (intent.hasExtra(DEVICE_NAME)) name = intent.getStringExtra(DEVICE_NAME)
        else if (intent.data != null) {
            name = intent.data!!.lastPathSegment!!.replace("_", " ")
            println(name)
        }
        collapsingToolbar.title = name

        toolBar.setOnClickListener { infoList.smoothScrollToPosition(0) }
        infoList.setHasFixedSize(true)
        adView.showAds()

        toolBar.updatePadding(top = getStatusBarHeight())

        deviceImage.setOnClickListener {
            if (deviceImage.drawable != null) {
                val bundle = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this, it, "DEVICE_IMAGE").toBundle()
                startActivity(intentFor<ImageActivity>(DEVICE_NAME to name), bundle)
            }
        }

        getData(name)
        SpecsUtils.loadImage(SpecsUtils.imageUri(name), deviceImage)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> consume { finish() }
        else -> super.onOptionsItemSelected(item)
    }

    private fun getData(name: String) = disposable.add(SpecsUtils.deviceInfo(name) {
        val sections = InfoListAdapter.Section.from(it)
        infoList.adapter = InfoListAdapter(sections)
    })

    companion object {
        const val DEVICE_NAME = "DEVICE_NAME"
    }
}