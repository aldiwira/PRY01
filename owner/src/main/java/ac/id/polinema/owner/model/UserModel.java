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

@Entity(tableName = "user")
public class UserModel implements Parcelable {

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
    private String noHandphone;

    @ColumnInfo(name = "password")
    @SerializedName("password")
    @Expose
    private String password;

    @ColumnInfo(name = "alamat")
    @SerializedName("alamat")
    @Expose
    private String address;

    @ColumnInfo(name = "createdAt")
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @ColumnInfo(name = "updateAt")
    @SerializedName("updateAt")
    @Expose
    private String updateAt;

    @Ignore
    public UserModel() {
    }

    public UserModel(@NonNull String idUser, String name, String noHandphone, String password,
                     String address, String createdAt, String updateAt) {
        this.idUser = idUser;
        this.name = name;
        this.noHandphone = noHandphone;
        this.password = password;
        this.address = address;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
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

    public String getNoHandphone() {
        return noHandphone;
    }

    public void setNoHandphone(String noHandphone) {
        this.noHandphone = noHandphone;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idUser);
        dest.writeString(this.name);
        dest.writeString(this.noHandphone);
        dest.writeString(this.password);
        dest.writeString(this.address);
        dest.writeString(this.createdAt);
        dest.writeString(this.updateAt);
    }

    protected UserModel(Parcel in) {
        this.idUser = in.readString();
        this.name = in.readString();
        this.noHandphone = in.readString();
        this.password = in.readString();
        this.address = in.readString();
        this.createdAt = in.readString();
        this.updateAt = in.readString();
    }

    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
