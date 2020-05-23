package in.games.OnlineMatka.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import in.games.OnlineMatka.Model.TableModel;
import in.games.OnlineMatka.R;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 03,February,2020
 */
public class ListItemAdapter extends BaseAdapter {
    List<TableModel> list;
    Context context;

    public ListItemAdapter(List<TableModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.row_list_item,null);
        TextView txtDigit=(TextView)itemView.findViewById(R.id.txtDigit);
        TextView  txtPoints=(TextView)itemView.findViewById(R.id.txtPoints);
        TextView  txtType=(TextView)itemView.findViewById(R.id.txtType);

        final TableModel tableModel=list.get(i);

        txtDigit.setText(tableModel.getDigits());
        txtPoints.setText(tableModel.getPoints());
        txtType.setText(tableModel.getType());

        return itemView;
    }
}
