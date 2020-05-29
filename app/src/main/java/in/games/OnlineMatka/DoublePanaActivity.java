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

public class DoublePanaActivity extends MyBaseActivity  {
    RadioButton rd_open,rd_close;
    RadioGroup rd_group;
    Common common;
    private int stat=0;
    private final String[] doublePanna={"118","226","244","299","334","488","550","668",
            "677","100","119","155","227","335","344","399","588",
            "669","110","200","228","255","336","499","660","688","778","166","229","300",
            "337","355","445","599","779","788","112","220","266","338","400","446","455","699","770","113",
            "122","177","339","366","447","500","799","889","600","114","277","330","448","466","556","880","899","115","133",
            "188","223","377","449","557","566","700","116","224","233","288","440","477",
            "558","800","990","117","144","199","225","388","559","577","667","900"};

    ListView list_table;
    TableAdaper tableAdaper;
    List<TableModel> list;
    int val_p=0;
    TextView  txtClose,txtOpen;
    private Button btnAdd,btnSave,btnType,btnGameType;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;

    TextView btnDelete;
    private TextView txtDigit,txtPoint,txtType;
    //    private TableRow tr;
    TextView bt_back;
    TextView txtMatka;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
    LoadingBar progressDialog;
    private String game_id;
    private String m_id ,start_time,end_time,bet_type;
    private TextView txtWallet_amount ,txt_timer,tv_timer;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_pana);
        common=new Common(DoublePanaActivity.this);
        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        bet_type=getIntent().getStringExtra("m_type");
        end_time = getIntent().getStringExtra("end_time");
        start_time= getIntent().getStringExtra("start_time");
        etPoints=(EditText)findViewById(R.id.etPoints);
        btnType=(Button)findViewById(R.id.btnBetType);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        txt_timer= findViewById(R.id.timer);
        tv_timer = findViewById(R.id.tv_timer);
        progressDialog=new LoadingBar(DoublePanaActivity.this);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        list=new ArrayList<>();
        rd_close=findViewById(R.id.rd_close);
        rd_open=findViewById(R.id.rd_open);
        rd_group=findViewById(R.id.rd_group);
        list_table=findViewById(R.id.list_table);

        txtMatka.setSelected(true);

        bt_back=(TextView)findViewById(R.id.txtBack);


        final AutoCompleteTextView editText=findViewById(R.id.etSingleDigit);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(DoublePanaActivity.this,android.R.layout.simple_list_item_1,doublePanna);
        editText.setAdapter(adapter);

        txtMatka.setText(dashName.toString()+"- Double Patti Board");
        if(bet_type.equalsIgnoreCase("Open"))
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

//        rd_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                RadioButton radioButton=(RadioButton)radioGroup.findViewById(i);
//                String getValue=radioButton.getText().toString();
//                if(getValue.equalsIgnoreCase("Open"))
//                {
//                    if(txt_timer.getVisibility()==View.GONE)
//                    {
//                        txt_timer.setVisibility(View.VISIBLE);
//                    }
//                    if(tv_timer.getVisibility()==View.VISIBLE)
//                    {
//                        tv_timer.setVisibility(View.GONE);
//                    }
//                }
//                else if(getValue.equalsIgnoreCase("Close"))
//                {
//                    if(txt_timer.getVisibility()==View.VISIBLE)
//                    {
//                        txt_timer.setVisibility(View.GONE);
//                    }
//                    if(tv_timer.getVisibility()==View.GONE)
//                    {
//                        tv_timer.setVisibility(View.VISIBLE);
//                    }
//                }
//
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
                    common.errorMessageDialog("Biding closed for today");
                    return;
                }
                else if(t.equals("Open"))
                {
                    common.setBetTypeTooText(dialog,txt_timer,txtOpen,txtClose,m_id,btnType,progressDialog,dww.toString(),tv_timer);
                }
                //
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

//        btnGameType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                details.setDateAndBetTpe(DoublePanaActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
//            }
//        });

        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bet = bet_type;
//                String bet="Select Type";
//                if(rd_close.isChecked())
//                {
//                    bet="close";
//                }
//                else if(rd_open.isChecked())
//                {
//                    bet="open";
//                }
                String dData=editText.getText().toString().trim();
                if(bet.equals("Select Type"))
                {
                    String message=getResources().getString(R.string.bid_closed);
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
                else if(!Arrays.asList(doublePanna).contains(dData))
                {
                    Toast.makeText(DoublePanaActivity.this,"This is invalid paana",Toast.LENGTH_LONG).show();
                    editText.setText("");
                    editText.requestFocus();
                    return;
                }
                else {
                    int pints = Integer.parseInt(etPoints.getText().toString().trim());
                    if (pints < 10) {
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

                       // btnType.setText("Select Type");
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
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

                String cur_time = format.format(date);

                try {
                    Date s_date = format.parse(start_time);
                    Date e_date = format.parse(end_time);
                    Date c_date = format.parse(cur_time);
                    long difference = c_date.getTime() - s_date.getTime();
                    long as=(difference/1000)/60;

                    long diff_close=c_date.getTime()-e_date.getTime();
                    long curr=(diff_close/1000)/60;
                    long current_time=c_date.getTime();
                    if (bet_type.equalsIgnoreCase("open"))
                    {
                        if (as < 0) {

                            common.setBidsDialog(Integer.parseInt(w),list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);

                        }
                        else
                        {
                            common.errorMessageDialog("Betting is Closed Now");
                        }
                    }
                    else if (bet_type.equalsIgnoreCase("close"))
                    {
                        if (curr < 0) {
                            common.setBidsDialog(Integer.parseInt(w),list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);

                        }
                        else {
                            common.errorMessageDialog("Betting is Closed Now");
                        }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }


//                common.setBidsDialog(Integer.parseInt(w),list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        //setSessionTimeOut(DoublePanaActivity.this);
        //private int stat=0;
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
                    btnGameType.setText(s_dt+" Bet " +bet_type.toUpperCase());
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





}
