package in.games.OnlineMatka;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.games.OnlineMatka.Adapter.TableAdaper;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Intefaces.VolleyCallBack;
import in.games.OnlineMatka.Model.TableModel;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.CustomJsonRequest;
import in.games.OnlineMatka.utils.LoadingBar;


public class DPMotorActivity extends MyBaseActivity  {
    Common common;
    private int stat=0;
    private Button btnAdd,btnSave,btnType,btnGameType;
    private TextView txtDigit,txtPoint,txtType;
     TextView txtMatka;
    private EditText etDgt,etPnt;
    ListView list_table;

    public static boolean isRunning=false;
    TableAdaper tableAdaper;
    List<TableModel> list;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    String matName="";
    TextView btnDelete;
    //    private TableRow tr;
    TextView bt_back;
    private String game_id;
    private String m_id;
    private TextView txtWallet_amount;
    RadioButton rd_open,rd_close;
    RadioGroup rd_group;
    private Dialog dialog;
    private TextView txtOpen,txtClose ,txt_timer,tv_timer;
    String dashName ,end_time,start_time;

 LoadingBar progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dpmotor);
        common=new Common(DPMotorActivity.this);
        rd_close=findViewById(R.id.rd_close);
        rd_open=findViewById(R.id.rd_open);
        rd_group=findViewById(R.id.rd_group);
        dashName=getIntent().getStringExtra("matkaName");
        game_id = getIntent().getStringExtra("game_id");
        m_id = getIntent().getStringExtra("m_id");
        end_time = getIntent().getStringExtra("end_time");
        start_time= getIntent().getStringExtra("start_time");
        list=new ArrayList<>();
        list_table=findViewById(R.id.list_table);
        txtDigit=(TextView)findViewById(R.id.dgt);
        tv_timer=(TextView)findViewById(R.id.tv_timer);
        txtPoint=(TextView)findViewById(R.id.pnt);
        txtType=(TextView)findViewById(R.id.type);
        txt_timer=findViewById(R.id.timer);
        btnType=(Button)findViewById(R.id.btnBetType);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        progressDialog=new LoadingBar(DPMotorActivity.this);

        txtMatka.setSelected(true);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        bt_back=(TextView)findViewById(R.id.txtBack);

        txtMatka.setText(dashName.toString()+"- DP MOTOR Board");
//        btnType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String game_type=btnGameType.getText().toString().trim();
//                String g[]=game_type.split(" ");
//                String t=g[3];
//                String dww=g[0];
//                //   Toast.makeText(OddEvenActivity.this,""+dww,Toast.LENGTH_LONG).show();
//                if(t.equals("Close"))
//                {
//                    errorMessageDialog(DPMotorActivity.this,"Biding closed for this date");
//                    return;
//                }
//                else if(t.equals("Open"))
//                {
//
//                    details.setBetTypeTooText(DPMotorActivity.this,dialog,start_time,end_time,txt_timer,txtOpen,txtClose,m_id,btnType,progressDialog,dww.toString(),tv_timer);
//                }
//                // Toast.makeText(OddEvenActivity.this,""+t.toString(),Toast.LENGTH_LONG).show();
//
//
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


//        btnGameType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                details.setDateAndBetTpe(DPMotorActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
//            }
//        });

        etDgt=(EditText)findViewById(R.id.etSingleDigit);
        etPnt=(EditText)findViewById(R.id.etPoints);

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
                String dt=btnGameType.getText().toString().trim();
                String d[]=dt.split(" ");

                String c=d[0].toString();
                String w= txtWallet_amount.getText().toString().trim();

