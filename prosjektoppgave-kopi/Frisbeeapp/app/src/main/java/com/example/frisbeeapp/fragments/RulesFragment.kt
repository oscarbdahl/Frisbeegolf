package com.example.frisbeeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.example.frisbeeapp.R
import com.example.frisbeeapp.databinding.FragmentRulesBinding

class RulesFragment : Fragment() {

    val context = this
    private var _binding: FragmentRulesBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRulesBinding.inflate(inflater, container, false)

        //Spinner
        val spinner: Spinner = binding.spinner.findViewById(R.id.spinner)
        // Lager en ArrayAdapter med string array og default spinner layout
        ArrayAdapter.createFromResource(
            requireContext().applicationContext,
            R.array.alleRegler,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Spesifiser layout som skal brukes naar listen av valg vises
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Setter adapter paa spinner
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            // Setter riktig tekst i textview til valget fra spinner
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when (spinner.selectedItem.toString()){
                    "Superenkle regler" -> {
                        readFile("superenkle")
                    }
                    "Etikette" -> {
                        readFile("etikette.txt")
                    }
                    "Sikkerhetsregler" -> {
                        readFile("sikkerhet.txt")
                    }
                    "Kasterekkefølge" -> {
                        readFile("kasterekkefolge.txt")
                    }
                    "Markering av disk" -> {
                        readFile("markering.txt")
                    }
                    "Mandatory/Mando" -> {
                        readFile("mandatory.txt")
                    }
                    "Out of Bounds/OB" -> {
                        readFile("ob.txt")
                    }
                    "Hazard" -> {
                        readFile("hazard.txt")
                    }
                    "Fulle regler" -> {
                        readFile("fulle.txt")
                    }
                }
            }
        }
        return binding.root
    }

    // Metode som leser inn valgt regel fra fil til textview
    fun readFile(fil: String)  {
        val valg = binding.valgtRegel.findViewById<TextView>(R.id.valgtRegel)

        // Dersom superenkle velges, leser den fra strings.xml
        if (fil == "superenkle"){
            val superString = getString(R.string.superenkleRegler)
            valg.text = superString
        } else{
            // Leser txt-fil fra res/raw
            val fileContent = this::class.java.getResource("/res/raw/$fil")?.readText()
            valg.text = fileContent
        }
    }

}