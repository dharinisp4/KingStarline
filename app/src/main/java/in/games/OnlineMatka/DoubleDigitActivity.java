package in.games.OnlineMatka;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import in.games.OnlineMatka.Adapter.MatkaCategoryAdapter;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Model.MatkasObjects;
import in.games.OnlineMatka.utils.CustomVolleyJsonArrayRequest;
import in.games.OnlineMatka.utils.LoadingBar;
import in.games.OnlineMatka.utils.Module;


public class DoubleDigitActivity extends MyBaseActivity {
    Common common;
    TextView bt_back;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private MatkaCategoryAdapter matkaCategoryAdapter;
    private ArrayList<MatkasObjects> matkaList;
    Module module;
    LoadingBar progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_digit);
       common=new Common(DoubleDigitActivity.this);
        bt_back = (TextView) findViewById(R.id.txtBack);
        module=new Module();
        progressDialog=new LoadingBar(DoubleDigitActivity.this);

        matkaList=new ArrayList();
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        matkaCategoryAdapter=new MatkaCategoryAdapter(this,matkaList);
        //matakListViewAdapter=new MatakListViewAdapter(this,matkaList);
        recyclerView.setAdapter(matkaCategoryAdapter);


        getMatkaData();



    }

    @Override
    protected void onStart() {
        super.onStart();
        //setSessionTimeOut(DoubleDigitActivity.this);
    }

    public void getMatkaData()
    {
        progressDialog.show();

        String json_tag="json_get_matkas";
        HashMap<String,String> map=new HashMap<>();

        CustomVolleyJsonArrayRequest customVolleyJsonArrayRequest=new CustomVolleyJsonArrayRequest(Request.Method.POST, URLs.URL_Matka, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                matkaList.clear();

                for(int i=0; i<response.length();i++)
                {
                    try
                    {
                        JSONObject jsonObject=response.getJSONObject(i);

                        MatkasObjects matkasObjects=new MatkasObjects();
                        matkasObjects.setId(jsonObject.getString("id"));
                        matkasObjects.setName(jsonObject.getString("name"));
                        matkasObjects.setStart_time(jsonObject.getString("start_time"));
                        matkasObjects.setEnd_time(jsonObject.getString("end_time"));
                        matkasObjects.setStarting_num(jsonObject.getString("starting_num"));
                        matkasObjects.setNumber(jsonObject.getString("number"));
                        matkasObjects.setEnd_num(jsonObject.getString("end_num"));
                        matkasObjects.setBid_start_time(jsonObject.getString("bid_start_time"));
                        matkasObjects.setBid_end_time(jsonObject.getString("bid_end_time"));
                        matkasObjects.setCreated_at(jsonObject.getString("created_at"));
                        matkasObjects.setUpdated_at(jsonObject.getString("updated_at"));
                        matkasObjects.setSat_start_time(jsonObject.getString("sat_start_time"));
                        matkasObjects.setSat_end_time(jsonObject.getString("sat_end_time"));
                        matkasObjects.setStatus(jsonObject.getString("status"));
                        matkaList.add(matkasObjects);
                        matkaCategoryAdapter.notifyDataSetChanged();


                    }
                    catch (Exception ex)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(DoubleDigitActivity.this,"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();

                        return;
                    }
                }

                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                String msg=common.VolleyErrorMessage(error);
                Toast.makeText(DoubleDigitActivity.this,"Error :"+msg,Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(customVolleyJsonArrayRequest,json_tag);


    }

}
