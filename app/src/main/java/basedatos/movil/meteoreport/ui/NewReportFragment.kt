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
import basedatos.movil.meteoreport.model.WeatherReport
import basedatos.movil.meteoreport.viewmodel.WeatherViewModel

class NewReportFragment : Fragment(R.layout.fragment_new_report) {

    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etCiudad = view.findViewById<EditText>(R.id.etCiudad)
        val etDescripcion = view.findViewById<EditText>(R.id.etDescripcion)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val ciudad = etCiudad.text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()

            if (ciudad.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val reporte = WeatherReport(
                city = ciudad,
                description = descripcion
            )

            viewModel.insertReport(reporte)
            Toast.makeText(requireContext(), "Reporte guardado ✅", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }
}