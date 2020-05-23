package in.games.OnlineMatka;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Model.UsersObjects;
import in.games.OnlineMatka.Prevalent.Prevalent;


public class LoginWithMpinActivity extends AppCompatActivity {
    Snackbar snackbar;
    View view;
    Common common;
    boolean isConnected;
    Button btnLogin,btn_Mpin;
    private Button dialog_btnMpin;

    EditText etMpin,dialog_etEmail;
    Dialog dialog;
    ProgressDialog progressDialog;
    static String URL_MPIN_Login = "https://malamaal.anshuwap.com/restApi/mpin/mpin_login.php";
    //static String URL_MPIN_Login = "http://jannat.projects.anshuwap.com/restApi/mpin/mpin_login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_mpin);
         common=new Common(LoginWithMpinActivity.this);
        btnLogin=(Button)findViewById(R.id.login_mpilogin_btn);
        etMpin=(EditText)findViewById(R.id.etMpin);
        btn_Mpin=(Button) findViewById(R.id.btn_mpin);

        boolean sdfff=common.isConnected();
        if(sdfff==true)
        {
            // Snackbar.make(findViewById(R.id.main_layout),"Network Status: ",Snackbar.LENGTH_INDEFINITE).show();
        }
        else
        {
            Snackbar.make(findViewById(R.id.main_layout),"No Internet Connection",Snackbar.LENGTH_INDEFINITE).show();
        }

