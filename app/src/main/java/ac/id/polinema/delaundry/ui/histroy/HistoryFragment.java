package ac.id.polinema.delaundry.ui.histroy;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import ac.id.polinema.delaundry.R;
import ac.id.polinema.delaundry.model.PriceModel;
import ac.id.polinema.delaundry.model.TransactionDetailModel;
import ac.id.polinema.delaundry.model.TransactionModel;
import ac.id.polinema.delaundry.ui.RecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static ac.id.polinema.delaundry.Utils.getDateReadable;

public class HistoryFragment extends Fragment
        implements RecyclerViewAdapter.Bind<TransactionModel> {

    private HistoryViewModel viewModel;

    @BindView(R.id.rv_history) RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter<>(
                R.layout.item_container_history, null, this);
        adapter.setOnItemClickListener(((_adapter, view, position) -> {
            RecyclerView rv = view.findViewById(R.id.rv_parent);
            if (rv.getVisibility() == View.GONE) {
                rv.setVisibility(View.VISIBLE);
            }
        }));

        recyclerView.setAdapter(adapter);
        viewModel.getHistory().observe(getViewLifecycleOwner(), adapter::setNewData);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void bind(BaseViewHolder holder, TransactionModel model) {
        TextView tvStatus = holder.itemView.findViewById(R.id.tv_status);
        String update = getDateReadable(model.getCreatedAt()) + " ~ " +
                getDateReadable(model.getUpdatedAt());
        holder.setText(R.id.tv_nota, model.getNoNota())
              .setText(R.id.tv_updated,update )
              .setText(R.id.tv_status, model.getStatusPaymentString());

        RecyclerViewAdapter<TransactionDetailModel> adapter = new RecyclerViewAdapter<>(
                R.layout.item_history, model.getTransactions(), this::bind);
        RecyclerView childRecyclerView = holder.itemView.findViewById(R.id.rv_parent);
        childRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        childRecyclerView.setAdapter(adapter);

        int color = (model.getStatusPayment()) ?
                R.color.tint_green : R.color.tint_red;
        ColorStateList stateList = requireContext().getResources().getColorStateList(color);
        tvStatus.setBackgroundTintList(stateList);
    }

    private void bind(BaseViewHolder holder, TransactionDetailModel model) {
        holder.setText(R.id.tv_weight, String.valueOf(model.getBobot()) + " Kg");
        viewModel.fetchDataPrices().observe(getViewLifecycleOwner(), prices -> {
            for (PriceModel price : prices) {
                if (price.getIdHarga() == model.getIdHarga()) {
                    holder.setText(R.id.tv_tipe, price.getKelas() + " / " + price.getType());
                }
            }
        });
    }
}
