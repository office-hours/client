package edu.cnm.deepdive.officehours.controller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import edu.cnm.deepdive.officehours.R;
import edu.cnm.deepdive.officehours.model.Appointment;
import edu.cnm.deepdive.officehours.model.Teacher;
import edu.cnm.deepdive.officehours.service.AppointmentDecorator;
import edu.cnm.deepdive.officehours.service.GoogleSignInService;
import edu.cnm.deepdive.officehours.viewmodel.MainViewModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DayViewDecorator, TextWatcher {

  private MainViewModel viewModel;
  private MaterialCalendarView calendarView;
  private List<Appointment> appointments;
  private List<Teacher> teachers;
  private AppointmentDecorator decorator;
  private AutoCompleteTextView teacher;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    calendarView = findViewById(R.id.calendar_view);
    decorator = new AppointmentDecorator(new ColorDrawable(Color.RED),
        new ColorDrawable(Color.GREEN), new ColorDrawable(Color.CYAN));
    calendarView.addDecorator(decorator);
    teacher = findViewById(R.id.teacher);
    setupViewModel();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;
    //noinspection SwitchStatementWithTooFewBranches
    switch (item.getItemId()) {
      case R.id.sign_out:
        signOut();
        break;
      default:
        handled = super.onOptionsItemSelected(item);
        break;
    }
    return handled;
  }

  private void setupViewModel() {
    viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    viewModel.getThrowable().observe(this, (throwable) -> {
      if (throwable != null) {
        showToast(throwable.getMessage());
      }
    });
/*
    viewModel.getAppointments().observe(this, (appointments) -> {
      Log.d(getClass().getName(), appointments.toString());
      this.appointments = appointments;
      decorator.setAppointments(appointments);
      calendarView.invalidateDecorators();
    });
*/
    viewModel.getTeachers().observe(this, (teachers) -> {
      this.teachers = teachers;
      ArrayAdapter<Teacher> adapter =
          new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, teachers);
      teacher.setAdapter(adapter);
    });
    getLifecycle().addObserver(viewModel);
  }


  private void showToast(String message) {
    Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
    toast.setGravity(Gravity.BOTTOM, 0, 0);
    toast.show();
  }
  private void signOut() {
    GoogleSignInService.getInstance().signOut()
        .addOnCompleteListener((task) -> {
          Intent intent = new Intent(this, LoginActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
        });
  }

  @Override
  public boolean shouldDecorate(CalendarDay day) {
    if (appointments != null) {
      for (Appointment appointment : appointments) {
        Calendar start = Calendar.getInstance();
        start.setTime(appointment.getStartTime());
        if (day.getYear() == start.get(Calendar.YEAR)
            && day.getMonth() == start.get(Calendar.MONTH)
            && day.getDay() == start.get(Calendar.DAY_OF_MONTH)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void decorate(DayViewFacade view) {
   view.setBackgroundDrawable(new ColorDrawable(Color.CYAN));
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @Override
  public void afterTextChanged(Editable s) {
    String name = teacher.getText().toString().trim();
    if (teachers != null) {
      for (Teacher teacher : teachers) {
          if (name.equalsIgnoreCase(teacher.getTeacherName())) {
            decorator.setAppointments(Arrays.asList(teacher.getAppointments()));
            decorator.setPolicies(Arrays.asList(teacher.getPolicies()));
            calendarView.invalidateDecorators();
            break;
          }
      }
      decorator.setAppointments(appointments);
      decorator.setPolicies(null);
      calendarView.invalidateDecorators();
    }
  }
}
