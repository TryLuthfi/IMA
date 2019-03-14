package indonesia.ima.com.ima;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class EventActivity extends AppCompatActivity {
    Context context;
    private static final String URL_PRODUCTS = "http://imaindonesia.000webhostapp.com/acarapreview.php";
//    private static final String URL_PRODUCTS = "https://heuroix.000webhostapp.com/contentpreview.php";
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    List<Acara> productList;
    RecyclerView recyclerView;
    private ProgressBar loading;

    private int[] myImageList = new int[]{R.drawable.anu1, R.drawable.anu2,
            R.drawable.anu3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(EventActivity.this));

        loading = findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);

        productList = new ArrayList<>();

        loadProducts();
    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);


                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new Acara(
                                        product.getString("id_acara"),
                                        product.getString("judul_acara"),
                                        product.getString("gambar_acara"),
                                        product.getString("jam_acara"),
                                        product.getString("tanggal_acara"),
                                        product.getString("alamat_acara"),
                                        product.getString("alamat_lengkap_acara"),
                                        product.getString("deskripsi_acara")
                                ));
                            }

                            AcaraAdapter adapter = new AcaraAdapter(EventActivity.this, productList);

                            if (adapter != null){
                                recyclerView.setAdapter(adapter);

                                loading.setVisibility(View.INVISIBLE);
                            }else {
                                Toast.makeText(EventActivity.this, "null", Toast.LENGTH_SHORT).show();
                            }

//                            loading.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(EventActivity.this).add(stringRequest);
    }

}
