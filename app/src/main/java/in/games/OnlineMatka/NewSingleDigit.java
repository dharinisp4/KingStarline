package in.games.OnlineMatka;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Intefaces.VolleyCallBack;
import in.games.OnlineMatka.Model.TableModel;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.LoadingBar;

public class NewSingleDigit extends AppCompatActivity implements View.OnClickListener {
    Common common;
    private Button btnSubmit,btnReset,btnType,btnGameType;
    EditText et_0 , et_1 ,et_2,et_3,et_4,et_5,et_6,et_7,et_8,et_9;
    String zero ="",one="",two="",three ="",four="",five="",six="",seven="",eight="",nine="";

    ListView list_table;

    List<TableModel> list;
    TextView bt_back;
    TextView txtMatka;
    private int stat=0;

    String matName="";
    int pints =0 ;
    boolean is_empty =true ,is_error = false;
    LoadingBar progressDialog;
    private String game_id;
    private String m_id ,end_time,start_time ,bet_type,dashName;
    private TextView txtWallet_amount,txt_timer,tv_timer,txtboard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_single_digit);
         dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        bet_type=getIntent().getStringExtra("m_type");
        end_time = getIntent().getStringExtra("end_time");
        start_time= getIntent().getStringExtra("start_time");
        list=new ArrayList<>();
        common=new Common(NewSingleDigit.this);

        list_table=findViewById(R.id.list_table);
        btnSubmit = findViewById(R.id.btn_sbmit);
        btnReset = findViewById(R.id.btnreset);
        btnType=(Button)findViewById(R.id.btnBetType);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        txt_timer = findViewById(R.id.timer);
        tv_timer= findViewById(R.id.tv_timer);
       txtMatka = findViewById(R.id.matkaname);
       txtboard=(TextView)findViewById(R.id.board);
       txtMatka.setText(dashName);
        txtMatka.setText(dashName.toString()+"- Single Digit Board");
       et_0 = findViewById(R.id.et_0);
       et_1 = findViewById(R.id.et_1);
       et_2 = findViewById(R.id.et_2);
       et_3 = findViewById(R.id.et_3);
       et_4 = findViewById(R.id.et_4);
       et_5 = findViewById(R.id.et_5);
       et_6 = findViewById(R.id.et_6);
       et_7 = findViewById(R.id.et_7);
       et_8 = findViewById(R.id.et_8);
       et_9 = findViewById(R.id.et_9);
        bt_back=(TextView)findViewById(R.id.txtBack);
        bt_back.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        progressDialog=new LoadingBar(NewSingleDigit.this);
        if(bet_type.equalsIgnoreCase("Open"))
        {
            if(txt_timer.getVisibility()== View.GONE)
            {
                txt_timer.setVisibility(View.VISIBLE);
            }
            if(tv_timer.getVisibility()==View.VISIBLE)
            {
                tv_timer.setVisibility(View.GONE);
            }
        }
        else if(bet_type.equalsIgnoreCase("Close"))
        {
            if(txt_timer.getVisibility()==View.VISIBLE)
            {
                txt_timer.setVisibility(View.GONE);
            }
            if(tv_timer.getVisibility()==View.GONE)
            {
                tv_timer.setVisibility(View.VISIBLE);
            }
        }

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        String cur_time = format.format(date);

        try {
            Date s_date = format.parse(start_time);
            Date e_date = format.parse(end_time);
            Date c_date = format.parse(cur_time);
            common.setCounterTimer( common.getTimeDifference(start_time),txt_timer);
            common.setEndCounterTimer( common.getTimeDifference(end_time),tv_timer);
//
//            if (c_date.before(s_date))
//            {
//
//                tv_timer.setVisibility(View.GONE);
//                txt_timer.setVisibility(View.VISIBLE);
//
//            }
//            else if (c_date.before(e_date) && c_date.after(s_date))
//            {
//                tv_timer.setVisibility(View.VISIBLE);
//                txt_timer.setVisibility(View.GONE);
//
//            }
//            else if (c_date.after(e_date))
//            {
//                txt_timer.setText("Bid Closed");
//            }
//            Log.e("date",s_date +"\n"+e_date +"\n"+c_date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.txtBack) {
            finish();
        } else if (id == R.id.btnreset) {
            clearCntrls();

        } else if (id == R.id.btn_sbmit) {
            String bet = bet_type;
            one =et_1.getText().toString();
            two = et_2.getText().toString();
            three= et_3.getText().toString();
            four = et_4.getText().toString();
            five = et_5.getText().toString();
            six = et_6.getText().toString();
           seven = et_7.getText().toString();
           eight = et_8.getText().toString();
            nine = et_9.getText().toString();
            zero = et_0.getText().toString();


          checkpoints(one,et_1,"1",bet_type);
          checkpoints(two,et_2,"2",bet_type);
          checkpoints(three,et_3,"3",bet_type);
          checkpoints(four,et_4,"4",bet_type);
          checkpoints(five,et_5,"5",bet_type);
          checkpoints(six,et_6,"6",bet_type);
          checkpoints(seven,et_7,"7",bet_type);
          checkpoints(eight,et_8,"8",bet_type);
          checkpoints(nine,et_9,"9",bet_type);
          checkpoints(zero,et_0,"0",bet_type);

                if (is_empty) {
                    Toast.makeText(NewSingleDigit.this, "Please enter some points", Toast.LENGTH_LONG).show();
                } else {
                    if (is_error)
                    {

                    }
                    else {
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
                        if (bet_type.equalsIgnoreCase("open")) {
                            if (as < 0) {

                                common.setBidsDialog(Integer.parseInt(w), list, m_id, c, game_id, w, dashName, progressDialog, btnSubmit, start_time, end_time);
                                clearCntrls();
                            } else {
                                common.errorMessageDialog("Betting is Closed Now");
                            }
                        } else if (bet_type.equalsIgnoreCase("close")) {
                            if (curr < 0) {
                                common.setBidsDialog(Integer.parseInt(w), list, m_id, c, game_id, w, dashName, progressDialog, btnSubmit, start_time, end_time);
                                clearCntrls();
                            } else {
                                common.errorMessageDialog("Betting is Closed Now");
                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                        common.setBidsDialog(Integer.parseInt(w), list, m_id, c, game_id, w, dashName, progressDialog, btnSubmit, start_time, end_time);

//                    list.clear();
                    }
                }
            }

        }




    @Override
    protected void onStart() {
        super.onStart();
        int m = Integer.parseInt(m_id.toString());
        if (m > Prevalent.Matka_count) {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ctt = dateFormat.format(date);
            btnGameType.setText("" + ctt);
            common.getStarlineGameData(String.valueOf(m), btnType, progressDialog);
            // btnType.setText("5:00");
            btnGameType.setClickable(false);
            stat = 1;
            btnType.setClickable(false);
            common.setWallet_Amount(txtWallet_amount, progressDialog, Prevalent.currentOnlineuser.getId());
        } else {
            stat = 2;
            common.setWallet_Amount(txtWallet_amount, progressDialog, Prevalent.currentOnlineuser.getId());
            common.getBetSession(m_id, progressDialog, new VolleyCallBack() {
                @Override
                public void getTimeDiffrence(HashMap<String, String> map) {

                    long s_diff = Long.parseLong(map.get("s_diff").toString());
                    long e_diff = Long.parseLong(map.get("e_diff").toString());
                    Date c_dat = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy EEEE");
                    String s_dt = dateFormat.format(c_dat);
                    btnGameType.setText(s_dt + " Bet " + bet_type.toUpperCase());
//                    if(e_diff>0)
//                    {
//
//                        btnGameType.setText(s_dt+" Bet Open");
//                    }
//                    else
//                    {
//                        btnGameType.setText(s_dt+" Bet Close");
//
//                    }
//
//                    if(s_diff>0)
//                    {
//                        rd_open.setChecked(true);
//                    }
//                    else if(s_diff<0 && e_diff>0)
//                    {
//                        rd_open.setChecked(false);
//                        rd_open.setEnabled(false);
//                        rd_close.setChecked(true);
//                    }
//                    else
//                    {
//                        rd_open.setChecked(false);
//                        rd_open.setEnabled(false);
//                        rd_close.setChecked(false);
//                        rd_close.setEnabled(false);
//
//                    }

                    progressDialog.dismiss();
                }
            });


        }
    }

        public void checkpoints(String s ,EditText editText, String digit, String bet_type)
        {
            if (!s.isEmpty()) {
                pints = Integer.parseInt(editText.getText().toString());
                if ( pints<10)
                {
                   editText.setError("Minimum Biding amount is 10");
                    editText.requestFocus();
                    is_error = true;
                }
                else {
                    list.add(new TableModel(digit, s, bet_type));
                    is_error = false;
                }
                is_empty = false;
            }

        }
        public void clearCntrls()
        {
            et_0.setText("");
            et_1.setText("");
            et_2.setText("");
            et_3.setText("");
            et_4.setText("");
            et_5.setText("");
            et_6.setText("");
            et_7.setText("");
            et_8.setText("");
            et_9.setText("");
        }
    }

