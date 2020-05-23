package in.games.OnlineMatka;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.utils.CustomJsonRequest;

public class splash_activity extends AppCompatActivity {

   float version_code;
   Common common;
   public static String home_text ="", withdrw_text="",tagline= "";
   // ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);
   common=new Common(splash_activity.this);
        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
             version_code = pInfo.versionCode;
           // Toast.makeText(splash_activity.this,""+version,Toast.LENGTH_LONG).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        getApiData();

    }

    private void getApiData() {

        String json_tag="json_splash_request";
        HashMap<String,String> params=new HashMap<String, String>();

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.GET, URLs.URL_INDEX, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
   Log.d("index",response.toString());
                try
                {
                    String text=response.getString("api");
                    tagline = response.getString("tag_line");
                    withdrw_text = response.getString("withdraw");
                    home_text = response.getString("home_text");
                    float ver_code=Float.parseFloat(response.getString("version"));
//                    Log.e("chck",ver_code + "\n"+version_code);

                    if(version_code==ver_code)
                    {
                        if(text.equals("welcome"))
                        {
                            Intent intent = new Intent(splash_activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Intent intent = new Intent(splash_activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(splash_activity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                         String msg=response.getString("message");
                        AlertDialog.Builder builder=new AlertDialog.Builder(splash_activity.this);
                        builder.setTitle("Version Information");
                        builder.setMessage(msg);
                        builder.setCancelable(false);
                         builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String url = null;
                                try {
                                    url = response.getString("link");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                startActivity(intent);

                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                finishAffinity();
                            }
                        });


                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();
                    }

                }
                catch (Exception ex)
                {
                    Toast.makeText(splash_activity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }


              //  Toast.makeText(splash_activity.this,""+response,Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Log.e("Volly Error", error.toString());
                String msg=common.VolleyErrorMessage(error);
                if(!msg.isEmpty())
                {
                    common.showToast(""+msg);
                }
            }
        });

        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);


    }

}
