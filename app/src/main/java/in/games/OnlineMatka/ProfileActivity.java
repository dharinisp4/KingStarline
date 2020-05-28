package in.games.OnlineMatka;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.CustomJsonRequest;
import in.games.OnlineMatka.utils.LoadingBar;

import static in.games.OnlineMatka.URLs.URL_REGIST;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etPAddress,etPCity,etPPinCode,etAccNo,etBankName,etIfscCode,etAccHolderName,etPaytm,etTez,etPhonePay ,et_dob ,et_email,et_mobile;
    Common common;
    LoadingBar progressDialog;
    String wrong="Something Went Wrong";
    private TextView btn_back;
    int   year,month,day;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Button btnDAddress,btnDBank,btnDPaytm,btnDGoogle,btnUpdatePass ,btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        common=new Common(this);

        btn_back=(TextView)findViewById(R.id.txt_back);
        progressDialog=new LoadingBar(this);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        common.setSessionTimeOut(this);
        btnDAddress=(Button)findViewById(R.id.add_address);
        btnDAddress.setOnClickListener(this);
        btnUpdatePass = findViewById(R.id.btn_update_pass);
        etPAddress=(EditText)findViewById(R.id.etAddress);
        etPCity=(EditText)findViewById(R.id.etCity);
        etPPinCode=(EditText)findViewById(R.id.etPin);
        btnDBank=(Button)findViewById(R.id.add_bank);
        btnDBank.setOnClickListener(this);
        btnDPaytm = findViewById(R.id.add_paytm);
        btnDPaytm.setOnClickListener(this);
        etAccNo=(EditText)findViewById(R.id.etAccNo);
        etBankName=(EditText)findViewById(R.id.etBankName);
        etIfscCode=(EditText)findViewById(R.id.etIfsc);
        etAccHolderName=(EditText)findViewById(R.id.etHName);
        etPaytm=(EditText)findViewById(R.id.etPaytmNo);
        etTez=(EditText)findViewById(R.id.etTezNo);
        etPhonePay=(EditText)findViewById(R.id.etPhone);
        et_dob = findViewById(R.id.et_dob);
        et_dob.setOnClickListener(this);
        et_email = findViewById(R.id.et_email);
        et_mobile = findViewById(R.id.etMobile);
        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);

        String ad= Prevalent.currentOnlineuser.getAddress();
        String ct=Prevalent.currentOnlineuser.getCity();
        String pn=Prevalent.currentOnlineuser.getPincode();
        String ac=Prevalent.currentOnlineuser.getAccountno().toString();
        String bn=Prevalent.currentOnlineuser.getBank_name().toString();
        String ic=Prevalent.currentOnlineuser.getIfsc_code().toString();
        String ah=Prevalent.currentOnlineuser.getAccount_holder_name().toString();
        String x=Prevalent.currentOnlineuser.getPhonepay_no().toString();
        String tz=Prevalent.currentOnlineuser.getTez_no().toString();
        String p=Prevalent.currentOnlineuser.getPaytm_no().toString();
        String mobile = Prevalent.currentOnlineuser.getMobileno();
        et_mobile.setText(mobile);
        et_mobile.setEnabled(false);

        setDataEditText(etPhonePay,x);
        setDataEditText(etPaytm,p);
        setDataEditText(etTez,tz);
        setDataEditText(etPAddress,ad);
        setDataEditText(etPCity,ct);
        setDataEditText(etPPinCode,pn);
        setDataEditText(etAccNo,ac);
        setDataEditText(etBankName,bn);
        setDataEditText(etIfscCode,ic);
        setDataEditText(etAccHolderName,ah);
    }

    private void storeBankDetails(final String accno,final String bankname,final String ifsc,final String hod_name,final String mailid) {

        progressDialog.show();
        Map<String,String> params=new HashMap<>();
        params.put("key","3");
        params.put("mobile",mailid);
        params.put("accountno",accno);
        params.put("bankname",bankname);
        params.put("ifsc",ifsc);
        params.put("accountholder",hod_name);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URL_REGIST, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    String success=response.getString("status");
                    if(success.equals("success"))
                    {
                        progressDialog.dismiss();
                        Prevalent.currentOnlineuser.setAccountno(accno);
                        Prevalent.currentOnlineuser.setBank_name(bankname);
                        Prevalent.currentOnlineuser.setIfsc_code(ifsc);
                        Prevalent.currentOnlineuser.setAccount_holder_name(hod_name);
                        Toast.makeText(ProfileActivity.this, "Bank Details Updated!!!", Toast.LENGTH_SHORT).show();

                    }
                    else if(success.equals("unsuccessful"))
                    {
                        progressDialog.dismiss();
                        String msg=response.getString("message");
                        Toast.makeText(ProfileActivity.this, ""+msg, Toast.LENGTH_SHORT).show();

                        return;
                    }


                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    common.showToast("Something Went Wrong");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=common.VolleyErrorMessage(error);
                if(!msg.isEmpty())
                {
                    common.showToast(msg);
                }
            }
        });

        AppController.getInstance().addToRequestQueue(customJsonRequest,"add_bank_json");

    }

    private void storeAddressData(final String a,final String c,final String p,final String mob) {

        progressDialog.show();
        String json_tag="add_address";
        Map<String,String> params=new HashMap<>();
        params.put("key","2");
        params.put("address",a);
        params.put("city",c);
        params.put("pin",p);
        params.put("mobile",mob);
        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URL_REGIST, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try
                {
                    String success=response.getString("status");
                    if(success.equals("success"))
                    {
                        progressDialog.dismiss();
                        Prevalent.currentOnlineuser.setAddress(a);
                        Prevalent.currentOnlineuser.setCity(c);
                        Prevalent.currentOnlineuser.setPincode(p);
                        Toast.makeText(ProfileActivity.this, "Address Updated!!!", Toast.LENGTH_SHORT).show();

                    }
                    else if(success.equals("unsuccessful"))
                    {
                        progressDialog.dismiss();
                        String msg=response.getString("message");
                        Toast.makeText(ProfileActivity.this, ""+msg, Toast.LENGTH_SHORT).show();

                        return;
                    }


                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=common.VolleyErrorMessage(error);
                if(!msg.isEmpty())
                {
                    common.showToast(msg);
                }
            }
        });

        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);

    }
    private void selectDate() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                year  = year;
                month = month;
                day   = dayOfMonth;

                // Show selected date
                et_dob.setText(new StringBuilder().append(day).append("-")
                        .append(month + 1) .append("-").append(year)
                        .append(" "));

            }
        },year,month,day);
        datePickerDialog.show();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id ==R.id.add_bank)
        {



                    String accno=etAccNo.getText().toString().trim();
                    String bankname=etBankName.getText().toString().trim();
                    String ifsc=etIfscCode.getText().toString().trim();
                    String hod_name=etAccHolderName.getText().toString().trim();

                    if(TextUtils.isEmpty(accno))
                    {
                        etAccNo.setError("Enter your account number");
                        etAccNo.requestFocus();
                        return;

                    }
                    else if(TextUtils.isEmpty(bankname))
                    {
                        etBankName.setError("Enter Bank Name");
                        etBankName.requestFocus();
                        return;

                    } else if(TextUtils.isEmpty(ifsc))
                    {
                        etIfscCode.setError("Enter ifsc code");
                        etIfscCode.requestFocus();
                        return;

                    } else if(TextUtils.isEmpty(hod_name))
                    {
                        etAccHolderName.setError("Enter acc holder name");
                        etAccHolderName.requestFocus();
                        return;

                    }
                    else
                    {
                        String mailid= Prevalent.currentOnlineuser.getMobileno().toString();
                        //                       Toast.makeText(DrawerProfileActivity.this,"Email :"+mailid,Toast.LENGTH_LONG).show();
                        storeBankDetails(accno,bankname,ifsc,hod_name,mailid);
                    }


                }

        else if (id == R.id.add_address)
        {
            String a=etPAddress.getText().toString().trim();
            String c=etPCity.getText().toString().trim();
            String p=etPPinCode.getText().toString().trim();

            if(TextUtils.isEmpty(a))
            {
                etPAddress.setError("Enter your Address");
                etPAddress.requestFocus();
                return;

            }
            else if(TextUtils.isEmpty(c))
            {
                etPCity.setError("Enter city name");
                etPCity.requestFocus();
                return;

            } else if(TextUtils.isEmpty(p))
            {
                etPPinCode.setError("Enter pin code");
                etPPinCode.requestFocus();
                return;

            }
            else
            {
                String mailid= Prevalent.currentOnlineuser.getMobileno().toString();
                //                       Toast.makeText(DrawerProfileActivity.this,"Email :"+mailid,Toast.LENGTH_LONG).show();
                storeAddressData(a,c,p,mailid);
            }


        }
        else if (id== R.id.add_paytm)
        {
            String phonepaynumber=etPhonePay.getText().toString().trim();
            String teznumber=etTez.getText().toString().trim();
            String paytmNumber=etPaytm.getText().toString().trim();
            if(TextUtils.isEmpty(paytmNumber))
            {
                etPaytm.setError("Enter Paytm Number");
                etPaytm.requestFocus();
                return;
            }
            else if(TextUtils.isEmpty(teznumber))
            {
                etTez.setError("Enter GooglePay Number");
                etTez.requestFocus();
                return;
            }
            else if(TextUtils.isEmpty(phonepaynumber))
            {
                etPhonePay.setError("Enter Phone Pay Number");
                etPhonePay.requestFocus();
                return;
            }
            else
            {
                String mailid= Prevalent.currentOnlineuser.getMobileno().toString();
                //                       Toast.makeText(DrawerProfileActivity.this,"Email :"+mailid,Toast.LENGTH_LONG).show();
                storeAccDetails(teznumber,paytmNumber,phonepaynumber,mailid);
            }
        }
        else if (id == R.id.et_dob)
        {
            selectDate();
        }

        else if (id == R.id.btn_update)
        {
            String email=et_email.getText().toString().trim();
            String mobile=et_mobile.getText().toString().trim();
            String dob=et_dob.getText().toString().trim();
            if(TextUtils.isEmpty(mobile))
            {
                et_mobile.setError("Enter Mobile Number");
                et_mobile.requestFocus();
                return;
            }
            else if(TextUtils.isEmpty(email))
            {
                et_email.setError("Enter email Address");
                et_email.requestFocus();
                return;
            }
            else if (!email.matches(emailPattern))
            {
                et_email.setError("Enter valid email Address");
                et_email.requestFocus();
                return;
            }
            else if(TextUtils.isEmpty(dob))
            {
                et_dob.setError("Enter Date of Birth");
                et_dob.requestFocus();
                return;
            }
            else
            {
                String mailid= Prevalent.currentOnlineuser.getMobileno().toString();
                //                       Toast.makeText(DrawerProfileActivity.this,"Email :"+mailid,Toast.LENGTH_LONG).show();
                storeProfileData(dob ,mobile,email);
            }
        }


        }
    public void setDataEditText(EditText edt,String data)
    {
        String s=data.toString();
        if(data.equalsIgnoreCase("null"))
        {

        }
        else
        {
            edt.setText(data.toString());
        }
    }

    private void storeAccDetails(final String teznumber, final String paytmno , final String phonepay, final String mailid) {


        progressDialog.show();
        Map<String,String> params=new HashMap<>();
        params.put("key","4");
        params.put("mobile",mailid);
        params.put("tez",teznumber);
        params.put("paytm",paytmno);
        params.put("phonepay",phonepay);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URL_REGIST, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String success=response.getString("status");
                    if(success.equals("success"))
                    {
                        progressDialog.dismiss();
                        Prevalent.currentOnlineuser.setTez_no(teznumber);
                        Prevalent.currentOnlineuser.setPaytm_no(paytmno);
                        Prevalent.currentOnlineuser.setPhonepay_no(phonepay);
                        Toast.makeText(ProfileActivity.this, "Mobile Numbers Updated!!!", Toast.LENGTH_SHORT).show();

                    }
                    else if(success.equals("unsuccessful"))
                    {
                        progressDialog.dismiss();
                        String msg=response.getString("message");
                        Toast.makeText(ProfileActivity.this, ""+msg, Toast.LENGTH_SHORT).show();

                        return;
                    }

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    progressDialog.dismiss();
                    common.showToast(wrong);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=common.VolleyErrorMessage(error);
                if(!msg.isEmpty())
                {
                    common.showToast(msg);
                }
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,"json_tez");

    }

    private void storeProfileData(final String dob , String mobile , final String email)
    {   progressDialog.show();
        Map<String,String> params=new HashMap<>();
        params.put("key","5");
        params.put("mobile",mobile);
        params.put("email",email);
        params.put("dob",dob);


        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URL_REGIST, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String success=response.getString("status");
                    if(success.equals("success"))
                    {
                        progressDialog.dismiss();
                        Prevalent.currentOnlineuser.setEmail(email);
                        Prevalent.currentOnlineuser.setDob(dob);

                        Toast.makeText(ProfileActivity.this, "Profile Updated!!!", Toast.LENGTH_SHORT).show();

                    }
                    else if(success.equals("unsuccessful"))
                    {
                        progressDialog.dismiss();
                        String msg=response.getString("message");
                        Toast.makeText(ProfileActivity.this, ""+msg, Toast.LENGTH_SHORT).show();

                        return;
                    }

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    progressDialog.dismiss();
                    common.showToast(wrong);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=common.VolleyErrorMessage(error);
                if(!msg.isEmpty())
                {
                    common.showToast(msg);
                }
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,"json_tez");

    }

}

