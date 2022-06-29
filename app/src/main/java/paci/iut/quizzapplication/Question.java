package paci.iut.quizzapplication;

import java.util.ArrayList;

public class Question {
    public String question;
    public String answer;
    public ArrayList<String> propositions;

    public Question(String question, String answer, ArrayList<String> propositions) {
        this.question = question;
        this.answer = answer;
        this.propositions = propositions;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ArrayList<String> getPropositions() {
        return propositions;
    }

    public void setPropositions(ArrayList<String> propositions) {
        this.propositions = propositions;
    }
}
