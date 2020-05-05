package ac.id.polinema.delaundry.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user")
public class UserModel {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "idUser")
    @SerializedName("idUser")
    @Expose
    private String idUser;

    @ColumnInfo(name = "nama")
    @SerializedName("nama")
    @Expose
    private String name;

    @ColumnInfo(name = "noHp")
    @SerializedName("noHp")
    @Expose
    private String noHp;

    @ColumnInfo(name = "password")
    @SerializedName("password")
    @Expose
    private String password;

    @ColumnInfo(name = "alamat")
    @SerializedName("alamat")
    @Expose
    private String address;

    @Ignore
    public UserModel() {
    }

    public UserModel(@NonNull String idUser, String name, String noHp, String password,
                     String address) {
        this.idUser = idUser;
        this.name = name;
        this.noHp = noHp;
        this.password = password;
        this.address = address;
    }

    @NonNull
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(@NonNull String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
