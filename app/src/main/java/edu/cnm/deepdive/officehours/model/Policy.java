package edu.cnm.deepdive.officehours.model;

import com.google.gson.annotations.Expose;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

public class Policy implements Content {

  @Expose
  private UUID id;

  @Expose
  private Teacher teacher;

  @Expose
  private Date startAvailable;

  @Expose
  private Date endAvailable;

  @Expose
  private Date created;

  @Expose
  private Date updated;

  @Expose
  private Date blockTime;

  @Expose
  private URL href;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public Date getStartAvailable() {
    return startAvailable;
  }

  public void setStartAvailable(Date startAvailable) {
    this.startAvailable = startAvailable;
  }

  public Date getEndAvailable() {
    return endAvailable;
  }

  public void setEndAvailable(Date endAvailable) {
    this.endAvailable = endAvailable;
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

  public Date getBlockTime() {
    return blockTime;
  }

  public void setBlockTime(Date blockTime) {
    this.blockTime = blockTime;
  }

  public URL getHref() {
    return href;
  }

  public void setHref(URL href) {
    this.href = href;
  }
}
