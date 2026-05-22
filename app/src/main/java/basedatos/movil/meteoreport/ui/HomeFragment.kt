package basedatos.movil.meteoreport.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import basedatos.movil.meteoreport.R
import basedatos.movil.meteoreport.viewmodel.WeatherViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTotal = view.findViewById<TextView>(R.id.tvTotalReportes)
        val tvPendientes = view.findViewById<TextView>(R.id.tvPendientes)
        val tvSincronizados = view.findViewById<TextView>(R.id.tvSincronizados)

        viewModel.allReports.observe(viewLifecycleOwner) { reportes ->
            tvTotal.text = reportes.size.toString()
            tvPendientes.text = reportes.count { !it.isSynced }.toString()
            tvSincronizados.text = reportes.count { it.isSynced }.toString()
        }

        view.findViewById<Button>(R.id.btnNuevoReporte).setOnClickListener {
            findNavController().navigate(R.id.toNewReportFromHome)
        }
        view.findViewById<Button>(R.id.btnVerReportes).setOnClickListener {
            findNavController().navigate(R.id.toReportList)
        }
        view.findViewById<Button>(R.id.btnAjustes).setOnClickListener {
            findNavController().navigate(R.id.toSettings)
        }
    }
}