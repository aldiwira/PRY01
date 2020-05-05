package ac.id.polinema.delaundry.model;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDetailModel {

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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getStatusString() {
        return (status) ? "DONE" : "PROGGRESS";
    }
}
