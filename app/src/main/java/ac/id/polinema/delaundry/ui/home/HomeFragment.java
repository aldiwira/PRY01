package ac.id.polinema.delaundry.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import ac.id.polinema.delaundry.R;
import ac.id.polinema.delaundry.model.PriceModel;
import ac.id.polinema.delaundry.ui.RecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static ac.id.polinema.delaundry.Utils.safeNavigate;

public class HomeFragment extends Fragment implements RecyclerViewAdapter.Bind<PriceModel> {

    private HomeViewModel viewModel;
    private RecyclerViewAdapter adapter;

    private List<PriceModel> prices = new ArrayList<>();

    @BindView(R.id.rv_home) RecyclerView recyclerView;
    @OnClick(R.id.floatingActionButton) void handleFAB() {
        safeNavigate(getView(), HomeFragmentDirections.actionToCreateTransactions());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new RecyclerViewAdapter<>(R.layout.item_price, prices, this);
        recyclerView.setAdapter(adapter);

        viewModel.getPrices().observe(getViewLifecycleOwner(), priceModels -> {
            adapter.setNewData(priceModels);

            for (PriceModel price : priceModels) {
                Log.d(this.getClass().getSimpleName(), "onActivityCreated: " + price.getIdHarga());
            }
        });
    }

    @Override
    public void bind(BaseViewHolder holder, PriceModel priceModel) {
        holder
                .setText(R.id.tv_tipe, priceModel.getType())
                .setText(R.id.tv_price, "Rp " + priceModel.getPrice().toString())
                .setText(R.id.tv_class, priceModel.getKelas());
    }
}
