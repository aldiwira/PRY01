package ac.id.polinema.owner.ui.histroy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ac.id.polinema.owner.R;
import butterknife.ButterKnife;

public class HistoryFragment extends Fragment {

    private HistoryViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.observeDataHistory(getViewLifecycleOwner(), transactions -> {});
    }
}
