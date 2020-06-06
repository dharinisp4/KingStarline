package in.games.OnlineMatka;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.games.OnlineMatka.Adapter.GameRateAdapter;
import in.games.OnlineMatka.Model.GameRateModel;
import in.games.OnlineMatka.utils.CustomJsonRequest;
import in.games.OnlineMatka.utils.LoadingBar;

public class DrawerGameRates extends MyBaseActivity {
TextView bt_back;
LoadingBar progressDialog;
TextView game_Name ,game_Rate ,game_range ,txt_timer;
ArrayList<GameRateModel> list;
ArrayList<GameRateModel> slist;
GameRateAdapter gameRateAdapter;
RecyclerView jannat_recycler,starline_recycler;
RecyclerView.LayoutManager layoutManager,layoutManager1;
// TextView txtsp,txtdp,txttp,txtsd,txtjd,txtrb,txths,txtfs,txts_sp,txts_dp,txts_tp,txts_sd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_game_rates);
        bt_back = (TextView) findViewById(R.id.txt_back);

        progressDialog=new LoadingBar(DrawerGameRates.this);
    //    progressDialog.setTitle("Please wait");

        jannat_recycler=findViewById(R.id.jannat_recycler);
        starline_recycler=findViewById(R.id.starline_recycler);

      getNotice();
      getStarnotice();
//      layoutManager=new GridLayoutManager(DrawerGameRates.this,2);
//      layoutManager1=new GridLayoutManager(DrawerGameRates.this,2);
      jannat_recycler.setLayoutManager(new LinearLayoutManager(this));
//        jannat_recycler.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(1), true));
       starline_recycler.setLayoutManager(new LinearLayoutManager(this));
//        starline_recycler.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(1), true));


        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    private void getStarnotice() {

        gameRateAdapter=new GameRateAdapter(slist,DrawerGameRates.this);
        starline_recycler.setAdapter(gameRateAdapter);
        gameRateAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();
      //  setSessionTimeOut(DrawerGameRates.this);
    }

    private void getNotice() {

        progressDialog.show();
      list=new ArrayList<>();
      slist=new ArrayList<>();
        String tag_json_obj = "json_notice_req";
        Map<String, String> params = new HashMap<String, String>();

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.URL_NOTICE, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status=response.getString("status");
                    if(status.equals("success"))
                    {
                        JSONArray array=response.getJSONArray("data");

                        for (int i=0; i<array.length();i++)
                        {
                            GameRateModel gameRateModel=new GameRateModel();
                            JSONObject object=array.getJSONObject(i);
                             gameRateModel.setId(object.getString("id"));
                             gameRateModel.setName(object.getString("name"));
                             gameRateModel.setRate_range(object.getString("rate_range"));
                             gameRateModel.setRate(object.getString("rate"));
                             String type=object.getString("type").toString();
                             gameRateModel.setType(type);
                             if(type.equals("0"))
                             {
                                 list.add(gameRateModel);

                             }
                             else
                             {
                                 slist.add(gameRateModel);
                             }


                        }
                        gameRateAdapter=new GameRateAdapter(list,DrawerGameRates.this);
                        jannat_recycler.setAdapter(gameRateAdapter);
                        gameRateAdapter.notifyDataSetChanged();

                    }
                    else
                    {
                        Toast.makeText(DrawerGameRates.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
                    }

                    progressDialog.dismiss();
                }
                catch (Exception ex)
                {progressDialog.dismiss();
                    Toast.makeText(DrawerGameRates.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(DrawerGameRates.this,""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,tag_json_obj);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        //Defining retrofit api service

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
