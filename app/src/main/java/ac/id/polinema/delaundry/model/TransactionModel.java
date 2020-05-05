package ac.id.polinema.delaundry.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionModel {

    @NonNull
    @ColumnInfo(name = "noNota")
    @SerializedName("noNota")
    @Expose
    private String noNota;

    @ColumnInfo(name = "pembayaran")
    @SerializedName("pembayaran")
    @Expose
    private String pay;

    @ColumnInfo(name = "statusPembayaran")
    @SerializedName("statusPembayaran")
    @Expose
    private boolean statusPayment;

    @ColumnInfo(name = "statusPengerjaan")
    @SerializedName("statusPengerjaan")
    @Expose
    private boolean proggress;

    @ColumnInfo(name = "methodeDelivery")
    @SerializedName("methodeDelivery")
    @Expose
    private boolean methodDelivery;

    @ColumnInfo(name = "updatedAt")
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @ColumnInfo(name = "createdAt")
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("detail_transactions")
    @Expose
    @Ignore
    private List<TransactionDetailModel> transactions;

    @Ignore
    public TransactionModel() {
    }

    public TransactionModel(@NonNull String noNota, String pay, boolean statusPayment,
                            boolean proggress, boolean methodDelivery, String updatedAt,
                            String createdAt, List<TransactionDetailModel> transactions) {
        this.noNota = noNota;
        this.pay = pay;
        this.statusPayment = statusPayment;
        this.proggress = proggress;
        this.methodDelivery = methodDelivery;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.transactions = transactions;
    }

    @NonNull
    public String getNoNota() {
        return noNota;
    }

    public void setNoNota(@NonNull String noNota) {
        this.noNota = noNota;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public boolean getStatusPayment() {
        return statusPayment;
    }

    public String getStatusPaymentString() {
        return statusPayment ? "LUNAS" : "BELUM LUNAS";
    }

    public void setStatusPayment(boolean statusPayment) {
        this.statusPayment = statusPayment;
    }

    public boolean isProggress() {
        return proggress;
    }

    public void setProggress(boolean proggress) {
        this.proggress = proggress;
    }

    public boolean isMethodDelivery() {
        return methodDelivery;
    }

    public void setMethodDelivery(boolean methodDelivery) {
        this.methodDelivery = methodDelivery;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<TransactionDetailModel> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDetailModel> transactions) {
        this.transactions = transactions;
    }
}
