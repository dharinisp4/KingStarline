package in.games.OnlineMatka;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.utils.CustomJsonRequest;

import static in.games.OnlineMatka.splash_activity.msg_status;

public class RegisterActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    String mob="",otp="";
    private Button btnRegister;
    TextView txt_back ;
    Common common;
    private EditText txtName,txtMobile,txtPass,txtConPass,txtUserName;
    ProgressBar pb;
    Activity ctx=RegisterActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txt_back = findViewById(R.id.txt_back);
        common=new Common(ctx);
        txtName=(EditText)findViewById(R.id.etName);
//        txtEmail=(EditText)findViewById(R.id.etEmail);
        txtMobile=(EditText)findViewById(R.id.etMobile);
        txtPass=(EditText)findViewById(R.id.etPass);
        txtConPass=(EditText)findViewById(R.id.etConPass);
        txtUserName=(EditText)findViewById(R.id.etUserName);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        progressDialog=new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Loading...");
        mob=getIntent().getStringExtra("mobile");
        txtMobile.setText(mob);
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();


            }
        });

      btnRegister.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              otp=common.getRandomKey(6);
              if(TextUtils.isEmpty(txtUserName.getText().toString()))
              {
                  txtUserName.setError("Please Enter User name");
                  txtUserName.requestFocus();
                  return;
              }
              else if(TextUtils.isEmpty(txtName.getText().toString()))
              {
                  txtName.setError("Please Enter name");
                  txtName.requestFocus();
                  return;
              }
              else  if(TextUtils.isEmpty(txtMobile.getText().toString()))
              {
                  txtMobile.setError("Please enter mobile number");
                  txtMobile.requestFocus();
                  return;
              }else  if(TextUtils.isEmpty(txtPass.getText().toString()))
              {
                  txtPass.setError("Please enter password");
                  txtPass.requestFocus();
                  return;
              }else  if(TextUtils.isEmpty(txtConPass.getText().toString()))
              {
                  txtConPass.setError("Please re-enter password");
                  txtConPass.requestFocus();
                  return;
              }

              else
              {
                  String phone_value=txtMobile.getText().toString().trim();
                  int sf=Integer.parseInt(phone_value.substring(0,1));
                  int len=phone_value.length();

//                  Toast.makeText(RegisterActivity.this,"we"+sf,Toast.LENGTH_LONG).show();

                  if(sf<6 || len<10)
                  {
                      Toast.makeText(RegisterActivity.this,"Invalid Mobile number \n" +
                              "mobile number never start with 0 and <6",Toast.LENGTH_LONG).show();
                  }
                  else
                  {
                      String pass=txtPass.getText().toString().trim();
                      String conpass=txtConPass.getText().toString().trim();
                      if(pass.equals(conpass))
                      {
                          String mobile=txtMobile.getText().toString();
                          String user_name=txtUserName.getText().toString();
                          String name=txtName.getText().toString();
                          sendOtpforPass(URLs.URL_VERIFICATION,user_name,name,mobile,otp,pass);
                      }
                      else
                      {
                          Toast.makeText(RegisterActivity.this,"password must be matched",Toast.LENGTH_LONG).show();
                          return;
                      }

                  }






              }


          }
      });





    }


    public String validMobile(String phone)
    {
        String value="";
        int len=phone.length();

        switch (len)
        {
            case 10:
                value=phone;
                break;
            case 13:
                value=phone.substring(3,13);
                break;
            default:
                value="invalid";

        }
        return value;
    }


    private void register(String phone_value) {

        progressDialog.show();
        final String uname=txtUserName.getText().toString().trim();
        final String fname=txtName.getText().toString().trim();
        final String fmobile=phone_value;
        final String fpass=txtPass.getText().toString().trim();
        final String fconpass=txtConPass.getText().toString().trim();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");
                            if(success.equals("success"))
                            {
                                progressDialog.dismiss();
//                                Toast.makeText(RegisterActivity.this, "Register successfull!!!", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(RegisterActivity.this,VerificationActivity.class);
                                intent.putExtra("user_name",uname);
                                intent.putExtra("name",fname);
                                intent.putExtra("mobile",fmobile);
                                intent.putExtra("pass",fpass);
                                intent.putExtra("type","r");
                                startActivity(intent);
                                finish();

                            }
                            else if(success.equals("unsuccessful"))
                            {
                                progressDialog.dismiss();
                                String msg=jsonObject.getString("message");
                                if(msg.equals("Mobile number already exists"))
                                {
                                    txtMobile.setText("");
                                    txtMobile.setError("Enter valid number");
                                    txtMobile.requestFocus();
                                }
                                else if(msg.equals("Email already exists"))
                                 {
                                     }
                                Toast.makeText(RegisterActivity.this, ""+msg, Toast.LENGTH_SHORT).show();


                            }



                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Registration failed"+e.getMessage(), Toast.LENGTH_SHORT).show();

                          //  btnReg.setVisibility(View.VISIBLE);


                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Registration failed"+error.getMessage(), Toast.LENGTH_SHORT).show();
                       // pb.setVisibility(View.GONE);

                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("key","1");
                params.put("username",uname);
                params.put("name",fname);
                params.put("mobile",fmobile);
//                params.put("email","");
                params.put("password",fpass);

                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void sendOtpforPass(String url,final String user_name, final String name,final String mobile,final String otp,final String pass) {
        progressDialog.show();
        HashMap<String,String> params=new HashMap<>();
        params.put("mobile",mobile);
        params.put("otp",otp);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("gen",""+response.toString());
                progressDialog.dismiss();
                try
                {
                    String res=response.getString("status");
                    if(res.equalsIgnoreCase("success"))
                    {
                        common.showToast(response.getString("message"));
                        Intent intent=new Intent(RegisterActivity.this,VerificationActivity.class);
                        intent.putExtra("user_name",user_name);
                        intent.putExtra("name",name);
                        intent.putExtra("mobile",mobile);
                        intent.putExtra("pass",pass);
                        intent.putExtra("otp",otp);
                        intent.putExtra("type","r");
                        startActivity(intent);
                    }
                    else
                    {
                        common.showToast(response.getString("message"));
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    common.showToast("Something went wrong");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=common.VolleyErrorMessage(error);
                if(!msg.isEmpty())
                {
                    common.showToast(""+msg);
                }
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest);

    }

}
