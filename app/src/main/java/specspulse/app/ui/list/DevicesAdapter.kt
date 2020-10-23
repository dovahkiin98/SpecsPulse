package specspulse.app.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import specspulse.app.databinding.DeviceGridBinding
import specspulse.app.model.Device
import specspulse.app.ui.details.DetailsActivity
import specspulse.app.utils.startActivity

class DevicesAdapter : RecyclerView.Adapter<DevicesAdapter.GridViewHolder>() {

    var devices = listOf<Device>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder =
        GridViewHolder(DeviceGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(devices[position])
    }

    override fun getItemCount() = this.devices.size

    inner class GridViewHolder(private val binding: DeviceGridBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                it.context.startActivity<DetailsActivity>(
                    DetailsActivity.DEVICE_NAME to binding.device!!.name,
                    DetailsActivity.DEVICE_LINK to binding.device!!.link,
                )
            }
        }

        fun bind(device: Device) {
            binding.device = device
            binding.deviceName.text = HtmlCompat.fromHtml(device.name, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
    }
}