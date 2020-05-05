package ac.id.polinema.delaundry.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ac.id.polinema.delaundry.model.UserModel;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    List<UserModel> get();

    @Insert
    void save(UserModel userModel);

    @Query("DELETE FROM user")
    void remove();
}
