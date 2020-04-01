package edu.cnm.deepdive.officehours.controller;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import edu.cnm.deepdive.officehours.R;
import java.util.Date;
import java.util.UUID;

public class AppointmentsFragment extends DialogFragment {

  private static final String TEACHER_ID_KEY = "teacher_id";
  private static final String DATE_KEY = "date";

  private View root;
  private UUID teacherId;

  public static AppointmentsFragment createInstance(UUID teacherId, Date date){
    AppointmentsFragment fragment = new AppointmentsFragment();
    Bundle args = new Bundle();
    args.putSerializable(TEACHER_ID_KEY, teacherId);
    args.putSerializable(DATE_KEY, date);
    fragment.setArguments(args);
    return fragment;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_appointments, null);
    return new AlertDialog.Builder(getContext())
        .setTitle("Appointments")
        .setIcon(android.R.drawable.ic_dialog_info)
        .setView(root)
        .setNeutralButton(android.R.string.ok, (dlg, which) -> {})
        .create();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

  }

}
