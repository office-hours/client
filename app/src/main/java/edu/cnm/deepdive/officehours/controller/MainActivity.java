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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
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

public class MainActivity extends AppCompatActivity
    implements OnItemSelectedListener {

  private MainViewModel viewModel;
  private MaterialCalendarView calendarView;
  private AppointmentDecorator decorator;
  private Spinner teacher;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    calendarView = findViewById(R.id.calendar_view);
    decorator = new AppointmentDecorator(new ColorDrawable(Color.RED),
        new ColorDrawable(Color.GREEN), new ColorDrawable(Color.CYAN));
    calendarView.addDecorator(decorator);
    teacher = findViewById(R.id.teacher);
    teacher.setOnItemSelectedListener(this);
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
    viewModel.getTeachers().observe(this, (teachers) -> {
      ArrayAdapter<Teacher> adapter =
          new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, teachers);
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
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    Teacher teacher = (Teacher) parent.getItemAtPosition(position);
    decorator.setAppointments(Arrays.asList(teacher.getAppointments()));
    decorator.setPolicies(Arrays.asList(teacher.getPolicies()));
    calendarView.invalidateDecorators();
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
    decorator.setAppointments(null);
    decorator.setPolicies(null);
    calendarView.invalidateDecorators();
  }

}
