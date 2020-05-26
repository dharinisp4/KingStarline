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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import in.games.OnlineMatka.Adapter.TableAdaper;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Intefaces.VolleyCallBack;
import in.games.OnlineMatka.Model.MatkasObjects;
import in.games.OnlineMatka.Model.TableModel;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.CustomJsonRequest;
import in.games.OnlineMatka.utils.LoadingBar;

public class JodiDigitActivity extends MyBaseActivity  {
    RadioButton rd_open,rd_close;
    RadioGroup rd_group;
    Common common;
    private final String[] singlePaana={"11","16","61","66",
            "12","21","17","71","26","62","67","76",
            "13","31","18","81","36","63","68","86",
            "14","41","19","91","46","64","69","96",
            "15","51","10","01","56","65","60","06",
            "22","27","72","77",
            "23","32","28","82","37","73","78","87",
            "24","42","29","92","47","74","79","97",
            "25","52","20","02","57","75","70","07",
            "33","38","83","88",
            "34","43","39","93","48","84","89","98",
            "35","53","30","03","58","85","80","08",
            "44","49","94","99",
            "45","54","40","04","59","95","90","09",
            "55","50","05","00",
            };
    ListView list_table;
    TableAdaper tableAdaper;
    List<TableModel> list;

    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
private int val_p=0;
    private Button btnAdd,btnSave,btnGameType;

    private TextView txtDigit,txtPoint,txtType,txtdgt;

    TextView btnDelete;

    TextView bt_back;
    TextView txtMatka;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
   LoadingBar progressDialog;
    private TextView txtWallet_amount;
    private String game_id;
    private String m_id ,end_time,start_time;
    private Dialog dialog;
    private TextView txtOpen,txtClose ,txt_timer,tv_timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jodi_digit);
         common=new Common(JodiDigitActivity.this);
        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        end_time = getIntent().getStringExtra("end_time");
        start_time= getIntent().getStringExtra("start_time");
        list=new ArrayList<>();
        rd_close=findViewById(R.id.rd_close);
        rd_open=findViewById(R.id.rd_open);
        rd_group=findViewById(R.id.rd_group);
        rd_open.setVisibility(View.GONE);
        txtdgt=findViewById(R.id.txtdgt);
        txtdgt.setText(getResources().getString(R.string.tab_jodi));
        list_table=findViewById(R.id.list_table);
        etPoints=(EditText)findViewById(R.id.etPoints);
        txt_timer = findViewById(R.id.timer);
        tv_timer= findViewById(R.id.tv_timer);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        progressDialog=new LoadingBar(JodiDigitActivity.this);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);


        final AutoCompleteTextView editText=findViewById(R.id.etSingleDigit);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(JodiDigitActivity.this,android.R.layout.simple_list_item_1,singlePaana);
        editText.setAdapter(adapter);

        bt_back=(TextView)findViewById(R.id.txtBack);

        txtMatka.setSelected(true);
        txtMatka.setText(dashName.toString()+"- Jodi Digit Board");

//        btnGameType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                common.setDateAndBetTpeTo(dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
//
//            }
//        });
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

        rd_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton=(RadioButton)radioGroup.findViewById(i);
                String getValue=radioButton.getText().toString();
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
                    if(txt_timer.getVisibility()==View.VISIBLE)
                    {
                        txt_timer.setVisibility(View.GONE);
                    }
                    if(tv_timer.getVisibility()==View.GONE)
                    {
                        tv_timer.setVisibility(View.VISIBLE);
                    }
