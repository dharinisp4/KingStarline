package in.games.OnlineMatka;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import in.games.OnlineMatka.Adapter.MatakListViewAdapter;

import in.games.OnlineMatka.Adapter.MatkaAdapter;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Model.MatkaObject;
import in.games.OnlineMatka.Model.MatkasObjects;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.fragments.HomeFragment;
import in.games.OnlineMatka.utils.LoadingBar;
import maes.tech.intentanim.CustomIntent;

import static in.games.OnlineMatka.splash_activity.home_text;
import static in.games.OnlineMatka.splash_activity.tagline;

public class HomeActivity extends MyBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FrameLayout frame_home;
    private TextView txtWallet,txtNotification ,txt_tagline ,txt_game_name;
    ArrayList<MatkaObject> list;
    LinearLayout lin_container;
    TextView user_profile_name;
    private Dialog dialog;
    private Button btn_dialog_ok ,btn_add;
    private CardView pgCard,callCard,cardReload;
    private String name="";
    private TextView txtWallet_amount;
    private TextView txtUserName,txtNumber;
    LoadingBar progressDialog;
    Common common;
    public static String mainName="";
    int flag =0 ;


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
        lin_container = findViewById(R.id.lin_container);
        frame_home = findViewById(R.id.frame_home);
       common=new Common(HomeActivity.this);
        txt_tagline.setText(tagline.toUpperCase());
        txt_game_name.setText(home_text.toUpperCase());
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

         pgCard=(CardView)findViewById(R.id.cardView3);
        callCard=(CardView)findViewById(R.id.cardView4);
        txtNumber=(TextView)findViewById(R.id.txtNumber);

        common.setMobileNumber(txtNumber);

        progressDialog=new LoadingBar(HomeActivity.this);

        common.setWallet_Amount(txtWallet,progressDialog,Prevalent.currentOnlineuser.getId());

        txtNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(HomeActivity.this,NotificationActivity.class);
                startActivity(intent);

            }
        });

        //txtUserName.setText(name.toUpperCase());


            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this,RequestActivity.class);
                    startActivity(intent);

                }
            });



        callCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                CustomIntent.customType(HomeActivity.this, "up-to-bottom");

             //matkaAdapter.notifyItemRemoved();
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
        HomeFragment fm=new HomeFragment();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_home, fm)
                .addToBackStack(null)
                .commit();



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
//                            HomeActivity.super.onBackPressed();
                            finishAffinity();

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
//            Intent intent=new Intent(HomeActivity.this, DrawerProfileActivity.class);
            Intent intent=new Intent(HomeActivity.this, ProfileActivity.class);
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

            Intent intent=new Intent(HomeActivity.this, BidActivity.class);
            intent.putExtra("type","game");
            startActivity(intent);

        }
        else if (id == R.id.nav_point_history) {

            Intent intent=new Intent(HomeActivity.this,Withdraw_history.class);
            intent.putExtra("type","points");
            startActivity(intent);
        }
        else if (id == R.id.nav_funds) {

            Intent intent=new Intent(HomeActivity.this, RequestActivity.class);
            intent.putExtra("type","add");
            startActivity(intent);
        }
        else if (id == R.id.nav_withdrw) {

            Intent intent=new Intent(HomeActivity.this, WithdrawalActivity.class);
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






}


