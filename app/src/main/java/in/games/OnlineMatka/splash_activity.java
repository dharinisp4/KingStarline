package in.games.OnlineMatka;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.utils.CustomJsonRequest;

public class splash_activity extends AppCompatActivity {

   float version_code;
   Common common;
   public static String home_text ="", withdrw_text="",tagline= "",withdrw_no="" ,min_add_amount="",message="",
           msg_status="",app_link="",share_link="",update_msg="";
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
                Log.e("asdasd",""+response.toString());
                try
                {
                    boolean resp=response.getBoolean("responce");
                    float ver_code=0;
                    String msg="";
                    if(resp)
                    {
                        JSONObject dataObj=response.getJSONObject("data");
                        tagline = dataObj.getString("tag_line");
                        message = dataObj.getString("message");
                        withdrw_text = dataObj.getString("withdraw_text").toLowerCase();
                        withdrw_no = dataObj.getString("withdraw_no");
                        home_text = dataObj.getString("home_text").toString();
                        min_add_amount = dataObj.getString("min_amount");
                        msg_status = dataObj.getString("msg_status");
                        app_link = dataObj.getString("app_link");
                        share_link = dataObj.getString("share_link");
                        update_msg = dataObj.getString("update_msg");
                        ver_code=Float.parseFloat(dataObj.getString("version"));
                        msg=dataObj.getString("message");
                        common.getNumbers(home_text.toString());
                    }
                    else
                    {
                        common.showToast(response.getString("message"));
                    }

                    if(version_code==ver_code)
                    {
                            Intent intent = new Intent(splash_activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                    }
                    else
                    {
                        AlertDialog.Builder builder=new AlertDialog.Builder(splash_activity.this);
                        builder.setTitle("Version Information");
                        builder.setMessage(update_msg);
                        builder.setCancelable(false);
                         builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String url = null;
                                try {
                                    url = app_link;
                                } catch (Exception e) {
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
                    ex.printStackTrace();
                    Toast.makeText(splash_activity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
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
