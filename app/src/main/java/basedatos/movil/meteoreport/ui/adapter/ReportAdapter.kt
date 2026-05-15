package basedatos.movil.meteoreport.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import basedatos.movil.meteoreport.R
import basedatos.movil.meteoreport.model.WeatherReport

class ReportAdapter : ListAdapter<WeatherReport, ReportAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCiudad: TextView = view.findViewById(R.id.tvCiudad)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reporte = getItem(position)
        holder.tvCiudad.text = reporte.city
        holder.tvDescripcion.text = reporte.description
    }

    class DiffCallback : DiffUtil.ItemCallback<WeatherReport>() {
        override fun areItemsTheSame(a: WeatherReport, b: WeatherReport) = a.id == b.id
        override fun areContentsTheSame(a: WeatherReport, b: WeatherReport) = a == b
    }
}