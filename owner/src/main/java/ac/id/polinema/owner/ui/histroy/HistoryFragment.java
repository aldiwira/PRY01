package ac.id.polinema.owner.ui.histroy;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import ac.id.polinema.owner.R;
import ac.id.polinema.owner.model.TransactionModel;
import ac.id.polinema.owner.ui.RecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static ac.id.polinema.owner.Utils.getDateReadable;

public class HistoryFragment extends Fragment
        implements RecyclerViewAdapter.Bind<TransactionModel> {

    private HistoryViewModel viewModel;

    @BindView(R.id.rv_history) RecyclerView recyclerView;

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
        RecyclerViewAdapter<TransactionModel> adapter = new RecyclerViewAdapter<>(
                R.layout.item_history, null, this);
        recyclerView.setAdapter(adapter);
        viewModel.observeDataHistory(getViewLifecycleOwner(), adapter::setNewData);
    }

    @Override
    public void bind(BaseViewHolder holder, TransactionModel model) {
        TextView tvStatus = holder.itemView.findViewById(R.id.tv_status);
        holder.setText(R.id.tv_created, getDateReadable(model.getCreatedAt()))
                .setText(R.id.tv_updated, model.getUser().getName())
                .setText(R.id.tv_status, model.getStatusPaymentString());

        int color = (model.getStatusPayment()) ? R.color.tint_green : R.color.tint_red;
        ColorStateList stateList = requireContext().getResources().getColorStateList(color);
        tvStatus.setBackgroundTintList(stateList);
    }
}
