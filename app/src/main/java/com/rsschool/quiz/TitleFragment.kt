package com.rsschool.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import com.rsschool.quiz.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {

    private var _binding: FragmentTitleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTitleBinding.inflate(inflater, container, false)
        val view = binding.root

        val answers = arrayListOf<Questions>(
            Questions(
                "How much: 1 + 2 ?",
                "2",
                "3",
                "4",
                "5",
                "6",
                "3"
            ),
            Questions(
                "How much: 2 + 2 ?",
                "2",
                "3",
                "4",
                "5",
                "6",
                "4"
            ),
            Questions(
                "How much: 3 + 2 ?",
                "2",
                "3",
                "4",
                "5",
                "6",
                "5"
            ),
            Questions(
                "How much: 4 + 2 ?",
                "2",
                "3",
                "4",
                "5",
                "6",
                "6"
            ),
            Questions(
                "How much: 2 + 2 * 2 ?",
                "8",
                "16",
                "4",
                "6",
                "12",
                "6"
            )
        )

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            ActivityCompat.finishAffinity(requireActivity())
        }

        binding.button.setOnClickListener {
            view.findNavController().navigate(
                TitleFragmentDirections.actionTitleFragmentToQuizFragment(
                    answers.toTypedArray(),
                    0
                )
            )
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}