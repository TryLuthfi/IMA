package indonesia.ima.com.ima;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArtikelActivity extends AppCompatActivity {
    Context context;
    private static final String URL_PRODUCTS = "http://imaindonesia.000webhostapp.com/artikelpreview.php";
    List<Artikel> productList;
    RecyclerView recyclerView;
    private ProgressBar loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel);

        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ArtikelActivity.this));

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
                                productList.add(new Artikel(
                                        product.getString("id_artikel"),
                                        product.getString("judul_artikel"),
                                        product.getString("deskripsi_artikel"),
                                        product.getString("artikel_created_date")
                                ));
                            }

                            ArtikelAdapter adapter = new ArtikelAdapter(ArtikelActivity.this, productList);

                            if (adapter != null){
                                recyclerView.setAdapter(adapter);

                                loading.setVisibility(View.INVISIBLE);
                            }else {
                                Toast.makeText(ArtikelActivity.this, "null", Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(ArtikelActivity.this).add(stringRequest);
    }

    public void back(View view) {
        finish();
    }
}
