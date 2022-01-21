package task.marami.Shubhaprada.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import task.marami.Shubhaprada.Interfaces.IBottomSheetForm;
import task.marami.Shubhaprada.Models.ProjectsData;
import task.marami.Shubhaprada.Presenters.BottomSheetPresenter;
import task.marami.Shubhaprada.R;
import task.marami.Shubhaprada.Utils.ConnectionDirectory;
import task.marami.Shubhaprada.Utils.Contents;
import task.marami.Shubhaprada.Utils.SingletonAlertDialog;

public class Bottom_Sheet_Form extends BottomSheetDialogFragment
        implements View.OnClickListener,IBottomSheetForm.BottomSheetView {
    EditText edt_plotno,edt_name;
    Spinner spi_sector;
    BottomSheetPresenter BSP;
    ProgressBar progressBar;
    Button btn_enq;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= LayoutInflater.from(getContext()).inflate(R.layout.activity_bottom_sheet_form,null );
        edt_plotno = (EditText) view.findViewById(R.id.edt_plot_no);
        edt_name = (EditText) view.findViewById(R.id.edt_p_name);
        spi_sector = (Spinner) view.findViewById(R.id.spinner2);
        progressBar = (ProgressBar) view.findViewById(R.id.prog_bottom_form);
        btn_enq = (Button) view.findViewById(R.id.btn_plot_request);
        btn_enq.setOnClickListener(this);
        ProjectsData pos= Contents.utiprojectsData;

        BSP=new BottomSheetPresenter(getContext(),this);
        BSP.onLoad(pos);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(ConnectionDirectory.isConnected(getContext())) {
            BSP.onSendEnquery(edt_plotno.getText().toString(), spi_sector.getSelectedItem().toString(),
                    edt_name.getText().toString());
        }
        else
        {
            Snackbar.make(v, Contents.No_Internet, Snackbar.LENGTH_LONG)
                    .setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        }
                    }).show();
        }
    }

    @Override
    public void onStartProg() {
        btn_enq.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onstopProg() {
        btn_enq.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(String res) {
        Toast.makeText(getActivity(), res, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(String message) {
        SingletonAlertDialog.alertDialogShow(getContext(),message);
    }

    @Override
    public void onSectorRend(ArrayAdapter<String> sctors) {
        spi_sector.setAdapter(sctors);
    }
}
