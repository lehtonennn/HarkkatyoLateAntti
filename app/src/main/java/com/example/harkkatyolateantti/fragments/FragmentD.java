package com.example.harkkatyolateantti.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.example.harkkatyolateantti.Question;
import com.example.harkkatyolateantti.R;

public class FragmentD extends Fragment {

    private TextView textViewQuestion;
    private Button buttonOption1;
    private Button buttonOption2;
    private Button buttonOption3;
    private Button buttonOption4;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d, container, false);

        textViewQuestion = view.findViewById(R.id.textViewQuestion);
        buttonOption1 = view.findViewById(R.id.buttonOption1);
        buttonOption2 = view.findViewById(R.id.buttonOption2);
        buttonOption3 = view.findViewById(R.id.buttonOption3);
        buttonOption4 = view.findViewById(R.id.buttonOption4);

        // Initialize questions
        initializeQuestions();

        // Display the first question
        showQuestion();

        // Set click listeners for options buttons
        buttonOption1.setOnClickListener(v -> checkAnswer(1));
        buttonOption2.setOnClickListener(v -> checkAnswer(2));
        buttonOption3.setOnClickListener(v -> checkAnswer(3));
        buttonOption4.setOnClickListener(v -> checkAnswer(4));

        return view;
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();

        // Add questions with correct and incorrect options
        questions.add(new Question("Question 1?", "Correct Answer 1", "Incorrect Answer 1", "Incorrect Answer 2", "Incorrect Answer 3", 1));
        questions.add(new Question("Question 2?", "Correct Answer 2", "Incorrect Answer 1", "Incorrect Answer 2", "Incorrect Answer 3", 2));
        questions.add(new Question("Mikä on Suomen suurin kunta pinta-alaltaan?", "Inari", "Sodankylä", "Enontekiö", "Rovaniemi", 1));
        questions.add(new Question("Mikä on Suomen pienin kunta pinta-alaltaan?", "Kaskinen", "Vårdö", "Kökar", "Sottunga", 4));
        questions.add(new Question("Missä kunnassa sijaitsee Suomen pohjoisin piste?", "Utsjoki", "Nuorgam", "Inari", "Sodankylä", 2));
        questions.add(new Question("Mikä on Suomen suurin kaupunki väkiluvultaan?", "Helsinki", "Espoo", "Tampere", "Vantaa", 1));
        questions.add(new Question("Missä kunnassa sijaitsee Suomen eteläisin piste?", "Hanko", "Parainen", "Kemiönsaari", "Raasepori", 1));
        questions.add(new Question("Kuinka monta kuntaa Suomessa on yhteensä?", "311", "295", "320", "301", 1));
        questions.add(new Question("Mikä on Suomen vanhin kaupunki perustamisvuodeltaan?", "Turku", "Porvoo", "Rauma", "Helsinki", 2));
        questions.add(new Question("Missä kunnassa sijaitsee Suomen läntisin piste?", "Kristiinankaupunki", "Kaskinen", "Vaasa", "Korsnäs", 4));

        // Shuffle the questions
        Collections.shuffle(questions);
    }

    private void showQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            textViewQuestion.setText(currentQuestion.getQuestion());
            buttonOption1.setText(currentQuestion.getOption1());
            buttonOption2.setText(currentQuestion.getOption2());
            buttonOption3.setText(currentQuestion.getOption3());
            buttonOption4.setText(currentQuestion.getOption4());
        } else {
            // All questions have been answered
            showResult();
        }
    }

    private void checkAnswer(int selectedOption) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        if (selectedOption == currentQuestion.getCorrectOption()) {
            correctAnswers++;
        }

        // Move to the next question
        currentQuestionIndex++;
        showQuestion();
    }

    private void showResult() {
        // Display the result to the user
        int totalQuestions = questions.size();
        String resultMessage = "You answered " + correctAnswers + " out of " + totalQuestions + " questions correctly.";
        // You can display this message in a TextView or show it in a dialog
    }
}