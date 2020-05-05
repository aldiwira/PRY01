package ac.id.polinema.owner.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "detail_transaksi")
public class TransactionDetailModel implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private int id;

    @ColumnInfo(name = "noNota")
    @SerializedName("noNota")
    @Expose
    private int noNota;

    @ColumnInfo(name = "idHarga")
    @SerializedName("idHarga")
    @Expose
    private int idHarga;

    @ColumnInfo(name = "bobot")
    @SerializedName("bobot")
    @Expose
    private int bobot;

    @ColumnInfo(name = "status")
    @SerializedName("status")
    @Expose
    private boolean status;

    @ColumnInfo(name = "updatedAt")
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @ColumnInfo(name = "createdAt")
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @Ignore
    public TransactionDetailModel() {
    }

    public TransactionDetailModel(int id, int noNota, int idHarga, int bobot, boolean status,
                                  String updatedAt, String createdAt) {
        this.id = id;
        this.noNota = noNota;
        this.idHarga = idHarga;
        this.bobot = bobot;
        this.status = status;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoNota() {
        return noNota;
    }

    public void setNoNota(int noNota) {
        this.noNota = noNota;
    }

    public int getIdHarga() {
        return idHarga;
    }

    public void setIdHarga(int idHarga) {
        this.idHarga = idHarga;
    }

    public int getBobot() {
        return bobot;
    }

    public void setBobot(int bobot) {
        this.bobot = bobot;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public String getStatusString() {
        return (status) ? "Selesai" : "Proggress";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.noNota);
        dest.writeInt(this.idHarga);
        dest.writeInt(this.bobot);
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
        dest.writeString(this.updatedAt);
        dest.writeString(this.createdAt);
    }

    protected TransactionDetailModel(Parcel in) {
        this.id = in.readInt();
        this.noNota = in.readInt();
        this.idHarga = in.readInt();
        this.bobot = in.readInt();
        this.status = in.readByte() != 0;
        this.updatedAt = in.readString();
        this.createdAt = in.readString();
    }

    public static final Parcelable.Creator<TransactionDetailModel> CREATOR = new Parcelable.Creator<TransactionDetailModel>() {
        @Override
        public TransactionDetailModel createFromParcel(Parcel source) {
            return new TransactionDetailModel(source);
        }

        @Override
        public TransactionDetailModel[] newArray(int size) {
            return new TransactionDetailModel[size];
        }
    };
}
