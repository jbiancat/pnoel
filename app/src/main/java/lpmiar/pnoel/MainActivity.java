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
    private List<Enfant> enfantsFiltreSage = new ArrayList<>();
    private List<Enfant> enfantsFiltreLettre = new ArrayList<>();
    private List<Enfant> enfantsFiltreKdo = new ArrayList<>();



    private List<String> tab = new ArrayList<>();
    private Integer lastFiltreSexe = null;
    private String lastFiltreDDN = null;
    private Integer lastFiltreSage = null;
    private Integer lastFiltreLettre = null;
    private Integer lastFiltreKdo = null;


    public final static String DEFAUT_VALUE = "Tous";
    public final static String DEFAUT_DDN = "Année de naissance";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);

        tab.add(DEFAUT_DDN);
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

        FloatingActionButton action = (FloatingActionButton) findViewById(R.id.filtrerFloatingBtn);

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFiltreDialog(liste,dataEnfant);
            }
        });
    }


        private void filtre(String sexe, String annee_naissance, String sage, String lettre, String kdo, ListView liste, EnfantArrayAdapter enfantsAdapter) {

            enfantsFiltreSage.clear();
            enfantsFiltreLettre.clear();
            enfantsFiltreKdo.clear();
            enfantsFiltre.clear();
            if (sexe.contains("ç")) sexe = sexe.replace("ç", "c");

            //Filtre sur sexe et annee de naissance
            if(sexe.equals(DEFAUT_VALUE)){
                if(!annee_naissance.equals(DEFAUT_DDN)){
                    //si tous les sexes et une date de naissance précise
                    for (Enfant e : enfants) {
                        if (String.valueOf(e.getAnneeNaissance()).equals(annee_naissance)) {
                            enfantsFiltre.add(e);
                        }
                    }
                } else {
                    for (Enfant e : enfants) {
                        enfantsFiltre.add(e);
                    }
                }
            } else {
                if(annee_naissance.equals(DEFAUT_DDN)){
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

            //si un de ces strings est differents de "Tous", on applique le filtre sur Sage, Lettre et Cadeau
            if (!sage.equals(DEFAUT_VALUE) || !lettre.equals(DEFAUT_VALUE) || !kdo.equals(DEFAUT_VALUE)){
                //filtre Sage
                if (sage.equals(Enfant.TEXT_SAGE)){
                    for (Enfant e : enfantsFiltre) {
                        if (e.isSage()) enfantsFiltreSage.add(e);
                    }
                } else if (sage.equals(Enfant.TEXT_PAS_SAGE)){
                    for (Enfant e : enfantsFiltre) {
                        if (!e.isSage()) enfantsFiltreSage.add(e);
                    }
                } else {
                    for(Enfant e : enfantsFiltre){
                        enfantsFiltreSage.add(e);
                    }
                }

                //filtre Lettre
                if (lettre.equals(Enfant.TEXT_LETTRE_RECU)){
                    for(Enfant e : enfantsFiltreSage){
                        if (e.isLettreRecu()) enfantsFiltreLettre.add(e);
                    }
                } else if (lettre.equals(Enfant.TEXT_LETTRE_nRECU)){
                    for(Enfant e : enfantsFiltreSage){
                        if (!e.isLettreRecu()) enfantsFiltreLettre.add(e);
                    }
                } else {
                    for (Enfant e : enfantsFiltreSage){
                        enfantsFiltreLettre.add(e);
                    }
                }

                //filtre kdo
                if (kdo.equals(Enfant.TEXT_KDO_LIVRE)){
                    for (Enfant e : enfantsFiltreLettre){
                        if (e.isCadeauLivre()) enfantsFiltreKdo.add(e);
                    }
                } else if (kdo.equals(Enfant.TEXT_KDO_nLIVRE)){
                    for (Enfant e : enfantsFiltreLettre){
                        if (!e.isCadeauLivre()) enfantsFiltreKdo.add(e);
                    }
                } else {
                    for (Enfant e : enfantsFiltreLettre){
                        enfantsFiltreKdo.add(e);
                    }
                }

                //On rafraichi la liste d'enfants filtré avec les trois derniers filtres
                enfantsFiltre.clear();
                for (Enfant e : enfantsFiltreKdo){
                    enfantsFiltre.add(e);
                }
            }

            
            enfantsAdapter.clear();
            enfantsAdapter.addAll(enfantsFiltre);
            liste.setAdapter(enfantsAdapter);
        }

        private boolean clearFiltre(ListView liste, EnfantArrayAdapter enfantsAdapter){
            enfantsFiltreSage.clear();
            enfantsFiltreLettre.clear();
            enfantsFiltreKdo.clear();
            enfantsFiltre.clear();
            
            enfantsAdapter.clear();
            enfantsAdapter.addAll(enfants);
            liste.setAdapter(enfantsAdapter);
            lastFiltreSexe = null;
            lastFiltreDDN = null;
            lastFiltreSage = null;
            lastFiltreLettre = null;
            lastFiltreKdo = null;
            return true;
        }

    private void callFiltreDialog(final ListView liste, final EnfantArrayAdapter dataEnfant) {
        final Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.popup_filtre);
        myDialog.setCancelable(false);

        //Boutons de la popin
        Button filtrerBtn = (Button) myDialog.findViewById(R.id.filtrerBtn);
        Button resetBtn = (Button) myDialog.findViewById(R.id.resetBtn);
        ImageButton closeBtn = (ImageButton) myDialog.findViewById(R.id.imageButton);


        //RadioGroup pour filtrer
        final RadioGroup radioGroup = myDialog.findViewById(R.id.radioGroup);
        final Spinner ddn = myDialog.findViewById(R.id.spinnerAge);
        final RadioGroup rgSage = myDialog.findViewById(R.id.rgSage);
        final RadioGroup rgLettre = myDialog.findViewById(R.id.rgLettre);
        final RadioGroup rgKdo = myDialog.findViewById(R.id.rgKdo);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tab);
        ddn.setAdapter(dataAdapter);

        if(lastFiltreSexe != null){
            radioGroup.check(lastFiltreSexe);
        }

        if (lastFiltreDDN != null && !lastFiltreDDN.isEmpty()){
            Integer spinnerPosition = dataAdapter.getPosition(lastFiltreDDN);
            ddn.setSelection(spinnerPosition);
        }

        if (lastFiltreSage != null ){
            rgSage.check(lastFiltreSage);
        }
        if (lastFiltreLettre != null){
            rgLettre.check(lastFiltreLettre);
        }
        if (lastFiltreKdo != null){
            rgKdo.check(lastFiltreKdo);
        }


        myDialog.show();

        filtrerBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //id de radiobutton selectionné
                int selectedId = radioGroup.getCheckedRadioButtonId();
                int idSage = rgSage.getCheckedRadioButtonId();
                int idLettre = rgLettre.getCheckedRadioButtonId();
                int idKdo = rgKdo.getCheckedRadioButtonId();

                //on recupère le button
                RadioButton rbSexe = (RadioButton) myDialog.findViewById(selectedId);
                RadioButton rbSage = (RadioButton) myDialog.findViewById(idSage);
                RadioButton rbLettre = (RadioButton) myDialog.findViewById(idLettre);
                RadioButton rbKdo = (RadioButton) myDialog.findViewById(idKdo);

                //on recupère le texte du button
                String sexeStr = rbSexe.getText().toString();
                String date_de_naissance = ddn.getSelectedItem().toString();
                String sageStr = rbSage.getText().toString();
                String lettreStr = rbLettre.getText().toString();
                String kdoStr = rbKdo.getText().toString();

                //on garde en mémoire les valeurs des filtres
                lastFiltreSexe = selectedId;
                lastFiltreDDN = date_de_naissance;
                lastFiltreSage = idSage;
                lastFiltreLettre = idLettre;
                lastFiltreKdo = idKdo;

                //on filtre
                filtre(sexeStr, date_de_naissance, sageStr, lettreStr, kdoStr, liste, dataEnfant);
                myDialog.hide();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFiltre(liste, dataEnfant);
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
