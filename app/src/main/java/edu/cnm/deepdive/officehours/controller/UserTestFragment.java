package edu.cnm.deepdive.officehours.controller;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import edu.cnm.deepdive.officehours.R;
import edu.cnm.deepdive.officehours.view.OfficeHoursRecyclerAdapter;
import edu.cnm.deepdive.officehours.viewmodel.MainViewModel;

public class UserTestFragment extends Fragment {

  private MainViewModel viewModel;
  private RecyclerView userList;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_test, container, false);
    setupUI(root);
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
//    setupViewModel();
  }

  private void setupUI(View root) {
    userList = root.findViewById(R.id.users_list);
  }

//  @SuppressWarnings("ConstantConditions")
//  private void setupViewModel() {
//    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
//    viewModel.getUsers().observe(getViewLifecycleOwner(), (users) -> {
//      OfficeHoursRecyclerAdapter adapter =
//          new OfficeHoursRecyclerAdapter(getContext(), users, (pos, user) ->
//              UserEditFragment.createAndShow(getChildFragmentManager(), user.getId()));
//      userList.setAdapter(adapter);
//    });
//  }

}
