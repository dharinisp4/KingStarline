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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.games.OnlineMatka.Adapter.TableAdaper;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Intefaces.VolleyCallBack;
import in.games.OnlineMatka.Model.TableModel;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.CustomJsonRequest;
import in.games.OnlineMatka.utils.LoadingBar;

public class SpMotorActivity extends MyBaseActivity   {
    Common common;
    private int stat=0;
    RadioButton rd_open,rd_close;
    RadioGroup rd_group;
    private final String[] triplePanna={"012","712","435","123","890","567","234","912","678","345"};
    private Button btnAdd,btnSave,btnType,btnGameType;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    TextView txtMatka;
    private TextView txtDigit,txtPoint,txtType;
    //  private TableLayout t1;
    TextView btnDelete;
    ListView list_table;
    TableAdaper tableAdaper;
    List<TableModel> list;
    TextView bt_back;
private EditText etDigits;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
    LoadingBar progressDialog;
    private String game_id,end_time,start_time;
    private String m_id;
    private TextView txtWallet_amount,txt_timer;
    private Dialog dialog;
    private TextView txtOpen,txtClose ,tv_timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_motor);
        common=new Common(SpMotorActivity.this);
        final String dashName = getIntent().getStringExtra("matkaName");
        game_id = getIntent().getStringExtra("game_id");
        m_id = getIntent().getStringExtra("m_id");
        end_time = getIntent().getStringExtra("end_time");
        start_time= getIntent().getStringExtra("start_time");
        etPoints = (EditText) findViewById(R.id.etPoints);
        rd_close=findViewById(R.id.rd_close);
        rd_open=findViewById(R.id.rd_open);
        rd_group=findViewById(R.id.rd_group);
        btnType = (Button) findViewById(R.id.btnBetType);
        btnGameType = (Button) findViewById(R.id.btnBetStatus);
        txtMatka = (TextView) findViewById(R.id.board);
        progressDialog = new LoadingBar(SpMotorActivity.this);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        txt_timer = findViewById(R.id.timer);
        tv_timer= findViewById(R.id.tv_timer);

        btnAdd = (Button) findViewById(R.id.digit_add);
        btnSave = (Button) findViewById(R.id.digit_save);
        list=new ArrayList<>();
        list_table=findViewById(R.id.list_table);
        bt_back=(TextView)findViewById(R.id.txtBack);
        txtMatka.setSelected(true);

        txtMatka.setText(dashName.toString()+"- SP Motor Board");

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


        etDigits = findViewById(R.id.etSingleDigit);
//        final AutoCompleteTextView editText = findViewById(R.id.etSingleDigit);
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(SpMotorActivity.this, android.R.layout.simple_list_item_1, triplePanna);
//        editText.setAdapter(adapter);

//        btnGameType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                details.setDateAndBetTpe(SpMotorActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
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

                String dData = etDigits.getText().toString().trim();
                if (bet.equals("Select Type")) {
                    String message=getResources().getString(R.string.bid_closed);
                    common.errorMessageDialog(message);
                    return;
                }
               else if (TextUtils.isEmpty(etDigits.getText().toString())) {
                    etDigits.setError("Please enter any digit");
                    etDigits.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(etPoints.getText().toString())) {
                    etPoints.setError("Please enter some point");
                    etPoints.requestFocus();
                    return;

                }  else {
                    int pints = Integer.parseInt(etPoints.getText().toString().trim());
                    if (pints < 10) {
                        //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                        etPoints.setError("Minimum Biding amount is 1");
                        etPoints.requestFocus();
                        return;


                    } else {
                        list.clear();
                        String d = etDigits.getText().toString();
                        String p = etPoints.getText().toString();
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



                        //Toast.makeText(SpMotorActivity.this,"DDat"+d[0],Toast.LENGTH_LONG).show();

                        //String asd=spInput(d);


                        //  String inputData = String.valueOf(assd);
                        String inputData =etDigits.getText().toString().trim();
                        if (inputData.equals("false")) {
                            Toast.makeText(SpMotorActivity.this, "Wrong input", Toast.LENGTH_LONG).show();
                        } else {
                            getDataSet(inputData, p, th);
                        }

//                    Toast.makeText(SpMotorActivity.this,"DDat"+asd,Toast.LENGTH_LONG).show();


                        etPoints.setText("");
                        etDigits.setText("");
                        etDigits.requestFocus();
                      //  btnType.setText("Select Type");
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
//                module.insertData(SpMotorActivity.this,list,m_id,c,game_id,w,dashName,progressDialog,btnSave);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        //private int stat=0;
        int m=Integer.parseInt(m_id.toString());
        if(m>Prevalent.Matka_count)
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
//            common.setBetDateDay(m_id,btnGameType,progressDialog);
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

    private void getDataSet(final String inputData, final String d, final String th) {
      //  Toast.makeText(SpMotorActivity.this,"at"+inputData,Toast.LENGTH_LONG).show();
        progressDialog.show();

        String json_tag="json_sp_motor";
        Map<String, String> params = new HashMap<>();
        params.put("arr", inputData);
        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.URL_SpMotor, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //    Toast.makeText(SpMotorActivity.this, "Data" + response, Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = response;

                    String status = jsonObject.getString("status");
                    JSONArray as = jsonObject.getJSONArray("data");

                    if (status.equals("success")) {
                        for (int i = 0; i <= as.length() - 1; i++) {
                            String p = as.getString(i);
                            //setTableData(p,d,th);
                            common.addData(p,d,th,list,tableAdaper,list_table,btnSave);

                            //arrayList.add(new SingleDigitObjects(p,d,th));
                        }
///                                Toast.makeText(SpMotorActivity.this, "Something wrong"+as, Toast.LENGTH_LONG).show();

                        progressDialog.dismiss();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SpMotorActivity.this, "Something wrong", Toast.LENGTH_LONG).show();

                    }


//                            JSONObject object=new JSONObject(response);
//                            String status=object.getString("status");
//                            List asd=Arrays.asList(object.getString("answer"));

                } catch (Exception ex) {
                    progressDialog.dismiss();
                    Toast.makeText(SpMotorActivity.this, "Error :" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(SpMotorActivity.this, "Error :" + error.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        });

        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);

    }

    }

