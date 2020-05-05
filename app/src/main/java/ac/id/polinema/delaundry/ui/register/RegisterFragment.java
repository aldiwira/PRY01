package ac.id.polinema.delaundry.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import ac.id.polinema.delaundry.R;
import ac.id.polinema.delaundry.repository.UserRepository;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static ac.id.polinema.delaundry.Utils.safeNavigate;
import static ac.id.polinema.delaundry.ui.register.RegisterFragmentDirections.registerToCreateAccount;

public class RegisterFragment extends Fragment implements Validator.ValidationListener {

    private UserRepository repository;
    private Validator validator;

    @NotEmpty(messageResId = R.string.warning_empty)
    @BindView(R.id.edt_nohandphone)
    EditText noHandphone;

    @OnClick(R.id.btn_register) void submit() {
        validator.validate();
    }

    @OnClick(R.id.tv_have_account)
    void login() {
        safeNavigate(getView(), RegisterFragmentDirections.registerToLogin());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        repository = new UserRepository(getContext());
        validator = new Validator(this);
        validator.setValidationListener(this);
        ButterKnife.bind(this, getView());
    }

    @Override
    public void onValidationSucceeded() {
        final String noHandphone = this.noHandphone.getText().toString();
        repository.register(noHandphone).observe(this, result -> {
            if (result) {
                safeNavigate(getView(), registerToCreateAccount(noHandphone));
            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
