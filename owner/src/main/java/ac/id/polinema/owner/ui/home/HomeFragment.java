package ac.id.polinema.owner.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
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

import ac.id.polinema.owner.R;
import ac.id.polinema.owner.Utils;
import ac.id.polinema.owner.model.TransactionModel;
import ac.id.polinema.owner.model.UserModel;
import ac.id.polinema.owner.ui.RecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.navigation.Navigation.findNavController;

public class HomeFragment extends Fragment implements RecyclerViewAdapter.Bind<TransactionModel> {

    private static String TAG = HomeFragment.class.getSimpleName();

    private HomeViewModel viewModel;
    private RecyclerViewAdapter<TransactionModel> adapter;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Utils.setActionBarTitleAndSub(getActivity(), "De Laundry", "");
        adapter = new RecyclerViewAdapter<>(R.layout.item_new_order, null, this);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            TransactionModel data = (TransactionModel) adapter.getData().get(position);
            NavDirections direction = HomeFragmentDirections.homeToAcceptOrder(data);
            findNavController(getView()).navigate(direction);
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.liveData.observe(getViewLifecycleOwner(), transactions ->
                adapter.setNewData(transactions)
        );
    }

    @Override
    public void bind(BaseViewHolder holder, TransactionModel o) {
        UserModel user =  o.getUser();
        holder.setText(R.id.tv_name, user.getName())
              .setText(R.id.tv_address, user.getAddress());
    }
}
