package basedatos.movil.meteoreport.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import basedatos.movil.meteoreport.R
import basedatos.movil.meteoreport.viewmodel.WeatherViewModel

class ReportDetailFragment : Fragment(R.layout.fragment_report_detail) {

    private val viewModel: WeatherViewModel by activityViewModels()
    private var reportId: Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reportId = arguments?.getInt("reportId") ?: -1

        val etCiudad = view.findViewById<EditText>(R.id.etCiudad)
        val etPais = view.findViewById<EditText>(R.id.etPais)
        val etTemperatura = view.findViewById<EditText>(R.id.etTemperatura)
        val etHumedad = view.findViewById<EditText>(R.id.etHumedad)
        val etViento = view.findViewById<EditText>(R.id.etViento)
        val etPresion = view.findViewById<EditText>(R.id.etPresion)
        val etDescripcion = view.findViewById<EditText>(R.id.etDescripcion)
        val btnActualizar = view.findViewById<Button>(R.id.btnActualizar)
        val btnEliminar = view.findViewById<Button>(R.id.btnEliminar)

        viewModel.allReports.observe(viewLifecycleOwner) { reportes ->
            val reporte = reportes.find { it.id == reportId }
            reporte?.let {
                etCiudad.setText(it.city)
                etPais.setText(it.country)
                etTemperatura.setText(it.temperature.toString())
                etHumedad.setText(it.humidity.toString())
                etViento.setText(it.windSpeed.toString())
                etPresion.setText(it.pressure.toString())
                etDescripcion.setText(it.description)

                btnActualizar.setOnClickListener { _ ->
                    val ciudad = etCiudad.text.toString().trim()
                    val descripcion = etDescripcion.text.toString().trim()

                    if (ciudad.isEmpty()) {
                        etCiudad.error = "La ciudad es obligatoria"
                        return@setOnClickListener
                    }
                    if (descripcion.isEmpty()) {
                        etDescripcion.error = "La descripción es obligatoria"
                        return@setOnClickListener
                    }

                    val actualizado = it.copy(
                        city = ciudad,
                        country = etPais.text.toString().trim(),
                        temperature = etTemperatura.text.toString().toDoubleOrNull() ?: it.temperature,
                        humidity = etHumedad.text.toString().toIntOrNull() ?: it.humidity,
                        windSpeed = etViento.text.toString().toDoubleOrNull() ?: it.windSpeed,
                        pressure = etPresion.text.toString().toIntOrNull() ?: it.pressure,
                        description = descripcion
                    )
                    viewModel.updateReport(actualizado)
                    Toast.makeText(requireContext(), "✅ Reporte actualizado", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }

                btnEliminar.setOnClickListener { _ ->
                    viewModel.deleteReport(it)
                    Toast.makeText(requireContext(), "🗑️ Reporte eliminado", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            }
        }
    }
}