package lpmiar.pnoel;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import android.widget.ArrayAdapter;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private List<Enfant> enfants = new ArrayList<>();

    private JSONObject jsonObject;
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

                filtre(rb.getText().toString(), date_de_naissance, liste);
            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tab);
        listeAge.setAdapter(dataAdapter);
        String url = "https://data.nantesmetropole.fr/api/records/1.0/search/?dataset=244400404_prenoms-enfants-nes-nantes&facet=prenom&facet=sexe&facet=annee_naissance";

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
                                dataEnfant.add(enfant);
                                Log.i("enfant:", enfant.toString());

                            }
                    }
                });
        }
        public void filtre(String sexe, String annee_naissance, ListView liste){
            String url = "https://data.nantesmetropole.fr/api/records/1.0/search/?dataset=244400404_prenoms-enfants-nes-nantes&facet=prenom&facet=sexe&facet=annee_naissance";

            if (! sexe.equals("Tous")){
                url = "https://data.nantesmetropole.fr/api/records/1.0/search/?dataset=244400404_prenoms-enfants-nes-nantes&facet=prenom&facet=sexe&facet=annee_naissance";
            }
            if (! annee_naissance.equals("ddn")){
                url = "https://data.nantesmetropole.fr/api/records/1.0/search/?dataset=244400404_prenoms-enfants-nes-nantes&facet=prenom&facet=sexe&facet=annee_naissance";
            }
            final EnfantArrayAdapter  dataEnfant = new EnfantArrayAdapter(this,R.layout.list_item_layout, R.id.text1, enfants);
            dataEnfant.clear();
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
                                dataEnfant.add(enfant);
                            }
                        }
                    });
            liste.setAdapter(dataEnfant);
        }
}