//                }

            }
        });


        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String date_b = btnGameType.getText().toString().trim();
                String x[] = date_b.split(" ");
                String vt = x[3];

                if (vt.equals("Open")) {


                    String dt=btnGameType.getText().toString().trim();
                    String d[]=dt.split(" ");

                    String c=d[0].toString();
                    String w= txtWallet_amount.getText().toString().trim();

//                    module.insertData(JodiDigitActivity.this,list,m_id,c,game_id,w,dashName,progressDialog,btnSave);
                   common.setBidsDialog(Integer.parseInt(w),list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);
                } else {
                    String message = "Biding closed for this date";
                    common.errorMessageDialog( message);
                    return;
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            //    String bet=btnType.getText().toString();
                String dData=editText.getText().toString().trim();
                 if(TextUtils.isEmpty(editText.getText().toString()))
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

                else if(!Arrays.asList(singlePaana).contains(dData))
                {
                    String message="Invalid Jodi";
                    common.errorMessageDialog(message);
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
                          String d = editText.getText().toString();
                          final String p = etPoints.getText().toString();
                          String g = btnGameType.getText().toString();
                          String th="close";

                          common.addData(d,p,th,list,tableAdaper,list_table,btnSave);


                          editText.setText("");
                          etPoints.setText("");
                          editText.requestFocus();
                         // btnType.setText("Select Type");


//                    String th=btnType.getText().toString();

                      }


                  }
            }
        });
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd/MM/yyyy");
        String day=calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
        String saveDate=currentDate.format(calendar.getTime());


        String full=saveDate+" "+day+" Bet";
        btnGameType.setText(full);





    }

    @Override
    protected void onStart() {
        super.onStart();
       // setSessionTimeOut(JodiDigitActivity.this);
       // Toast.makeText(JodiDigitActivity.this,""+m_id,Toast.LENGTH_LONG).show();
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
                    rd_close.setChecked(true);
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

    public void getBetDateDay(final String m_id)
    {
        String json_request_tag="matka_with_id";
        HashMap<String,String> params=new HashMap<String, String>();
        params.put("id",m_id);
        progressDialog.show();

        CustomJsonRequest customJsonRequest=new CustomJsonRequest( Request.Method.POST, URLs.URL_MATKA_WITH_ID, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try
                {
                    String status=response.getString("status");
                    if(status.equals("success"))
                    {
                        JSONObject jsonObject=response.getJSONObject("data");
                        MatkasObjects matkasObjects = new MatkasObjects();
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

                        String dt=new SimpleDateFormat("EEEE").format(new Date());
                        String bid_start = "";
                        String bid_end="";
//                        String bid_start = matkasObjects.getBid_start_time();
//                        String bid_end=matkasObjects.getBid_end_time().toString();

                        if(dt.equals("Sunday"))
                        {
                            bid_start=matkasObjects.getStart_time();
                            bid_end=matkasObjects.getEnd_time();
                        }
                        else if(dt.equals("Saturday"))
                        {
                            bid_start=matkasObjects.getSat_start_time();
                            bid_end=matkasObjects.getSat_end_time();

                        }
                        else
                        {
                            bid_start=matkasObjects.getBid_start_time();
                            bid_end=matkasObjects.getBid_end_time();

                        }
                        String time1 = bid_start.toString();
                        String time2 = bid_end.toString();
                        Date cdate=new Date();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        String time3=format.format(cdate);
                        Date date1 = null;
                        Date date2=null;
                        Date date3=null;
                        try {
                            date1 = format.parse(time1);
                            date2 = format.parse(time2);
                            date3=format.parse(time3);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                        long difference = date3.getTime() - date1.getTime();
                        long as=(difference/1000)/60;

                        long diff_close=date3.getTime()-date2.getTime();
                        long c=(diff_close/1000)/60;

                        Date c_dat=new Date();
                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
                        String s_dt=dateFormat.format(c_dat);
                        String n_dt= common.getNextDate(s_dt);
                        String a_dt= common.getNextDate(n_dt);
                       // Toast.makeText(JodiDigitActivity.this,""+as,Toast.LENGTH_LONG).show();
                        if(as>0)
                        {progressDialog.dismiss();
                            btnGameType.setText(s_dt+" Bet Close");


                             //Toast.makeText(OddEvenActivity.this,""+s_dt+"  Close",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            btnGameType.setText(s_dt+" Bet Open");

                        }


//                        }


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(JodiDigitActivity.this,"Something wrong",Toast.LENGTH_LONG).show();


                    }



                    //Toast.makeText(context,""+response,Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    Toast.makeText(JodiDigitActivity.this,"something went wrong ",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(JodiDigitActivity.this,""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,json_request_tag);
    }

}
