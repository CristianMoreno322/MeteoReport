package basedatos.movil.meteoreport.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import basedatos.movil.meteoreport.R
import basedatos.movil.meteoreport.data.local.AppDatabase
import basedatos.movil.meteoreport.data.remote.RetrofitClient
import basedatos.movil.meteoreport.ui.adapter.ReportAdapter
import basedatos.movil.meteoreport.viewmodel.WeatherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SyncFragment : Fragment(R.layout.fragment_sync) {

    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvEstado = view.findViewById<TextView>(R.id.tvEstadoConexion)
        val tvEnviados = view.findViewById<TextView>(R.id.tvEnviados)
        val tvPendientes = view.findViewById<TextView>(R.id.tvPendientesSync)
        val btnSync = view.findViewById<Button>(R.id.btnSincronizarAhora)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerPendientes)
        val tvSinPendientes = view.findViewById<TextView>(R.id.tvSinPendientes)

        val adapter = ReportAdapter { }
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        val isConnected = isNetworkAvailable()
        tvEstado.text = if (isConnected) "🟢 Conectado a internet" else "🔴 Sin conexión"

        viewModel.allReports.observe(viewLifecycleOwner) { reportes ->
            val pendientes = reportes.filter { !it.isSynced }
            val sincronizados = reportes.filter { it.isSynced }
            tvEnviados.text = sincronizados.size.toString()
            tvPendientes.text = pendientes.size.toString()
            adapter.submitList(pendientes)
            tvSinPendientes.visibility = if (pendientes.isEmpty()) View.VISIBLE else View.GONE
            recycler.visibility = if (pendientes.isEmpty()) View.GONE else View.VISIBLE
        }

        btnSync.setOnClickListener {
            if (!isNetworkAvailable()) {
                Toast.makeText(requireContext(), "❌ Sin conexión a internet", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(requireContext(), "🔄 Sincronizando...", Toast.LENGTH_SHORT).show()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val dao = AppDatabase.getInstance(requireContext()).weatherDao()
                    val pending = dao.getPendingSync()
                    for (report in pending) {
                        val response = RetrofitClient.api.getCurrentWeather(report.city)
                        val updated = report.copy(
                            temperature = response.main.temp,
                            humidity = response.main.humidity,
                            windSpeed = response.wind.speed,
                            pressure = response.main.pressure,
                            weatherMain = response.weather[0].main,
                            description = response.weather[0].description,
                            iconCode = response.weather[0].icon,
                            isSynced = true
                        )
                        dao.insertReport(updated)
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "✅ Sincronización completa", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "❌ Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                    e.printStackTrace()
                }
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}