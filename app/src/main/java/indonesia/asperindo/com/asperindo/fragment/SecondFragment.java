package indonesia.asperindo.com.asperindo.fragment;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import indonesia.asperindo.com.asperindo.activity.ProfileActivity;
import indonesia.asperindo.com.asperindo.R;
import indonesia.asperindo.com.asperindo.request.RequestHandler;
import indonesia.asperindo.com.asperindo.konfigurasi.konfigurasi;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {
    private TextView nama;
    private TextView no_telp;
    private TextView bidang_usaha;
    private TextView nama_usaha;
    private String JSON_STRING;
    private String id_userS;
    private String namaS;
    private String no_telpS;
    private String bidang_usahaS;
    private String nama_usahaS;
    private String id;
    public ProfileActivity profile;

    @Override
    public void onStart() {
        super.onStart();
        getJSON();
    }

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        nama = view.findViewById(R.id.nama);
        no_telp = view.findViewById(R.id.no_telp);
        bidang_usaha = view.findViewById(R.id.bidang_usaha);
        nama_usaha = view.findViewById(R.id.nama_usaha);
        profile = (ProfileActivity) getActivity();
        id = profile.mPostKeyIdUser;


        return view;
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
                no_telpS = jo.getString(konfigurasi.no_telp);
                bidang_usahaS = jo.getString(konfigurasi.bidang_usaha);
                nama_usahaS = jo.getString(konfigurasi.nama_usaha);

                HashMap<String, String> data = new HashMap<>();
                data.put(konfigurasi.id_user, id_userS);

                if(id.equals(jo.getString("id_user"))){
                    nama.setText(namaS);
                    no_telp.setText(no_telpS);
                    bidang_usaha.setText(bidang_usahaS);
                    nama_usaha.setText(nama_usahaS);
                }
                list.add(data);

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

}
