package com.rsschool.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.rsschool.quiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private lateinit var answers: MutableList<Questions>
    private var numberQuestions: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val arguments = QuizFragmentArgs.fromBundle(requireArguments())
        answers = arguments.questions.toMutableList()

        numberQuestions = arguments.numberQuestion

        newTheme()

        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        visibilityButton()

        fillingFragment(answers[numberQuestions].answerUser)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            earlyQuestion()
        }

        binding.apply {
            toolbar.setNavigationOnClickListener {
                earlyQuestion()
            }

            radioGroup.setOnCheckedChangeListener { _, id ->
                answers[numberQuestions].answerUser = when (id) {
                    R.id.option_one -> 0
                    R.id.option_two -> 1
                    R.id.option_three -> 2
                    R.id.option_four -> 3
                    R.id.option_five -> 4
                    else -> -1
                }
                binding.nextButton.visibility = View.VISIBLE
            }

            previousButton.setOnClickListener {
                earlyQuestion()
            }

            nextButton.setOnClickListener {
                if (numberQuestions == answers.size - 1) {
                    view?.findNavController()?.navigate(
                        QuizFragmentDirections.actionQuizFragmentToResultFragment(
                            answers.toTypedArray()
                        )
                    )
                } else {
                    numberQuestions++
                    view?.findNavController()?.navigate(
                        QuizFragmentDirections.actionQuizFragmentSelf(
                            answers.toTypedArray(), numberQuestions
                        )
                    )
                }
            }
        }
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillingFragment(answer: Int) {
        binding.apply {
            radioGroup.clearCheck()
            toolbar.title = "Question ${numberQuestions + 1} / ${answers.size}"

            question.text = answers[numberQuestions].question
            optionOne.text = answers[numberQuestions].answer1 ?: ""
            optionTwo.text = answers[numberQuestions].answer2 ?: ""
            optionThree.text = answers[numberQuestions].answer3 ?: ""
            optionFour.text = answers[numberQuestions].answer4 ?: ""
            optionFive.text = answers[numberQuestions].answer5 ?: ""

            when (answer) {
                0 -> radioGroup.check(R.id.option_one)
                1 -> radioGroup.check(R.id.option_two)
                2 -> radioGroup.check(R.id.option_three)
                3 -> radioGroup.check(R.id.option_four)
                4 -> radioGroup.check(R.id.option_five)
            }
        }
    }

    private fun earlyQuestion() {
        if (numberQuestions != 0) {
            numberQuestions--
            view?.findNavController()?.navigate(
                QuizFragmentDirections.actionQuizFragmentSelf(
                    answers.toTypedArray(), numberQuestions
                )
            )
        }
    }

    private fun visibilityButton() {
        if (numberQuestions == 0) {
            binding.toolbar.navigationIcon = null
            binding.previousButton.visibility = View.INVISIBLE
        }
        if (answers[numberQuestions].answerUser != -1)
            binding.nextButton.visibility = View.VISIBLE
        if (numberQuestions == answers.size - 1)
            binding.nextButton.text = "Submit"
    }

    private fun newTheme() {
        val theme: Int
        val color: Int
        when (numberQuestions) {
            0 -> {
                theme = R.style.Theme_Quiz_First
                color = R.color.quiz_first_statusBarColor
            }
            1 -> {
                theme = R.style.Theme_Quiz_Second
                color = R.color.quiz_second_statusBarColor
            }
            2 -> {
                theme = R.style.Theme_Quiz_Third
                color = R.color.quiz_third_statusBarColor
            }
            3 -> {
                theme = R.style.Theme_Quiz_Fourth
                color = R.color.quiz_fourth_statusBarColor
            }
            else -> {
                theme = R.style.Theme_Quiz_Fifth
                color = R.color.quiz_fifth_statusBarColor
            }
        }
        requireContext().setTheme(theme)
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireActivity(), color)
    }

}