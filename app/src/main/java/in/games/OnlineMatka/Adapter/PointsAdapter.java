package in.games.OnlineMatka.Adapter;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.games.OnlineMatka.Model.TableModel;
import in.games.OnlineMatka.R;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder> {
    public static ArrayList<TableModel> jodi_list ;
 List<String> digit_list ;
    Activity activity;
public static Boolean is_empty = true , is_error = false;

    public PointsAdapter(List<String> digit_list, Activity activity) {
        this.digit_list = digit_list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_new_digits,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.txt_digits.setText(digit_list.get(i));


        viewHolder.et_points.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String points =s.toString();
                if (points.isEmpty())
                {
                    is_empty =true;
                }
                else {
                    is_empty = false;
                    int p = Integer.parseInt(points);
                    if (p < 10) {
//                        viewHolder.et_points.setError("Minimum bid points is 10");
//                        viewHolder.et_points.requestFocus();
                        is_error = true;

                    } else {
                        is_empty = false;
                        is_error = false;
                        jodi_list.add(new TableModel( digit_list.get(i),points, "close"));
                    }
                }
            }
        });
//

    }

    @Override
    public int getItemCount() {
        return digit_list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_digits ;
        EditText et_points;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_digits = itemView.findViewById(R.id.txt_digit);
            et_points = itemView.findViewById(R.id.et_points);
            jodi_list = new ArrayList<>();
        }
    }

    public static void resetControls()
    {

    }
}
