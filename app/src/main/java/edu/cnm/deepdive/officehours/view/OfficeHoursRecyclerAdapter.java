package edu.cnm.deepdive.officehours.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.officehours.R;
import edu.cnm.deepdive.officehours.model.Appointment;
import edu.cnm.deepdive.officehours.model.Student;
import edu.cnm.deepdive.officehours.model.Teacher;
import edu.cnm.deepdive.officehours.model.User;
import edu.cnm.deepdive.officehours.view.OfficeHoursRecyclerAdapter.Holder;
import java.util.List;

public class OfficeHoursRecyclerAdapter extends RecyclerView.Adapter<Holder> {


  private final Context context;
  private final List<Appointment> appointments;
  private final OnUserClickListener listener;

  public OfficeHoursRecyclerAdapter(Context context, List<Appointment> appointments, OnUserClickListener listener) {
    this.context = context;
    this.appointments = appointments;
    this.listener = listener;

  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View root = LayoutInflater.from(context).inflate(R.layout.item_appointment, parent, false);
    return new Holder(root);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position, appointments.get(position));
  }

  @Override
  public int getItemCount() {
    return appointments.size();
  }

  @FunctionalInterface
  public interface OnUserClickListener {

    void onUserClick(int position, Appointment appointment);

  }

  class Holder extends RecyclerView.ViewHolder {

    private final TextView appointmentInfo;

    private Holder(View root) {
      super(root);
      appointmentInfo = root.findViewById(R.id.time_range);
    }

    private void bind(int position, Appointment appointment) {
      appointmentInfo.setText(context.getString(R.string.user_format, appointment.getStatus()));
      Student student = appointment.getStudent();
      String studentName = (student != null) ? student.getStudentName() : null;
      String attributionS = (studentName != null)
          ? context.getString(R.string.attribution_format, studentName)
          : null;
      Teacher teacher = appointment.getTeacher();
      String teacherName = (teacher != null) ? teacher.getTeacherName() : null;
      String attributionT = (teacherName != null)
          ? context.getString(R.string.attribution_format, studentName)
          : null;

      itemView.setOnClickListener((v) -> listener.onUserClick(getAdapterPosition(), appointment));
      itemView.setTag(appointment);
    }

  }

}
