package si.asovic.backend.model;

public class ShoppingCartItem {

    private String flavour;
    private String nic;
    private Integer amount;

    public String getFlavour() {
        return flavour;
    }
    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String getNic() {
        return nic;
    }
    public void setNic(String nic) {
        this.nic = nic;
    }

    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ShoppingCartItem{" +
                "flavour='" + flavour + '\'' +
                ", nic='" + nic + '\'' +
                ", amount=" + amount +
                '}';
    }
}
