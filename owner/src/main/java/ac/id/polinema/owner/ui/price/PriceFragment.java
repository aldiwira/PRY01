package ac.id.polinema.owner.ui.price;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import ac.id.polinema.owner.App;
import ac.id.polinema.owner.MainActivity;
import ac.id.polinema.owner.R;
import ac.id.polinema.owner.model.PriceModel;
import ac.id.polinema.owner.ui.RecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.navigation.Navigation.findNavController;

public class PriceFragment extends Fragment implements RecyclerViewAdapter.Bind<PriceModel> {

    private final String TAG = PriceFragment.class.getSimpleName();

    private RecyclerViewAdapter<PriceModel> adapter;
    private PriceViewModel viewModel;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_price, container, false);
        viewModel = new ViewModelProvider(this).get(PriceViewModel.class);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new RecyclerViewAdapter<>(R.layout.item_price, null, this);
        adapter.addChildClickViewIds(R.id.iv_edit);
        adapter.setOnItemChildClickListener((_adapter, _view, position) -> {
            List<PriceModel> prices = _adapter.getData();
            PriceModel price = prices.get(position);

            Log.i(TAG, "onViewCreated: view id="+ _view.getId());
            Log.i(TAG, "onViewCreated: price id="+ price.getIdHarga());

            NavDirections direction = PriceFragmentDirections
                    .homeToEditPriceActivity().setDataPrice(price);
            findNavController(view).navigate(direction);
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.loadData();
        viewModel.liveData.observe(getViewLifecycleOwner(), prices -> adapter.setNewData(prices));
    }

    @Override
    public void bind(BaseViewHolder holder, PriceModel priceModel) {
        Log.i(TAG, "bind: id="+ priceModel.getIdHarga());
        holder.setText(R.id.tv_tipe, priceModel.getType())
                .setText(R.id.tv_kelas, priceModel.getKelas())
                .setText(R.id.tv_price, priceModel.getPrice().toString());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.account_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(getContext(), EditPriceActivity.class);
                NavDirections direction = PriceFragmentDirections
                        .homeToEditPriceActivity();
                findNavController(getView()).navigate(direction);
                break;

            case R.id.menu_logout:
                App.setSharedPreferences(App.NO_HANDPHONE, "");
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
                break;
        }

        return true;
    }
}
