package ac.id.polinema.owner.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import ac.id.polinema.owner.R;
import ac.id.polinema.owner.Utils;
import ac.id.polinema.owner.api.BodyRequest;
import ac.id.polinema.owner.model.PriceModel;
import ac.id.polinema.owner.model.TransactionDetailModel;
import ac.id.polinema.owner.model.TransactionModel;
import ac.id.polinema.owner.ui.RecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static ac.id.polinema.owner.Utils.showMessage;
import static androidx.navigation.Navigation.findNavController;

public class AcceptOrderFragment extends Fragment implements
        RecyclerViewAdapter.Bind<TransactionDetailModel> {

    private RecyclerViewAdapter<TransactionDetailModel> adapter;
    private List<BodyRequest.Weight> weights;
    private AcceptOrderFragmentArgs args;
    private HomeViewModel viewModel;

    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    @OnClick(R.id.btn_save) void save() {
        boolean isAnyEmpty = false;
        for (int i=0; i < adapter.getData().size(); i++) {
            View view = recyclerView.getChildAt(i);
            EditText editText = view.findViewById(R.id.edt_bobot);
            String userInput  = editText.getText().toString();
            if (!TextUtils.isEmpty(userInput)) {
                int weight = Integer.parseInt(userInput);
                weights.get(i).setWeight(weight);
            } else {
                isAnyEmpty = true;
            }
        }
        if (!isAnyEmpty) {
            String idBill = args.getData().getNoNota();
            viewModel.acceptOrder(idBill, weights, result -> {
                if (result) {
                    findNavController(getView()).popBackStack();
                    Toast.makeText(getContext(),
                            "Berhasil Menerima Order.", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    showMessage(getContext(), "Terjadi kesalahan pada aplikasi.");
                }
            });
        } else {
            showMessage(getContext(), "Mohon untuk dilengkapi.");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = AcceptOrderFragmentArgs.fromBundle(getArguments());
        weights = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accept_order, container, false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TransactionModel data = args.getData();
        Utils.setActionBarTitleAndSub(getActivity(), data.getUser().getName(),
                Utils.getDateTimeReadable(data.getCreatedAt()));
        List<TransactionDetailModel> listData = data.getTransactions();
        adapter = new RecyclerViewAdapter<>(R.layout.item_accept_order, listData, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void bind(BaseViewHolder holder, TransactionDetailModel model) {
        viewModel.loadDataPrice().observe(getViewLifecycleOwner(), prices -> {
            for (PriceModel price : prices) {
                if (price.getIdHarga() == model.getIdHarga()) {
                    weights.add(new BodyRequest.Weight(model.getIdHarga()));
                    String text = price.getKelas() + " / " + price.getType();
                    holder.setText(R.id.tv_kind, text);
                }
            }
        });
    }
}
