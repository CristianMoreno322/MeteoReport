package basedatos.movil.meteoreport.ui

import android.widget.Toast
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateConnectivityIndicator()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTotal = view.findViewById<TextView>(R.id.tvTotalReportes)
        val tvPendientes = view.findViewById<TextView>(R.id.tvPendientes)
        val tvSincronizados = view.findViewById<TextView>(R.id.tvSincronizados)
        val tvConectividad = view.findViewById<TextView>(R.id.tvConectividad)

        viewModel.allReports.observe(viewLifecycleOwner) { reportes ->
            tvTotal.text = reportes.size.toString()
            tvPendientes.text = reportes.count { !it.isSynced }.toString()
            tvSincronizados.text = reportes.count { it.isSynced }.toString()
        }

        updateConnectivityIndicator()


        view.findViewById<Button>(R.id.btnBuscarClima).setOnClickListener {
            findNavController().navigate(R.id.toWeatherSearch)
        }

        view.findViewById<Button>(R.id.btnSincronizar).setOnClickListener {
            val intent = Intent("basedatos.movil.meteoreport.SYNC_NOW")
            requireContext().sendBroadcast(intent)
            Toast.makeText(requireContext(), "🔄 Sincronizando...", Toast.LENGTH_SHORT).show()
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
        view.findViewById<Button>(R.id.btnSync).setOnClickListener {
            findNavController().navigate(R.id.toSync)
        }
        view.findViewById<Button>(R.id.btnAbout).setOnClickListener {
            findNavController().navigate(R.id.toAbout)
        }

    }

    private fun updateConnectivityIndicator() {
        val tvConectividad = view?.findViewById<TextView>(R.id.tvConectividad) ?: return
        val isConnected = isNetworkAvailable()
        if (isConnected) {
            tvConectividad.text = "🟢 Conectado a internet"
            tvConectividad.setTextColor(android.graphics.Color.parseColor("#2E7D32"))
        } else {
            tvConectividad.text = "🔴 Sin conexión — modo offline"
            tvConectividad.setTextColor(android.graphics.Color.parseColor("#C62828"))
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter("basedatos.movil.meteoreport.NETWORK_STATUS")
        requireContext().registerReceiver(
            networkReceiver,
            filter,
            Context.RECEIVER_NOT_EXPORTED  // ← agrega esta línea
        )
        updateConnectivityIndicator()
    }
    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(networkReceiver)
    }
}