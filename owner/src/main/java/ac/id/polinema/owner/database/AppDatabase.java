package ac.id.polinema.owner.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ac.id.polinema.owner.model.PriceModel;
import ac.id.polinema.owner.model.TransactionDetailModel;
import ac.id.polinema.owner.model.TransactionModel;
import ac.id.polinema.owner.model.UserModel;

@Database(entities = {PriceModel.class, UserModel.class, TransactionModel.class,
        TransactionDetailModel.class},
        version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PriceDao priceDao();
    public abstract UserDao userDao();

}
