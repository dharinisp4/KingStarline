package in.games.OnlineMatka;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.games.OnlineMatka.Adapter.TransactionHistoryAdapter;
import in.games.OnlineMatka.Model.TransactionHistoryObjects;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.LoadingBar;

public class TransactionHistory extends MyBaseActivity {

    ArrayList<TransactionHistoryObjects> list;
    TransactionHistoryAdapter transactionHistoryAdapter;
    private RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;


    TextView txtBack;
  LoadingBar progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        txtBack=(TextView)findViewById(R.id.txtBack);
        progressDialog=new LoadingBar(TransactionHistory.this);
     recyclerView=findViewById(R.id.recyclerView);


        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        list=new ArrayList<>();

        recyclerView.setHasFixedSize(false);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        list=new ArrayList();


        String user_id= Prevalent.currentOnlineuser.getId().toString().trim();

       transactionHistoryAdapter =new TransactionHistoryAdapter(TransactionHistory.this,list);
        recyclerView.setAdapter(transactionHistoryAdapter);



           getTranssactionHistoryData(user_id);



    }

    @Override
    protected void onStart() {
        super.onStart();
       // setSessionTimeOut(TransactionHistory.this);
    }

    private void getTranssactionHistoryData(final String user_id) {

        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_transaction_history, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if(status.equals("success"))
                    {

                        JSONArray jsonArray=jsonObject.getJSONArray("msg");

                        for (int i=0; i<jsonArray.length();i++)
                        {
                            JSONObject objects=jsonArray.getJSONObject(i);

                            TransactionHistoryObjects objects1=new TransactionHistoryObjects();

                            objects1.setId(objects.getString("id"));
                            objects1.setAmt(objects.getString("amt"));
                            objects1.setBid_id(objects.getString("bid_id"));
                            objects1.setDate(objects.getString("date"));
                            objects1.setDigits(objects.getString("digits"));
                            objects1.setGame_id(objects.getString("game_id"));
                            objects1.setMatka_id(objects.getString("matka_id"));
                            objects1.setTime(objects.getString("time"));
                            objects1.setType(objects.getString("type"));
                            objects1.setUser_id(objects.getString("user_id"));



                            list.add(objects1);
                        }

                        transactionHistoryAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                    else if(status.equals("unsuccessful"))
                    {

                    }
                }
                catch (Exception ex)
                {
                    progressDialog.dismiss();
                    Toast.makeText(TransactionHistory.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
                    return;
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TransactionHistory.this,"Error"+error.toString(),Toast.LENGTH_LONG).show();
//                        Log.e("Volley",error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();

                params.put("us_id",user_id);
              //  params.put("matka_id",matka_id);


                // params.put("phonepay",phonepaynumber);


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
