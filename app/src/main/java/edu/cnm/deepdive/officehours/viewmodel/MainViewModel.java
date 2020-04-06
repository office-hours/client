package edu.cnm.deepdive.officehours.viewmodel;

import android.util.Log;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import edu.cnm.deepdive.officehours.model.Appointment;
import edu.cnm.deepdive.officehours.model.Student;
import edu.cnm.deepdive.officehours.model.Teacher;
import edu.cnm.deepdive.officehours.model.User;
import edu.cnm.deepdive.officehours.service.GoogleSignInService;
import edu.cnm.deepdive.officehours.service.OfficeHoursRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MainViewModel extends ViewModel implements LifecycleObserver {

  private MutableLiveData<List<User>> users;
  private MutableLiveData<List<Student>> students;
  private MutableLiveData<List<Teacher>> teachers;
  private MutableLiveData<List<Appointment>> appointments;
  private MutableLiveData<List<Appointment>> dailyAppointments;

  private MutableLiveData<User> user;
  private MutableLiveData<Student> student;
  private MutableLiveData<Teacher> teacher;
  private MutableLiveData<Appointment> appointment;

  private final MutableLiveData<Throwable> throwable;
  private final OfficeHoursRepository repository;
  private CompositeDisposable pending;


  public MainViewModel() {
    repository = OfficeHoursRepository.getInstance();
    pending = new CompositeDisposable();
    users = new MutableLiveData<>();
    students = new MutableLiveData<>();
    teachers = new MutableLiveData<>();
    appointments = new MutableLiveData<>();
    dailyAppointments = new MutableLiveData<>();
    user = new MutableLiveData<>();
    student = new MutableLiveData<>();
    teacher = new MutableLiveData<>();
    appointment = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
//    refreshUsers();
    refreshStudents();
    refreshTeachers();
//    refreshAppointments();
  }

  public LiveData<List<User>> getUsers() {
    return users;
  }

  public LiveData<List<Student>> getStudents() {
    return students;
  }

  public LiveData<List<Teacher>> getTeachers() {
    return teachers;
  }

  public LiveData<List<Appointment>> getAppointments() {
    return appointments;
  }

  public LiveData<List<Appointment>> getDailyAppointments() {
    return dailyAppointments;
  }

  public LiveData<User> getUser() {
    return user;
  }

  public LiveData<Student> getStudent() {
    return student;
  }

  public LiveData<Teacher> getTeacher() {
    return teacher;
  }

  public LiveData<Appointment> getAppointment() {
    return appointment;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void refreshUsers() {
    throwable.postValue(null);
    GoogleSignInService.getInstance().refresh()
        .addOnSuccessListener((account) -> {
          pending.add(
              repository.getAllUsers(account.getIdToken())
                  .subscribe(
                      users::postValue,
                      throwable::postValue
                  )
          );
        })
        .addOnFailureListener(throwable::postValue);
  }

  public void refreshStudents() {
    throwable.postValue(null);
    GoogleSignInService.getInstance().refresh()
        .addOnSuccessListener((account) -> {
          pending.add(
              repository.getAllStudents(account.getIdToken())
                  .subscribe(
                      students::postValue,
                      throwable::postValue
                  )
          );
        })
        .addOnFailureListener(throwable::postValue);
  }

  public void refreshTeachers() {
    throwable.postValue(null);
    GoogleSignInService.getInstance().refresh()
        .addOnSuccessListener((account) ->
            pending.add(
                repository.getAllTeachers(account.getIdToken())
                    .doOnSuccess((whatever) -> Log.d(getClass().getName(), "doOnSuccess"))
                    .doAfterSuccess((whatever) -> Log.d(getClass().getName(), "doAfterSuccess"))
                    .subscribe(
                        teachers::postValue,
                        throwable::postValue
                    )
            ))
        .addOnFailureListener(throwable::postValue);
  }

  public void refreshAppointments() {
    throwable.postValue(null);
    GoogleSignInService.getInstance().refresh()
        .addOnSuccessListener((account) -> {
          pending.add(
              repository.getAllAppointments(account.getIdToken())
                  .subscribe(
                      appointments::postValue,
                      throwable::postValue
                  )
          );
        })
        .addOnFailureListener(throwable::postValue);
  }


  public void setUserId(UUID id) {
    throwable.setValue(null);
    GoogleSignInService.getInstance().refresh()
        .addOnSuccessListener(
            (account) -> pending.add(
                repository.getUser(account.getIdToken(), id)
                    .subscribe(
                        user::postValue,
                        throwable::postValue
                    )
            )
        )
        .addOnFailureListener(throwable::postValue);
  }

  public void setStudentId(UUID id) {
    throwable.setValue(null);
    GoogleSignInService.getInstance().refresh()
        .addOnSuccessListener(
            (account) -> pending.add(
                repository.getStudent(account.getIdToken(), id)
                    .subscribe(
                        student::postValue,
                        throwable::postValue
                    )
            )
        )
        .addOnFailureListener(throwable::postValue);
  }

  public void setTeacherId(UUID id) {
    throwable.setValue(null);
    GoogleSignInService.getInstance().refresh()
        .addOnSuccessListener(
            (account) -> pending.add(
                repository.getTeacher(account.getIdToken(), id)
                    .subscribe(
                        teacher::postValue,
                        throwable::postValue
                    )
            )
        )
        .addOnFailureListener(throwable::postValue);
  }

  public void fetchDailyAppointments(UUID teacherId, Date date) {

  }
  // TODO Add method setAppointmentId.

  public void save(User user) {
    throwable.setValue(null);
    GoogleSignInService.getInstance().refresh()
        .addOnSuccessListener((account) -> {
          pending.add(
              repository.save(account.getIdToken(), user)
                  .subscribe(
                      () -> {
                        this.user.postValue(null);
                        refreshUsers();
                        refreshStudents();
                        refreshTeachers();
                        refreshAppointments();
                      },
                      throwable::postValue
                  )
          );
        })
        .addOnFailureListener(throwable::postValue);
  }

  public void save(Appointment appointment) {
    throwable.setValue(null);
    GoogleSignInService.getInstance().refresh()
        .addOnSuccessListener((account) -> {
          pending.add(
              repository.save(account.getIdToken(), appointment)
                  .subscribe(
                      () -> {
                        this.appointment.postValue(null);
                        refreshUsers();
                        refreshStudents();
                        refreshTeachers();
                        refreshAppointments();
                      },
                      throwable::postValue
                  )
          );
        })
        .addOnFailureListener(throwable::postValue);
  }


  @OnLifecycleEvent(Event.ON_STOP)
  private void clearPending() {
    pending.clear();
  }


}
