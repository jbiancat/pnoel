package lpmiar.pnoel;


import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.widget.ArrayAdapter;


public class MainActivity extends AppCompatActivity {

    private List<Enfant> enfants = new ArrayList<>();
    private List<Enfant> enfantsFiltre = new ArrayList<>();
    private List<String> tab = new ArrayList<>();
    private Integer lastFiltreSexe = null;
    private String lastFiltreDDN = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);

        tab.add("Année de naissance");
        for (Integer i = 2005; i <2018;  i ++){
            tab.add(i.toString());
        }

        super.onCreate(savedInstanceState);

        final ListView liste = findViewById(R.id.liste);
        final EnfantArrayAdapter  dataEnfant = new EnfantArrayAdapter(this, R.layout.list_item_layout);
        liste.setAdapter(dataEnfant);



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

        FloatingActionButton action = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFiltreDialog(liste,dataEnfant);
            }
        });
    }


        private void filtre(String sexe, String annee_naissance, ListView liste, EnfantArrayAdapter enfantsAdapter) {
            boolean filtreCleared = false;
            enfantsFiltre.clear();
            if (sexe.contains("ç")) sexe = sexe.replace("ç", "c");

            if(sexe.equals("Tous")){
                if(!annee_naissance.equals("Année de naissance")){
                    //si tous les sexes et une date de naissance précise
                    for (Enfant e : enfants) {
                        if (String.valueOf(e.getAnneeNaissance()).equals(annee_naissance)) {
                            enfantsFiltre.add(e);
                        }
                    }
                } else {
                    filtreCleared = clearFiltre(liste, enfantsAdapter);
                }
            } else {
                if(annee_naissance.equals("Année de naissance")){
                    //si un sexe précis et pas de date de naissance
                    for (Enfant e : enfants) {
                        if (e.getSexe().equals(sexe.toUpperCase())) {
                            enfantsFiltre.add(e);
                        }
                    }
                } else {
                    //si un sexe précis et une date de naissance précise
                    for (Enfant e : enfants) {
                        if (e.getSexe().equals(sexe.toUpperCase()) && String.valueOf(e.getAnneeNaissance()).equals(annee_naissance)) {
                            enfantsFiltre.add(e);
                        }
                    }
                }
            }

            //si le filtre n'a pas été reset, on set la liste filtrée
            if (!filtreCleared) {
                enfantsAdapter.clear();
                enfantsAdapter.addAll(enfantsFiltre);
                liste.setAdapter(enfantsAdapter);
            }
        }

        private boolean clearFiltre(ListView liste, EnfantArrayAdapter enfantsAdapter){
            enfantsAdapter.clear();
            enfantsAdapter.addAll(enfants);
            liste.setAdapter(enfantsAdapter);
            return true;
        }

    private void callFiltreDialog(final ListView liste, final EnfantArrayAdapter dataEnfant) {
        final Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.popup_filtre);
        myDialog.setCancelable(false);
        Button filtrerBtn = (Button) myDialog.findViewById(R.id.filtrerBtn);
        Button resetBtn = (Button) myDialog.findViewById(R.id.resetBtn);
        ImageButton closeBtn = (ImageButton) myDialog.findViewById(R.id.imageButton);

        final RadioGroup radioGroup = myDialog.findViewById(R.id.radioGroup);
        final Spinner ddn = myDialog.findViewById(R.id.spinnerAge);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tab);
        ddn.setAdapter(dataAdapter);

        if(lastFiltreSexe != null){
            radioGroup.check(lastFiltreSexe);
        }

        if (lastFiltreDDN != null && !lastFiltreDDN.isEmpty()){
            Integer spinnerPosition = dataAdapter.getPosition(lastFiltreDDN);
            ddn.setSelection(spinnerPosition);
        }


        myDialog.show();

        filtrerBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) myDialog.findViewById(selectedId);
                String date_de_naissance = ddn.getSelectedItem().toString();

                lastFiltreSexe = selectedId;
                lastFiltreDDN = date_de_naissance;

                filtre(rb.getText().toString(), date_de_naissance, liste, dataEnfant);
                myDialog.hide();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFiltre(liste, dataEnfant);
                lastFiltreSexe = null;
                lastFiltreDDN = null;
                myDialog.hide();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.hide();
            }
        });


    }
}
