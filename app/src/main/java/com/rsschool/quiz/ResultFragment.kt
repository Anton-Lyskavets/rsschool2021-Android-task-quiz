package com.rsschool.quiz

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import com.rsschool.quiz.databinding.FragmentResultBinding


class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var answers: MutableList<Questions>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        val arguments = ResultFragmentArgs.fromBundle(requireArguments())
        answers = arguments.questions.toMutableList()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            view?.findNavController()
                ?.navigate(ResultFragmentDirections.actionResultFragmentToTitleFragment())
        }

        binding.apply {

            "${rightAnswers()} / ${answers.size} ".also { fin.text = it }

            buttonAgain.setOnClickListener {
                view?.findNavController()
                    ?.navigate(ResultFragmentDirections.actionResultFragmentToTitleFragment())
            }

            buttonShare.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.setType("text/plain")
                    .putExtra(Intent.EXTRA_SUBJECT, "Quiz results")
                    .putExtra(Intent.EXTRA_TEXT, createMessage())
                startActivity(intent)
            }

            buttonExit.setOnClickListener {
                ActivityCompat.finishAffinity(requireActivity())
            }
        }
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createMessage(): String {
        var count = 1
        val stringBuilder = StringBuilder("")
        return stringBuilder.apply {
            append("Your result: ${rightAnswers()} of ${answers.size} \n\n")
            for (question in answers) {
                append(
                    "${count++}) ${question.question}\n" +
                            "Your answer: ${answerUser(question)} \n\n"
                )
            }
        }.toString()
    }

    private fun rightAnswers(): Int {
        return answers.filter { it.rightAnswer == answerUser(it) }.size
    }

    private fun answerUser(questions: Questions): String {
        val answer = questions.answerUser
        return when (answer) {
            0 -> questions.answer1 ?: ""
            1 -> questions.answer2 ?: ""
            2 -> questions.answer3 ?: ""
            3 -> questions.answer4 ?: ""
            4 -> questions.answer5 ?: ""
            else -> ""
        }
    }
}