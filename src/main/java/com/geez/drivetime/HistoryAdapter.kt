
package com.geez.drivetime
/*
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geez.drivetime.model.entities.DriveTimeClass
import kotlinx.android.synthetic.main.item_history_row.view.*

class HistoryAdapter(private val context : Context, private val items: ArrayList<DriveTimeClass>)
    : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        //private lateinit var binding: ItemHistoryRowBinding

        val llHistoryMainItem: LinearLayout = view.ll_history_item_main
        val tvDate: TextView = view.tvDate
        val tvHours: TextView = view.tvHours
        val tvDriveStart: TextView = view.tvDriveStart
        val tvDriveFinish: TextView = view.tvDriveFinish
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_history_row, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.tvDate.text = item.date
        holder.tvHours.text = item.hours
        holder.tvDriveStart.text = item.driveStart
        holder.tvDriveFinish.text = item.driveFinish

        holder.itemView.setOnClickListener{
            if (context is History) {
                context.updateRecordDialog(item)
            }
        }

        if (position % 2 == 0) {
            holder.llHistoryMainItem.setBackgroundColor(
                Color.parseColor("#18191A")
                //Color.parseColor("#EBEBEB")
            )
        } else {
            holder.llHistoryMainItem.setBackgroundColor(
                Color.parseColor("#242526")
                //Color.parseColor("#FFFFFF")
            )
        }

        */
/*holder.ivDelete.setOnClickListener { view ->

            if (context is MainActivity) {
                context.deleteRecordAlertDialog(item)
            }
        }*//*

    }

    override fun getItemCount(): Int {
        return items.size
    }
}
*/
