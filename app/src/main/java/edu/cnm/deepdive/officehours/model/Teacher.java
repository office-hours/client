package edu.cnm.deepdive.officehours.model;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.net.URL;
import java.util.UUID;

public class Teacher implements Content {

  @Expose
  private UUID id;

  @Expose
  private User user;

  @Expose
  @SerializedName("appointment")
  private Appointment[] appointments;

  @Expose
  @SerializedName("policy")
  private Policy[] policies;

  @Expose
  private String teacherName;

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

  public Policy[] getPolicies() {
    return policies;
  }

  public void setPolicies(Policy[] policies) {
    this.policies = policies;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public URL getHref() {
    return href;
  }

  public void setHref(URL href) {
    this.href = href;
  }

  @NonNull
  @Override
  public String toString() {
    return teacherName;
  }
}

