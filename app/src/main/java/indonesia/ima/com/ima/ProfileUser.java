package indonesia.ima.com.ima;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileUser extends AppCompatActivity {
    private ImageView gambar, gambar2;
    private TextView nama, nama2, username, password, email, alamat, notelp;
    private String id_userS;
    private String namaS;
    private String usernameS;
    private String passwordS;
    private String no_telpS;
    private String email_pribadiS;
    private String foto_profileS;
    private String chapterS;
    private String register_date;
    private String JSON_STRING;
    private RelativeLayout linear;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        gambar = findViewById(R.id.gambar);
        gambar2 = findViewById(R.id.gambar2);
        nama = findViewById(R.id.nama);
        nama2 = findViewById(R.id.nama2);
        email = findViewById(R.id.email);
        alamat = findViewById(R.id.alamat);
        notelp = findViewById(R.id.notelp);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        linear = findViewById(R.id.linear);
        loading = findViewById(R.id.loading);

        linear.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);

        getJSON();
    }

    @SuppressLint("SetTextI18n")
    private void showData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY3);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                id_userS = jo.getString(konfigurasi.id_user);
                namaS = jo.getString(konfigurasi.nama);
                usernameS = jo.getString(konfigurasi.username);
                passwordS = jo.getString(konfigurasi.password);
                no_telpS = jo.getString(konfigurasi.no_telp);
                email_pribadiS = jo.getString(konfigurasi.email_pribadi);
                foto_profileS = jo.getString(konfigurasi.foto_profile);
                chapterS = jo.getString(konfigurasi.chapter);
                register_date = jo.getString(konfigurasi.register_date);

                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_user, id_userS);

                list.add(data);

                String id_user = getIdUser();
                if (id_user.equals(jo.getString("id_user"))) {
                    nama.setText(namaS);
                    nama2.setText(namaS);
                    username.setText(usernameS);
                    password.setText(passwordS);
                    email.setText(email_pribadiS);
                    alamat.setText(chapterS);
                    notelp.setText(no_telpS);
                    Glide.with(ProfileUser.this)
                            .load("http://imaindonesia.000webhostapp.com/userimage/" + foto_profileS)
                            .apply(RequestOptions.circleCropTransform())
                            .into(gambar);
                    Glide.with(ProfileUser.this)
                            .load("http://imaindonesia.000webhostapp.com/userimage/" + foto_profileS)
                            .apply(RequestOptions.circleCropTransform())
                            .into(gambar2);

                }
                linear.setVisibility(View.VISIBLE);
                loading.setVisibility(View.INVISIBLE);
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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_DATAUSER);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    public String getIdUser() {
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_user = preferences.getString("id_user", "null");
        return id_user;
    }
}
