package in.games.OnlineMatka;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import in.games.OnlineMatka.Adapter.PointsAdapter;
import in.games.OnlineMatka.Adapter.TableAdaper;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Intefaces.VolleyCallBack;
import in.games.OnlineMatka.Model.TableModel;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.LoadingBar;

import static in.games.OnlineMatka.Adapter.PointsAdapter.is_empty;
import static in.games.OnlineMatka.Adapter.PointsAdapter.is_error;
import static in.games.OnlineMatka.Adapter.PointsAdapter.jodi_list;
import static in.games.OnlineMatka.Objects.sp_input_data.group_jodi_digits;

public class NewJodi extends AppCompatActivity implements View.OnClickListener {
    Common common;

    List<TableModel> list;
List<String> digit_list ;
    private int val_p=0;
    private Button btnSubmit,btnReset,btnGameType;
    TextView bt_back;
    TextView txtMatka;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
    LoadingBar progressDialog;
    private TextView txtWallet_amount;
    private String game_id;
    private String m_id ,end_time,start_time ,bet_type;
    private Dialog dialog;
    private TextView txtOpen,txtClose ,txt_timer,tv_timer;
    RecyclerView rv_digits ;
    PointsAdapter pointsAdapter ;
   String dashName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_jodi);
        common=new Common(NewJodi.this);
        dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        bet_type=getIntent().getStringExtra("m_type");
        end_time = getIntent().getStringExtra("end_time");
        start_time= getIntent().getStringExtra("start_time");
        list=new ArrayList<>();
       rv_digits = findViewById(R.id.jodi_points);
        etPoints=(EditText)findViewById(R.id.etPoints);
        txt_timer = findViewById(R.id.timer);
        tv_timer= findViewById(R.id.tv_timer);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        progressDialog=new LoadingBar(NewJodi.this);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        bt_back=(TextView)findViewById(R.id.txtBack);
        btnSubmit = findViewById(R.id.btn_sbmit);
        btnReset = findViewById(R.id.btnreset);
        txtMatka.setSelected(true);
        txtMatka.setText(dashName.toString()+"- Jodi Board");
        bt_back.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnReset.setOnClickListener(this);
       digit_list =  Arrays.asList(group_jodi_digits);
        pointsAdapter = new PointsAdapter(digit_list,NewJodi.this);
        rv_digits.setLayoutManager(new GridLayoutManager(this,2));
        rv_digits.setAdapter(pointsAdapter);

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        String cur_time = format.format(date);

        try {
            Date s_date = format.parse(start_time);
            Date e_date = format.parse(end_time);
            Date c_date = format.parse(cur_time);
            common.setCounterTimer( common.getTimeDifference(start_time),txt_timer);
            common.setEndCounterTimer( common.getTimeDifference(end_time),tv_timer);

            if (c_date.before(s_date))
            {
                if(txt_timer.getVisibility()==View.VISIBLE)
                {
                    txt_timer.setVisibility(View.GONE);
                }
                tv_timer.setVisibility(View.VISIBLE);
//                txt_timer.setVisibility(View.VISIBLE);

            }
            else if (c_date.before(e_date) && c_date.after(s_date))
            {
                if(txt_timer.getVisibility()==View.VISIBLE)
                {
                    txt_timer.setVisibility(View.GONE);
                }
                tv_timer.setVisibility(View.VISIBLE);
//                txt_timer.setVisibility(View.GONE);

            }
            else if (c_date.after(e_date))
            {
                if(tv_timer.getVisibility()==View.VISIBLE)
                {
                    tv_timer.setVisibility(View.GONE);
                }
                txt_timer.setText("Bid Closed");
            }
            Log.e("date",s_date +"\n"+e_date +"\n"+c_date);

        } catch (ParseException e) {
            e.printStackTrace();
        }





    }

    @Override
    protected void onStart() {
        super.onStart();
        common.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId());
//        details.setBetDateDay(JodiDigitActivity.this,m_id,btnGameType,progressDialog);
//        getBetDateDay(m_id);
        common.getBetSession(m_id, progressDialog, new VolleyCallBack() {
            @Override
            public void getTimeDiffrence(HashMap<String, String> map) {

                long s_diff=Long.parseLong(map.get("s_diff").toString());
                long e_diff=Long.parseLong(map.get("e_diff").toString());
                Date c_dat=new Date();
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
                String s_dt=dateFormat.format(c_dat);
                btnGameType.setText(s_dt+" Bet " +bet_type.toUpperCase());
//                if(e_diff>0)
//                {
//
//                    btnGameType.setText(s_dt+" Bet Open");
//                }
//                else
//                {
//                    btnGameType.setText(s_dt+" Bet Close");
//                }


                progressDialog.dismiss();
            }
        });


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.txtBack)
        {
            finish();
        }
        else if (id == R.id.btn_sbmit) {
            if (is_empty) {
                Toast.makeText(NewJodi.this, "Please enter some points", Toast.LENGTH_LONG).show();
            } else {
                if (is_error) {
                    Toast.makeText(NewJodi.this, "Minimum bid amount is 10", Toast.LENGTH_LONG).show();
                } else {
                    String dt = btnGameType.getText().toString().trim();
                    String d[] = dt.split(" ");

                    String c = d[0].toString();
                    String w = txtWallet_amount.getText().toString().trim();
                    Date date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

                    String cur_time = format.format(date);

                    try {
                        Date s_date = format.parse(start_time);
                        Date e_date = format.parse(end_time);
                        Date c_date = format.parse(cur_time);
                        long difference = c_date.getTime() - s_date.getTime();
                        long as = (difference / 1000) / 60;

                        long diff_close = c_date.getTime() - e_date.getTime();
                        long curr = (diff_close / 1000) / 60;
                        long current_time = c_date.getTime();

//                            if (as < 0) {
//
//                                common.setBidsDialog(Integer.parseInt(w), list, m_id, c, game_id, w, dashName, progressDialog, btnSubmit, start_time, end_time);
////                                clearCntrls();
//                            }
//
//                          else  if (curr < 0) {
//                                common.setBidsDialog(Integer.parseInt(w), list, m_id, c, game_id, w, dashName, progressDialog, btnSubmit, start_time, end_time);
////                                clearCntrls();
//                            } else {
//                                common.errorMessageDialog("Betting is Closed Now");
//                            }


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    common.setBidsDialog(Integer.parseInt(w), jodi_list, m_id, c, game_id, w, dashName, progressDialog, btnSubmit, start_time, end_time);

//                    list.clear();
                }
            }

        }
        else if (id == R.id.btnreset)
        {

        }
    }
}
