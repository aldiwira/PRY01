package ac.id.polinema.delaundry.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ac.id.polinema.delaundry.model.PriceModel;

@Dao
public interface PriceDao {

    @Insert
    void insert(PriceModel model);

    @Query("SELECT * FROM prices")
    List<PriceModel> getAll();

    @Query("SELECT idHarga FROM prices WHERE tipe IN (:types)")
    List<Integer> getIdBytype(List<String> types);

    @Query("DELETE FROM prices")
    void removeAll();
}
