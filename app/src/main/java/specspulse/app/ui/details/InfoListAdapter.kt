package specspulse.app.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import specspulse.app.R
import specspulse.app.databinding.ViewInfoBinding
import specspulse.app.databinding.ViewInfoChildBinding
import specspulse.app.model.DeviceDetail
import specspulse.app.utils.inflateLayout

class InfoListAdapter(flatList: List<DeviceDetail>) : RecyclerView.Adapter<InfoListAdapter.InfoHolder>() {

    private val sections = mutableListOf<DeviceDetailsSection>()

    init {
        var currentTitle = flatList.first().sectionName
        val detailsList = mutableListOf<DeviceDetail>()

        flatList.forEach {
            if (currentTitle != it.sectionName) {
                sections += DeviceDetailsSection(currentTitle, ArrayList(detailsList))

                currentTitle = it.sectionName

                detailsList.clear()
            }

            detailsList += it
        }
    }

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InfoHolder(
        ViewInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: InfoHolder, position: Int) {
        holder.bind(sections[position])
    }

    override fun getItemCount() = sections.size

    inner class InfoHolder(private val binding: ViewInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(section: DeviceDetailsSection) {
            binding.sectionTitle = section.title

            binding.list.apply {
                setHasFixedSize(true)
                setRecycledViewPool(viewPool)
                adapter = InfoCardAdapter(section.details)
            }
        }

        inner class InfoCardAdapter(private val details: List<DeviceDetail>) : RecyclerView.Adapter<InfoCardAdapter.InfoCardHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InfoCardHolder(
                ViewInfoChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            override fun onBindViewHolder(holder: InfoCardHolder, position: Int) {
                holder.bind(details[position])
            }

            override fun getItemCount() = details.size

            inner class InfoCardHolder(private val binding: ViewInfoChildBinding) : RecyclerView.ViewHolder(binding.root) {
                fun bind(info: DeviceDetail) {
                    binding.spec = info
                }
            }
        }
    }

    class DeviceDetailsSection(
        val title: String,
        val details: List<DeviceDetail>,
    )
}
