package ac.id.polinema.delaundry.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "prices")
public class PriceModel {

    @SerializedName("idHarga")
    @Expose
    @PrimaryKey
    @ColumnInfo(name = "idHarga")
    private int idHarga;

    @SerializedName("kelas")
    @Expose
    @ColumnInfo(name = "kelas")
    private String kelas;

    @SerializedName("tipe")
    @Expose
    @ColumnInfo(name = "tipe")
    private String type;

    @SerializedName("harga")
    @Expose
    @ColumnInfo(name = "harga")
    private Long price;

    @ColumnInfo(name = "createdAt")
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @ColumnInfo(name = "updateAt")
    @SerializedName("updateAt")
    @Expose
    private String updateAt;

    @Ignore
    public PriceModel() {
    }

    public PriceModel(int idHarga, String kelas, String type, Long price, String createdAt,
                      String updateAt) {
        this.idHarga = idHarga;
        this.kelas = kelas;
        this.type = type;
        this.price = price;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    public int getIdHarga() {
        return idHarga;
    }

    public void setIdHarga(int idHarga) {
        this.idHarga = idHarga;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
