package edu.cnm.deepdive.officehours;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.officehours.service.GoogleSignInService;
import edu.cnm.deepdive.officehours.service.OfficeHoursRepository;
import edu.cnm.deepdive.officehours.service.OfficeHoursService;

public class OfficeHoursApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleSignInService.setContext(this);
    Stetho.initializeWithDefaults(this);
  }
}
