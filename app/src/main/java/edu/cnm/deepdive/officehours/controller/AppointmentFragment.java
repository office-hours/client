/*
package edu.cnm.deepdive.officehours.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import edu.cnm.deepdive.officehours.R;
import edu.cnm.deepdive.officehours.viewmodel.MainViewModel;

public class AppointmentFragment extends Fragment {

  private MainViewModel viewModel;
  private RecyclerView appointmentsList;

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.*/
/* create layout resource*//*
, container, false);
    setupUI(root);
    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupViewModel();
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
      @Nullable ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    ContextMenuRecyclerView.ContextMenuInfo info =
        (ContextMenuRecyclerView.ContextMenuInfo) menuInfo;
    getActivity().getMenuInflater().inflate(R.menu.quote_context, menu);
    menu.findItem(R.id.edit_quote).setOnMenuItemClickListener(item -> {
      QuoteEditFragment.createAndShow(
          getChildFragmentManager(), ((Quote) info.getView().getTag()).getId());
      return true;
    });
    menu.findItem(R.id.delete_quote).setOnMenuItemClickListener(item -> {
      viewModel.remove((Quote) info.getView().getTag());
      return true;
    });
  }

  private void setupUI(View root) {
    quotesList = root.findViewById(R.id.quotes_list);
    registerForContextMenu(quotesList);
    FloatingActionButton addQuote = root.findViewById(R.id.add_quote);
    addQuote.setOnClickListener((v) ->
        QuoteEditFragment.createAndShow(getChildFragmentManager(), null));
  }

  @SuppressWarnings("ConstantConditions")
  private void setupViewModel() {
    viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    viewModel.getQuotes().observe(getViewLifecycleOwner(), (quotes) -> {
      QuoteRecyclerAdapter adapter =
          new QuoteRecyclerAdapter(getContext(), quotes, (pos, quote) ->
              QuoteEditFragment.createAndShow(getChildFragmentManager(), quote.getId()));
      quotesList.setAdapter(adapter);
    });
  }

}
*/
