package ac.id.polinema.delaundry.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ac.id.polinema.delaundry.model.PriceModel;
import ac.id.polinema.delaundry.model.UserModel;

@Database(
        entities = {PriceModel.class, UserModel.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PriceDao priceDao();
    public abstract UserDao userDao();
}
