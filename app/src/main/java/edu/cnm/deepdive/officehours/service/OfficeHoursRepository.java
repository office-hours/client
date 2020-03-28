package edu.cnm.deepdive.officehours.service;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OfficeHoursRepository {

  private static final int NETWORK_POOL_SIZE = 10;
  private static final String OAUTH_HEADER_FORMAT = "Bearer %s";
  private static final String ISO_DATE_FORMAT = "yyyy-MM-dd";

  private final OfficeHoursService proxy;
  private final Executor networkPool;
  private final DateFormat formatter;

  private OfficeHoursRepository(){
    proxy = OfficeHoursService.getInstance();
    networkPool = Executors.newFixedThreadPool(NETWORK_POOL_SIZE);
    formatter = new SimpleDateFormat(ISO_DATE_FORMAT);
  }

  public static OfficeHoursRepository getInstance() {
    return InstanceHolder.INSTANCE;
  }

  private Single<User> getUser(String token, UUID id) {
    return proxy.getUser(String.format(OAUTH_HEADER_FORMAT, token), id)
        .subscribeOn(Schedulers.from(networkPool));
  }

  public Single<List<User>> getAllUsers(String token) {
    return proxy.getAllUsers(String.format(OAUTH_HEADER_FORMAT, token))
        .subscribeOn(Schedulers.from(networkPool));
  }

  private Single<Student> getStudent(String token, UUID id) {
    return proxy.getStudent(String.format(OAUTH_HEADER_FORMAT, token), id)
        .subscribeOn(Schedulers.from(networkPool));
  }

  public Single<List<Student>> getAllStudents(String token) {
    return proxy.getAllStudents(String.format(OAUTH_HEADER_FORMAT, token))
        .subscribeOn(Schedulers.from(networkPool));
  }
  private Single<Teacher> getTeacher(String token, UUID id) {
    return proxy.getTeacher(String.format(OAUTH_HEADER_FORMAT, token), id)
        .subscribeOn(Schedulers.from(networkPool));
  }

  public Single<List<Teacher>> getAllTeachers(String token) {
    return proxy.getAllTeachers(String.format(OAUTH_HEADER_FORMAT, token))
        .subscribeOn(Schedulers.from(networkPool));
  }
  private Single<Appointment> getAppointment(String token, UUID id) {
    return proxy.getAppointment(String.format(OAUTH_HEADER_FORMAT, token), id)
        .subscribeOn(Schedulers.from(networkPool));
  }

  public Single<List<Appointment>> getAllAppointments(String token) {
    return proxy.getAllAppointments(String.format(OAUTH_HEADER_FORMAT, token))
        .subscribeOn(Schedulers.from(networkPool));
  }

  private static class InstanceHolder {

    private static final OfficeHoursRepository INSTANCE = new OfficeHoursRepository();
  }
}