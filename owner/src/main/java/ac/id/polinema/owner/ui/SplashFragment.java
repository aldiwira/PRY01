package ac.id.polinema.owner.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ac.id.polinema.owner.App;
import ac.id.polinema.owner.R;

import static ac.id.polinema.owner.helper.Helper.safeNavigate;
import static ac.id.polinema.owner.ui.SplashFragmentDirections.splashToHome;
import static ac.id.polinema.owner.ui.SplashFragmentDirections.toLoginFragment;

public class SplashFragment extends Fragment {

    private Runnable runAsync = () -> {
        if (App.isLogIn()) {
            safeNavigate(getView(), splashToHome());
            getActivity().finish();
        } else {
            safeNavigate(getView(), toLoginFragment());
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Handler().postDelayed(runAsync, 3000);
    }
}
