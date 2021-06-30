// Object in which orders are saved
// TODO uredi getters in setters

package si.asovic.backend.data.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"bottle"
})

//@EntityListeners(NewOrderListener.class)
@Entity(name="purchases")
@Table(name="purchases")
public class OrderEntity implements Serializable{
    
	private static final long serialVersionUID = 8994124296617397940L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="username")
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name="status", columnDefinition="int default 0")
	private int status;
	
	@Column(columnDefinition="date")
	private LocalDate order_date;

	public LocalDate getOrder_date() {
		return order_date;
	}

	public void setOrder_date(LocalDate order_date) {
		this.order_date = order_date;
	}

	@Column(name = "comment")
	private String comment;

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="orderid")
    @JsonProperty("bottle")
    private List<BottleEntity> bottle;

	@Transient
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("bottle")
    public List<BottleEntity> getBottle() {
    	return bottle;
    }

    @JsonProperty("bottle")
    public void setBottle(List<BottleEntity> bottle) {
    	this.bottle = bottle;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
    	return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
    	this.additionalProperties.put(name, value);
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
