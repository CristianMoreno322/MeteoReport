package basedatos.movil.meteoreport.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import basedatos.movil.meteoreport.R

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btnVerReportes).setOnClickListener {
            findNavController().navigate(R.id.toReportList)
        }
        view.findViewById<Button>(R.id.btnAjustes).setOnClickListener {
            findNavController().navigate(R.id.toSettings)
        }
    }
}