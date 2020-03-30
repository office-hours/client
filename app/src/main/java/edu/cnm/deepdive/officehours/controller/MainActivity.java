package edu.cnm.deepdive.officehours.controller;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
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
import edu.cnm.deepdive.officehours.service.GoogleSignInService;
import edu.cnm.deepdive.officehours.viewmodel.MainViewModel;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DayViewDecorator {

  private MainViewModel viewModel;
  private MaterialCalendarView calendarView;
  private List<Appointment> appointments;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    calendarView = findViewById(R.id.calendar_view);
    calendarView.addDecorator(this);
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
    viewModel.getAppointments().observe(this, (appointments) -> {
      Log.d(getClass().getName(), appointments.toString());
      this.appointments = appointments;
      calendarView.invalidateDecorators();
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
        if (day.getYear() == appointment.getStartTime().getYear()
            && day.getMonth() == appointment.getStartTime().getMonth()
            && day.getDay() == appointment.getStartTime().getDay()) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void decorate(DayViewFacade view) {
    Log.d(getClass().getName(), "Decorate");
  }
}
