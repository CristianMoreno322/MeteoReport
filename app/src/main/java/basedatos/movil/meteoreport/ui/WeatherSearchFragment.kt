package basedatos.movil.meteoreport.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import basedatos.movil.meteoreport.R
import basedatos.movil.meteoreport.viewmodel.WeatherViewModel

class WeatherSearchFragment : Fragment(R.layout.fragment_weather_search) {

    private val viewModel: WeatherViewModel by activityViewModels()
    private var buscando = false
    private var ciudadBuscada = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etCiudad = view.findViewById<EditText>(R.id.etBuscarCiudad)
        val btnBuscar = view.findViewById<Button>(R.id.btnBuscar)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val cardResultado = view.findViewById<LinearLayout>(R.id.cardResultado)
        val tvCiudad = view.findViewById<TextView>(R.id.tvCiudadResultado)
        val tvTemperatura = view.findViewById<TextView>(R.id.tvTemperatura)
        val tvHumedad = view.findViewById<TextView>(R.id.tvHumedad)
        val tvViento = view.findViewById<TextView>(R.id.tvViento)
        val tvDescripcion = view.findViewById<TextView>(R.id.tvDescripcionClima)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardarClima)
        val tvError = view.findViewById<TextView>(R.id.tvError)

        btnBuscar.setOnClickListener {
            val ciudad = etCiudad.text.toString().trim()
            if (ciudad.isEmpty()) {
                etCiudad.error = "Ingresa una ciudad"
                return@setOnClickListener
            }
            ciudadBuscada = ciudad.lowercase()
            buscando = true
            progressBar.visibility = View.VISIBLE
            cardResultado.visibility = View.GONE
            tvError.visibility = View.GONE
            viewModel.fetchWeather(ciudad)
        }

        viewModel.allReports.observe(viewLifecycleOwner) { reportes ->
            if (!buscando) return@observe
            val resultado = reportes.firstOrNull {
                it.isSynced && it.city.lowercase().contains(ciudadBuscada)
            }
            if (resultado != null) {
                buscando = false
                progressBar.visibility = View.GONE
                cardResultado.visibility = View.VISIBLE
                tvCiudad.text = "📍 ${resultado.city}, ${resultado.country}"
                tvTemperatura.text = "🌡️ Temperatura: ${resultado.temperature}°C"
                tvHumedad.text = "💧 Humedad: ${resultado.humidity}%"
                tvViento.text = "💨 Viento: ${resultado.windSpeed} m/s"
                tvDescripcion.text = "☁️ ${resultado.description}"
            }
        }

        btnGuardar.setOnClickListener {
            Toast.makeText(requireContext(), "✅ Reporte guardado automáticamente", Toast.LENGTH_SHORT).show()
        }
    }
}