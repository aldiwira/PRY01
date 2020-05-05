package ac.id.polinema.owner.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "transaksi")
public class TransactionModel implements Parcelable {

    @PrimaryKey
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

    @SerializedName("user")
    @Expose
    @Ignore
    private UserModel user;

    public TransactionModel() {
    }

    @Ignore
    public TransactionModel(@NonNull String noNota, String pay, boolean statusPayment,
                            boolean proggress, boolean methodDelivery, String updatedAt,
                            String createdAt) {
        this.noNota = noNota;
        this.pay = pay;
        this.statusPayment = statusPayment;
        this.proggress = proggress;
        this.methodDelivery = methodDelivery;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    @Ignore
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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public TransactionDetailModel[] getTransactionsArray() {
        TransactionDetailModel[] array = new TransactionDetailModel[transactions.size()];
        array = transactions.toArray(array);
        return array;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.noNota);
        dest.writeString(this.pay);
        dest.writeByte(this.statusPayment ? (byte) 1 : (byte) 0);
        dest.writeByte(this.proggress ? (byte) 1 : (byte) 0);
        dest.writeByte(this.methodDelivery ? (byte) 1 : (byte) 0);
        dest.writeString(this.updatedAt);
        dest.writeString(this.createdAt);
        dest.writeTypedList(this.transactions);
        dest.writeParcelable(this.user, flags);
    }

    protected TransactionModel(Parcel in) {
        this.noNota = in.readString();
        this.pay = in.readString();
        this.statusPayment = in.readByte() != 0;
        this.proggress = in.readByte() != 0;
        this.methodDelivery = in.readByte() != 0;
        this.updatedAt = in.readString();
        this.createdAt = in.readString();
        this.transactions = in.createTypedArrayList(TransactionDetailModel.CREATOR);
        this.user = in.readParcelable(UserModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<TransactionModel> CREATOR = new Parcelable.Creator<TransactionModel>() {
        @Override
        public TransactionModel createFromParcel(Parcel source) {
            return new TransactionModel(source);
        }

        @Override
        public TransactionModel[] newArray(int size) {
            return new TransactionModel[size];
        }
    };
}
