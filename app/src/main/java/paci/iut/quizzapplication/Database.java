package paci.iut.quizzapplication;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Database {

    public static MutableLiveData<Integer> login(String username, String password) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        MutableLiveData<Integer> exist = new MutableLiveData<Integer>(-1);

        database.collection("users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            ArrayList<String> listFriends = new ArrayList<String>();
                            for (DocumentSnapshot d : list) {
                                String dataUsername = d.getData().get("pseudo").toString();
                                String dataPassword = d.getData().get("password").toString();
                                List<String> test = (ArrayList)d.getData().get("friends");
                                if (username.equals(dataUsername) && password.equals(dataPassword)) {
                                    exist.setValue(0);
                                    listFriends = (ArrayList)d.getData().get("friends");
                                }
                            }

                            if(exist.getValue() == -1) {
                                exist.setValue(1);
                            }
                        }
                    }
                });

        return exist;
    }

    public static MutableLiveData<ArrayList<String>> getFriends(String username) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        MutableLiveData<ArrayList<String>> listFriends = new MutableLiveData<>(new ArrayList<String>());
        database.collection("users").document(username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot queryDocumentSnapshots) {
                        ArrayList<String> friends = (ArrayList) queryDocumentSnapshots.getData().get("friends");
                        listFriends.setValue(friends);
                    }

                });
        return listFriends;
    }

    public static MutableLiveData<Utilisateur> getUser(String username) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        MutableLiveData<Utilisateur> utilisateur = new MutableLiveData<>(new Utilisateur());
        database.collection("users").document(username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.exists()) {
                            String pseudo = queryDocumentSnapshots.getData().get("pseudo").toString();
                            Integer points = Integer.parseInt(queryDocumentSnapshots.getData().get("points").toString());
                            Utilisateur u = new Utilisateur(pseudo, points);
                            utilisateur.setValue(u);
                        }

                    }

                });
        return utilisateur;
    }

    public static MutableLiveData<ArrayList<Utilisateur>> getScore() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        MutableLiveData<ArrayList<Utilisateur>> listFriends = new MutableLiveData<>(new ArrayList<Utilisateur>());
        database.collection("users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Utilisateur> listScore = new ArrayList<Utilisateur>();
                        List<DocumentSnapshot> users = (ArrayList) queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : users) {
                            String dataPseudo = d.getData().get("pseudo").toString();
                            Integer dataPoints = Integer.parseInt(d.getData().get("points").toString());
                            Utilisateur u = new Utilisateur(dataPseudo, dataPoints);
                            listScore.add(u);
                        }
                        listFriends.setValue(listScore);
                    }

                });
        return listFriends;
    }

    public static MutableLiveData<ArrayList<Question>> getQuestions() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        MutableLiveData<ArrayList<Question>> questions = new MutableLiveData<ArrayList<Question>>(new ArrayList<Question>());
        database.collection("question").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            Random rdm = new Random();
                            ArrayList<Integer> indexList = new ArrayList<>();
                            while (indexList.size() < 5) {
                                Integer rdmIndex = rdm.nextInt(list.size());
                                if(!indexList.contains(rdmIndex)) {
                                    indexList.add(rdmIndex);
                                }
                            }
                            for(Integer i = 0; i < indexList.size(); i++) {

                                Map<String, Object> data = list.get(indexList.get(i)).getData();
                                questions.getValue().add(new Question(
                                        data.get("intitule").toString(),
                                        data.get("answer").toString(),
                                        (ArrayList<String>)data.get("propositions"))
                                );
                            }
                            questions.setValue(questions.getValue());
                        }
                    }
                });
        return questions;
    }

    public static MutableLiveData<Boolean> updateScore(Utilisateur user) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        MutableLiveData<Boolean> success = new MutableLiveData(false);

        database.collection("users").document(user.getPseudo()).update("points", user.getPoints())
                .addOnSuccessListener(new OnSuccessListener() {

                    @Override
                    public void onSuccess(Object o) {
                        success.setValue(true);
                    }

                });

        return success;
    }
}
