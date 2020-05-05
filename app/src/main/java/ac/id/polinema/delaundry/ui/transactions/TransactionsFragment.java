package ac.id.polinema.delaundry.ui.transactions;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
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

public class TransactionsFragment extends Fragment implements
        RecyclerViewAdapter.Bind<TransactionModel> {

    private static final String TAG = TransactionsFragment.class.getSimpleName();

    private TransactionsViewModel viewModel;

    @BindView(R.id.rv_transaction) RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerViewAdapter<TransactionModel> adapter = new RecyclerViewAdapter<>(
                R.layout.item_container_nested_recyclerview, null, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        viewModel.getActive()
                 .observe(getViewLifecycleOwner(), adapter::setNewData);
    }

    @Override
    public void bind(BaseViewHolder holder, TransactionModel model) {
        Log.d(TAG, "bind: detail transactions="+ model.getTransactions());
        Log.d(TAG, "bind: status="+ model.getNoNota());

        holder.setText(R.id.title, model.getNoNota());
        RecyclerView childRecyclerView = holder.itemView.findViewById(R.id.rv_parent);
        RecyclerViewAdapter<TransactionDetailModel> adapter = new RecyclerViewAdapter<>(
                R.layout.item_transactions, model.getTransactions(), this::bind);

        childRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        childRecyclerView.setAdapter(adapter);
    }

    private void bind(BaseViewHolder holder, TransactionDetailModel model) {
        TextView tvStatus = holder.itemView.findViewById(R.id.tv_status);
        holder.setText(R.id.tv_weight, model.getBobot() + " Kg")
              .setText(R.id.tv_status, model.getStatusString());

        int color = !model.getStatus() ? R.color.tint_green : R.color.tint_red;
        ColorStateList stateList = requireContext().getResources().getColorStateList(color);
        tvStatus.setBackgroundTintList(stateList);
        viewModel.fetchDataPrices().observe(getViewLifecycleOwner(), prices -> {
            for (PriceModel price : prices) {
                if (price.getIdHarga() == model.getIdHarga()) {
                    holder.setText(R.id.tv_tipe, price.getKelas() + " / " + price.getType());
                }
            }
        });
    }
}
