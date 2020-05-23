package in.games.OnlineMatka;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class BetTypeDialog extends AppCompatDialogFragment  {


    TextView txtOpen,txtClose;
    public static String data="";
    private BetTypeDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();

        final View view=layoutInflater.inflate(R.layout.layout_bettype,null);

        txtOpen=(TextView)view.findViewById(R.id.typeOpening);
        txtClose=(TextView)view.findViewById(R.id.typeClosing);




        txtOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String dt=txtOpen.getText().toString();
            listener.applyText(dt);
            dismiss();
            }
        });
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dt=txtClose.getText().toString();
                listener.applyText(dt);
                dismiss();
            }
        });

        builder.setView(view);


        return builder.create();
    }

    private void setData(TextView txtOpen, TextView txtClose) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try
        {
            listener=(BetTypeDialogListener)context;
        }
        catch(ClassCastException ex)
        {
 throw new ClassCastException(context.toString()+"must implement BetTypeDialogListener ");
        }

    }

    public interface BetTypeDialogListener
    {
        void applyText(String data);
    }
}

