package in.games.OnlineMatka;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import in.games.OnlineMatka.Adapter.MatakListViewAdapter;

import in.games.OnlineMatka.Adapter.MatkaAdapter;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Model.MatkaObject;
import in.games.OnlineMatka.Model.MatkasObjects;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.LoadingBar;
import maes.tech.intentanim.CustomIntent;

import static in.games.OnlineMatka.splash_activity.home_text;
import static in.games.OnlineMatka.splash_activity.tagline;

public class HomeActivity extends MyBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView txtWallet,txtNotification ,txt_tagline ,txt_game_name;
    ArrayList<MatkaObject> list;
    TextView user_profile_name;
    SwipeRefreshLayout swipe;
    private  MatkaAdapter matkaAdapter;
    private MatakListViewAdapter matakListViewAdapter;
    private Dialog dialog;
    private Button btn_dialog_ok ,btn_add ,btn_withdrw;
    private CardView pgCard,callCard,cardReload;
    private String name="";
    private TextView txtWallet_amount;
    private TextView txtUserName,txtNumber;
    private ArrayList<MatkasObjects> matkaList;
    private ListView listView;
    LoadingBar progressDialog;
    Common common;
    public static String mainName="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtNotification=(TextView)findViewById(R.id.txtNotification);
        txtWallet=(TextView)findViewById(R.id.txtWallet);
        txt_game_name = findViewById(R.id.game_name);
        txt_tagline = findViewById(R.id.tagline);
        btn_add = findViewById(R.id.add_points);
        btn_withdrw = findViewById(R.id.withdrw_points);
       common=new Common(HomeActivity.this);
        //setSupportActionBar(toolbar);

        txt_tagline.setText(tagline);
        txt_game_name.setText(home_text);
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


            }
        },intentFilter);

        list=new ArrayList<>();

         matkaList=new ArrayList();
         pgCard=(CardView)findViewById(R.id.cardView3);
        callCard=(CardView)findViewById(R.id.cardView4);
        listView=findViewById(R.id.listView);
        swipe=findViewById(R.id.swipe_layout);
        txtNumber=(TextView)findViewById(R.id.txtNumber);

        common.setMobileNumber(txtNumber);

        progressDialog=new LoadingBar(HomeActivity.this);


        txtWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog=new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.dialog_wallet_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                btn_dialog_ok=(Button)dialog.findViewById(R.id.wallet_ok);
                txtWallet_amount=(TextView)dialog.findViewById(R.id.wallet_amount);

                dialog.setTitle("Wallet Amount");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                common.setWallet_Amount(txtWallet_amount,progressDialog,Prevalent.currentOnlineuser.getId());

                btn_dialog_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        txtNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(HomeActivity.this,NotificationActivity.class);
                startActivity(intent);

            }
        });

        //txtUserName.setText(name.toUpperCase());

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
       // lstView=findViewById(R.id.listView);
       // cardReload=findViewById(R.id.cardView1);

            btn_withdrw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this,WithdrawalActivity.class);
                    startActivity(intent);

                }
            });
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this,RequestActivity.class);
                    startActivity(intent);

                }
            });


              swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                  @Override
                  public void onRefresh() {

                      onStart();
                      swipe.setRefreshing(false);
                  }
              });
        callCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



//Toast.makeText(HomeActivity.this,sd,Toast.LENGTH_LONG).show();
//
                Intent intent=new Intent(Intent.ACTION_DIAL);
                String number=txtNumber.getText().toString().trim();
                intent.setData(Uri.parse("tel: "+number));
                startActivity(intent);
            }
        });
        pgCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,PlayGameActivity.class);
                startActivity(intent);

             //matkaAdapter.notifyItemRemoved();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String dt=new SimpleDateFormat("EEEE").format(new Date());
                MatkasObjects objects=matkaList.get(position);
