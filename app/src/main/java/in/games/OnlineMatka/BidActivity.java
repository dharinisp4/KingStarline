package in.games.OnlineMatka;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.games.OnlineMatka.Adapter.BidHistoryListViewAdapter;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Model.BidHistoryObjects;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.CustomVolleyJsonArrayRequest;
import in.games.OnlineMatka.utils.LoadingBar;

import static in.games.OnlineMatka.URLs.Bid_Histry_Url;


public class BidActivity extends MyBaseActivity {
    Common common;
    private ListView recyclerView;
    ArrayList<BidHistoryObjects> list;
    //RecyclerView.LayoutManager layoutManager;
   LoadingBar progressDialog;
    private BidHistoryListViewAdapter bidHistoryAdapter;
    private TextView bt_back;
    private String user_id;
    private String matka_id;
    WebView webView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid);
        common=new Common(BidActivity.this);
        matka_id=getIntent().getStringExtra("matka_id");
        progressDialog=new LoadingBar(BidActivity.this);
        webView = findViewById(R.id.bid_histry_webview);
        recyclerView=(ListView) findViewById(R.id.recyclerView);
        bt_back=(TextView)findViewById(R.id.txtBack);
     //   user_id= "3";
        user_id= Prevalent.currentOnlineuser.getId().toString().trim();
//        recyclerView.setHasFixedSize(false);
//        layoutManager= new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
        list=new ArrayList();

        //user_id= Prevalent.currentOnlineuser.getId().toString().trim();

        bidHistoryAdapter=new BidHistoryListViewAdapter(this,list);
        recyclerView.setAdapter(bidHistoryAdapter);

       // getMatkaData();
//        getBidData(user_id,matka_id);
        webView.loadUrl(Bid_Histry_Url+user_id);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.setInitialScale(1);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
      //  setSessionTimeOut(BidActivity.this);
    }

    private void getBidData(final String user_id, final String matka_id) {

        final String json_request_tag="json_bid_history_tag";
        HashMap<String,String> params=new HashMap<String, String>();
        params.put("us_id",user_id);
        params.put("matka_id",matka_id);

        progressDialog.show();

         list.clear();

        CustomVolleyJsonArrayRequest customVolleyJsonArrayRequest=new CustomVolleyJsonArrayRequest(Request.Method.POST, URLs.URL_BID_HISTORY, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try
                {
                                            JSONArray jsonArray=response;
                        String h=jsonArray.getString(0);
                        if(h.equals("null") || h.equals(null))
                        {
                            progressDialog.dismiss();
                            common.errorMessageDialog("No history for this Matka");
                        }
                        else
                        {

                            for(int i=0; i<=jsonArray.length()-1;i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                BidHistoryObjects matkasObjects = new BidHistoryObjects();
                                matkasObjects.setId(jsonObject.getString("id"));
                                matkasObjects.setUser_id(jsonObject.getString("user_id"));
                                matkasObjects.setMatka_id(jsonObject.getString("matka_id"));
                                matkasObjects.setBet_type(jsonObject.getString("bet_type"));
                                matkasObjects.setPoints(jsonObject.getString("points"));
                                matkasObjects.setDigits(jsonObject.getString("digits"));
                                matkasObjects.setDate(jsonObject.getString("date"));
                                matkasObjects.setTime(jsonObject.getString("time"));
                                matkasObjects.setName(jsonObject.getString("name"));
                                matkasObjects.setGame_id(jsonObject.getString("game_id"));
                                matkasObjects.setStatus(jsonObject.getString("status"));
                                matkasObjects.setPlay_for(jsonObject.getString("play_for"));
                                matkasObjects.setPlay_on(jsonObject.getString("play_on"));
                                matkasObjects.setDay(jsonObject.getString("day"));
                                list.add(matkasObjects);


                            }
                            bidHistoryAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();


                        }
                        //progressDialog.dismiss();

                }
                catch (Exception ex)
                {
                    progressDialog.dismiss();
                    Toast.makeText(BidActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                common.errorMessageDialog("Something Went Wrong");

              //  Toast.makeText(BidActivity.this,"Error"+error.toString(),Toast.LENGTH_LONG).show();
//                        Log.e("Volley",error.toString());
                progressDialog.dismiss();
            }
        });

        AppController.getInstance().addToRequestQueue(customVolleyJsonArrayRequest,json_request_tag);




    }

    public void getMatkaData()
    {
        progressDialog.show();

        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URLs.Url_bid_history, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i=0; i<response.length();i++)
                        {
                            try
                            {
                                JSONObject jsonObject=response.getJSONObject(i);

                                BidHistoryObjects matkasObjects=new BidHistoryObjects();
                                matkasObjects.setId(jsonObject.getString("id"));
                                matkasObjects.setUser_id(jsonObject.getString("user_id"));
                                matkasObjects.setMatka_id(jsonObject.getString("matka_id"));
                                matkasObjects.setBet_type(jsonObject.getString("bet_type"));
                                matkasObjects.setPoints(jsonObject.getString("points"));
                                matkasObjects.setDigits(jsonObject.getString("digits"));
                                matkasObjects.setDate(jsonObject.getString("date"));
                                matkasObjects.setTime(jsonObject.getString("time"));
                                matkasObjects.setName(jsonObject.getString("name"));
                                matkasObjects.setGame_id(jsonObject.getString("game_id"));
                                list.add(matkasObjects);
                            }
                            catch (Exception ex)
                            {
                                progressDialog.dismiss();
                                Toast.makeText(BidActivity.this,"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();

                                return;
                            }
                        }
                        bidHistoryAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();


                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(BidActivity.this,"Error"+error.toString(),Toast.LENGTH_LONG).show();
//                        Log.e("Volley",error.toString());
                        progressDialog.dismiss();



                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();

                params.put("us_id","2");


                // params.put("phonepay",phonepaynumber);


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);



    }



}
