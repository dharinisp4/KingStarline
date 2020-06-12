package in.games.OnlineMatka.DialogAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.games.OnlineMatka.R;

public class DialogAddress extends AppCompatDialogFragment {

    private EditText etAddres,etCity,etPinCode;
    private Button btnSave;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        final View view=layoutInflater.inflate(R.layout.dialog_address_layout,null);

        etAddres=(EditText)view.findViewById(R.id.etAddress);
        etCity=(EditText)view.findViewById(R.id.etCity);
        etPinCode=(EditText)view.findViewById(R.id.etPin);
       btnSave=(Button)view.findViewById(R.id.add_address);

       btnSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String a=etAddres.getText().toString().trim();
               String c=etCity.getText().toString().trim();
               String p=etPinCode.getText().toString().trim();

               Toast.makeText(getActivity(),"a:- "+a.toString()+"c :-"+c+"p :-"+p.toString(),Toast.LENGTH_LONG).show();
               dismiss();
           }
       });


        builder.setView(view);


        return builder.create();
    }
}
