package lpmiar.pnoel;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import android.widget.ArrayAdapter;


public class MainActivity extends AppCompatActivity {

    private List<Enfant> enfants = new ArrayList<>();
    private JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MainActivity context = this;

        setContentView(R.layout.activity_main);

        List<String> tab = new ArrayList<>();
        tab.add("Age");
        for (Integer i = 0; i <13;  i ++){
            tab.add(i.toString());
        }
        super.onCreate(savedInstanceState);
        Spinner listeAge = findViewById(R.id.spinner);
        final ListView liste = findViewById(R.id.liste);
        Button button = findViewById(R.id.filtrer);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tab);
        listeAge.setAdapter(dataAdapter);
        String url = "https://data.nantesmetropole.fr/api/records/1.0/search/?dataset=244400404_prenoms-enfants-nes-nantes&facet=prenom&facet=sexe&facet=annee_naissance";

        Ion.with(context)
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            jsonObject = new JSONObject(result);
                            JSONArray jr = jsonObject.getJSONArray("records");
                            for (int i = 0; i < jr.length(); i++) {
                                JSONObject o = jr.getJSONObject(i);
                                JSONObject fields = o.getJSONObject("fields");
                                Enfant enfant = new Enfant(fields.getInt("annee_naissance"), fields.getString("prenom"), fields.getString("sexe"));
                                enfants.add(enfant);
                                ArrayAdapter<Enfant> dataEnfant = new ArrayAdapter<Enfant>(context, android.R.layout.simple_list_item_1, enfants);
                                liste.setAdapter(dataEnfant);
                            }
                            Log.i("alluile1", enfants.toString());
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        }
}
