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
import java.util.Map;

import in.games.OnlineMatka.Adapter.Notice_RateAdapter;
import in.games.OnlineMatka.Model.GameRateModel;
import in.games.OnlineMatka.utils.CustomJsonRequest;
import in.games.OnlineMatka.utils.LoadingBar;


public class DrawerNoticeBoardActivity extends MyBaseActivity {
TextView bt_back;
LoadingBar progressDialog;
RecyclerView rv_rates ;
    ArrayList<GameRateModel> list;
    ArrayList<GameRateModel> slist;
    Notice_RateAdapter adapter ;

TextView txtsp,txtdp,txttp,txtsd,txtjd,txths,txtfs,txtrb,txtNumber,txts_sd,txts_sp,txts_dp,txts_tp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_notice_board);
        bt_back=(TextView)findViewById(R.id.txt_back);
        progressDialog=new LoadingBar(DrawerNoticeBoardActivity.this);
     //   progressDialog.setTitle("Please wait");

        rv_rates =findViewById( R.id.recycler_rates );
        rv_rates.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false ) );


//        txtsp=(TextView)findViewById(R.id.t1);
//        txtdp=(TextView)findViewById(R.id.t2);
//        txttp=(TextView)findViewById(R.id.t3);
//        txtsd=(TextView)findViewById(R.id.t4);
//        txtjd=(TextView)findViewById(R.id.t5);
//        txths=(TextView)findViewById(R.id.t6);
//        txtfs=(TextView)findViewById(R.id.t7);
//        txtrb=(TextView)findViewById(R.id.t8);
        txts_sd=(TextView)findViewById(R.id.txtst);
        txts_sp=(TextView)findViewById(R.id.txtst1);
        txts_dp=(TextView)findViewById(R.id.txtst2);
        txts_tp=(TextView)findViewById(R.id.txtst3);

        txtNumber=(TextView)findViewById(R.id.number);



        getNotice();
//        GameRateModel gmRateModel=new GameRateModel();
//        gmRateModel=slist.get(0);
//        txts_sd.setText(slist.get(0).getName()+" :-  "+slist.get(0).getRate_range()+" : " +slist.get(0).getRate());
//        txts_sp.setText(slist.get(1).getName()+" :-  "+slist.get(1).getRate_range()+" : " +slist.get(1).getRate());
//        txts_dp.setText(slist.get(2).getName()+" :-  "+slist.get(2).getRate_range()+" : " +slist.get(2).getRate());
//        txts_tp.setText(slist.get(3).getName()+" :-  "+slist.get(3).getRate_range()+" : " +slist.get(3).getRate());
//details.setMobileNumber(DrawerNoticeBoardActivity.this,txtNumber);

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
        //setSessionTimeOut(DrawerNoticeBoardActivity.this);
    }

