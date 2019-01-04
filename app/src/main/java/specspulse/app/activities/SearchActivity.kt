package specspulse.app.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.filter_dialog.*
import kotlinx.android.synthetic.main.filter_dialog.view.*
import specspulse.app.R
import specspulse.app.adapters.DevicesAdapter
import specspulse.app.data.SpecsUtils
import specspulse.app.model.Device
import specspulse.app.utils.showAds
import java.util.*

class SearchActivity : AppCompatActivity() {

    private lateinit var filteredList: List<Device>
    private val list = ArrayList<Device>()
    private val adapter = DevicesAdapter()
    private val disposable = CompositeDisposable()

    private val filterMap = mutableMapOf(
        "EndDate" to (DateFormat.format("yyyy/MM/dd", Calendar.getInstance()).toString() to Calendar.getInstance().timeInMillis),
        "StartDate" to ("2000/01/01" to 946677600000),
        "type" to ("All" to 0L),
        "os" to ("0" to 0L),
        "MaxOS" to ("77" to 0L),
        "Screen1" to ("0" to 0L),
        "Screen2" to ("0" to 0L),
        "Sort" to ("Release date" to 0L),
        "Manufacturer" to ("All" to 0L)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(searchBar)
        setupSearch()

        if (savedInstanceState != null) filterMap.forEach {
            val first = savedInstanceState.getString("${it.key}_STRING")
            val second = savedInstanceState.getLong(it.key)
            filterMap[it.key] = first to second
        }

        getData()

        adView.showAds()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        filterMap.forEach {
            outState.putLong(it.key, it.value.second)
            outState.putString(it.key + "_STRING", it.value.first)
        }
    }

    private fun setupSearch() {
        searchBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                searchClear.visibility = if (text.isEmpty()) View.GONE else View.VISIBLE
                applyFilter(text)
            }
        })
        searchClear.setOnClickListener { searchBox.setText("") }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        R.id.action_filter -> {
            //region Filter Views
            val view = View.inflate(this, R.layout.filter_dialog, null).apply {
                //region Listeners
                val startCal = Calendar.getInstance().apply { timeInMillis = filterMap["StartDate"]!!.second }
                startDate.setOnClickListener {
                    DatePickerDialog(it.context, { _, year, month, day ->
                        val cal = Calendar.getInstance()
                        cal.set(year, month, day)
                        val date = DateFormat.format("yyyy/MM/dd", cal).toString()
                        filterMap["StartDate"] = date to cal.timeInMillis
                        startDate.text = date
                    }, startCal[Calendar.YEAR], startCal[Calendar.MONTH], startCal[Calendar.DAY_OF_MONTH]).show()
                }
                val endCal = Calendar.getInstance().apply { timeInMillis = filterMap["EndDate"]!!.second }
                endDate.setOnClickListener {
                    DatePickerDialog(it.context, { _, year, month, day ->
                        val cal = Calendar.getInstance()
                        cal.set(year, month, day)
                        val date = DateFormat.format("yyyy/MM/dd", cal).toString()
                        filterMap["EndDate"] = date to cal.timeInMillis
                        endDate.text = date
                    }, endCal[Calendar.YEAR], endCal[Calendar.MONTH], endCal[Calendar.DAY_OF_MONTH]).show()
                }
                osSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>) = Unit

                    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                        val index = when (position) {
                            1 -> R.array.android_versions
                            2 -> R.array.ios_versions
                            3 -> R.array.windows_versions
                            4 -> 0
                            else -> 0
                        }
                        val array = if (index != 0) resources.getStringArray(index) else arrayOf("All")
                        val adapter = ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, array)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        versionSpinner.adapter = adapter
                    }
                }
                //endregion

                //region Setters
                sortSpinner.setSelection(filterMap["Sort"]?.second?.toInt()!!)
                typeSpinner.setSelection(filterMap["type"]?.second?.toInt()!!)
                manuSpinner.setSelection(filterMap["Manufacturer"]?.second?.toInt()!!)
                startDate.text = filterMap["StartDate"]?.first
                endDate.text = filterMap["EndDate"]?.first
                screenSpinnerA.setSelection(filterMap["Screen1"]?.second?.toInt()!!)
                screenSpinnerB.setSelection(filterMap["Screen2"]?.second?.toInt()!!)
                osSpinner.setSelection(filterMap["os"]?.second?.toInt()!!)
                versionSpinner.setSelection(filterMap["MaxOS"]?.second?.toInt()!!)
                //endregion
            }

            //region Dialog
            val dialog = BottomSheetDialog(this).apply {
                setContentView(view)
            }
            view.findViewById<ImageView>(R.id.closeButton).setOnClickListener {
                filterMap["Sort"] = sortSpinner.selectedItem.toString() to sortSpinner.selectedItemPosition.toLong()
                filterMap["type"] = typeSpinner.selectedItem.toString() to typeSpinner.selectedItemPosition.toLong()
                filterMap["Manufacturer"] = manuSpinner.selectedItem.toString() to screenSpinnerA.selectedItemPosition.toLong()
                filterMap["Screen1"] = screenSpinnerA.selectedItem.toString() to screenSpinnerA.selectedItemPosition.toLong()
                filterMap["Screen2"] = screenSpinnerB.selectedItem.toString() to screenSpinnerB.selectedItemPosition.toLong()
                filterMap["os"] = osSpinner.selectedItemPosition.toString() to osSpinner.selectedItemPosition.toLong()
                filterMap["MaxOS"] = versionSpinner.selectedItemPosition.toString(8) to versionSpinner.selectedItemPosition.toLong()

                applyFilter()
                dialog.dismiss()
            }
            dialog.show()
            //endregion
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun applyFilter(text: CharSequence = searchBox.text) {
        val manu = if (filterMap["Manufacturer"]?.first == "All") "" else filterMap["Manufacturer"]?.first
        val type = if (filterMap["type"]?.first == "All") "" else filterMap["type"]?.first
        val startDate = filterMap["StartDate"]!!.second
        val endDate = filterMap["EndDate"]!!.second
        val screen1 = filterMap["Screen1"]?.first
        val screen2 = filterMap["Screen2"]?.first
        val screen = "$screen1.$screen2".toDouble()
        val os = filterMap["os"]!!.second
        var version = filterMap["MaxOS"]!!.first
        if (version.toInt() < 8) version = "0$version"
        val minOS = "$os$version".toInt(8)
        val maxOS = if (os == 0L) "777".toInt(8) else "${os}77".toInt(8)

        filteredList = list.asSequence().filter {
            it.name.contains(text, true)
                && it.manu.contains(manu as CharSequence, true)
                && it.type.contains(type as CharSequence, true)
                && (it.date in (startDate + 1)..(endDate - 1))
                && it.screen >= screen
                && (it.oSInt in minOS..maxOS)
        }.sortedBy {
            when (filterMap["Sort"]?.first) {
                "Release date" -> -it.date as Comparable<Any>
                "screen Size" -> it.screen as Comparable<Any>
                "Operating System" -> it.oSInt as Comparable<Any>
                "Manufacturer" -> it.manu as Comparable<Any>
                else -> it.name as Comparable<Any>
            }
        }.toList()
        adapter.devices = filteredList
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    private fun getData() = disposable.add(SpecsUtils.devicesList {
        progressBar.visibility = View.GONE
        list.addAll(it)
        adapter.devices = list
        searchList.adapter = adapter
        applyFilter()
    })
}