//                module.insertData(DPMotorActivity.this,list,m_id,c,game_id,w,dashName,progressDialog,btnSave);
                common.setBidsDialog(Integer.parseInt(w),list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);
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


                if (bet.equals("Select Type")) {
                    String message=getResources().getString(R.string.bid_closed);
                    common.errorMessageDialog(message);
                    return;
                }
               else if (TextUtils.isEmpty(etDgt.getText().toString())) {
                    etDgt.setError("Please enter any digit");
                    etDgt.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(etPnt.getText().toString())) {
                    etPnt.setError("Please enter some point");
                    etPnt.requestFocus();
                    return;

                }  else {
                    int pints = Integer.parseInt(etPnt.getText().toString().trim());
                    if (pints < 10) {
                        //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                        etPnt.setError("Minimum Biding amount is 1");
                        etPnt.requestFocus();
                        return;


                    } else {
                        String d = etDgt.getText().toString();
                        final String p = etPnt.getText().toString();

                        String g=null;
                        if(stat==1)
                        {
                            g="open";
//
                        }
                        else if(stat==2)
                        {
                            if(bet.equals("open"))
                            {
                                g="open";

                            }
                            else  if(bet.equals("close"))
                            {
                                g="close";

                            }

                        }

                      //  String g = btnType.getText().toString();

                        String inputData =etDgt.getText().toString().trim();
                        if (inputData.equals("false")) {
                            Toast.makeText(DPMotorActivity.this, "Wrong input", Toast.LENGTH_LONG).show();
                        } else {
                            getDataSet(inputData, p, g);
                        }

//                    Toast.makeText(SpMotorActivity.this,"DDat"+asd,Toast.LENGTH_LONG).show();


                        etDgt.setText("");
                        etPnt.setText("");
                        etDgt.requestFocus();
                       // btnType.setText("Select Type");

                                //arrayList.add(new SingleDigitObjects(data[i].toString(),p,g));




                        //                  Toast.makeText(DPMotorActivity.this,"Data :"+d+"\n"+p+"\n"+g,Toast.LENGTH_LONG).show();
//                    List<String> assd=new ArrayList<String>();
//
//                    String[] s=d.split("");
//
//
//                    //Toast.makeText(SpMotorActivity.this,"DDat"+d[0],Toast.LENGTH_LONG).show();
//
//
//
//                    for(int i=0;i<=s.length-1;i++)
//                    {
//
//                        assd.add(i, Arrays.asList(s).get(i)
//                        );
//                    }
//                    assd.remove(0);
//
//                    String inputData=assd.toString();
//
//                    getDataSet(inputData,p,g);
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

    public void showToast(String s)
    {
    Toast.makeText(DPMotorActivity.this,""+s,Toast.LENGTH_SHORT).show();
    }


    private void getDataSet(final String inputData,final String p,final String g) {

        progressDialog.show();

        String json_tag="json_dpmotor";
        Map<String, String> params = new HashMap<>();
        params.put("arr", inputData);
        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.URL_DpMotor, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    //    Toast.makeText(SpMotorActivity.this, "Data" + response, Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = response;

                    String status = jsonObject.getString("status");
                    JSONArray as = jsonObject.getJSONArray("data");

                    if (status.equals("success")) {
                        for (int i = 0; i <= as.length() - 1; i++) {
                            String q = as.getString(i);
                         //   setTableData(q,p,g);
                            common.addData(q,p,g,list,tableAdaper,list_table,btnSave);

                            //arrayList.add(new SingleDigitObjects(p,d,th));
                        }
                    //    Toast.makeText(DPMotorActivity.this, "Something wrong"+as, Toast.LENGTH_LONG).show();

                        progressDialog.dismiss();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(DPMotorActivity.this, "Something wrong", Toast.LENGTH_LONG).show();

                    }


//                            JSONObject object=new JSONObject(response);
//                            String status=object.getString("status");
//                            List asd=Arrays.asList(object.getString("answer"));

                } catch (Exception ex) {
                    progressDialog.dismiss();
                    Toast.makeText(DPMotorActivity.this, "Error :" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(DPMotorActivity.this, "Error :" + error.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);

    }

    @Override
    protected void onStart() {
        super.onStart();
     //   setSessionTimeOut(DPMotorActivity.this);
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
//            details.setBetDateDay(DPMotorActivity.this,m_id,btnGameType,progressDialog);
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

//    private void setTableData(String datum, final String p, String g) {
//
//
//        tr=new TableRow(DPMotorActivity.this);
//        txtDigit=new TextView(DPMotorActivity.this);
//        txtPoint=new TextView(DPMotorActivity.this);
//        txtType=new TextView(DPMotorActivity.this);
//        btnDelete=new TextView(DPMotorActivity.this);
//
//        btnDelete.setCompoundDrawablesWithIntrinsicBounds(R.drawable.del_btn,0,0,0);
//        TableLayout.LayoutParams layoutParams=new TableLayout.LayoutParams();
//        layoutParams.setMargins(0,10,0,10);
//        tr.setLayoutParams(layoutParams);
//        //    tr.setElevation(20);
//        // tr.setDividerPadding(20);
//        tr.setPadding(10,10,10,10);
//        txtPoint.setText(p);
//        txtDigit.setText(datum.toString());
//        txtType.setText(g.toString());
//        tr.setBackgroundColor(Color.LTGRAY);
//        tr.addView(txtDigit);
//        tr.addView(txtPoint);
//        tr.addView(txtType);
//        tr.addView(btnDelete);
//        //              t1.removeAllViews();
//        t1.addView(tr);
//        // t1.removeViewAt(i);
//        //tr.removeViewAt(i);
//
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                View row=(View)v.getParent();
//                ViewGroup container= ((ViewGroup)row.getParent());
//                container.removeView(row);
//                container.invalidate();
//                int we= t1.getChildCount();
//                int points=Integer.parseInt(p);
//                int tot_pnt=we*points;
//
//                btnSave.setText("(BIDS="+we+")(Points="+tot_pnt+")");
//
//            }
//        });
//
//        int we= t1.getChildCount();
//        int points=Integer.parseInt(p);
//        int tot_pnt=we*points;
//
//        btnSave.setText("(BIDS="+we+")(Points="+tot_pnt+")");
//
//
//
//
//    }

//    private void getDataSet(final String inputData,final String d,final String g) {
//
//        progressDialog.show();
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLSPMotor,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try
//                        {
//
//                            JSONObject jsonObject=new JSONObject(response);
//
//                            String status =jsonObject.getString("status");
//                            JSONArray as =jsonObject.getJSONArray("data");
//
//                            if(status.equals("success"))
//                            {
//                                for(int i=0; i<=as.length()-1; i++)
//                                {
//                                    String p= as.getString(i);
//                                    arrayList.add(new SingleDigitObjects(p,d,g));
//                                }
//
//                                adapter1.notifyDataSetChanged();
//
//                                progressDialog.dismiss();
//                                Toast.makeText(DPMotorActivity.this,"Data"+as,Toast.LENGTH_LONG).show();
//                            }
//                            else
//                            {
//                                progressDialog.dismiss();
//                                Toast.makeText(DPMotorActivity.this,"Something wrong",Toast.LENGTH_LONG).show();
//
//                            }
//
////
////                            JSONObject object=new JSONObject(response);
////                            String status=object.getString("status");
////                            List asd=Arrays.asList(object.getString("answer"));
////                            if(status.equals("success"))
////                            {
////                                progressDialog.dismiss();
////                                Toast.makeText(SpMotorActivity.this,"Data"+asd,Toast.LENGTH_LONG).show();
////
////                            }
////                            else
////                            {
////                                progressDialog.dismiss();
////                                Toast.makeText(SpMotorActivity.this,"Something Wrong",Toast.LENGTH_LONG).show();
////                                return;
////                            }
//
//                        }
//                        catch (Exception ex)
//                        {
//                            progressDialog.dismiss();
//                            Toast.makeText(DPMotorActivity.this,"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();
//                            return;
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                })
//        {
//
//            protected Map<String,String> getParams() throws AuthFailureError {
//
//                Map<String, String> params = new HashMap<>();
//                params.put("arr", inputData);
//                //params.put("password",pass);
//                return params;
//            }
//
//        };
//        RequestQueue requestQueue= Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//    }



}
