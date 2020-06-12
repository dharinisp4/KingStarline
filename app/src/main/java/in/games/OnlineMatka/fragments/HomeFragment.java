package in.games.OnlineMatka.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import in.games.OnlineMatka.Adapter.MatakListViewAdapter;
import in.games.OnlineMatka.Adapter.MatkaNewAdapter;
import in.games.OnlineMatka.Common.Common;
import in.games.OnlineMatka.Model.MatkasObjects;
import in.games.OnlineMatka.R;
import in.games.OnlineMatka.URLs;
import in.games.OnlineMatka.WithdrawalActivity;
import in.games.OnlineMatka.utils.LoadingBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
//    SwipeRefreshLayout swipe;
    RecyclerView listView;
    Common common;
    Button btn_withdrw;
    ImageView refresh;
    ArrayList<MatkasObjects> matkaList;
    MatkaNewAdapter newAdapter;
    private MatakListViewAdapter matakListViewAdapter;
    LoadingBar progressDialog;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);
//        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                onStart();
//                swipe.setRefreshing(false);
//            }
//        });
        return view;
    }
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    protected void onStart() {
//        super.onStart();
//        getMatkaData();
//        matakListViewAdapter=new MatakListViewAdapter(getActivity(),matkaList);
//        listView.setAdapter(matakListViewAdapter);
//    }


    @Override
    public void onStart() {
        super.onStart();
        getMatkaData();
//        matakListViewAdapter=new MatakListViewAdapter(getActivity(),matkaList);
//        listView.setAdapter(matakListViewAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initViews(View v) {
//        swipe=v.findViewById(R.id.swipe_layout);
        listView=v.findViewById(R.id.listView);
        btn_withdrw = v.findViewById(R.id.withdrw_points);
        refresh = v.findViewById(R.id.refresh);

        matkaList=new ArrayList<>();
        listView.setNestedScrollingEnabled(false);
        progressDialog=new LoadingBar(getActivity());
        common=new Common(getActivity());
     btn_withdrw.setOnClickListener(this);
        refresh.setOnClickListener(this);
    }

    public void getMatkaData()
    {
        progressDialog.show();

        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URLs.URL_Matka, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //   matkaAdapter.notifyDataSetChanged();

                        Log.d("matka",String.valueOf(response));

                        matkaList.clear();

                        for(int i=0; i<response.length();i++)
                        {
                            try
                            {
                                JSONObject jsonObject=response.getJSONObject(i);

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
                                matkaList.add(matkasObjects);

                            }
                            catch (Exception ex)
                            {
                                progressDialog.dismiss();
                               Toast.makeText(getActivity(),"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();

                                return;
                            }
                        }

                        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        newAdapter=new MatkaNewAdapter(getActivity(),matkaList);
                        listView.setAdapter(newAdapter);
                        newAdapter.notifyDataSetChanged();

                        progressDialog.dismiss();


                    }

                },

                new Response.ErrorListener() {
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
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);



    }

    @Override
    public void onClick(View view) {
        int id =view.getId();
        if(id == R.id.withdrw_points)
        {
            Intent intent = new Intent(getActivity(), WithdrawalActivity.class);
            startActivity(intent);

        }
        else if(id == R.id.refresh)
        {
            getMatkaData();
        }
    }
}
