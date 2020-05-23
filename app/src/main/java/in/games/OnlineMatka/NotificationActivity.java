package in.games.OnlineMatka;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.games.OnlineMatka.Adapter.NotificationAdapter;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Model.NotificationObjects;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.LoadingBar;

public class NotificationActivity extends MyBaseActivity {

    Common common;
    Switch aSwitch;
   LoadingBar progressDialog;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<NotificationObjects> list;
    TextView btn_back;
    TextView txtNot,txtSwitch;
    NotificationAdapter notificationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        txtNot=(TextView)findViewById(R.id.m_noti);
        txtSwitch=(TextView)findViewById(R.id.text_notification);
     common=new Common(NotificationActivity.this);
        btn_back=(TextView) findViewById(R.id.txt_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
aSwitch=findViewById(R.id.notification_switch);
        progressDialog=new LoadingBar(NotificationActivity.this);

        list=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.INVISIBLE);

        recyclerView.setHasFixedSize(false);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("notify_pref",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();


        if(aSwitch.isChecked())
        {
            editor.putString("status","1");
            editor.commit();

            txtSwitch.setText("ON");
            //txtNot.setForeground(Color.RED);
            String email= Prevalent.currentOnlineuser.getMobileno().toString();
            //  getNotificationData();
            getNotificationWtihEmail(email);
           // getNotificationData();

            notificationAdapter=new NotificationAdapter(NotificationActivity.this,list);
            recyclerView.setAdapter(notificationAdapter);

            recyclerView.setVisibility(View.VISIBLE);
        }
        else {
            editor.putString("status","0");
            editor.commit();

            txtSwitch.setText("OFF");
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }
});

    }

    @Override
    protected void onStart() {
        super.onStart();
        //setSessionTimeOut(NotificationActivity.this);

        SharedPreferences preferences=getSharedPreferences("notify_pref",0);
        if(preferences.contains("status"))
        {
           String as= preferences.getString("status","");
           if(as.equals("1"))
           {
               aSwitch.setChecked(true);
               String email= Prevalent.currentOnlineuser.getMobileno().toString();
               //  getNotificationData();
               getNotificationWtihEmail(email);
               // getNotificationData();

               notificationAdapter=new NotificationAdapter(NotificationActivity.this,list);
               recyclerView.setAdapter(notificationAdapter);

               recyclerView.setVisibility(View.VISIBLE);

           }
           //Toast.makeText(NotificationActivity.this,""+as,Toast.LENGTH_LONG).show();
        }
        else
        {
            //Toast.makeText(NotificationActivity.this,"something worn",Toast.LENGTH_LONG).show();
        }




    }

    private void getNotificationWtihEmail(final String email) {

        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_notification, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                list.clear();

                try
                {
                    JSONObject jsonObject=new JSONObject(response);

                    String status=jsonObject.getString("status");

                    if(status.equals("success"))
                    {

                        JSONArray jsonArray=jsonObject.getJSONArray("data");

                        for(int i=0; i<jsonArray.length();i++)
                        {

                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                            NotificationObjects matkasObjects=new NotificationObjects();
                            matkasObjects.setNotification_id(jsonObject1.getString("notification_id"));
                            matkasObjects.setNotification(jsonObject1.getString("notification"));
                            matkasObjects.setTime(jsonObject1.getString("time"));

                            list.add(matkasObjects);
                            notificationAdapter.notifyDataSetChanged();


                        }

                        progressDialog.dismiss();



                    }
                    else if (status.equals("unsuccessfull"))
                    {
                        String object=jsonObject.getString("data");
                 
                        progressDialog.dismiss();

                        common.errorMessageDialog(""+object);

                    }
                    else
                    {

                    }
                }
                catch (Exception ex)
                {
                    progressDialog.dismiss();
                    Toast.makeText(NotificationActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }



            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(NotificationActivity.this, "Error :" + error.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        })
        {

            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("mobile", email);
                //params.put("password",pass);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    public void getNotificationData()
    {
        progressDialog.show();

        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URLs.Url_notification, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //   matkaAdapter.notifyDataSetChanged();

                        list.clear();

                        for(int i=0; i<response.length();i++)
                        {
                            try
                            {
                                JSONObject jsonObject=response.getJSONObject(i);

                                NotificationObjects matkasObjects=new NotificationObjects();
                                matkasObjects.setNotification_id(jsonObject.getString("notification_id"));
                                matkasObjects.setNotification(jsonObject.getString("notification"));
                                matkasObjects.setTime(jsonObject.getString("time"));

                                list.add(matkasObjects);
                                notificationAdapter.notifyDataSetChanged();


                            }
                            catch (Exception ex)
                            {
                                progressDialog.dismiss();
                                Toast.makeText(NotificationActivity.this,"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();

                                return;
                            }
                        }

                        progressDialog.dismiss();


                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(NotificationActivity.this,"Error"+error.toString(),Toast.LENGTH_LONG).show();
//                        Log.e("Volley",error.toString());
                        progressDialog.dismiss();

                    }
                });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);



    }


}
