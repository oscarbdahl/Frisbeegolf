package com.example.frisbeeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.frisbeeapp.databinding.FragmentScoreBoardBinding
import com.example.frisbeeapp.viewmodel.CourseViewModel


class ScoreBoardFragment : Fragment() {

    // deklarerer og initialiserer viewbinding til null
    private var _binding: FragmentScoreBoardBinding? = null
    private val binding get() = _binding!!

    // delt viewmodel, brukt til aa hente valgt bane
    private val model: CourseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Liste over 18 EditText. Hver EditText er ett hull i applikasjonen.
        val listOfEditText = listOf(binding.hull1Input, binding.hull2Input, binding.hull3Input, binding.hull4Input, binding.hull5Input, binding.hull6Input,
            binding.hull7Input, binding.hull8Input, binding.hull9Input,binding.hull10Input, binding.hull11Input, binding.hull12Input, binding.hull13Input,
            binding.hull14Input, binding.hull15Input, binding.hull16Input, binding.hull17Input, binding.hull18Input)

        //Henter par og viser det på skjermen
        model.singleCourse.observe(viewLifecycleOwner) {
            binding.parTotal.text = it.par.toString()
        }

        //Funksjonen når "Beregn poeng"-knapp trykkes
        binding.regnTotal.setOnClickListener {
            val convertedList = convertNumbers(listOfEditText)
            showNumbers(convertedList)
        }
    }

    //Tar inn lista med EditText og gjør om input til en liste av Int. Returnerer denne lista.
    private fun convertNumbers(list : List<EditText>) : MutableList<Int> {
        val listInt: MutableList<Int> = mutableListOf()
        for (item in list){
            if (item.text.toString() != ""){
                val amount = item.text.toString().toInt()
                listInt.add(amount)
            }
        }
        return listInt
    }

    //Legger sammen tallene i lista og returnerer totalen
    fun addTotal(list: MutableList<Int>) : Int{
        var total = 0
        for (item in list){
            total += item
        }
        return total
    }

    //Regner differanse og poeng
    fun calculateScore(total : Int, totalPar : Int) : Int{
        //Hvis total er under par
        val score : Int = if (total < totalPar){
            val differanse = totalPar - total
            0 - differanse
        //Hvis total er over par
        } else{
            val differanse = total - totalPar
            0 + differanse
        }
        return score
    }

    //Bruker metodene addTotal() og calculateScore() til å regne ut verdiene
    //Viser verdiene på skjermen
    fun showNumbers(list : MutableList<Int>) : Int{
        val total = addTotal(list)

        //Viser en feilmelding gjennom en toast dersom totalen er negativ
        if (total < 0){
            val text = "Antall kast er et negativt tall. Prøv igjen."
            val duration = Toast.LENGTH_LONG

            val toast = Toast.makeText(this.context, text, duration)
            toast.show()
        }
        //Viser verdiene på skjermen dersom totalen er positiv
        else{
            val totalDisplay = binding.visTotal
            totalDisplay.text = total.toString()

            val totalPar = binding.parTotal.text.toString().toInt()
            val score = calculateScore(total, totalPar)
            binding.score.text = score.toString()
        }
        return total
    }

}