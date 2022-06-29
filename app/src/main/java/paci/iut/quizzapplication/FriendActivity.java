package paci.iut.quizzapplication;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class FriendActivity extends AppCompatActivity {

    public MutableLiveData<ArrayList<Utilisateur>> listFriends = new MutableLiveData<ArrayList<Utilisateur>>();
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String username = bundle.getString("username");

        Database.getScore().observe(this, new Observer<ArrayList<Utilisateur>>() {
            @Override
            public void onChanged(ArrayList<Utilisateur> strings) {
                if(strings != null)listFriends.setValue(strings);
            }
        });

        Database.getFriends(username).observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(FriendActivity.this, android.R.layout.simple_list_item_1, strings);
                ListView listView = (ListView)findViewById(R.id.listFriends);
                listView.setAdapter(adapter);
                ArrayList<Integer> listeScore = new ArrayList<Integer>();
                Utilisateur user = new Utilisateur();
                if(listFriends != null) {
                    for(Utilisateur u : listFriends.getValue()) {
                        for(int i = 0; i < strings.size(); i++) {
                            if(u.pseudo.equals(strings.get(i))) {
                                listeScore.add(u.points);
                            }
                            if(u.pseudo.equals(username)) {
                                user = u;
                            }
                        }
                    }
                    ArrayAdapter<Integer> adapterScore = new ArrayAdapter<Integer>(FriendActivity.this, android.R.layout.simple_list_item_1, listeScore);
                    ListView listViewScore = (ListView)findViewById(R.id.scoreFriends);
                    listViewScore.setAdapter(adapterScore);

                    String userAdapter = "Bonjour " + username + ", vous avez " + user.getPoints() + " points";
                    TextView userView = (TextView)findViewById(R.id.utilisateur);
                    userView.setText(userAdapter);
                }
            }
        });

        this.start = this.findViewById(R.id.button);

        this.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendActivity.this, QuestionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            };
        });
    }
}
