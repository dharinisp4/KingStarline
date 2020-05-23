package in.games.OnlineMatka;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import in.games.OnlineMatka.Adapter.TableAdaper;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Intefaces.VolleyCallBack;
import in.games.OnlineMatka.Model.TableModel;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.LoadingBar;


public class TriplePanaActivity extends MyBaseActivity  {

    Common common;
    private final String[] triplePanna={"000","777","444","111","888","555","222","999","666","333"};
    RadioButton rd_open,rd_close;
    RadioGroup rd_group;
    ListView list_table;
    TableAdaper tableAdaper;
    List<TableModel> list;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    private Button btnAdd,btnSave,btnType,btnGameType;
    private TextView txtDigit,txtPoint,txtType;
    //  private TableLayout t1;
    TextView btnDelete;
    //    private TableRow tr;
    TextView bt_back;
    private int stat=0;
    int val_p=0;

    TextView txtMatka;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
   LoadingBar progressDialog;
    private String game_id;
    private String m_id ,end_time,start_time;
    private TextView txtWallet_amount;
    private Dialog dialog;
    private TextView txtOpen,txtClose ,txt_timer,tv_timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triple_pana);
        common=new Common(TriplePanaActivity.this);
        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        end_time = getIntent().getStringExtra("end_time");
        start_time= getIntent().getStringExtra("start_time");
        etPoints=(EditText)findViewById(R.id.etPoints);
        btnType=(Button)findViewById(R.id.btnBetType);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        progressDialog=new LoadingBar(TriplePanaActivity.this);
        txtMatka.setSelected(true);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        txt_timer = findViewById(R.id.timer);
        bt_back=(TextView)findViewById(R.id.txtBack);
        list=new ArrayList<>();
        list_table=findViewById(R.id.list_table);
        rd_close=findViewById(R.id.rd_close);
        rd_open=findViewById(R.id.rd_open);
        rd_group=findViewById(R.id.rd_group);
        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);
tv_timer = findViewById(R.id.tv_timer);
        final AutoCompleteTextView editText=findViewById(R.id.etSingleDigit);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(TriplePanaActivity.this,android.R.layout.simple_list_item_1,triplePanna);
        editText.setAdapter(adapter);

        txtMatka.setText(dashName.toString()+"- Triple Pana Board");

