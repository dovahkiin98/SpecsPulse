package specspulse.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.info_header.view.*
import specspulse.app.R
import specspulse.app.databinding.InfoChildBinding
import specspulse.app.model.Info
import specspulse.app.model.Specification

class InfoListAdapter(private val flatList: List<Section>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = if (viewType == 0)
        InfoHeaderHolder(LayoutInflater.from(parent.context).inflate(R.layout.info_header, parent, false))
    else InfoCardHolder(LayoutInflater.from(parent.context).inflate(R.layout.info_child, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        if (holder is InfoHeaderHolder) holder.setTitle(flatList[position].title!!)
        else (holder as InfoCardHolder).setInfo(flatList[position].specification!!)

    override fun getItemViewType(position: Int) =
        if (flatList[position].specification == null) 0
        else 1

    override fun getItemCount() = flatList.size

    class Section(val title: String = "", val specification: Specification? = null) {
        companion object {
            fun from(list: List<Info>) = ArrayList<Section>().apply {
                for (info in list) {
                    add(Section(title = info.Title))
                    info.Data.mapTo(this) { Section(specification = it) }
                }
            }
        }
    }

    class InfoHeaderHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun setTitle(title: String) {
            itemView.title.text = title
        }
    }

    class InfoCardHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun setInfo(info: Specification) {
            val binding = DataBindingUtil.bind<InfoChildBinding>(itemView)!!
            binding.spec = info
            binding.infoIcon.setImageResource(getIcon(itemView.context, info))
        }

        private fun getIcon(context: Context, specification: Specification) = when (val icon = specification.Icon) {
            "NULL" -> -1
            "" -> 0
            else -> context.resources.getIdentifier(icon, "drawable", context.packageName)
        }
    }
}