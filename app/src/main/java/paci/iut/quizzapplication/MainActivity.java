package paci.iut.quizzapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private Button connexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.login = this.findViewById(R.id.username);
        this.password = this.findViewById(R.id.password);
        this.connexion = this.findViewById(R.id.login);

        this.connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database.login(login.getText().toString(), password.getText().toString())
                        .observe(MainActivity.this, new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer success) {
                                if(success == 0) {
                                    Intent intent = new Intent(MainActivity.this, FriendActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("username", login.getText().toString());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
                                else if(success == 1) {
                                    Toast.makeText(MainActivity.this, "Le pseudo ou le mot de passe est incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}