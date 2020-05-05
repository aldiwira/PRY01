package ac.id.polinema.owner.ui.login;

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
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import ac.id.polinema.owner.R;
import ac.id.polinema.owner.repository.UserRepository;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static ac.id.polinema.owner.helper.Helper.safeNavigate;
import static ac.id.polinema.owner.ui.login.LoginFragmentDirections.loginToHome;

public class LoginFragment extends Fragment implements
        Validator.ValidationListener {

    private UserRepository repository;
    private Validator validator;

    @NotEmpty(messageResId = R.string.warning_empty)
    @BindView(R.id.edt_nohandphone)
    EditText noHandphone;

    @NotEmpty(messageResId = R.string.warning_empty)
    @Password(min = 7, scheme = Password.Scheme.ANY)
    @BindView(R.id.edt_password)
    EditText password;

    @OnClick(R.id.btn_login) void submit() {
        validator.validate();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
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
        String noHandphone = this.noHandphone.getText().toString();
        String password = this.password.getText().toString();
        repository.login(noHandphone, password).observe(this, result -> {
            if (result) {
                safeNavigate(getView(), loginToHome());
                getActivity().finish();
            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
