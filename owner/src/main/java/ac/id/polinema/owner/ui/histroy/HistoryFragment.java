package ac.id.polinema.owner.ui.histroy;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import ac.id.polinema.owner.R;
import ac.id.polinema.owner.model.PriceModel;
import ac.id.polinema.owner.model.TransactionDetailModel;
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
                R.layout.item_container_history, null, this);
        adapter.addChildClickViewIds(R.id.iv_expand, R.id.tv_status);
        adapter.setOnItemChildClickListener(((_adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_status:
                    break;

                case R.id.iv_expand:
                    RecyclerView rv = (RecyclerView) _adapter.getViewByPosition(position, R.id.rv_parent);
                    Drawable drawable;
                    if (rv.getVisibility() == View.GONE) {
                        rv.setVisibility(View.VISIBLE);
                        drawable = getResources().getDrawable(R.drawable.ic_expand_less_24dp);
                    } else {
                        rv.setVisibility(View.GONE);
                        drawable = getResources().getDrawable(R.drawable.ic_expand_more_24dp);
                    }
                    ((ImageView) view).setImageDrawable(drawable);
                    break;
            }
        }));

        recyclerView.setAdapter(adapter);
        viewModel.observeDataHistory(getViewLifecycleOwner(), adapter::setNewData);
    }

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
                R.color.tint_red : R.color.tint_green ;
        ColorStateList stateList = requireContext().getResources().getColorStateList(color);
        tvStatus.setBackgroundTintList(stateList);
    }

    private void bind(BaseViewHolder holder, TransactionDetailModel model) {
        holder.setText(R.id.tv_weight, model.getBobot() + " Kg");
        viewModel.fetchDataPrices().observe(getViewLifecycleOwner(), prices -> {
            for (PriceModel price : prices) {
                if (price.getIdHarga() == model.getIdHarga()) {
                    holder.setText(R.id.tv_tipe, price.getKelas() + " / " + price.getType());
                }
            }
        });
    }
}
