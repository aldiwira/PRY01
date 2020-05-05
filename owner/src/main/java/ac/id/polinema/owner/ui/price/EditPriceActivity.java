package ac.id.polinema.owner.ui.price;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import ac.id.polinema.owner.R;
import ac.id.polinema.owner.model.PriceModel;
import ac.id.polinema.owner.repository.Repository;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditPriceActivity extends AppCompatActivity implements Validator.ValidationListener {

    private PriceViewModel viewModel;
    private Validator validator;
    private PriceModel arg;

    @NotEmpty
    @BindView(R.id.edt_tipe)
    EditText edtTipe;

    @NotEmpty
    @BindView(R.id.edt_kelas)
    EditText edtKelas;

    @NotEmpty
    @BindView(R.id.edt_harga)
    EditText edtHarga;

    @OnClick(R.id.btn_save) void submit() {
        validator.validate();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_price);
        ButterKnife.bind(this);

        arg = EditPriceActivityArgs.fromBundle(getIntent().getExtras()).getDataPrice();
        if (arg != null) {
            fillData(arg);
        }

        viewModel = new ViewModelProvider(this).get(PriceViewModel.class);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    private void fillData(PriceModel arg) {
        edtTipe.setText(arg.getType());
        edtKelas.setText(arg.getKelas());
        edtHarga.setText(String.valueOf(arg.getPrice()));
    }

    @Override
    public void onValidationSucceeded() {
        if (arg != null) {
            arg.setType(edtTipe.getText().toString());
            arg.setKelas(edtKelas.getText().toString());
            arg.setPrice(Long.valueOf(edtHarga.getText().toString()));
            viewModel.update(arg, runWhenHaveDone("Update"));
        } else {
            arg = new PriceModel();
            arg.setType(edtTipe.getText().toString());
            arg.setKelas(edtKelas.getText().toString());
            arg.setPrice(Long.valueOf(edtHarga.getText().toString()));
            viewModel.save(arg, runWhenHaveDone("Menambahkan"));
        }
    }

    private Repository.RunWhenHaveDone<Boolean> runWhenHaveDone(String action) {
        return status -> {
            String message = (status) ? "Berhasil " : "Gagal ";
            message += action + " Data";
            Toast.makeText(EditPriceActivity.this, message, Toast.LENGTH_LONG)
                    .show();
            finish();
        };
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
