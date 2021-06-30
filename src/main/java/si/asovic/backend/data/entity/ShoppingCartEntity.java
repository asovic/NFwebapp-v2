package si.asovic.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "shopping_cart")
public class ShoppingCartEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String flavour;
    private String nicotine;
    private Integer amount;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getFlavour() {
        return flavour;
    }
    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String getNicotine() {
        return nicotine;
    }
    public void setNicotine(String nicotine) {
        this.nicotine = nicotine;
    }

    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
