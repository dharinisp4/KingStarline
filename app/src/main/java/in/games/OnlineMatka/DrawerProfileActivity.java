package in.games.OnlineMatka;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Prevalent.Prevalent;
import in.games.OnlineMatka.utils.CustomJsonRequest;
import in.games.OnlineMatka.utils.LoadingBar;

import static in.games.OnlineMatka.URLs.URL_REGIST;

public class DrawerProfileActivity extends MyBaseActivity {
    private EditText etPAddress,etPCity,etPPinCode,etAccNo,etBankName,etIfscCode,etAccHolderName,etPaytm,etTez,etPhonePay;
    private Dialog dialog;
    Common common;
    LoadingBar progressDialog;
    ProgressBar pb;
    String wrong="Something Went Wrong";
    private TextView btn_back;
    private RelativeLayout cvAddress,cvBank,cvPaytm,cvGoogle,cvPhone;
    private Button btnDAddress,btnDBank,btnDPaytm,btnDGoogle,btnDPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_profile);
        common=new Common(DrawerProfileActivity.this);
        cvAddress=findViewById(R.id.cvAddress);
        cvBank=findViewById(R.id.cvBank);
        cvPaytm=findViewById(R.id.cvPaytm);
        cvGoogle=findViewById(R.id.cvGoogle);
        cvPhone=findViewById(R.id.cvPhone);
        btn_back=(TextView)findViewById(R.id.txt_back);
        progressDialog=new LoadingBar(DrawerProfileActivity.this);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        common.setSessionTimeOut(DrawerProfileActivity.this);
        cvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog=new Dialog(DrawerProfileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_address_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                btnDAddress=(Button)dialog.findViewById(R.id.add_address);
                etPAddress=(EditText)dialog.findViewById(R.id.etAddress);
                etPCity=(EditText)dialog.findViewById(R.id.etCity);
                etPPinCode=(EditText)dialog.findViewById(R.id.etPin);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                String ad=Prevalent.currentOnlineuser.getAddress();
                String ct=Prevalent.currentOnlineuser.getCity();
                String pn=Prevalent.currentOnlineuser.getPincode();

                 setDataEditText(etPAddress,ad);
                 setDataEditText(etPCity,ct);
                 setDataEditText(etPPinCode,pn);

                btnDAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
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




//                        Toast.makeText(DrawerProfileActivity.this,"a12:- "+a.toString()+"c :-"+c+"p :-"+p.toString(),Toast.LENGTH_LONG).show();
                       // Toast.makeText(DrawerProfileActivity.this,"Your address save successfully.",Toast.LENGTH_LONG).show();


                    }
                });

            }
        });

        cvBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(DrawerProfileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.setContentView(R.layout.dialog_bank_layout);
                btnDBank=(Button)dialog.findViewById(R.id.add_bank);
                etAccNo=(EditText)dialog.findViewById(R.id.etAccNo);
                etBankName=(EditText)dialog.findViewById(R.id.etBankName);
                etIfscCode=(EditText)dialog.findViewById(R.id.etIfsc);
                etAccHolderName=(EditText)dialog.findViewById(R.id.etHName);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

//                etAccNo.setText(Prevalent.currentOnlineuser.getAccountno().);
//                etBankName.setText(Prevalent.currentOnlineuser.getBank_name());
//                etIfscCode.setText(Prevalent.currentOnlineuser.getIfsc_code());
//                etAccHolderName.setText(Prevalent.currentOnlineuser.getAccount_holder_name());

               String ac=Prevalent.currentOnlineuser.getAccountno().toString();
               String bn=Prevalent.currentOnlineuser.getBank_name().toString();
               String ic=Prevalent.currentOnlineuser.getIfsc_code().toString();
               String ah=Prevalent.currentOnlineuser.getAccount_holder_name().toString();

               setDataEditText(etAccNo,ac);
               setDataEditText(etBankName,bn);
               setDataEditText(etIfscCode,ic);
               setDataEditText(etAccHolderName,ah);
                btnDBank.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
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
                });
            }
        });

        cvPaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(DrawerProfileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_paytm_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                btnDPaytm=(Button)dialog.findViewById(R.id.add_paytm);
                etPaytm=(EditText)dialog.findViewById(R.id.etPaytmNo);


                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                String p=Prevalent.currentOnlineuser.getPaytm_no().toString();
                setDataEditText(etPaytm,p);


                btnDPaytm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String paytmNumber=etPaytm.getText().toString().trim();
                        if(TextUtils.isEmpty(paytmNumber))
                        {
                            etPaytm.setError("Enter Paytm Number");
                            etPaytm.requestFocus();
                            return;
                        }
                        else
                        {
                            String mailid= Prevalent.currentOnlineuser.getMobileno().toString();
                            //                       Toast.makeText(DrawerProfileActivity.this,"Email :"+mailid,Toast.LENGTH_LONG).show();
                            storePaytmDetails(paytmNumber,mailid);
                        }

                       // Toast.makeText(DrawerProfileActivity.this,"Your paytm number save successfully.",Toast.LENGTH_LONG).show();


                    }
                });
            }
        });

        cvGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(DrawerProfileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_tez_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                btnDGoogle=(Button)dialog.findViewById(R.id.add_tez);
                etTez=(EditText)dialog.findViewById(R.id.etTezNo);



                dialog.show();
String tz=Prevalent.currentOnlineuser.getTez_no().toString();

             setDataEditText(etTez,tz);
             btnDGoogle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        //Toast.makeText(DrawerProfileActivity.this,"Your paytm number save successfully.",Toast.LENGTH_LONG).show();
                        String teznumber=etTez.getText().toString().trim();


                        if(TextUtils.isEmpty(teznumber))
                        {
                            etTez.setError("Enter GooglePay Number");
                            etTez.requestFocus();
                            return;
                        }
                        else
                        {
                            String mailid= Prevalent.currentOnlineuser.getMobileno().toString();
                            //                       Toast.makeText(DrawerProfileActivity.this,"Email :"+mailid,Toast.LENGTH_LONG).show();
                            storeTezDetails(teznumber,mailid);
                        }



                    }
                });
            }
        });

        cvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(DrawerProfileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_phone_layout);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                btnDPhone=(Button)dialog.findViewById(R.id.add_Phone);
               etPhonePay=(EditText)dialog.findViewById(R.id.etPhone);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                String x=Prevalent.currentOnlineuser.getPhonepay_no().toString();

                setDataEditText(etPhonePay,x);
                btnDPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String phonepaynumber=etPhonePay.getText().toString().trim();

                        if(TextUtils.isEmpty(phonepaynumber))
                        {
                            etPhonePay.setError("Enter Phone Pay Number");
                            etPhonePay.requestFocus();
                            return;
                        }
                        else
                        {
                            String mailid= Prevalent.currentOnlineuser.getMobileno().toString();
                            //                       Toast.makeText(DrawerProfileActivity.this,"Email :"+mailid,Toast.LENGTH_LONG).show();
                            storePhonePayDetails(phonepaynumber,mailid);
                        }


                    }
                });
            }
        });
    }

    private void storePhonePayDetails(final String phonepaynumber,final String mailid) {
        progressDialog.show();
        Map<String,String> params=new HashMap<>();
        params.put("key","6");
        params.put("mobile",mailid);
        params.put("phonepay",phonepaynumber);

   CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URL_REGIST, params, new Response.Listener<JSONObject>() {
       @Override
       public void onResponse(JSONObject response) {
        try
        {
            String success=response.getString("status");
            if(success.equals("success"))
            {
                progressDialog.dismiss();
                Prevalent.currentOnlineuser.setPhonepay_no(phonepaynumber);
                Toast.makeText(DrawerProfileActivity.this, "Phone Pay Number Updated!!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
            else if(success.equals("unsuccessful"))
            {
                progressDialog.dismiss();
                String msg=response.getString("message");
                Toast.makeText(DrawerProfileActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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
   AppController.getInstance().addToRequestQueue(customJsonRequest,"json_phone");

    }

    private void storeTezDetails(final String teznumber,final String mailid) {


        progressDialog.show();
        Map<String,String> params=new HashMap<>();
        params.put("key","4");
        params.put("mobile",mailid);
        params.put("tez",teznumber);
        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URL_REGIST, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
             try {
                 String success=response.getString("status");
                 if(success.equals("success"))
                 {
                     progressDialog.dismiss();
                     Prevalent.currentOnlineuser.setTez_no(teznumber);
                     Toast.makeText(DrawerProfileActivity.this, "Google Pay Number Updated!!!", Toast.LENGTH_SHORT).show();
                     dialog.dismiss();
                 }
                 else if(success.equals("unsuccessful"))
                 {
                     progressDialog.dismiss();
                     String msg=response.getString("message");
                     Toast.makeText(DrawerProfileActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                     dialog.dismiss();
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

    private void storePaytmDetails(final String paytmNumber,final String mailid) {

        progressDialog.show();
        Map<String,String> params=new HashMap<>();
        params.put("key","5");
        params.put("mobile",mailid);
        params.put("paytm",paytmNumber);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URL_REGIST, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               try
               {
                   String success=response.getString("status");
                   if(success.equals("success"))
                   {
                       progressDialog.dismiss();
                       Prevalent.currentOnlineuser.setPaytm_no(paytmNumber);
                       Toast.makeText(DrawerProfileActivity.this, "Paytm Number Updated!!!", Toast.LENGTH_SHORT).show();
                       dialog.dismiss();
                   }
                   else if(success.equals("unsuccessful"))
                   {
                       progressDialog.dismiss();
                       String msg=response.getString("message");
                       Toast.makeText(DrawerProfileActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                       dialog.dismiss();
                       return;
                   }

               }
               catch (Exception ex)
               {
                   ex.printStackTrace();
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
        AppController.getInstance().addToRequestQueue(customJsonRequest,"json_paytm");
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
                      Toast.makeText(DrawerProfileActivity.this, "Bank Details Updated!!!", Toast.LENGTH_SHORT).show();
                      dialog.dismiss();
                  }
                  else if(success.equals("unsuccessful"))
                  {
                      progressDialog.dismiss();
                      String msg=response.getString("message");
                      Toast.makeText(DrawerProfileActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                      dialog.dismiss();
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
                      Toast.makeText(DrawerProfileActivity.this, "Address Updated!!!", Toast.LENGTH_SHORT).show();
                      dialog.dismiss();
                  }
                  else if(success.equals("unsuccessful"))
                  {
                      progressDialog.dismiss();
                      String msg=response.getString("message");
                      Toast.makeText(DrawerProfileActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                      dialog.dismiss();
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

}
