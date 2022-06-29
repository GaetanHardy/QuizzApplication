package paci.iut.quizzapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    ArrayList<Question> listQuestion = new ArrayList<Question>();
    private Button button1;
    private Button button2;
    private Button button3;
    private Button suivant;
    private ArrayList<Question> listQuestions= new ArrayList<Question>();
    Integer score = 0;
    private Question questionA;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String username = bundle.getString("username");
        MutableLiveData<Utilisateur> user = new MutableLiveData<>(new Utilisateur());
        Database.getUser(username).observe(this, new Observer<Utilisateur>() {
            @Override
            public void onChanged(Utilisateur utilisateur) {
                if(utilisateur != null) user.setValue(utilisateur);
            }
        });

        this.suivant = this.findViewById(R.id.suivant);

        suivant.setEnabled(false);

        this.button1 = this.findViewById(R.id.reponseA);
        this.button2 = this.findViewById(R.id.reponseB);
        this.button3 = this.findViewById(R.id.reponseC);
        button1.setBackgroundColor(Color.CYAN);
        button2.setBackgroundColor(Color.CYAN);
        button3.setBackgroundColor(Color.CYAN);
        suivant.setBackgroundColor(Color.CYAN);

        Database.getQuestions().observe(this, new Observer<ArrayList<Question>>() {
            @Override
            public void onChanged(ArrayList<Question> questions) {
                if (!questions.isEmpty()) {
                    listQuestions = questions;
                    Question question = questions.get(0);

                    questionA = question;
                    TextView listView = (TextView) findViewById(R.id.question);
                    listView.setText(question.question);

                    Button buttonA = (Button) findViewById(R.id.reponseA);
                    buttonA.setText(question.propositions.get(0));

                    Button buttonB = (Button) findViewById(R.id.reponseB);
                    buttonB.setText(question.propositions.get(1));

                    Button buttonC = (Button) findViewById(R.id.reponseC);
                    buttonC.setText(question.propositions.get(2));
                    questions.remove(0);
                }
            }
        });



        this.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(questionA.propositions.get(0).charAt(0) == (questionA.answer.charAt(0))) {
                    button1.setBackgroundColor(Color.GREEN);
                    button1.setClickable(false);
                    button2.setClickable(false);
                    button3.setClickable(false);
                    suivant.setEnabled(true);
                    score += 20;
                } else {
                    button1.setBackgroundColor(Color.RED);
                    button1.setClickable(false);
                    button2.setClickable(false);
                    button3.setClickable(false);
                    suivant.setEnabled(true);
                }
            }
        });

        this.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(questionA.propositions.get(1).charAt(0) == (questionA.answer.charAt(0))) {
                    button2.setBackgroundColor(Color.GREEN);
                    button1.setClickable(false);
                    button2.setClickable(false);
                    button3.setClickable(false);
                    suivant.setEnabled(true);
                    score += 20;
                } else {
                    button2.setBackgroundColor(Color.RED);
                    button1.setClickable(false);
                    button2.setClickable(false);
                    button3.setClickable(false);
                    suivant.setEnabled(true);
                }
            }
        });

        this.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(questionA.propositions.get(2).charAt(0) == (questionA.answer.charAt(0))) {
                    button3.setBackgroundColor(Color.GREEN);
                    button1.setClickable(false);
                    button2.setClickable(false);
                    button3.setClickable(false);
                    suivant.setEnabled(true);
                    score += 20;
                } else {
                    button3.setBackgroundColor(Color.RED);
                    button1.setClickable(false);
                    button2.setClickable(false);
                    button3.setClickable(false);
                    suivant.setEnabled(true);
                }
            }
        });

        this.suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listQuestions.size() > 0) {
                    suivant.setEnabled(false);
                    button1.setClickable(true);
                    button2.setClickable(true);
                    button3.setClickable(true);
                    button1.setBackgroundColor(Color.CYAN);
                    button2.setBackgroundColor(Color.CYAN);
                    button3.setBackgroundColor(Color.CYAN);
                    Question question = listQuestions.get(0);

                    questionA = question;
                    TextView listView = (TextView) findViewById(R.id.question);
                    listView.setText(question.question);

                    Button buttonA = (Button) findViewById(R.id.reponseA);
                    buttonA.setText(question.propositions.get(0));

                    Button buttonB = (Button) findViewById(R.id.reponseB);
                    buttonB.setText(question.propositions.get(1));

                    Button buttonC = (Button) findViewById(R.id.reponseC);
                    buttonC.setText(question.propositions.get(2));
                    listQuestions.remove(0);
                } else {
                    if(user != null) {
                        user.getValue().setPoints(score);
                        Database.updateScore(user.getValue());
                        Intent intent = new Intent(QuestionActivity.this, FriendActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("username", username);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
}
