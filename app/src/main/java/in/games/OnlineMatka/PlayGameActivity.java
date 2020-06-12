package in.games.OnlineMatka;

import android.content.Intent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import in.games.OnlineMatka.Adapter.MatakListViewAdapter;
import in.games.OnlineMatka.Adapter.PGAdapter;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Model.PGObjects;
import in.games.OnlineMatka.Model.Starline_Objects;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.CustomVolleyJsonArrayRequest;
import in.games.OnlineMatka.utils.LoadingBar;

public class PlayGameActivity extends MyBaseActivity {

    Common common;
    TextView txtsp,txtdp,txtsd,txttp;

    private ListView listView;
    ArrayList<PGObjects> list;
    ArrayList<Starline_Objects> arrayList;
    PGAdapter pgAdapter;
    MatakListViewAdapter matakListViewAdapter;
    private TextView btn_back;
   LoadingBar progressDialog;
    TextView bt_back;
    SwipeRefreshLayout swipe;
    TextView txtMatka;
    private TextView txtWallet_amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        progressDialog=new LoadingBar(PlayGameActivity.this);

     common=new Common(PlayGameActivity.this);
        txtMatka=(TextView)findViewById(R.id.board);
        txtMatka.setText("Jannat Online Games Starline Dashboard");
        txtMatka.setSelected(true);
        swipe=findViewById(R.id.swipe_layout);

        txtsp=(TextView)findViewById(R.id.txtst_sp);
        txtdp=(TextView)findViewById(R.id.txtst_dp);
        txttp=(TextView)findViewById(R.id.txtst_tp);
        txtsd=(TextView)findViewById(R.id.txtst_sd);

        bt_back=(TextView)findViewById(R.id.txtBack);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        listView=findViewById(R.id.listView);
        getMatkaData();
        arrayList=new ArrayList<Starline_Objects>();
        pgAdapter=new PGAdapter(this,arrayList);
        listView.setAdapter(pgAdapter);
       // btn_back=(TextView) findViewById(R.id.txt_back);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getNotice();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                onStart();
                swipe.setRefreshing(false);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Starline_Objects starline_objects=arrayList.get(position);
                //String stime=starline_objects.getS_game_time();