        // final String[] net = new String[1];
        IntentFilter intentFilter=new IntentFilter(NetworkStateChangeReciever.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                boolean isNetworkAvailable=intent.getBooleanExtra(NetworkStateChangeReciever.IS_NETWORK_AVAILABLE,false);
                String netwotkStatus=isNetworkAvailable ? "connected" : "disconnected";

                if(netwotkStatus.equals("disconnected"))
                {
                    Snackbar.make(findViewById(R.id.main_layout),"No Internet Connection",Snackbar.LENGTH_INDEFINITE).show();
                }
                else
                {
                    Snackbar.make(findViewById(R.id.main_layout),"Connected",Snackbar.LENGTH_LONG).show();
                }
                //       net[0] =netwotkStatus;


                //  Toast.makeText(MainActivity.this,""+netwotkStatus,Toast.LENGTH_LONG).show();

            }
        },intentFilter);


        progressDialog=new ProgressDialog(LoginWithMpinActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mid=etMpin.getText().toString().trim();
                if(TextUtils.isEmpty(mid))
                {
                    etMpin.setError("Enter MPIN");
                    etMpin.requestFocus();
                    return;
                }
                else
                {
                    getMPINData(mid);
                }

            }
        });

        btn_Mpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog=new Dialog(LoginWithMpinActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_foraget_mpin_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog_btnMpin=(Button)dialog.findViewById(R.id.forget_mpin);
                dialog_etEmail=(EditText)dialog.findViewById(R.id.etForget_email);

                dialog.setCanceledOnTouchOutside(false);
                dialog.show();


                dialog_btnMpin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(TextUtils.isEmpty(dialog_etEmail.getText().toString()))
                        {
                            dialog_etEmail.setError("Enter registered Email Id");
                            dialog_etEmail.requestFocus();
                            return;
                        }
                        else
                        {
                            String mail=dialog_etEmail.getText().toString().trim();
                            getForgotMpin(mail);
                        }

                    }
                });
            }
        });

    }

    private void getForgotMpin(final String mail) {


        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_forgot_mpin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");
                            if(success.equals("success"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(LoginWithMpinActivity.this, "Mail sent to your email address!!!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else if(success.equals("unsuccessful"))
                            {
                                progressDialog.dismiss();
                                String msg=jsonObject.getString("message");
                                Toast.makeText(LoginWithMpinActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                return;
                            }



                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(LoginWithMpinActivity.this, "Updation failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            //  btnReg.setVisibility(View.VISIBLE);


                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss(); if(error instanceof NoConnectionError){
                            ConnectivityManager cm = (ConnectivityManager)getApplicationContext()
                                    .getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo activeNetwork = null;
                            if (cm != null) {
                                activeNetwork = cm.getActiveNetworkInfo();
                            }
                            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
                                Toast.makeText(LoginWithMpinActivity.this, "Server is not connected to internet.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginWithMpinActivity.this, "Your device is not connected to internet.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else if (error instanceof NetworkError || error.getCause() instanceof ConnectException
                                || (error.getCause().getMessage() != null
                                && error.getCause().getMessage().contains("connection"))){
                            Toast.makeText(LoginWithMpinActivity.this, "Your device is not connected to internet.",
                                    Toast.LENGTH_SHORT).show();
                        } else if (error.getCause() instanceof MalformedURLException){
                            Toast.makeText(LoginWithMpinActivity.this, "Bad Request.", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError || error.getCause() instanceof IllegalStateException
                                || error.getCause() instanceof JSONException
                                || error.getCause() instanceof XmlPullParserException){
                            Toast.makeText(LoginWithMpinActivity.this, "Parse Error (because of invalid json or xml).",
                                    Toast.LENGTH_SHORT).show();
                        } else if (error.getCause() instanceof OutOfMemoryError){
                            Toast.makeText(LoginWithMpinActivity.this, "Out Of Memory Error.", Toast.LENGTH_SHORT).show();
                        }else if (error instanceof AuthFailureError){
                            Toast.makeText(LoginWithMpinActivity.this, "server couldn't find the authenticated request.",
                                    Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError || error.getCause() instanceof ServerError) {
                            Toast.makeText(LoginWithMpinActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
                        }else if (error instanceof TimeoutError || error.getCause() instanceof SocketTimeoutException
                                || error.getCause() instanceof ConnectTimeoutException
                                || error.getCause() instanceof SocketException
                                || (error.getCause().getMessage() != null
                                && error.getCause().getMessage().contains("Connection timed out"))) {
                            Toast.makeText(LoginWithMpinActivity.this, "Connection timeout error",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginWithMpinActivity.this, "An unknown error occurred.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })

        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();

                params.put("email",mail);
                // params.put("phonepay",phonepaynumber);


                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);




    }

    @Override
    protected void onStart() {
        super.onStart();
        //setSessionTimeOut(LoginWithMpinActivity.this);
    }

    private void getMPINData(final String mid) {

        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_MPIN_Login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals(null))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(LoginWithMpinActivity.this,"Wrong MPIN",Toast.LENGTH_LONG).show();
                            return;
                        }
                        else
                        {

                            try
                            {
                                JSONObject jsonObject = new JSONObject(response);
                                //Toast.makeText(MainActivity.this,"User "+jsonObject.getString("name").toString(),Toast.LENGTH_LONG).show();
                                //progressDialog.dismiss();
                                //Log.d("a",response);
                                UsersObjects users = new UsersObjects();
                                users.setId(jsonObject.getString("id"));
                                users.setName(jsonObject.getString("name"));
                                users.setUsername(jsonObject.getString("username"));
                                users.setMobileno(jsonObject.getString("mobileno"));
                                users.setEmail(jsonObject.getString("email"));
                                users.setAddress(jsonObject.getString("address"));
                                users.setCity(jsonObject.getString("city"));
                                users.setPincode(jsonObject.getString("pincode"));
                                users.setPassword(jsonObject.getString("password"));
                                users.setAccountno(jsonObject.getString("accountno"));
                                users.setBank_name(jsonObject.getString("bank_name"));
                                users.setIfsc_code(jsonObject.getString("ifsc_code"));
                                users.setAccount_holder_name(jsonObject.getString("account_holder_name"));
                                users.setPaytm_no(jsonObject.getString("paytm_no"));
                                users.setTez_no(jsonObject.getString("tez_no"));
                                users.setPhonepay_no(jsonObject.getString("phonepay_no"));                                 Prevalent.currentOnlineuser=users;

                                 progressDialog.dismiss();

                                Intent intent = new Intent(LoginWithMpinActivity.this, HomeActivity.class);
                                intent.putExtra("username", jsonObject.getString("username").toString());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                progressDialog.dismiss();
                                finish();

                                //Toast.makeText(LoginWithMpinActivity.this,""+users.getId(),Toast.LENGTH_LONG).show();

                            }
                            catch (Exception ex)
                            {
                                progressDialog.dismiss();
                                Toast.makeText(LoginWithMpinActivity.this,"MPIN Not exist or Already loggedin"+ex.getMessage(),Toast.LENGTH_LONG).show();
                                etMpin.setText("");
                                return;
                            }

                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(LoginWithMpinActivity.this,"MPIN not exist.",Toast.LENGTH_LONG).show();
                        etMpin.setText("");
                        return;
                    }
                })
        {
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<>();
                params.put("mid",mid);

                return params;
            }



        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //networkStateReceiver.removeListener(MainActivity.this);
//        this.unregisterReceiver(networkStateReceiver);
    }


}
