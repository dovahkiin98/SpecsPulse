package specspulse.app.ui.details

import android.app.ActivityOptions
import android.os.Bundle
import android.util.Pair
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.plus
import specspulse.app.R
import specspulse.app.data.SpecsUtils
import specspulse.app.utils.consume
import specspulse.app.utils.showAds
import specspulse.app.utils.startActivity
import specspulse.app.utils.statusBarHeight

class InfoActivity : AppCompatActivity(R.layout.activity_info) {

    private var scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolBar)
        var name = ""
        if (intent.hasExtra(DEVICE_NAME)) name = intent.getStringExtra(DEVICE_NAME)!!
        else if (intent.data != null) {
            name = intent.data!!.lastPathSegment!!.replace("_", " ")
            println(name)
        }
        collapsingToolbar.title = name

        toolBar.setOnClickListener { infoList.smoothScrollToPosition(0) }
        infoList.setHasFixedSize(true)
        adView.showAds()

        toolBar.updatePadding(top = statusBarHeight)

        deviceImage.setOnClickListener {
            if (deviceImage.drawable != null) {
                val bundle = ActivityOptions
                    .makeSceneTransitionAnimation(this, Pair.create(it, it.transitionName)).toBundle()
                startActivity<ImageActivity>(DEVICE_NAME to name)
            }
        }

        getData(name)
        SpecsUtils.loadImage(SpecsUtils.imageUri(name), deviceImage)
    }

    override fun onStop() {
        super.onStop()

        scope.cancel()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> consume { finish() }
        else -> super.onOptionsItemSelected(item)
    }

    private fun getData(name: String) {
        scope += SpecsUtils.deviceInfo(name, {
            val sections = InfoListAdapter.Section.from(it)
            infoList.adapter = InfoListAdapter(sections)
//            infoList.adapter = NewInfoListAdapter(it)
            loading.isVisible = false
        }, {

        })
    }

    companion object {
        const val DEVICE_NAME = "DEVICE_NAME"
    }
}