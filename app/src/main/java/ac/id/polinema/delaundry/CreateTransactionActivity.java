package ac.id.polinema.delaundry;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ac.id.polinema.delaundry.model.DefaultValue;
import ac.id.polinema.delaundry.model.PriceList;
import ac.id.polinema.delaundry.model.PriceModel;
import ac.id.polinema.delaundry.repository.PriceRepository;
import ac.id.polinema.delaundry.repository.TransactionRepository;
import ac.id.polinema.delaundry.ui.RecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static ac.id.polinema.delaundry.Utils.showMessage;

public class CreateTransactionActivity extends AppCompatActivity implements
        RecyclerViewAdapter.Bind<PriceList> {

    private static String TAG = CreateTransactionActivity.class.getSimpleName();

    private RecyclerViewAdapter<PriceList> adapter;
    private List<String> type;

    @BindView(R.id.rv_transaction) RecyclerView recyclerView;
    @BindView(R.id.cg_methode) ChipGroup chipGroup;

    @OnClick(R.id.btn_order) void order() {
        TransactionRepository repository = new TransactionRepository(this);
        int methodChecked = chipGroup.getCheckedChipId();
        String method = (methodChecked == R.id.chip_datang_ke_toko) ?
                DefaultValue.MethodDelivery.DATANG_KE_TOKO : DefaultValue.MethodDelivery.ANTAR_JEMPUT;

        if (type.size() == 0 && methodChecked == -1) {
            Log.i(TAG, "order: User Error=Tidak memilih tipe dan method");
            showMessage(CreateTransactionActivity.this,
                    "Pilih Tipe Laundry dan Metode Delivery terlebih dahulu");
            return;
        }

        Log.i(TAG, "order: method delivery="+ method);
        Log.i(TAG, "order: list prices="+ type.toString());
        repository.order(type, method).observe(this, result -> {
            if (result) {
                Toast.makeText(CreateTransactionActivity.this,
                        "Berhasil Membuat Order Baru", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);
        ButterKnife.bind(this);
        getPrices();

        type = new ArrayList<>();
        adapter = new RecyclerViewAdapter<>(R.layout.item_order, null, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void bind(BaseViewHolder holder, PriceList priceList) {
        ChipGroup chipGroup = holder.findView(R.id.cg_type);
        holder.setText(R.id.tv_class, priceList.getKelas());

        for (PriceModel price: priceList.getPriceModels()) {
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.layout_chip,
                    chipGroup, false);
            chip.setText(price.getType());
            chip.setOnClickListener(click -> {
                String label = chip.getText().toString();
                Log.i(TAG, "bind: chip onClickListener; label checked="+ label);
                if (chip.isChecked()) {
                    type.add(label);
                } else {
                    type.remove(label);
                }
            });

            chipGroup.addView(chip);
        }
    }

    private void getPrices() {
        PriceRepository priceRepository = new PriceRepository(this);
        priceRepository.loadPrices(false).observe(this, prices -> {
            Set<String> kelas = new HashSet<>();
            for (PriceModel price: prices) kelas.add(price.getKelas());

            List<PriceList> priceLists = new ArrayList<>();
            for (String s: kelas) {
                List<PriceModel> tmp = new ArrayList<>();
                for (PriceModel price: prices) {
                    if (price.getKelas().equals(s)) {
                        tmp.add(price);
                    }
                }

                priceLists.add(new PriceList(s, tmp));
            }

            adapter.setNewData(priceLists);
        });
    }
}
