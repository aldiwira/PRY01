package ac.id.polinema.owner.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyRequest {

    public static class Weight {
        @SerializedName("id") int id;
        @SerializedName("weight") int weight;

        public Weight(int id) {
            this.id = id;
        }

        public Weight(int id, int weight) {
            this.id = id;
            this.weight = weight;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }

    public static class Order {

        @SerializedName("idBill")
        private String idBill;

        @SerializedName("weights")
        private List<Weight> weights;

        public Order(){
        }

        public Order(String idBill, List<Weight> weights) {
            this.idBill = idBill;
            this.weights = weights;
        }

        public String getIdBill() {
            return idBill;
        }

        public void setIdBill(String idBill) {
            this.idBill = idBill;
        }

        public List<Weight> getWeights() {
            return weights;
        }

        public void setWeights(List<Weight> weights) {
            this.weights = weights;
        }
    }
    /* End Class Order */
}
