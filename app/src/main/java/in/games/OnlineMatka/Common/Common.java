package in.games.OnlineMatka.Common;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
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
import java.util.Random;
import java.util.concurrent.TimeUnit;

import in.games.OnlineMatka.Adapter.ListItemAdapter;
import in.games.OnlineMatka.Adapter.TableAdaper;
import in.games.OnlineMatka.AppController;
import in.games.OnlineMatka.GameActivity;
import in.games.OnlineMatka.HomeActivity;
import in.games.OnlineMatka.Intefaces.VolleyCallBack;
import in.games.OnlineMatka.MainActivity;
import in.games.OnlineMatka.Model.MatkasObjects;
import in.games.OnlineMatka.Model.Starline_Objects;
import in.games.OnlineMatka.Model.TableModel;
import in.games.OnlineMatka.Model.WalletObjects;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.R;
import in.games.OnlineMatka.URLs;
import in.games.OnlineMatka.utils.CustomJsonRequest;
import in.games.OnlineMatka.utils.LoadingBar;
import in.games.OnlineMatka.utils.Module;
import maes.tech.intentanim.CustomIntent;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 20,May,2020
 */
public class Common {
    Context context;

    public Common(Context context) {
        this.context = context;
    }

    public void showToast(String s)
    {
        Toast.makeText(context,""+s,Toast.LENGTH_SHORT).show();
    }
    public void setMobileNumber(final TextView txt)
    {
        String json_tag_request="json_mobile_request";
        HashMap<String,String> params=new HashMap<String, String>();
        CustomJsonRequest customVolleyJsonArrayRequest=new CustomJsonRequest(Request.Method.GET, URLs.URL_MOBILE, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
                    JSONObject object=response;
                    String mobile=object.getString("mobile");
                    int m_cnt=Integer.parseInt(object.getString("count"));
                    txt.setText(mobile);
                    Prevalent.Matka_count=m_cnt;
                }
                catch (Exception ex)
                {
                    Toast.makeText(context,""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);

            }
        });

        AppController.getInstance().addToRequestQueue(customVolleyJsonArrayRequest,json_tag_request);
    }

    public String VolleyErrorMessage(VolleyError error) {
        String str_error = "";
        if (error instanceof TimeoutError) {
            str_error = "Connection Timeout";
        } else if (error instanceof AuthFailureError) {
            str_error = "Session Timeout";
            //TODO
        } else if (error instanceof ServerError) {
            str_error = "Server not responding please try again later";
            //TODO
        } else if (error instanceof NetworkError) {
            str_error = "No Internet Connection";
            //TODO
        } else if (error instanceof ParseError) {
            //TODO
            str_error = "An Unknown error occur";
        } else if (error instanceof NoConnectionError) {
            str_error = "No Internet Connection";
        } else {
            str_error = "Something Went Wrong";
        }

        return str_error;
    }

    public void errorMessageDialog(String message)
    {

        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_error_message_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtMessage=(TextView)dialog.findViewById(R.id.txtmessage);
        Button btnOk=(Button)dialog.findViewById(R.id.btnOK);
        dialog.setCanceledOnTouchOutside(false);
        if(dialog.isShowing())
        {
            dialog.dismiss();
        }
        dialog.show();

        txtMessage.setText(message);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    public void setWallet_Amount(final TextView txt, final LoadingBar progressDialog, final String mid) {
        progressDialog.show();

        String json_tag_request = "json_wallet_tag";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("user_id", mid);

        CustomJsonRequest customJsonRequest = new CustomJsonRequest(Request.Method.POST, URLs.URL_WALLET, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.equals(null)) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "You have no money in wallet", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    try {

                        JSONObject jsonObject = response;

                        String status = jsonObject.getString("status");

                        if (status.equals("success")) {
                            JSONObject object = jsonObject.getJSONObject("message");
                            WalletObjects walletObjects = new WalletObjects();
                            walletObjects.setUser_id(object.getString("user_id"));
                            walletObjects.setWallet_points(object.getString("wallet_points"));
                            walletObjects.setWallet_id(object.getString("wallet_id"));
                            progressDialog.dismiss();
                            txt.setText(walletObjects.getWallet_points());
                        } else if (status.equals("failed")) {
                            progressDialog.dismiss();
                            txt.setText("0");
                            Toast.makeText(context, "You have no points", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            progressDialog.dismiss();
                            txt.setText("0");
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                            return;
                        }


                    } catch (Exception ex) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error :" + ex.getMessage(), Toast.LENGTH_LONG).show();
                        return;

                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);

                // Toast.makeText(context, "Error :" + error.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        });

        AppController.getInstance().addToRequestQueue(customJsonRequest, json_tag_request);
    }
    //Function for net Connectivity.......

    public boolean isConnected()
    {
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo=cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting())
        {
            NetworkInfo wifi=cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileNet=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobileNet != null && mobileNet.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
            {
                return true;
            }
            else {
                return false;
            }

        }
        else
        {
            return false;
        }
    }

    public long getTimeDifference(String time) {
        long diff_e_s=0;
        Date date = new Date();
        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
        String cur_time = parseFormat.format(date);
        try {
            final Date s_time = parseFormat.parse(cur_time.trim());
            Date e_time = parseFormat.parse(time.trim());
            diff_e_s = e_time.getTime() - s_time.getTime();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return diff_e_s;
    }

    public void setBetTypeTooText( Dialog dialog, final TextView txt_timer, TextView txtOpen, TextView txtClose, String m_id, final Button btnType, LoadingBar progressDialog, final String c_date, final TextView tv_timer)
    {
        dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_bettype);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        txtOpen=(TextView)dialog.findViewById(R.id.typeOpening);
        txtClose=(TextView)dialog.findViewById(R.id.typeClosing);

        setDataTo(txtOpen,txtClose,m_id,progressDialog,c_date);

        final Dialog finalDialog = dialog;
        final TextView finalTxtOpen = txtOpen;
        txtOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnType.setText(finalTxtOpen.getText().toString());
                txt_timer.setVisibility(View.VISIBLE);
                tv_timer.setVisibility(View.GONE);
                finalDialog.dismiss();
            }
        });

        final TextView finalTxtClose = txtClose;
        final Dialog finalDialog1 = dialog;
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnType.setText(finalTxtClose.getText().toString());
                tv_timer.setVisibility(View.VISIBLE);
                txt_timer.setVisibility(View.GONE);
                finalDialog1.dismiss();
            }
        });
    }

    private void setDataTo(final TextView txtOpen, final TextView txtClose, final String m_id, final LoadingBar progressDialog, final String date_cuurent) {
        progressDialog.show();

        String json_tag="json_reg_tag";
        HashMap<String,String> params=new HashMap<String, String>();
        params.put("id",m_id);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.URL_MATKA_WITH_ID, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONObject object=response;
                    String status=object.getString("status");
                    if(status.equals("success"))
                    {
                        JSONObject jsonObject=object.getJSONObject("data");
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


                        String dt=new SimpleDateFormat("EEEE").format(new Date());
                        String bid_start="";
                        if(dt.equals("Saturday"))
                        {
                            bid_start=matkasObjects.getSat_start_time();
                        }

                        else if(dt.equals("Sunday"))
                        {
                            bid_start=matkasObjects.getStart_time();
                        }
                        else
                        {
                            bid_start=matkasObjects.getBid_start_time();

                        }
                        Date current_time=new Date();
                        SimpleDateFormat sformat=new SimpleDateFormat("HH:mm:ss");
                        String c_date=sformat.format(current_time);

                        String startTimeSplliting[]=bid_start.split(":");
                        int s_hours=Integer.parseInt(startTimeSplliting[0]);
                        int s_min=Integer.parseInt(startTimeSplliting[1]);
                        int s_sec=Integer.parseInt(startTimeSplliting[2]);
                        String currentTimeSplitting[]=c_date.split(":");
                        int c_hours=Integer.parseInt(currentTimeSplitting[0]);
                        int c_min=Integer.parseInt(currentTimeSplitting[1]);
                        int c_sec=Integer.parseInt(currentTimeSplitting[2]);

                        int flag=0;
                        if(s_hours>c_hours)
                        {
                            flag=1;
                        }
                        else if(s_hours==c_hours)
                        {
                            if(s_min>c_min)
                            {
                                flag=1;
                            }
                            else if(s_min==c_min)
                            {
                                if(s_sec>c_sec)
                                {
                                    flag=1;
                                }
                                else
                                {
                                    flag=0;
                                }
                                flag=0;
                            }
                            else
                            {
                                flag=0;
                            }
                        }
                        else
                        {
                            flag=0;
                        }

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");

                        Date d1=simpleDateFormat.parse(date_cuurent);
                        Date date=new Date();
                        SimpleDateFormat dateFormat1=new SimpleDateFormat("dd/MM/yyyy");
                        String s1=dateFormat1.format(date);
                        Date d2=simpleDateFormat.parse(s1);
                        if(d1.compareTo(d2)==0)
                        {
                            if(flag==1)
                            {
                                txtOpen.setText("Open Bet");
                                txtClose.setText("Close Bet");
                            }
                            else if(flag==0)
                            {
                                txtOpen.setVisibility(View.GONE);
                                txtClose.setText("Close Bet");

                            }

                        }
                        else
                        {
                            txtOpen.setText("Open Bet");
                            txtClose.setText("Close Bet");
                        }

                        progressDialog.dismiss();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(context,"Something ",Toast.LENGTH_LONG).show();

                    }
                }
                catch(Exception ex)
                {
                    progressDialog.dismiss();
                    Toast.makeText(context,"Something "+ex.getMessage(),Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);


            }
        });

        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);


    }
    public void addData(String digit, String point, String type, List<TableModel> list, TableAdaper tableAdaper, ListView list_table, Button btnSave) {
        list.add(new TableModel(digit, point, type));
        tableAdaper = new TableAdaper(list, context, btnSave);
        list_table.setAdapter(tableAdaper);
        tableAdaper.notifyDataSetChanged();
        int we = list.size();
        int points = Integer.parseInt(point);
        int tot_pnt = Integer.parseInt(getSumOfPoints(list));

        btnSave.setText("(BIDS=" + we + ")(Points=" + tot_pnt + ")");
    }

    public String getSumOfPoints(List<TableModel> list) {
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum = sum + Integer.parseInt(list.get(i).getPoints());
        }

        return String.valueOf(sum);
    }

    public void setBidsDialog(int wallet_amount , final List<TableModel> list, final String m_id, final String c, final String  game_id, final String w, final String dashName, final LoadingBar progressDialog, final Button btnSave,final String start_time,final String end_time)
    {
        TableRow tr1;
        ListItemAdapter listItemAdapter = new ListItemAdapter(list,context);
        TextView txtD1,txtP1,txtT1;

        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.starline_save_layout);
        if(dialog.isShowing())
        {
            dialog.dismiss();
        }
        dialog.show();
        final TableLayout tableLayout=(TableLayout)dialog.findViewById(R.id.tblLayout1);
        ListView listView = dialog.findViewById(R.id.list_item);
        Button btn_dialog_add=(Button)dialog.findViewById(R.id.btnOk);
        TextView btnDialogCancel=(Button)dialog.findViewById(R.id.btnCancel);
        TextView txtCountBids=(TextView)dialog.findViewById(R.id.txtCountBids);
        TextView txtAmount=(TextView)dialog.findViewById(R.id.txtAmount);
        TextView txtBeforeAmount=(TextView)dialog.findViewById(R.id.txtBeforeAmount);
        TextView txtAfterAmount=(TextView)dialog.findViewById(R.id.txtAfterAmount);
        TextView matka_name = dialog.findViewById( R.id.matka_name );
        matka_name.setText( dashName );

        listView.setAdapter(listItemAdapter);

        int amt=0;
        for(int j=0;j<list.size();j++)
        {
            amt=amt+Integer.parseInt(list.get(j).getPoints());
        }

        int bid_count=list.size();
        txtCountBids.setText(String.valueOf(String.valueOf(bid_count)));
        txtAmount.setText(String.valueOf(amt));
        int w_a=wallet_amount;
        int after_amt=w_a-amt;
        txtBeforeAmount.setText(String.valueOf(w_a));
        txtAfterAmount.setText(String.valueOf(after_amt));
        btn_dialog_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData(list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);

            }
        });

        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }

    public void insertData(List<TableModel> list, String m_id, String c, String game_id, String w, String dashName, LoadingBar progressDialog, Button btnSave,final String start_time,final String end_time) {
        int er = list.size();
        if (er <= 0) {
            String message = "Please Add Some Bids";
            errorMessageDialog(message);
            return;
        } else {
            try {
                int amt = 0;
                ArrayList list_digits = new ArrayList();
                ArrayList list_type = new ArrayList();
                ArrayList list_points = new ArrayList();
                int rows = list.size();

                for (int i = 0; i < rows; i++) {


                    TableModel tableModel = list.get(i);

                    String asd = tableModel.getDigits().toString();
                    String asd1 = tableModel.getPoints().toString();
                    String asd2 = tableModel.getType().toString();
                    int b = 9;

                    if (asd2.equals("close")) {
                        b = 1;
                    } else if (asd2.equals("open")) {
                        b = 0;
                    }


                    amt = amt + Integer.parseInt(asd1);

                    char quotes = '"';
                    list_digits.add(quotes + asd + quotes);
                    list_points.add(asd1);
                    list_type.add(b);

          }


                String id = Prevalent.currentOnlineuser.getId().toString().trim();
                String matka_id = m_id.toString().trim();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("points", list_points);
                jsonObject.put("digits", list_digits);
                jsonObject.put("bettype", list_type);
                jsonObject.put("user_id", id);
                jsonObject.put("matka_id", matka_id);
                jsonObject.put("date", c);
                jsonObject.put("game_id", game_id);

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(jsonObject);

                int wallet_amount = Integer.parseInt(w);
                if (wallet_amount < amt) {

                    String message = "Insufficient Amount";
                    errorMessageDialog(message);
                    return;

                } else {
                    btnSave.setEnabled(false);

                    updateWalletAmount(jsonArray, progressDialog, dashName, m_id,start_time,end_time);

                }
            } catch (Exception ex) {
                Toast.makeText(context, "Err" + ex.getMessage(), Toast.LENGTH_LONG).show();
            }

        }

    }

    public void setDateAndBetTpe(Dialog dialog, final String m_id, TextView txtCurrentDate, TextView txtNextDate, TextView txtAfterNextDate,TextView txtDate_id,final Button btnGameType,LoadingBar progressDialog)
    {
        dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_date_bet);
        txtCurrentDate=(TextView)dialog.findViewById(R.id.currentDate);
        txtNextDate=(TextView)dialog.findViewById(R.id.nextDate);
        txtAfterNextDate=(TextView)dialog.findViewById(R.id.afterNextDate);
        txtDate_id=(TextView)dialog.findViewById(R.id.date_id);

        txtDate_id.setVisibility(View.GONE);
        dialog.setCanceledOnTouchOutside(false);

        //setData(txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,m_id,progressDialog,OddEvenActivity.this);
        dialog.show();
        //getMatkaDateData(context,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,progressDialog);
        getDateData(m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,progressDialog);


        final Dialog finalDialog = dialog;
        final TextView finalTxtCurrentDate = txtCurrentDate;
        txtCurrentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String c= finalTxtCurrentDate.getText().toString();

                ///   String as=getDataString(c);
                btnGameType.setText(c.toString());
                finalDialog.dismiss();
            }
        });

        final Dialog finalDialog1 = dialog;
        final TextView finalTxtNextDate = txtNextDate;
        txtNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String c= finalTxtNextDate.getText().toString();

                // String as=getDataString(c);
                btnGameType.setText(c.toString());
                finalDialog1.dismiss();
            }
        });

        final Dialog finalDialog2 = dialog;
        final TextView finalTxtAfterNextDate = txtAfterNextDate;
        txtAfterNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String c= finalTxtAfterNextDate.getText().toString();

                // String as=getDataString(c);
                btnGameType.setText(c.toString());
                finalDialog2.dismiss();
            }
        });
    }

    // Function for Get Game Date and Day

    public void getDateData(final String m_id, final TextView txtCurrentDate, final TextView txtNextDate, final TextView txtAfterNextDate, final LoadingBar progressDialog)
    {
        progressDialog.show();
        String json_tag="json_matka_id";
        HashMap<String,String> params=new HashMap<String, String>();
        params.put("id",m_id);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.URL_MATKA_WITH_ID, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = response;
                    String status = jsonObject.getString("status");
                    if (status.equals("success")) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        MatkasObjects matkasObjects = new MatkasObjects();
                        matkasObjects.setId(object.getString("id"));
                        matkasObjects.setName(object.getString("name"));
                        matkasObjects.setStart_time(object.getString("start_time"));
                        matkasObjects.setEnd_time(object.getString("end_time"));
                        matkasObjects.setStarting_num(object.getString("starting_num"));
                        matkasObjects.setNumber(object.getString("number"));
                        matkasObjects.setEnd_num(object.getString("end_num"));
                        matkasObjects.setBid_start_time(object.getString("bid_start_time"));
                        matkasObjects.setBid_end_time(object.getString("bid_end_time"));
                        matkasObjects.setSat_start_time(object.getString("sat_start_time"));
                        matkasObjects.setSat_end_time(object.getString("sat_end_time"));
                        matkasObjects.setCreated_at(object.getString("created_at"));
                        matkasObjects.setUpdated_at(object.getString("updated_at"));
                        matkasObjects.setStatus(object.getString("status"));

                        String bid_start = "";
                        String bid_end = "";
                        String dt = new SimpleDateFormat("EEEE").format(new Date());

                        String st_time = "";
                        String st_time1 = "";
                        String st_time2 = "";

                        if (dt.equals("Saturday")) {
                            st_time = matkasObjects.getSat_start_time();
                        } else if (dt.equals("Sunday")) {
                            st_time = matkasObjects.getStart_time();
                        } else {
                            st_time = matkasObjects.getBid_start_time();
                        }

                        String dt1 = getNextDay(dt);
                        if (dt1.equals("Saturday")) {
                            st_time1 = matkasObjects.getSat_start_time();
                        } else if (dt1.equals("Sunday")) {
                            st_time1 = matkasObjects.getStart_time();
                        } else {
                            st_time1 = matkasObjects.getBid_start_time();
                        }

                        String dt2 = getNextDay(dt1);
                        if (dt2.equals("Saturday")) {
                            st_time2 = matkasObjects.getSat_start_time();
                        } else if (dt2.equals("Sunday")) {
                            st_time2 = matkasObjects.getStart_time();
                        } else {
                            st_time2 = matkasObjects.getBid_start_time();
                        }


                        String nd="";
                        String and="";
                        String cd="";


                        if (st_time.equals("") && st_time.equals("null")) {

                            txtCurrentDate.setText(dt + " Bet Close");
                            cd="c";

                            if (st_time1.equals("") && st_time1.equals("null")) {
                                txtNextDate.setText(dt1 + " Bet Close");
                                nd="c";
                            } else {
                                txtNextDate.setText(dt1 + " Bet Open");
                                nd="o";
                            }
                            if (st_time2.equals("") && st_time2.equals("null")) {
                                txtAfterNextDate.setText(dt2 + " Bet Close");
                                and="c";
                            } else {
                                txtAfterNextDate.setText(dt2 + " Bet Open");
                                and="o";
                            }


                            //  Toast.makeText(context,"Somtehin",Toast.LENGTH_LONG).show();
                        } else {
//                                Toast.makeText(context,""+matkasObjects.getSat_start_time(),Toast.LENGTH_LONG).show();

                            bid_start = st_time;
                            bid_end = matkasObjects.getBid_end_time().toString();

                            String time1 = bid_start.toString();
                            String time2 = bid_end.toString();

                            Date cdate = new Date();


                            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                            String time3 = format.format(cdate);
                            Date date1 = null;
                            Date date2 = null;
                            Date date3 = null;
                            try {
                                date1 = format.parse(time1);
                                date2 = format.parse(time2);
                                date3 = format.parse(time3);
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }

                            long difference = date3.getTime() - date1.getTime();
                            long as = (difference / 1000) / 60;

                            long diff_close = date3.getTime() - date2.getTime();
                            long c = (diff_close / 1000) / 60;

                            Date c_dat = new Date();
                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy EEEE");
                            String s_dt = dateFormat2.format(c_dat);
                            String n_dt = getNextDate(s_dt);
                            String a_dt = getNextDate(n_dt);
                            if (as < 0) {
                                progressDialog.dismiss();
                                //btn.setText(s_dt+" Bet Open");
                                txtCurrentDate.setText(s_dt + " Bet Open");

                                //Toast.makeText(OddEvenActivity.this,""+s_dt+"  Open",Toast.LENGTH_LONG).show();
                            } else if (c > 0) {
                                progressDialog.dismiss();
                                txtCurrentDate.setText(s_dt + " Bet Close");

                                // Toast.makeText(OddEvenActivity.this,""+s_dt+"  Close",Toast.LENGTH_LONG).show();
                            } else {
                                progressDialog.dismiss();
                                //btn.setText(s_dt+" Bet Open");
                                txtCurrentDate.setText(s_dt + " Bet Open");


                            }

                            if(nd.equals("c"))
                            {
                                txtNextDate.setText(n_dt + " Bet Close");

                            }
                            else
                            {
                                txtNextDate.setText(n_dt + " Bet Open");

                            }

                            if(and.equals("c"))
                            {
                                txtAfterNextDate.setText(a_dt + " Bet Close");

                            }
                            else
                            {
                                txtAfterNextDate.setText(a_dt + " Bet Open");

                            }

                        }



                    }
                } catch(Exception ex){
                    progressDialog.dismiss();
                    Toast.makeText(context, "Something erong" + ex.getMessage(), Toast.LENGTH_LONG).show();

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);
            }
        });

        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);


    }
    //Function for get Next Day
    public String getNextDay(String currentDate)
    {
        String nextDate="";

        try
        {
            Calendar calendar=Calendar.getInstance();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE");
            Date c=simpleDateFormat.parse(currentDate);
            calendar.setTime(c);
            calendar.add(Calendar.DAY_OF_WEEK,1);
            nextDate=simpleDateFormat.format(calendar.getTime());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //Toast.makeText(OddEvenActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
        }

        return nextDate.toString();
    }

    // Function for get Next Date
    public String getNextDate(String currentDate)
    {
        String nextDate="";
        try
        {
            Calendar calendar=Calendar.getInstance();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
            Date c=simpleDateFormat.parse(currentDate);
            calendar.setTime(c);
            calendar.add(Calendar.DAY_OF_WEEK,1);
            nextDate=simpleDateFormat.format(calendar.getTime());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //Toast.makeText(OddEvenActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
        }

        return nextDate.toString();
    }

    public void currentDateDay(Button btn)
    {
        String date=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        Date date1=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE");
        String day =simpleDateFormat.format(date1);
        btn.setText(date+" "+day);

    }

    public void  setCounterTimer(long diff,final TextView txt_timer)
    {
        CountDownTimer countDownTimer = new CountDownTimer(diff, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String text = String.format(Locale.getDefault(), " %02d : %02d : %02d ",

                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 60, TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                txt_timer.setText(text);

            }

            @Override
            public void onFinish() {

                txt_timer.setText("Bid Closed");

            }
        }.start();

    }

    public void setEndCounterTimer(long diff,final TextView txt_timer)
    {
        CountDownTimer countDownTimer = new CountDownTimer(diff, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String text = String.format(Locale.getDefault(), " %02d : %02d : %02d ",

                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 60, TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);

                txt_timer.setText(text);
            }
            @Override
            public void onFinish() {
                txt_timer.setText("Bid Closed");
            }
        }.start();

    }

    public void setBetDateDay(final String m_id,final Button btnGameType, final LoadingBar progressDialog)
    {
        String json_request_tag="matka_with_id";
        HashMap<String,String> params=new HashMap<String, String>();
        params.put("id",m_id);
        progressDialog.show();

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.URL_MATKA_WITH_ID, params, new Response.Listener<JSONObject>() {
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
                        Log.e("dataaaa",""+c);
                        Date c_dat=new Date();
                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
                        String s_dt=dateFormat.format(c_dat);
                        String n_dt= getNextDate(s_dt);
                        String a_dt= getNextDate(n_dt);

                        if(c>0)
                        {progressDialog.dismiss();
                            btnGameType.setText(s_dt+" Bet Close");


                            // Toast.makeText(OddEvenActivity.this,""+s_dt+"  Close",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            btnGameType.setText(s_dt+" Bet Open");

                        }


//                        }


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context,"Something wrong",Toast.LENGTH_LONG).show();


                    }

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    Toast.makeText(context,"something went wrong ",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,json_request_tag);
    }

    public void getStarlineGameData(final String m_id, final Button btnType,final LoadingBar progressDialog)
    {
        progressDialog.show();

        String json_tag="json_starline";
        HashMap<String,String> params=new HashMap<>();
        params.put("id",m_id);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.URL_StarLine_id, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
                    JSONObject jsonObject=response;
                    Starline_Objects matkasObjects=new Starline_Objects();
                    matkasObjects.setId(jsonObject.getString("id"));
                    matkasObjects.setS_game_time(jsonObject.getString("s_game_time"));
                    matkasObjects.setS_game_number(jsonObject.getString("s_game_number"));

                    progressDialog.dismiss();
                    btnType.setText(matkasObjects.getS_game_time());

                }
                catch(Exception ex)
                {
                    progressDialog.dismiss();
                    Toast.makeText(context,"Something "+ex.getMessage(),Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);



    }

    //Funtion for getDateDataTo
    public void setDateAndBetTpeTo(Dialog dialog, final String m_id, TextView txtCurrentDate, TextView txtNextDate, TextView txtAfterNextDate,TextView txtDate_id,final Button btnGameType,LoadingBar progressDialog)
    {
        dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_date_bet);


        txtCurrentDate=(TextView)dialog.findViewById(R.id.currentDate);
        txtNextDate=(TextView)dialog.findViewById(R.id.nextDate);
        txtAfterNextDate=(TextView)dialog.findViewById(R.id.afterNextDate);
        txtDate_id=(TextView)dialog.findViewById(R.id.date_id);

        txtDate_id.setVisibility(View.GONE);
        dialog.setCanceledOnTouchOutside(false);

        //setData(txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,m_id,progressDialog,OddEvenActivity.this);
        dialog.show();
        getDateDataTo(m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,progressDialog);


        final Dialog finalDialog = dialog;
        final TextView finalTxtCurrentDate = txtCurrentDate;
        txtCurrentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String c= finalTxtCurrentDate.getText().toString();

                ///   String as=getDataString(c);
                btnGameType.setText(c.toString());
                finalDialog.dismiss();
            }
        });

        final Dialog finalDialog1 = dialog;
        final TextView finalTxtNextDate = txtNextDate;
        txtNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String c= finalTxtNextDate.getText().toString();

                // String as=getDataString(c);
                btnGameType.setText(c.toString());
                finalDialog1.dismiss();
            }
        });

        final Dialog finalDialog2 = dialog;
        final TextView finalTxtAfterNextDate = txtAfterNextDate;
        txtAfterNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String c= finalTxtAfterNextDate.getText().toString();

                // String as=getDataString(c);
                btnGameType.setText(c.toString());
                finalDialog2.dismiss();
            }
        });
    }
    //Function for button btnGametype in RedBracket

    public void getDateDataTo(final String m_id, final TextView txtCurrentDate, final TextView txtNextDate, final TextView txtAfterNextDate, final LoadingBar progressDialog)
    {

        progressDialog.show();

        String json_tag="json_getDtat";
        HashMap<String,String> params=new HashMap<>();
        params.put("id",m_id);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.Url_matka_with_id, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response;
                    String status = jsonObject.getString("status");
                    if (status.equals("success")) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        MatkasObjects matkasObjects = new MatkasObjects();
                        matkasObjects.setId(object.getString("id"));
                        matkasObjects.setName(object.getString("name"));
                        matkasObjects.setStart_time(object.getString("start_time"));
                        matkasObjects.setStarting_num(object.getString("starting_num"));
                        matkasObjects.setNumber(object.getString("number"));
                        matkasObjects.setEnd_num(object.getString("end_num"));
                        matkasObjects.setBid_start_time(object.getString("bid_start_time"));
                        matkasObjects.setBid_end_time(object.getString("bid_end_time"));
                        matkasObjects.setCreated_at(object.getString("created_at"));
                        matkasObjects.setUpdated_at(object.getString("updated_at"));
                        matkasObjects.setStatus(object.getString("status"));

                        String bid_start = matkasObjects.getBid_start_time();
                        String bid_end=matkasObjects.getBid_end_time().toString();

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
                        String n_dt= getNextDate(s_dt);
                        String a_dt= getNextDate(n_dt);
                        if(as<0)
                        {
                            progressDialog.dismiss();
                            //btn.setText(s_dt+" Bet Open");
                            txtCurrentDate.setText(s_dt+" Bet Open");
                            txtNextDate.setText(n_dt+" Bet Open");
                            txtAfterNextDate.setText(a_dt+" Bet Open");

                            //Toast.makeText(OddEvenActivity.this,""+s_dt+"  Open",Toast.LENGTH_LONG).show();
                        }
                        else //if(c>0)
                        {progressDialog.dismiss();
                            txtCurrentDate.setText(s_dt+" Bet Close");
                            txtNextDate.setText(n_dt+" Bet Open");
                            txtAfterNextDate.setText(a_dt+" Bet Open");

                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context,"Something erong",Toast.LENGTH_LONG).show();


                    }
                } catch (Exception ex) {
                    progressDialog.dismiss();
                    Toast.makeText(context,"Something erong"+ex.getMessage(),Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);

            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);



    }

    //Function for red_bracket,jodi group

    public void setBetDateDayTo( final String m_id, final Button btnGameType, final LoadingBar progressDialog) {

        progressDialog.show();

        String json_tag="json_bet";
        HashMap<String,String> params=new HashMap<>();
        params.put("id",m_id);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.Url_matka_with_id, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = response;
                    String status = jsonObject.getString("status");
                    if (status.equals("success")) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        MatkasObjects matkasObjects = new MatkasObjects();
                        matkasObjects.setId(object.getString("id"));
                        matkasObjects.setName(object.getString("name"));
                        matkasObjects.setStart_time(object.getString("start_time"));
                        matkasObjects.setStarting_num(object.getString("starting_num"));
                        matkasObjects.setNumber(object.getString("number"));
                        matkasObjects.setEnd_num(object.getString("end_num"));
                        matkasObjects.setBid_start_time(object.getString("bid_start_time"));
                        matkasObjects.setBid_end_time(object.getString("bid_end_time"));
                        matkasObjects.setCreated_at(object.getString("created_at"));
                        matkasObjects.setUpdated_at(object.getString("updated_at"));
                        matkasObjects.setStatus(object.getString("status"));

                        String bid_start = matkasObjects.getBid_start_time();
                        String bid_end=matkasObjects.getBid_end_time().toString();

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
                        String n_dt= getNextDate(s_dt);
                        String a_dt= getNextDate(n_dt);

                        if(as>0)
                        {progressDialog.dismiss();
                            btnGameType.setText(s_dt+" Bet Close");


                            // Toast.makeText(OddEvenActivity.this,""+s_dt+"  Close",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            btnGameType.setText(s_dt+" Bet Open");

                        }


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context,"Something erong",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    progressDialog.dismiss();
                    Toast.makeText(context,"Something erong"+ex.getMessage(),Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);
            }
        });

        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);
    }

    //New function for getBetType and Bet Date
    public void getBetSession( final String m_id, final LoadingBar progressDialog, final VolleyCallBack callBack)
    {
        String json_request_tag="matka_with_id";
        HashMap<String,String> params=new HashMap<String, String>();
        params.put("id",m_id);
        progressDialog.show();

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.URL_MATKA_WITH_ID, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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


                        Module module=new Module();
                        String time1 = bid_start.toString();
                        String time2 = bid_end.toString();
                        long start_diff=getTimeDifference(time1);
                        long end_diff=getTimeDifference(time2);
                        HashMap<String ,String> map=new HashMap<>();
                        map.put("s_diff",String.valueOf(start_diff));
                        map.put("e_diff",String.valueOf(end_diff));
                        callBack.getTimeDiffrence(map);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context,"Something wrong",Toast.LENGTH_LONG).show();


                    }
           }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    Toast.makeText(context,"something went wrong ",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,json_request_tag);
    }

    public void setSessionTimeOut(final Context context)
    {
        try
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    sessionTimeOut();

                }
            },600000);
        }
        catch (Exception ex)
        {

        }

    }

    public void sessionTimeOut()
    {

        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_error_message_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtMessage=(TextView)dialog.findViewById(R.id.txtmessage);
        Button btnOk=(Button)dialog.findViewById(R.id.btnOK);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        txtMessage.setText("Session TimeOut");

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent1=new Intent(context, MainActivity.class);
                dialog.dismiss();
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent1);
                CustomIntent.customType(context, "up-to-bottom");


            }
        });

    }

    public void updateWalletAmount(final JSONArray jsonArray,final LoadingBar progressDialog,final String matka_name,final String m_id,final String start_time,final String end_time )
    {
        final String data=String.valueOf(jsonArray);
        String json_request_tag="json_insert_request";
        HashMap<String,String> params=new HashMap<String, String>();
        params.put("data",data);

     //  Toast.makeText(context,""+data,Toast.LENGTH_LONG).show();
        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
        progressDialog.show();


        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.URL_INSERT_DATA, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
                    Log.d("insert_data",response.toString());
                    JSONObject jsonObject=response;
                    // Toast.makeText(context,""+response.toString(),Toast.LENGTH_LONG).show();
                    final String status=jsonObject.getString("status");
                    progressDialog.dismiss();
                    if(status.equals("success"))
                    {

                        //updateWalletAmount(id,amt,context);
                        Intent intent=new Intent(context, GameActivity.class);
                        intent.putExtra("matkaName",matka_name);
                        intent.putExtra("m_id",m_id);
                        intent.putExtra("end_time",end_time);
                        intent.putExtra("start_time",start_time);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        context.startActivity(intent);
                        CustomIntent.customType(context, "up-to-bottom");
                        Toast.makeText(context,"Bid Added Successfully.",Toast.LENGTH_LONG).show();
                    }
                    else if(status.equals("failed"))
                    {
                        String sd=status.toString();
                        errorMessageDialog(sd.toString());
                        // Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show();
                    }
                    else if(status.equals("timeout"))
                    {

                        final Dialog myDialog=new Dialog(context);
                        myDialog.setContentView(R.layout.dialog_error_message_dialog);
                        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        TextView txtmessage=(TextView)myDialog.findViewById(R.id.txtmessage);
                        Button btnOK=(Button) myDialog.findViewById(R.id.btnOK);

                        txtmessage.setText("Biding closed for this date");
                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                myDialog.dismiss();

                                String sd=status.toString();
                                //errorMessageDialog(context,sd.toString());
                                Intent intent=new Intent(context, HomeActivity.class);
                                context.startActivity(intent);
                            }
                        });

                        myDialog.show();
//                        String sd=status.toString();
//                        errorMessageDialog(context,sd.toString());
//                        Intent intent=new Intent(context,HomeActivity.class);
//                        context.startActivity(intent);


                    }




                }
                catch (Exception ex)
                {
                    progressDialog.dismiss();
                    Toast.makeText(context,"Err"+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);


            }
        });
        customJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        AppController.getInstance().addToRequestQueue(customJsonRequest,json_request_tag);




    }

    public String getRandomKey(int i)
    {
        final String characters="0123456789";
        StringBuilder stringBuilder=new StringBuilder();
        while (i>0)
        {
            Random ran=new Random();
            stringBuilder.append(characters.charAt(ran.nextInt(characters.length())));
            i--;
        }
        return stringBuilder.toString();
    }


}


