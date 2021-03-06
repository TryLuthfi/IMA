package indonesia.asperindo.com.asperindo.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import indonesia.asperindo.com.asperindo.R;
import indonesia.asperindo.com.asperindo.fragment.FirstFragment;
import indonesia.asperindo.com.asperindo.fragment.SecondFragment;
import indonesia.asperindo.com.asperindo.konfigurasi.konfigurasi;
import indonesia.asperindo.com.asperindo.request.RequestHandler;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    @BindView(R.id.btn_fragment1)
    Button btnFragment1;
    @BindView(R.id.btn_fragment2)
    Button btnFragment2;

    public String mPostKeyIdUser = null;
    private TextView username;
    private ImageView foto_profile;
    private String JSON_STRING;
    private String namaS, foto_profileS, id_userS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        FragmentManager fm =  getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.add(R.id.frame_container, new FirstFragment());
        fragmentTransaction.commit();


        username = findViewById(R.id.username);
        foto_profile = findViewById(R.id.foto_profile);
        mPostKeyIdUser = getIntent().getExtras().getString("id_user");

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
                foto_profileS = jo.getString(konfigurasi.foto_profile);

                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_user, id_userS);

                list.add(data);

                if (mPostKeyIdUser.equals(jo.getString("id_user"))) {
                    username.setText("" + namaS);
                    Glide.with(getApplicationContext()).load("http://imaindonesia.000webhostapp.com/userimage/" + foto_profileS).into(foto_profile);
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
                String s = rh.sendGetRequest(konfigurasi.URL_GET_DATAUSER);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @OnClick(R.id. btn_fragment1 )
    public void onBtnFragment1Clicked() {
        btnFragment1.setTextColor(Color.parseColor("#FFFFFF"));
        btnFragment2.setTextColor(Color.parseColor("#c23c1b"));
        btnFragment1.setBackgroundColor(ContextCompat.getColor(ProfileActivity.this,R.color.orange ));
        btnFragment2.setBackgroundColor(ContextCompat.getColor(ProfileActivity.this,R.color.transparent ));
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id. frame_container , new FirstFragment());
        fragmentTransaction.commit();
    }

    @OnClick(R.id. btn_fragment2 )
    public void onBtnFragment2Clicked() {
        btnFragment2.setTextColor(Color.parseColor("#FFFFFF"));
        btnFragment1.setTextColor(Color.parseColor("#c23c1b"));
        btnFragment1.setBackgroundColor(ContextCompat.getColor(ProfileActivity.this,R.color.transparent ));
        btnFragment2.setBackgroundColor(ContextCompat.getColor(ProfileActivity.this,R.color.orange ));
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id. frame_container , new SecondFragment());
        fragmentTransaction.commit();
    }

    public void back(View view) {
        finish();
    }
}
