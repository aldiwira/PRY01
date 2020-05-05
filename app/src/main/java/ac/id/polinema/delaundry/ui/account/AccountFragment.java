package ac.id.polinema.delaundry.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ac.id.polinema.delaundry.App;
import ac.id.polinema.delaundry.MainActivity;
import ac.id.polinema.delaundry.R;
import ac.id.polinema.delaundry.model.UserModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountFragment extends Fragment {

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_nohandphone)
    TextView tvNoHandphone;

    @BindView(R.id.tv_address)
    TextView tvAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        UserModel user = App.getUserModel();
        if (user != null) {
            tvName.setText(user.getName());
            tvNoHandphone.setText(user.getNoHp());
            tvAddress.setText(user.getAddress());
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.account_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                App.setSharedPreferences(App.NO_HANDPHONE, "");
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
                break;
        }
        return true;
    }
}