//    private void getNotice() {
//
//        progressDialog.show();
//
//        String tag_json_obj = "json_notice_req";
//        Map<String, String> params = new HashMap<String, String>();
//
//        CustomVolleyJsonArrayRequest jsonArrayRequest=new CustomVolleyJsonArrayRequest(Request.Method.GET, URLs.URL_NOTICE, params, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//                try {
//
//
//                    //[{"id":"1","sp_rate":"160","dp_rate":"320","tp_rate":"800","sd_rate":"10","jd_rate":"100","hs_rate":"1000","fs_rate":"10000","rb_rate":"100","contact":"7354224579","s_sd_rate":"10","s_sp_rate":"160","s_dp_rate":"320","s_tp_rate":"1000","rate_range":"1"}]
//                    JSONObject jsonObject=response.getJSONObject(0);
//                    String range=jsonObject.getString("rate_range");
//                    txtsp.setText("* Single Pana :- "+range+" : "+jsonObject.getString("sp_rate"));
//                    txtdp.setText("* Double Pana :- "+range+" : "+jsonObject.getString("dp_rate"));
//                    txttp.setText("* Triple Pana :- "+range+" : "+jsonObject.getString("tp_rate"));
//                    txtsd.setText("* Single Digit :- "+range+" : "+jsonObject.getString("sd_rate"));
//                    txtjd.setText("* Jodi Digit :- "+range+" : "+jsonObject.getString("jd_rate"));
//                    txths.setText("* Half Sangam Digits :- "+range+" : "+jsonObject.getString("hs_rate"));
//                    txtfs.setText("* Full Sangam Digits :- "+range+" : "+jsonObject.getString("fs_rate"));
//                    txtrb.setText("* Red Brackets :- "+range+" : "+jsonObject.getString("rb_rate"));
//                    txts_sd.setText("* Single Digit :- "+range+" : "+jsonObject.getString("s_sd_rate"));
//                    txts_sp.setText("* Single Pana :- "+range+" : "+jsonObject.getString("s_sp_rate"));
//                    txts_dp.setText("* Double Pana :- "+range+" : "+jsonObject.getString("s_dp_rate"));
//                    txts_tp.setText("* Triple Pana :- "+range+" : "+jsonObject.getString("s_tp_rate"));
//                   // txtNumber.setText(jsonObject.getString("contact"));
//                   // Toast.makeText(DrawerNoticeBoardActivity.this,""+jsonObject,Toast.LENGTH_LONG).show();
//
//                    progressDialog.dismiss();
//                }
//                catch (Exception ex)
//                {
//                    progressDialog.dismiss();
//                   // Toast.makeText(DrawerNoticeBoardActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//               // Toast.makeText(DrawerNoticeBoardActivity.this,""+error.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        });
//        AppController.getInstance().addToRequestQueue(jsonArrayRequest,tag_json_obj);
//    }

    private void getNotice() {

        progressDialog.show();
        list=new ArrayList<>();
        slist=new ArrayList<>();
        String tag_json_obj = "json_notice_req";
        Map<String, String> params = new HashMap<String, String>();

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.URL_NOTICE, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.d("rates",response.toString());
                    String status=response.getString("status");
                    if(status.equals("success"))
                    {
                        JSONArray array=response.getJSONArray("data");

                        for (int i=0; i<array.length();i++)
                        {
                            GameRateModel gameRateModel=new GameRateModel();
                            JSONObject object=array.getJSONObject(i);
                            gameRateModel.setId(object.getString("id"));
                            gameRateModel.setName(object.getString("name"));
                            gameRateModel.setRate_range(object.getString("rate_range"));
                            gameRateModel.setRate(object.getString("rate"));
                            String type=object.getString("type").toString();
                            gameRateModel.setType(type);
                            if(type.equals("0"))
                            {
                                list.add(gameRateModel);

                            }
                            else
                            {
                                slist.add(gameRateModel);
                            }


                        }
                        adapter=new Notice_RateAdapter(list,DrawerNoticeBoardActivity.this);
                        rv_rates.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        txts_sd.setText(slist.get(0).getName()+" :-  "+slist.get(0).getRate_range()+" : " +slist.get(0).getRate());
                        txts_sp.setText(slist.get(1).getName()+" :-  "+slist.get(1).getRate_range()+" : " +slist.get(1).getRate());
                        txts_dp.setText(slist.get(2).getName()+" :-  "+slist.get(2).getRate_range()+" : " +slist.get(2).getRate());
                        txts_tp.setText(slist.get(3).getName()+" :-  "+slist.get(3).getRate_range()+" : " +slist.get(3).getRate());
                        //Toast.makeText(DrawerNoticeBoardActivity.this,""+slist.size(),Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(DrawerNoticeBoardActivity.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
                    }

                    progressDialog.dismiss();
                }
                catch (Exception ex)
                {progressDialog.dismiss();
                    Toast.makeText(DrawerNoticeBoardActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(DrawerNoticeBoardActivity.this,""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,tag_json_obj);
    }
}
