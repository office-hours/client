package edu.cnm.deepdive.officehours.controller;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.officehours.R;
import edu.cnm.deepdive.officehours.model.Student;
import edu.cnm.deepdive.officehours.model.User;
import edu.cnm.deepdive.officehours.viewmodel.MainViewModel;
import java.util.List;
import java.util.UUID;

public class UserEditFragment  extends DialogFragment implements TextWatcher {

  private static final String ID_KEY = "id";

  private UUID id;
  private AlertDialog dialog;
  private View root;
  private MainViewModel viewModel;
  private EditText userNicknameText;
  private AutoCompleteTextView userName;
  private List<User> users;
  private User user;

  public static void createAndShow(FragmentManager manager, UUID id) {
    UserEditFragment fragment = new UserEditFragment();
    Bundle args = new Bundle();
    args.putSerializable(ID_KEY, id);
    fragment.setArguments(args);
    fragment.show(manager, UserEditFragment.class.getName());
  }

  @SuppressWarnings("ConstantConditions")
  @SuppressLint("InflateParams")
  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    if (getArguments() != null) {
      id = (UUID) getArguments().getSerializable(ID_KEY);
    } else {
      id = null;
    }
    root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_user_edit, null, false);
    userNicknameText = root.findViewById(R.id.users_list);
    userNicknameText.setText("");
    userNicknameText.addTextChangedListener(this);
    dialog = new Builder(getContext())
        .setIcon(R.drawable.ic_message_black_24dp)
        .setView(root)
        .setPositiveButton(android.R.string.ok, (dlg, which) -> save())
        .setNegativeButton(android.R.string.cancel, (dlg, which) -> {})
        .create();
    dialog.setOnShowListener((dlg) -> checkSubmitConditions());
    return dialog;
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return root;
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    if (id != null) {
      viewModel.getUser().observe(getViewLifecycleOwner(), (user) -> {
        if (user != null) {
          this.user = user;
          userNicknameText.setText(user.getNickName());
          if (user.getNickName() != null) {
            userName.setText(user.getNickName());
          }
        }
      });
      viewModel.setUserId(id);
    } else {
      user = new User();
    }
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {}

  @Override
  public void afterTextChanged(Editable s) {
    checkSubmitConditions();
  }

  private void save() {
    user.setNickName(userNicknameText.getText().toString().trim());
    String name = userName.getText().toString().trim();
    if (!name.isEmpty()) {
      for ( User u : users) {
        if (name.equalsIgnoreCase(u.getNickName())) {
          user = u;
          break;
        }
      }
      if (user == null) {
        user = new User();
        user.setNickName(name);
      }
    }
    user.setNickName(name);
    viewModel.save(user);
  }

  private void checkSubmitConditions() {
    Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
    positive.setEnabled(!userNicknameText.getText().toString().trim().isEmpty());
  }


}
