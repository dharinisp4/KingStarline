package in.games.OnlineMatka;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.games.OnlineMatka.Adapter.Request_HistoryAdapter;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Objects.Fund_Request_HistoryObject;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.CustomVolleyJsonArrayRequest;
import in.games.OnlineMatka.utils.LoadingBar;

public class Fund_RequestActivity extends MyBaseActivity {

    Common common;
    private RecyclerView recyclerView;
    ArrayList<Fund_Request_HistoryObject> list;
    LoadingBar progressDialog;
    RecyclerView.LayoutManager layoutManager;
    TextView txtBack;
    private Request_HistoryAdapter request_historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund__request);
        common=new Common(Fund_RequestActivity.this);
        progressDialog=new LoadingBar(Fund_RequestActivity.this);


        list=new ArrayList();

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        common.setSessionTimeOut(Fund_RequestActivity.this);

        txtBack=(TextView)findViewById(R.id.txtBack);
        request_historyAdapter=new Request_HistoryAdapter(this,list);
        //matakListViewAdapter=new MatakListViewAdapter(this,matkaList);
        recyclerView.setAdapter(request_historyAdapter);

        String User_id= Prevalent.currentOnlineuser.getId();
//        Toast.makeText(this,""+User_id,Toast.LENGTH_LONG).show();
       getRequestData(User_id);


       txtBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
    }

    private void getRequestData(final String user_id) {

        progressDialog.show();
        String json_tag="json_req";
        final HashMap<String,String> params=new HashMap<String,String>();
        params.put("user_id",user_id);

        list.clear();
      CustomVolleyJsonArrayRequest customJsonRequest=new CustomVolleyJsonArrayRequest(Request.Method.POST, URLs.Url_req_history, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("asdasd",""+response.toString());
                   try
                    {
                        if( response.length()!=0)
                        {
//                        String status=response.getString("status");
//                        if(status.equals("success"))
//                        {
//                            JSONArray jsonArray=response.getJSONArray("data");
//                            progressDialog.dismiss();
                            for(int i=0; i<=response.length()-1;i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                Fund_Request_HistoryObject matkasObjects = new Fund_Request_HistoryObject();
                                matkasObjects.setRequest_id(jsonObject.getString("request_id"));
                                matkasObjects.setRequest_points(jsonObject.getString("request_points"));
                                matkasObjects.setTime(jsonObject.getString("time"));
                                matkasObjects.setRequest_status(jsonObject.getString("request_status"));
                                matkasObjects.setUser_id(jsonObject.getString("user_id"));
                                list.add(matkasObjects);


                            }
                            request_historyAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
//
                        }
                        else
                        {
                            common.errorMessageDialog("No History Found");
                        }

                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(Fund_RequestActivity.this,"There is no history for this game",Toast.LENGTH_LONG).show();
//                        Log.e("Volley",error.toString());
                        progressDialog.dismiss();
                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=common.VolleyErrorMessage(error);
                common.errorMessageDialog(msg);

            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);
    }

}
