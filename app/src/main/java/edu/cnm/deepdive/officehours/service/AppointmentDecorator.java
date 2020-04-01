package edu.cnm.deepdive.officehours.service;

import android.graphics.drawable.Drawable;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import edu.cnm.deepdive.officehours.model.Appointment;
import edu.cnm.deepdive.officehours.model.Policy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class AppointmentDecorator implements DayViewDecorator {

  private List<Appointment> appointments;
  private List<Policy> policies;
  private final Drawable appointmentDrawable;
  private final Drawable availableDrawable;
  private final Drawable mixedDrawable;

  public AppointmentDecorator(
      Drawable appointmentDrawable, Drawable availableDrawable,
      Drawable mixedDrawable) {
    this.appointmentDrawable = appointmentDrawable;
    this.availableDrawable = availableDrawable;
    this.mixedDrawable = mixedDrawable;
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
    if (policies != null) {
      Calendar target = Calendar.getInstance();
      target.set(Calendar.YEAR, day.getYear());
      target.set(Calendar.MONTH, day.getMonth());
      target.set(Calendar.DAY_OF_MONTH, day.getDay());
      // TODO Search for target in policies.
      for (Policy policy : policies) {
        Calendar start = Calendar.getInstance();
        start.setTime(policy.getStartAvailable());
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
    // TODO Figure out how to know which we're decorating.
    view.setBackgroundDrawable(mixedDrawable);
  }

  public List<Appointment> getAppointments() {
    return appointments;
  }

  public void setAppointments(List<Appointment> appointments) {
    if (appointments != null) {
      this.appointments = new ArrayList<>(appointments);
      Collections.sort(this.appointments);
    } else {
      this.appointments = null;
    }
  }

  public List<Policy> getPolicies() {
    return policies;
  }

  public void setPolicies(List<Policy> policies) {
    if (policies != null) {
      this.policies = new ArrayList<>(policies);
      Collections.sort(this.policies);
    } else {
      this.policies = null;
    }
  }
}
