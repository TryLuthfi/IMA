package indonesia.ima.com.ima;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    public static final String LOGIN_URL = "http://imaindonesia.000webhostapp.com/login.php";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String LOGIN_SUCCESS = "success";
    private Context context;
    private LinearLayout buttonLogin;
    private EditText usernameE, passwordE;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;

        String id_user = getIdUser();
        if (id_user != "null") {
            gotoCourseActivity();
        }

        progressDialog = new ProgressDialog(context);
        usernameE = findViewById(R.id.usernameE);
        passwordE = findViewById(R.id.passwordE);
        buttonLogin = findViewById(R.id.signinbutton);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        //Getting values from edit texts
        final String username = usernameE.getText().toString().trim();
        final String password = passwordE.getText().toString().trim();
        progressDialog.setMessage("Login Process...");
        showDialog();
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("idpelangan",response.toString());
                        //If we are getting success from server
                        if (response.contains(LOGIN_SUCCESS)) {
                            hideDialog();
                            String id_user = response.toString().split(";")[1];
                            Log.e("iniidpelanggan", id_user);
                            setPreference(id_user);
                            gotoCourseActivity();

                        } else {
                            hideDialog();
                            //Displaying an error message on toast
                            Toast.makeText(context, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        hideDialog();
                        Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(KEY_USERNAME, username);
                params.put(KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);

    }
    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void gotoCourseActivity() {
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    void setPreference(String id_user){
        SharedPreferences mSettings = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("id_user", id_user);
        editor.apply();
    }
    private String getIdUser(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_user = preferences.getString("id_user", "null");
        return id_user;
    }
}
