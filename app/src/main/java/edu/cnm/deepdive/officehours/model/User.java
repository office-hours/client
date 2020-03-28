package edu.cnm.deepdive.officehours.model;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

public class User implements Content {

  @Expose
  private UUID id;

  @Expose
  private String oauth;

  @Expose
  private String nickName;

  @Expose
  private String email;

  @Expose
  private Date created;

  @Expose
  private Teacher teacher;

  @Expose
  private Student student;

  @Expose
  private URL href;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getOauth() {
    return oauth;
  }

  public void setOauth(String oauth) {
    this.oauth = oauth;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
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
    return nickName;
  }
}
