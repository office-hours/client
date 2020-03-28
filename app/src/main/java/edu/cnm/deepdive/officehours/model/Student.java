package edu.cnm.deepdive.officehours.model;

import com.google.gson.annotations.Expose;
import java.net.URL;
import java.util.UUID;

public class Student implements Content {

  @Expose
  private UUID id;

  @Expose
  private User user;

  @Expose
  private Appointment[] appointments;

  @Expose
  private String studentName;

  @Expose
  private URL href;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Appointment[] getAppointments() {
    return appointments;
  }

  public void setAppointments(Appointment[] appointments) {
    this.appointments = appointments;
  }

  public String getStudentName() {
    return studentName;
  }

  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  public URL getHref() {
    return href;
  }

  public void setHref(URL href) {
    this.href = href;
  }
}