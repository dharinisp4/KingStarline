package in.games.OnlineMatka;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import com.google.android.material.snackbar.Snackbar;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.cardview.widget.CardView;

import android.telephony.PhoneNumberUtils;
import android.text.Html;
import android.util.Log;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.ArrayList;

import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Model.MatkaObject;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.fragments.HomeFragment;
import in.games.OnlineMatka.utils.LoadingBar;
import maes.tech.intentanim.CustomIntent;
import okhttp3.internal.Util;

import static in.games.OnlineMatka.splash_activity.home_text;
import static in.games.OnlineMatka.splash_activity.message;
import static in.games.OnlineMatka.splash_activity.tagline;

public class HomeActivity extends MyBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    FrameLayout frame_home;
    private TextView txtWallet,txtNotification ,txt_tagline;
    ArrayList<MatkaObject> list;
    LinearLayout lin_container;
    TextView user_profile_name,txt_admin,tv_admin,txt_coadmin,tv_coadmin;
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
        tv_coadmin = findViewById(R.id.tv_coadmin);
        txt_tagline = findViewById(R.id.tagline);
        btn_add = findViewById(R.id.add_points);
        lin_container = findViewById(R.id.lin_container);
        frame_home = findViewById(R.id.frame_home);
        txt_admin=findViewById(R.id.txt_admin);
        tv_admin=findViewById(R.id.tv_admin);
        txt_coadmin=findViewById(R.id.txt_coadmin);
        tv_coadmin=findViewById(R.id.tv_coadmin);
       common=new Common(HomeActivity.this);
        txt_tagline.setText(Html.fromHtml(message.toString()).toString().toUpperCase());
//        txt_game_name.setText(Html.fromHtml(home_text.toString()).toString().toUpperCase());
        tv_admin.setPaintFlags(tv_admin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_coadmin.setPaintFlags(tv_coadmin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txt_admin.setText(common.getNumbers(home_text.toString())[0]);
        tv_admin.setText(common.getNumbers(home_text.toString())[1]);
        txt_coadmin.setText(common.getNumbers(home_text.toString())[2]);
        tv_coadmin.setText(common.getNumbers(home_text.toString())[3]);
        tv_admin.setOnClickListener(this);
        tv_coadmin.setOnClickListener(this);
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

    @Override
    protected void onStart() {
        super.onStart();
        common.setWallet_Amount(txtWallet,progressDialog,Prevalent.currentOnlineuser.getId());


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

    @SuppressLint("NewApi")
    public void whatsapp( String phone,String message) {
//        String formattedNumber = PhoneNumberUtils.formatNumber(phone);
//        try{
//            Log.e("fdsfsdfsd",""+formattedNumber+" - "+message);
//            Intent sendIntent =new Intent(Intent.ACTION_SEND);
//            sendIntent.putExtra("jid", formattedNumber +"@s.whatsapp.net");
//            sendIntent.setPackage("com.whatsapp");
//            sendIntent.setType("text/plain");
//            sendIntent.putExtra(Intent.EXTRA_TEXT,message);
////            startActivity(Intent.createChooser(sendIntent, "Share with"));
//            startActivity(sendIntent);
//        }
//        catch(Exception e)
//        {
//            Toast.makeText(HomeActivity.this,"Error/n"+ e.toString(),Toast.LENGTH_SHORT).show();
//        }

        PackageManager packageManager = getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);

        try {
            String url = "https://api.whatsapp.com/send?phone=+91"+ phone +"&text=" + URLEncoder.encode(message, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
   if(v.getId()==R.id.tv_admin)
   {
       if(!(common.getNumbers(home_text.toString())[1].toString().isEmpty()))
       {
           whatsapp(common.getNumbers(home_text.toString())[1].toString(),"Hello, Admin!");
       }

   }
   else if(v.getId()==R.id.tv_coadmin)
   {
       if(!(common.getNumbers(home_text.toString())[3].toString().isEmpty()))
       {
           whatsapp(common.getNumbers(home_text.toString())[3].toString(),"Hello, Co-Admin!");
       }
   }
    }
}


