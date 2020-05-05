package ac.id.polinema.owner.ui.transactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ac.id.polinema.owner.R;
import ac.id.polinema.owner.model.PriceModel;
import ac.id.polinema.owner.model.TransactionDetailModel;
import ac.id.polinema.owner.ui.RecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static ac.id.polinema.owner.Utils.getDateReadable;
import static ac.id.polinema.owner.Utils.setActionBarTitleAndSub;
import static androidx.navigation.Navigation.findNavController;

public class DetailTransactionFragment extends Fragment implements
        RecyclerViewAdapter.Bind<TransactionDetailModel>{

    private TransactionsViewModel viewModel;
    private RecyclerViewAdapter<TransactionDetailModel> adapter;
    private DetailTransactionFragmentArgs args;

    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = DetailTransactionFragmentArgs.fromBundle(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_detail_transaction, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setActionBarTitleAndSub(getActivity(), args.getTransactionBy(),
                getDateReadable(args.getTransactionCreated()));

        List<TransactionDetailModel> list = new ArrayList<>();
        list.addAll(Arrays.asList(args.getData()));
        adapter = new RecyclerViewAdapter<>(R.layout.item_detail_transaction, list, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void bind(BaseViewHolder holder, TransactionDetailModel transactionDetailModel) {
        holder.setText(R.id.tv_weight, transactionDetailModel.getBobot()+ " Kg");
        CheckBox checkBox = holder.findView(R.id.cb_status);
        checkBox.setChecked(transactionDetailModel.isStatus());
        checkBox.setText(transactionDetailModel.getStatusString());
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                transactionDetailModel.setStatus(isChecked);
                checkBox.setText(transactionDetailModel.getStatusString());
                checkBox.setEnabled(false);
                viewModel.updateItem(transactionDetailModel.getId());
            }
        });
        if (transactionDetailModel.isStatus()) {
            checkBox.setEnabled(false);
        }
        viewModel.fetchDataPrices().observe(getViewLifecycleOwner(), prices -> {
            for (PriceModel price : prices) {
                if (price.getIdHarga() == transactionDetailModel.getIdHarga()) {
                    holder.setText(R.id.tv_tipe, price.getKelas() + " / " + price.getType());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            findNavController(getView()).popBackStack();
        }

        return super.onOptionsItemSelected(item);
    }
}