//
      // Toast.makeText(HomeActivity.this,""+Prevalent.Matka_count,Toast.LENGTH_LONG).show();

                // String st=txtStatus.getText().toString();
                String m_id=objects.getId().toString().trim();
                String matka_name=objects.getName().toString().trim();

                    // Toast.makeText(context,"Position"+m_id,Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(HomeActivity.this, GameActivity.class);
                //    intent.putExtra("tim",position);
                    intent.putExtra("matkaName",matka_name);
                    intent.putExtra("m_id",m_id);
                    intent.putExtra("end_time",objects.getBid_end_time());
                    intent.putExtra("start_time",objects.getBid_start_time());
                  //  intent.putExtra("bet","cb");
                    startActivity(intent);
                CustomIntent.customType(HomeActivity.this, "up-to-bottom");


            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       navigationView.setItemIconTintList(HomeActivity.this.getResources().getColorStateList(R.color.colorPrimaryDark));
       txtUserName=(TextView)navigationView.getHeaderView(0).findViewById(R.id.profile_user_name);
       if(Prevalent.currentOnlineuser.getName().isEmpty() || Prevalent.currentOnlineuser.getName().equals(""))
       {

       }
       else {
           txtUserName.setText(Prevalent.currentOnlineuser.getName());
       }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);

            builder.setMessage("Do you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HomeActivity.super.onBackPressed();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();


 // setSessionTimeOut(HomeActivity.this);


        getMatkaData();
        matakListViewAdapter=new MatakListViewAdapter(HomeActivity.this,matkaList);
        listView.setAdapter(matakListViewAdapter);
       // listView.setSmoothScrollbarEnabled(true);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            Intent intent=new Intent(HomeActivity.this,NotificationActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.action_wallet)
        {
             dialog=new Dialog(HomeActivity.this);
             dialog.setContentView(R.layout.dialog_wallet_layout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
             btn_dialog_ok=(Button)dialog.findViewById(R.id.wallet_ok);
             txtWallet_amount=(TextView)dialog.findViewById(R.id.wallet_amount);

             dialog.setTitle("Wallet Amount");
             dialog.setCanceledOnTouchOutside(false);
             dialog.show();

             common.setWallet_Amount(txtWallet_amount,progressDialog,Prevalent.currentOnlineuser.getId());

            btn_dialog_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
            Intent intent=new Intent(HomeActivity.this, DrawerProfileActivity.class);
            startActivity(intent);
      }
        else if (id == R.id.nav_home)
        {
            Intent intent=new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(intent);
        }
 else if (id == R.id.nav_mpin) {
            Intent intent=new Intent(HomeActivity.this, DrawerGenMpinActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_how_toPlay) {

            Intent intent=new Intent(HomeActivity.this, DrawerHowToPlayActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_history) {

            Intent intent=new Intent(HomeActivity.this, HistoryActivity.class);
            intent.putExtra("type","game");
            startActivity(intent);

        }
        else if (id == R.id.nav_point_history) {

            Intent intent=new Intent(HomeActivity.this, HistoryActivity.class);
            intent.putExtra("type","points");
            startActivity(intent);
        }
        else if (id == R.id.nav_funds) {

            Intent intent=new Intent(HomeActivity.this, FundsActivity.class);
            intent.putExtra("type","add");
            startActivity(intent);
        }
        else if (id == R.id.nav_withdrw) {

            Intent intent=new Intent(HomeActivity.this, FundsActivity.class);
            intent.putExtra("type","withdraw");
            startActivity(intent);
        }
        else if (id == R.id.nav_gameRates) {
            Intent intent=new Intent(HomeActivity.this, DrawerGameRates.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_noticeBoard) {
            Intent intent=new Intent(HomeActivity.this, DrawerNoticeBoardActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_logout) {

            AlertDialog.Builder builder=new AlertDialog.Builder(this);

            builder.setMessage("LOGOUT?")
                    .setCancelable(false)
                    .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(HomeActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void getMatkaData()
    {
        progressDialog.show();

        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URLs.URL_Matka, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //   matkaAdapter.notifyDataSetChanged();

                        Log.d("matka",String.valueOf(response));

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
                                matakListViewAdapter.notifyDataSetChanged();


                            }
                            catch (Exception ex)
                            {
                                progressDialog.dismiss();
                                Toast.makeText(HomeActivity.this,"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();

                                return;
                            }
                        }

                        progressDialog.dismiss();


                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                      String msg=common.VolleyErrorMessage(error);
                      if(!msg.isEmpty())
                      {
                          common.showToast(""+msg);
                      }
                    }
                });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);



    }



}


