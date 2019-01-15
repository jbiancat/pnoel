package lpmiar.pnoel;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.widget.ArrayAdapter;


public class MainActivity extends AppCompatActivity {

    private List<Enfant> enfants = new ArrayList<>();
    private List<Enfant> enfantsFiltre = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final Spinner ddn = findViewById(R.id.spinner);
        List<String> tab = new ArrayList<>();
        tab.add("ddn");
        for (Integer i = 1990; i <2018;  i ++){
            tab.add(i.toString());
        }
        super.onCreate(savedInstanceState);
        Spinner listeAge = findViewById(R.id.spinner);
        final ListView liste = findViewById(R.id.liste);
        final EnfantArrayAdapter  dataEnfant = new EnfantArrayAdapter(this, R.layout.list_item_layout);
        liste.setAdapter(dataEnfant);
        Button button = findViewById(R.id.filtrer);
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton)findViewById(selectedId);
                String date_de_naissance = ddn.getSelectedItem().toString();

                filtre(rb.getText().toString(), date_de_naissance, liste, dataEnfant);
            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tab);
        listeAge.setAdapter(dataAdapter);
        String url = "https://data.nantesmetropole.fr/api/records/1.0/search/?dataset=244400404_prenoms-enfants-nes-nantes&rows=1000&facet=prenom&facet=sexe&facet=annee_naissance";

        Ion.with(this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        JsonArray jArray = result.get("records").getAsJsonArray();
                        Iterator<JsonElement> ite = jArray.iterator();
                            while (ite.hasNext()) {
                                JsonObject fields = ite.next().getAsJsonObject().get("fields").getAsJsonObject();
                                Enfant enfant = new Enfant(fields.get("annee_naissance").getAsInt(), fields.get("prenom").getAsString(), fields.get("sexe").getAsString());
                                enfants.add(enfant);
                                dataEnfant.add(enfant);
                                Log.i("enfant:", enfant.toString());

                            }
                    }
                });
    }


        public void filtre(String sexe, String annee_naissance, ListView liste, EnfantArrayAdapter enfantsAdapter){

            //Recuperation de la liste d'enfant depuis l'ArrayAdapter et filtrer sur le sexe et l'annee de naissance
            enfantsFiltre.clear();
            if(sexe.contains("ç")) sexe = sexe.replace("ç","c");
            for (Enfant e : enfants) {
                if (e.getSexe().equals(sexe.toUpperCase()) && String.valueOf(e.getAnneeNaissance()).equals(annee_naissance)){
                    enfantsFiltre.add(e);
                }
            }


            if (sexe.equals("Tous")){
                clearFiltre(liste, enfantsAdapter);
            } else {
                enfantsAdapter.clear();
                enfantsAdapter.addAll(enfantsFiltre);
                liste.setAdapter(enfantsAdapter);
            }

        }

        public void clearFiltre(ListView liste, EnfantArrayAdapter enfantsAdapter){
            enfantsAdapter.clear();
            enfantsAdapter.addAll(enfants);
            liste.setAdapter(enfantsAdapter);

        }
}
