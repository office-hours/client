package edu.cnm.deepdive.officehours.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.officehours.R;
import edu.cnm.deepdive.officehours.model.Student;
import edu.cnm.deepdive.officehours.model.User;
import edu.cnm.deepdive.officehours.view.OfficeHoursRecyclerAdapter.Holder;
import java.util.List;

public class OfficeHoursRecyclerAdapter extends RecyclerView.Adapter<Holder> {


  private final Context context;
  private final List<User> users;
  private final OnUserClickListener listener;

  public OfficeHoursRecyclerAdapter(Context context, List<User> users, OnUserClickListener listener) {
    this.context = context;
    this.users = users;
    this.listener = listener;

  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View root = LayoutInflater.from(context).inflate(R.layout.item_user_test, parent, false);
    return new Holder(root);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind(position, users.get(position));
  }

  @Override
  public int getItemCount() {
    return users.size();
  }

  @FunctionalInterface
  public interface OnUserClickListener {

    void onUserClick(int position, User user);

  }

  class Holder extends RecyclerView.ViewHolder {

    private final TextView userInfo;

    private Holder(View root) {
      super(root);
      userInfo = root.findViewById(R.id.user_item);
    }

    private void bind(int position, User user) {
      userInfo.setText(context.getString(R.string.user_format, user.getNickName()));
      Student student = user.getStudent();
      String studentName = (student != null) ? student.getStudentName() : null;
      String attribution = (studentName != null)
          ? context.getString(R.string.attribution_format, studentName)
          : null;
      itemView.setOnClickListener((v) -> listener.onUserClick(getAdapterPosition(), user));
      itemView.setTag(user);
    }

  }

}
