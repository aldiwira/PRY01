package ac.id.polinema.delaundry.ui.create_account;

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
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import ac.id.polinema.delaundry.App;
import ac.id.polinema.delaundry.R;
import ac.id.polinema.delaundry.model.UserModel;
import ac.id.polinema.delaundry.repository.UserRepository;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static ac.id.polinema.delaundry.Utils.safeNavigate;
import static ac.id.polinema.delaundry.ui.create_account.CreateAccountFragmentDirections.createAccountToHome;

public class CreateAccountFragment extends Fragment implements Validator.ValidationListener {

    private UserRepository repository;
    private Validator validator;

    @NotEmpty(messageResId = R.string.warning_empty)
    @BindView(R.id.edt_name)
    EditText name;

    @NotEmpty(messageResId = R.string.warning_empty)
    @BindView(R.id.edt_address)
    EditText address;

    @NotEmpty(messageResId = R.string.warning_empty)
    @Password(min = 7, scheme = Password.Scheme.ANY)
    @BindView(R.id.edt_password)
    EditText password;

    @NotEmpty(messageResId = R.string.warning_empty)
    @ConfirmPassword(messageResId = R.string.warning_password)
    @BindView(R.id.edt_confirm_password)
    EditText confirmPassword;

    private String noHandphone;

    @OnClick(R.id.btn_register) void register() {
        validator.validate();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateAccountFragmentArgs args = CreateAccountFragmentArgs.fromBundle(getArguments());
        noHandphone = args.getNoHandphone();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_account, container, false);
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
        String name = this.name.getText().toString();
        String address = this.address.getText().toString();
        String password = this.password.getText().toString();
        UserModel model = new UserModel();
        model.setNoHp(noHandphone);
        model.setName(name);
        model.setAddress(address);
        model.setPassword(password);

        repository.createAccount(model).observe(this, result -> {
            if (result) {
                App.setSharedPreferences(App.IS_FIRST_TIME_LAUNCH, false);
                App.setSharedPreferences(App.NO_HANDPHONE, noHandphone);

                safeNavigate(getView(), createAccountToHome());
                getActivity().finish();
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
