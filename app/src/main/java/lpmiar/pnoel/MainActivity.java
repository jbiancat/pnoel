package lpmiar.pnoel;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
        setContentView(R.layout.activity_main);

        List<String> tab = new ArrayList<>();
        tab.add("Age");
        for (Integer i = 0; i <13;  i ++){
            tab.add(i.toString());
        }
        super.onCreate(savedInstanceState);
        Spinner listeAge = findViewById(R.id.spinner);
        ListView liste = findViewById(R.id.liste);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tab);

        listeAge.setAdapter(dataAdapter);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://data.nantesmetropole.fr/api/records/1.0/search/?dataset=244400404_prenoms-enfants-nes-nantes&facet=prenom&facet=sexe&facet=annee_naissance";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray jr = jsonObject.getJSONArray("records");
                            for (int i=0; i<jr.length(); i++) {
                                JSONObject o = jr.getJSONObject(i);
                                JSONObject fields = o.getJSONObject("fields");
                                Log.i("prout", o.toString());
                                Enfant e = new Enfant(fields.getInt("annee_naissance"), fields.getString("prenom"), fields.getString("sexe"));
                                enfants.add(e);
                                Log.i("nullos",e.toString());
                            }
                            Log.i("enfants",enfants.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Display the first 500 characters of the response string.
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("echec", "echec");
            }
        });

        ArrayAdapter<Enfant> dataEnfant = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, enfants);
        liste.setAdapter(dataEnfant);

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
