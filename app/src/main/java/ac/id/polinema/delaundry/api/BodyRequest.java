package ac.id.polinema.delaundry.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyRequest {

    public static class Order {

        @SerializedName("idUser")
        private String idUser;

        @SerializedName("methodeDelivery")
        private String methodDelivery;

        @SerializedName("orders")
        private List<Integer> idPrices;

        public Order(){
        }

        public Order(String idUser, String methodDelivery, List<Integer> idPrices) {
            this.idUser = idUser;
            this.methodDelivery = methodDelivery;
            this.idPrices = idPrices;
        }

        public String getIdUser() {
            return idUser;
        }

        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }

        public String getMethodDelivery() {
            return methodDelivery;
        }

        public void setMethodDelivery(String methodDelivery) {
            this.methodDelivery = methodDelivery;
        }

        public List<Integer> getIdPrices() {
            return idPrices;
        }

        public void setIdPrices(List<Integer> idPrices) {
            this.idPrices = idPrices;
        }
    }
    /* End Class Order */
}
