package in.games.OnlineMatka;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.utils.CustomJsonRequest;
import maes.tech.intentanim.CustomIntent;

import static in.games.OnlineMatka.URLs.URL_UPDATE_PASS;


public class UpdatePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv_back;
    EditText et_pass,et_con_pass;
    Button btn_update;
    String mobile="";
    ProgressDialog loadingBar;
    Common common;
    Activity ctx=UpdatePasswordActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        initViews();
    }

    private void initViews() {
        iv_back=findViewById(R.id.iv_back);
        et_pass=findViewById(R.id.et_pass);
        mobile=getIntent().getStringExtra("mobile");
        et_con_pass=findViewById(R.id.et_con_pass);
        btn_update=findViewById(R.id.btn_update);
        loadingBar=new ProgressDialog(ctx);
        loadingBar.setMessage("Loading...");
        common=new Common(ctx);
        btn_update.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back)
        {
            Intent intent=new Intent(ctx,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            CustomIntent.customType(UpdatePasswordActivity.this, "up-to-bottom");
            finish();
        }
        else if(view.getId() == R.id.btn_update)
        {
            String pass=et_pass.getText().toString();
            String cpass=et_con_pass.getText().toString();
            if(pass.isEmpty())
            {
                common.showToast("Enter Password");
                et_pass.requestFocus();
            }
            else if(cpass.isEmpty())
            {
                common.showToast("Enter Confirm Password");
                et_pass.requestFocus();
            }
            else if(pass.length()<6 || cpass.length()<6)
            {
                common.showToast("Password is too short");
                et_pass.requestFocus();
            }
            else
            {
                if(pass.equals(cpass))
                {
                    updatePassword(mobile,pass);
                }
                else
                {
                    common.showToast("Password must br matched");
                }
            }
        }
    }

    private void updatePassword(String mobile,String pass) {
        loadingBar.show();
        HashMap<String,String> params=new HashMap<>();
        params.put("mobile",mobile);
        params.put("password",pass);
        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URL_UPDATE_PASS, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingBar.dismiss();
             try
             {
                 String res=response.getString("status");
                 if(res.equalsIgnoreCase("success"))
                 {
                     common.showToast(response.getString("message"));
                     Intent intent=new Intent(ctx,MainActivity.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     startActivity(intent);
                     CustomIntent.customType(UpdatePasswordActivity.this, "up-to-bottom");
                     finish();
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
                loadingBar.dismiss();
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
