package lpmiar.pnoel;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity {

    private List<Enfant> enfants = new ArrayList<>();
    private JSONObject jsonObject;
    private List<String> tab = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tab.add("Age");
        for (Integer i = 0; i <13;  i ++){
            tab.add(i.toString());
        }
        super.onCreate(savedInstanceState);
        Spinner listeAge = findViewById(R.id.spinner);

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                tab
        );        //Enfin on passe l'adapter au Spinner et c'est tout
        listeAge.setAdapter(adapter);

        setContentView(R.layout.activity_main);
        RequestQueue rq = Volley.newRequestQueue(this);
        String url = "https://data.nantesmetropole.fr/api/records/1.0/search/?dataset=244400404_prenoms-enfants-nes-nantes&facet=prenom&facet=sexe&facet=annee_naissance";
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonObject = new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