//        btnGameType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                details.setDateAndBetTpe(TriplePanaActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
//            }
//        });
        btnType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String game_type=btnGameType.getText().toString().trim();
                String g[]=game_type.split(" ");
                String t=g[3];
                String dww=g[0];
                //   Toast.makeText(OddEvenActivity.this,""+dww,Toast.LENGTH_LONG).show();
                if(t.equals("Close"))
                {
                    common.errorMessageDialog("Biding closed for this date");
                    return;
                }
                else if(t.equals("Open"))
                {
                    common.setBetTypeTooText(dialog,txt_timer,txtOpen,txtClose,m_id,btnType,progressDialog,dww.toString(),tv_timer);
                }
                // Toast.makeText(OddEvenActivity.this,""+t.toString(),Toast.LENGTH_LONG).show();

            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

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
            if (c_date.before(s_date))
            {

                tv_timer.setVisibility(View.GONE);
                txt_timer.setVisibility(View.VISIBLE);

            }
            else if (c_date.before(e_date) && c_date.after(s_date))
            {
                tv_timer.setVisibility(View.VISIBLE);
                txt_timer.setVisibility(View.GONE);

            }
            else if (c_date.after(e_date))
            {
                txt_timer.setText("Bid Closed");
            }
            Log.e("date",s_date +"\n"+e_date +"\n"+c_date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        rd_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton=(RadioButton)radioGroup.findViewById(i);
                String getValue=radioButton.getText().toString();
                if(getValue.equalsIgnoreCase("Open"))
                {
                    if(txt_timer.getVisibility()==View.GONE)
                    {
                        txt_timer.setVisibility(View.VISIBLE);
                    }
                    if(tv_timer.getVisibility()==View.VISIBLE)
                    {
                        tv_timer.setVisibility(View.GONE);
                    }
                }
                else if(getValue.equalsIgnoreCase("Close"))
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

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bet="Select Type";
                if(rd_close.isChecked())
                {
                    bet="close";
                }
                else if(rd_open.isChecked())
                {
                    bet="open";
                }

                String dData=editText.getText().toString().trim();
                if(bet.equals("Select Type"))
                {  String message=getResources().getString(R.string.bid_closed);
                    common.errorMessageDialog(message);
                    return;
                }
               else if(TextUtils.isEmpty(editText.getText().toString()))
                {
                    editText.setError("Please enter any digit");
                    editText.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(etPoints.getText().toString()))
                {
                    etPoints.setError("Please enter some point");
                    etPoints.requestFocus();
                    return;

                }

                else if(!Arrays.asList(triplePanna).contains(dData))
                {
                    Toast.makeText(TriplePanaActivity.this,"This is invalid paana",Toast.LENGTH_LONG).show();
                    editText.setText("");
                    editText.requestFocus();
                    return;
                }
                else {
                    int pints = Integer.parseInt(etPoints.getText().toString().trim());
                    if (pints < 1) {
                        //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                        etPoints.setError("Minimum Biding amount is 1");
                        etPoints.requestFocus();
                        return;


                    } else {
                        String th = null;
                        if(stat==1)
                        {
                            th="open";
                        }
                        else if(stat==2)
                        {
                            if(bet.equals("open"))
                            {
                                th="open";
                            }
                            else  if(bet.equals("close"))
                            {
                                th="close";
                            }

                        }
                        String d = editText.getText().toString();
                        final String p = etPoints.getText().toString();
                        String g = btnGameType.getText().toString();


                        common.addData(d,p,th,list,tableAdaper,list_table,btnSave);

                        editText.setText("");
                        etPoints.setText("");

                        editText.requestFocus();
                     //   btnType.setText("Select Type");
                    }
                }
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dt=btnGameType.getText().toString().trim();
                String d[]=dt.split(" ");

                String c=d[0].toString();
                String w= txtWallet_amount.getText().toString().trim();
                common.setBidsDialog(Integer.parseInt(w),list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);
//                module.insertData(TriplePanaActivity.this,list,m_id,c,game_id,w,dashName,progressDialog,btnSave);


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
       // setSessionTimeOut(TriplePanaActivity.this);
        int m=Integer.parseInt(m_id.toString());
        if(m> Prevalent.Matka_count)
        {
            Date date=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String ctt=dateFormat.format(date);
            btnGameType.setText(""+ctt);
            common.getStarlineGameData(String.valueOf(m),btnType,progressDialog);
            // btnType.setText("5:00");
            btnGameType.setClickable(false);
            stat=1;
            btnType.setClickable(false);
            common.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId());
        }
        else
        {
            stat=2;
            common.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId());
            common.getBetSession(m_id, progressDialog, new VolleyCallBack() {
                @Override
                public void getTimeDiffrence(HashMap<String, String> map) {

                    long s_diff=Long.parseLong(map.get("s_diff").toString());
                    long e_diff=Long.parseLong(map.get("e_diff").toString());
                    Date c_dat=new Date();
                    SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
                    String s_dt=dateFormat.format(c_dat);
                    if(e_diff>0)
                    {

                        btnGameType.setText(s_dt+" Bet Open");
                    }
                    else
                    {
                        btnGameType.setText(s_dt+" Bet Close");

                    }

                    if(s_diff>0)
                    {
                        rd_open.setChecked(true);
                    }
                    else if(s_diff<0 && e_diff>0)
                    {
                        rd_open.setChecked(false);
                        rd_open.setEnabled(false);
                        rd_close.setChecked(true);
                    }
                    else
                    {
                        rd_open.setChecked(false);
                        rd_open.setEnabled(false);
                        rd_close.setChecked(false);
                        rd_close.setEnabled(false);

                    }

                    progressDialog.dismiss();
                }
            });


        }




    }


}
