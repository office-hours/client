package edu.cnm.deepdive.officehours.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.officehours.BuildConfig;
import edu.cnm.deepdive.officehours.model.Appointment;
import edu.cnm.deepdive.officehours.model.Student;
import edu.cnm.deepdive.officehours.model.Teacher;
import edu.cnm.deepdive.officehours.model.User;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OfficeHoursService {

  @GET("users")
  Single<List<User>> getAllUsers(@Header("Authorization") String oauthHeader);

  @GET("users/{id}")
  Single<User> getUser(@Header("Authorization") String oauthHeader, @Path("id") UUID id);

  @PUT("users/{id}")
  Single<User> putUser(
      @Header("Authorization") String oauthHeader, @Body User user, @Path("id") UUID id);

  @GET("students")
  Single<List<Student>> getAllStudents(@Header("Authorization") String oauthHeader);

  @GET("students/{id}")
  Single<Student> getStudent(@Header("Authorization") String oauthHeader, @Path("id") UUID id);

  @GET("teachers")
  Single<List<Teacher>> getAllTeachers(@Header("Authorization") String oauthHeader);

  @GET("users/{id}")
  Single<Teacher> getTeacher(@Header("Authorization") String oauthHeader, @Path("id") UUID id);

  @GET("appointments")
  Single<List<Appointment>> getAllAppointments(@Header("Authorization") String oauthHeader);

  @GET("appointments/{id}")
  Single<Appointment> getAppointment(@Header("Authorization") String oauthHeader, @Path("id") UUID id);

  @POST("appointments")
  Single<Appointment> postAppointment(@Header("Authorization") String oauthHeader, @Body Appointment appointment);

  // TODO Add method that allows to get appointments by a range of dates.


  static OfficeHoursService getInstance(){
    return InstanceHolder.INSTANCE;
  }

  class InstanceHolder {

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final OfficeHoursService INSTANCE;

    static {
      Gson gson  = new GsonBuilder()
          .setDateFormat(TIMESTAMP_FORMAT)
          .excludeFieldsWithoutExposeAnnotation()
          .create();
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(Level.BODY);
      OkHttpClient client = new OkHttpClient.Builder()
          .readTimeout(60, TimeUnit.SECONDS)
          .addInterceptor(interceptor)
          .build();
      Retrofit retrofit = new Retrofit.Builder()
          .addConverterFactory(GsonConverterFactory.create(gson))
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .client(client)
          .baseUrl(BuildConfig.BASE_URL)
          .build();
      INSTANCE = retrofit.create(OfficeHoursService.class);
    }

  }

}
