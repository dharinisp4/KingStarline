package in.games.OnlineMatka.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import in.games.OnlineMatka.R;


public class StarlineDialogAdapter extends BaseAdapter {
    Context context;
    private ArrayList<String> list;

    public StarlineDialogAdapter(Context context, ArrayList<String> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.starline_save_layout,null);
//        TextView txtc1=(TextView)view.findViewById(R.id.txtC1);
//        TextView txtc2=(TextView)view.findViewById(R.id.txtC2);
//        TextView txtc3=(TextView)view.findViewById(R.id.txtC3);
        String data=list.get(position);
        String data_array[]=data.split("-");
        String digit=data_array[0].toString();
        String points=data_array[1].toString();
        String bet=data_array[2].toString();
//        txtc1.setText(digit);
//        txtc2.setText(points);
//        txtc3.setText(bet);


        return view;
    }
}
