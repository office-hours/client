package edu.cnm.deepdive.officehours.controller;


import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.officehours.R;
import edu.cnm.deepdive.officehours.controller.DateTimePickerFragment.Mode;
import edu.cnm.deepdive.officehours.model.Appointment;
import edu.cnm.deepdive.officehours.model.Appointment.Subject;
import edu.cnm.deepdive.officehours.model.Student;
import edu.cnm.deepdive.officehours.viewmodel.MainViewModel;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class EditAppointmentFragment extends DialogFragment
    implements DateTimePickerFragment.OnChangeListener {

  private static final String ID_KEY = "id";

  private UUID id;
  private View root;
  private Calendar start;
  private Calendar end;
  private Appointment appointment;
  private Spinner subjects;
  private EditText startDate;
  private EditText startTime;
  private EditText endTime;
  private Spinner students;
  private MainViewModel viewModel;
  private DateFormat dateFormat;
  private DateFormat timeFormat;

  public static EditAppointmentFragment createInstance(UUID id) {
    EditAppointmentFragment fragment = new EditAppointmentFragment();
    Bundle args = new Bundle();
    args.putSerializable(ID_KEY, id);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if(getArguments() != null) {
      id = (UUID) getArguments().getSerializable(ID_KEY);
    }
    dateFormat = android.text.format.DateFormat.getMediumDateFormat(getContext());
    timeFormat = android.text.format.DateFormat.getTimeFormat(getContext());
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_appointment, null);
    return new AlertDialog.Builder(getContext())
        .setTitle((id == null) ? "New Appointment" : "Edit Appointment")
        .setIcon(R.drawable.ic_today)
        .setView(root)
        .setNegativeButton(android.R.string.cancel, (dialog, which) -> {})
        .setPositiveButton(android.R.string.ok, (dialog, which) -> {
          appointment.setSubject((Subject) subjects.getSelectedItem());
          appointment.setStartTime(start.getTime());
          appointment.setEndTime(end.getTime());
          appointment.setStudent((Student) students.getSelectedItem());
          viewModel.save(appointment);
        })
        .create();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    subjects = root.findViewById(R.id.subjects);
    ArrayAdapter<Subject> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,
        Arrays.asList(Subject.values()));
    subjects.setAdapter(adapter);
    students = root.findViewById(R.id.students);
    startDate = root.findViewById(R.id.start_date);
    startDate.setOnClickListener(v -> {
      DateTimePickerFragment fragment = DateTimePickerFragment.createInstance(Mode.DATE, start,  R.id.start_date);
      fragment.show(getChildFragmentManager(), fragment.getClass().getName());
    });
    startTime = root.findViewById(R.id.start_time);
    startTime.setOnClickListener(v -> {
      DateTimePickerFragment fragment = DateTimePickerFragment.createInstance(Mode.TIME, start,  R.id.start_time);
      fragment.show(getChildFragmentManager(), fragment.getClass().getName());
    });
    endTime = root.findViewById(R.id.end_time);
    endTime.setOnClickListener(v -> {
      DateTimePickerFragment fragment = DateTimePickerFragment.createInstance(Mode.TIME, end,  R.id.end_time);
      fragment.show(getChildFragmentManager(), fragment.getClass().getName());
    });
    setStart(Calendar.getInstance());
    setEnd(Calendar.getInstance());
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    viewModel.getStudents().observe(getViewLifecycleOwner(), (students) -> {
      ArrayAdapter<Student> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, students);
      this.students.setAdapter(adapter);
    });
    if(id == null) {
      appointment = new Appointment();
    } else {
      // TODO Request and observe existing info on specified appointment.
    }
  }

  public void setStart(Calendar start) {
    this.start = start;
    startDate.setText(dateFormat.format(start.getTime()));
    startTime.setText(timeFormat.format(start.getTime()));
  }

  public void setEnd(Calendar end) {
    this.end = end;
    endTime.setText(timeFormat.format(end.getTime()));
  }

  @Override
  public void onChange(Calendar calendar, int tag) {
    switch (tag) {
      case R.id.start_date:
        end.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        end.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        end.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        setStart(calendar);
        break;
      case R.id.start_time:
        setStart(calendar);
        break;
      case R.id.end_time:
        setEnd(calendar);
        break;

    }
  }
}
