package in.games.OnlineMatka;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import in.games.OnlineMatka.utils.CustomVolleyJsonArrayRequest;
import in.games.OnlineMatka.utils.LoadingBar;

public class DrawerHowToPlayActivity extends MyBaseActivity {

    TextView bt_back,txtData,txtLink;
    RelativeLayout rel_click;
    LoadingBar progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_how_to_play);
        bt_back = (TextView) findViewById(R.id.txt_back);
        progressDialog=new LoadingBar(DrawerHowToPlayActivity.this);

        txtData=(TextView)findViewById(R.id.w2);
        txtLink=(TextView)findViewById(R.id.link);
        rel_click=(RelativeLayout)findViewById(R.id.rel_click);

        rel_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String h= txtLink.getText().toString().trim();
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(h));
                   startActivity(intent);
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        getHowToPlayData();



    }

    @Override
    protected void onStart() {
        super.onStart();
        //setSessionTimeOut(DrawerHowToPlayActivity.this);
    }

    private void getHowToPlayData() {
        progressDialog.show();
        String json_request_tag="json_how_request";
        HashMap<String,String> params=new HashMap<String,String>();

        CustomVolleyJsonArrayRequest customVolleyJsonArrayRequest=new CustomVolleyJsonArrayRequest(Request.Method.GET, URLs.URL_PLAY, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try
                {
                    JSONObject jsonObject=response.getJSONObject(0);
                    String data=jsonObject.getString("data");
                    String link=jsonObject.getString("link");
                    txtData.setText(data);
                    txtLink.setText(String.valueOf(link));
                    progressDialog.dismiss();
                }
                catch (Exception ex)
                {
                    progressDialog.dismiss();
                    Toast.makeText(DrawerHowToPlayActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(DrawerHowToPlayActivity.this,""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(customVolleyJsonArrayRequest,json_request_tag);


    }
}
