package edu.cnm.deepdive.officehours.model;

import com.google.gson.annotations.Expose;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

public class Appointment implements Content, Comparable<Appointment> {

  @Expose
  private UUID id;

  @Expose
  private Student student;

  @Expose
  private Teacher teacher;

  @Expose
  private Date startTime;

  @Expose
  private Date endTime;

  @Expose
  private Date created;

  @Expose
  private Date updated;

  @Expose
  private URL href;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  public URL getHref() {
    return href;
  }

  public void setHref(URL href) {
    this.href = href;
  }

  @Override
  public int compareTo(Appointment other) {
    int comparison = startTime.compareTo(other.startTime);
    if (comparison == 0) {
      comparison = endTime.compareTo(other.endTime);
    }
    return comparison;
  }
}
