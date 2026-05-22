package basedatos.movil.meteoreport.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import basedatos.movil.meteoreport.R
import basedatos.movil.meteoreport.ui.adapter.ReportAdapter
import basedatos.movil.meteoreport.viewmodel.WeatherViewModel

class ReportListFragment : Fragment(R.layout.fragment_report_list) {

    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvVacio = view.findViewById<TextView>(R.id.tvVacio)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerReportes)

        val adapter = ReportAdapter { reporte ->
            val bundle = bundleOf("reportId" to reporte.id)
            findNavController().navigate(R.id.toDetail, bundle)
        }

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        viewModel.allReports.observe(viewLifecycleOwner) { reportes ->
            adapter.submitList(reportes)
            if (reportes.isEmpty()) {
                tvVacio.visibility = View.VISIBLE
                recycler.visibility = View.GONE
            } else {
                tvVacio.visibility = View.GONE
                recycler.visibility = View.VISIBLE
            }
        }

        view.findViewById<Button>(R.id.btnNuevoReporte).setOnClickListener {
            findNavController().navigate(R.id.toNewReport)
        }
    }
}