                //boolean sTime=getTimeStatus(String.valueOf(starline_objects.getS_game_time()));
                int sTime=getTimeFormatStatus(starline_objects.getS_game_time());
                Date date=new Date();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH");
                String ddt=simpleDateFormat.format(date);
                int c_tm=Integer.parseInt(ddt);
                //Toast.makeText(PlayGameActivity.this,"sTime "+sTime+"\n dt"+ddt,Toast.LENGTH_LONG).show();
                if(sTime<=c_tm)
                {
                    common.errorMessageDialog("Betting is closed for today");
                    return;

                }
                else
                {
                    String s_id=starline_objects.getId();
                    String matka_name="STARLINE";

                    Intent intent=new Intent(PlayGameActivity.this, GameActivity.class);
                    intent.putExtra("tim",position);
                    intent.putExtra("matkaName",matka_name);
                    intent.putExtra("m_id",s_id);
                    intent.putExtra("bet","ocb");
                    startActivity(intent);

                    //Toast.makeText(PlayGameActivity.this,""+s_id,Toast.LENGTH_LONG).show();
                }
//                if(flg==1)
//                {
//                    Toast.makeText(PlayGameActivity.this,"close",Toast.LENGTH_LONG).show();
//                }
//                else if(flg==2)
//                {
//                    Toast.makeText(PlayGameActivity.this,"open",Toast.LENGTH_LONG).show();
//                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //setSessionTimeOut(PlayGameActivity.this);
        common.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId());
    }

    public void getMatkaData()
    {
        progressDialog.show();

        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URLs.URL_StarLine, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i=0; i<response.length();i++)
                        {
                            try
                            {
                                JSONObject jsonObject=response.getJSONObject(i);

                                Starline_Objects matkasObjects=new Starline_Objects();
                                matkasObjects.setId(jsonObject.getString("id"));
                                matkasObjects.setS_game_time(jsonObject.getString("s_game_time"));
                                matkasObjects.setS_game_number(jsonObject.getString("s_game_number"));

                                arrayList.add(matkasObjects);
                            }
                            catch (Exception ex)
                            {
                                progressDialog.dismiss();
                                Toast.makeText(PlayGameActivity.this,"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();

                                return;
                            }
                        }
                        pgAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();


                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(PlayGameActivity.this,"Error"+error.toString(),Toast.LENGTH_LONG).show();
//                        Log.e("Volley",error.toString());
                        progressDialog.dismiss();

                    }
                });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);



    }

    public static String getBigdate(String dt)
    {
        int hours=0;
        String d[]=dt.split(" ");
        String f1=d[0].toString();
        String f2=d[1].toString();

        String s[]=f1.split(":");
        int h=Integer.parseInt(s[0].toString());
        int m=Integer.parseInt(s[1].toString());

        if(f2.equals("PM"))
        {
            hours=12+h;
        }
        else
        {
            hours=h;
        }

        String time_24_format=hours+":"+String.valueOf(m)+"0"+":00";
        return time_24_format;


    }

    public static boolean getTimeStatus(String dt)
    {
        String f_time=getBigdate(dt);

        Date cdate=new Date();



        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String time3=format.format(cdate);
        Date date1 = null;
        Date date3=null;
        try {
            date1 = format.parse(f_time);
            date3=format.parse(time3);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        //Toast.makeText(PlayGameActivity.this,"diff :  "+date1.toString(),Toast.LENGTH_LONG).show();

        long difference = date3.getTime() - date1.getTime();
        long as=(difference/1000)/60;

        if(as>0)
            return false;
        else
            return true;


    }

   public int getTimeFormatStatus(String time)
   {
       //02:00 PM;
       String t[]=time.split(" ");
       String time_type=t[1].toString();
       String t1[]=t[0].split(":");
      int tm=Integer.parseInt(t1[0].toString());

      if(time_type.equals("AM"))
      {

      }
      else
      {
          if(tm==12)
          {

          }
          else
          {
              tm=12+tm;
          }
      }
       return tm;

   }


    private void getNotice() {

        progressDialog.show();

        String tag_json_obj = "json_notice_req";
        Map<String, String> params = new HashMap<String, String>();

        CustomVolleyJsonArrayRequest jsonArrayRequest=new CustomVolleyJsonArrayRequest(Request.Method.GET, URLs.URL_NOTICE, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {


                    //[{"id":"1","sp_rate":"160","dp_rate":"320","tp_rate":"800","sd_rate":"10","jd_rate":"100","hs_rate":"1000","fs_rate":"10000","rb_rate":"100","contact":"7354224579","s_sd_rate":"10","s_sp_rate":"160","s_dp_rate":"320","s_tp_rate":"1000","rate_range":"1"}]
                    JSONObject jsonObject=response.getJSONObject(0);
                    String range=jsonObject.getString("rate_range");
                    txtsp.setText("* Single Pana :- "+range+" : "+jsonObject.getString("sp_rate"));
                    txtdp.setText("* Double Pana :- "+range+" : "+jsonObject.getString("dp_rate"));
                    txttp.setText("* Triple Pana :- "+range+" : "+jsonObject.getString("tp_rate"));
                    txtsd.setText("* Single Digit :- "+range+" : "+jsonObject.getString("sd_rate"));

                    // txtNumber.setText(jsonObject.getString("contact"));
                    // Toast.makeText(DrawerNoticeBoardActivity.this,""+jsonObject,Toast.LENGTH_LONG).show();

                    progressDialog.dismiss();
                }
                catch (Exception ex)
                {progressDialog.dismiss();
                    Toast.makeText(PlayGameActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(PlayGameActivity.this,""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest,tag_json_obj);
    }

}
