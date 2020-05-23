package in.games.OnlineMatka.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import in.games.OnlineMatka.Model.SingleDigitObjects;
import in.games.OnlineMatka.R;
import in.games.OnlineMatka.utils.LoadingBar;

public class SingleDigitAdapter extends BaseAdapter {

    LoadingBar progressDialog;
    private Context context;
    ArrayList<SingleDigitObjects> list;

    public SingleDigitAdapter(Context context, ArrayList<SingleDigitObjects> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        final View view=View.inflate(context, R.layout.singled_list_item_layout,null);

        progressDialog=new LoadingBar(context);

        TextView txtP=(TextView)view.findViewById(R.id.pg_digit);
        TextView txtd=(TextView)view.findViewById(R.id.pg_points);
        TextView txtbet=(TextView)view.findViewById(R.id.pg_betType);
        Button btnDelete=(Button)view.findViewById(R.id.pg_delete);


        txtP.setText(list.get(position).getPoints().toString());
        txtd.setText(list.get(position).getDigits().toString());
        txtbet.setText(list.get(position).getBettype().toString());


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Toast.makeText(context,""+position,Toast.LENGTH_LONG).show();
                list.remove(position);
               // notifyDataSetInvalidated();
                //view.setTag(list.get(position).getPoints());
                //list.clear();
                   //view.refreshDrawableState();
                   notifyDataSetChanged();
                //progressDialog.dismiss();
            }
        });
        view.setTag(list.get(position).getPoints());



        return view;
    }
}
