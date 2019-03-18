package indonesia.ima.com.ima;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailArtikel extends AppCompatActivity {
    private String mPostKeyIdArtikel = null;
    private String JSON_STRING;
    TextView nama, deskripsi_artikel, artikel_created_date, judul_artikel;
    private CollapsingToolbarLayout collapsingToolbar;
    private String id_user, id_artikel,namaArtikel, judulArtikel, artikelDate, deskripsiArtikel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artikel);

        mPostKeyIdArtikel = getIntent().getExtras().getString("id_artikel");

        deskripsi_artikel = findViewById(R.id.deskripsi_artikel);
        artikel_created_date = findViewById(R.id.artikel_created_date);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        getJSON();
    }

    @SuppressLint("SetTextI18n")
    private void showData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY2);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                id_artikel = jo.getString(konfigurasi.id_artikel);
                judulArtikel = jo.getString(konfigurasi.judul_artikel);
                artikelDate = jo.getString(konfigurasi.artikel_created_date);
                deskripsiArtikel = jo.getString(konfigurasi.deskripsi_artikel);

                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_artikel, id_artikel);

                list.add(data);

                if (mPostKeyIdArtikel.equals(jo.getString("id_artikel"))) {
                    deskripsi_artikel.setText(deskripsiArtikel);
                    artikel_created_date.setText(artikelDate);
                    collapsingToolbar.setTitle(judulArtikel);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showData();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_DATAARTIKEL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
