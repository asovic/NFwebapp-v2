// Object in which orders are saved
// TODO uredi getters in setters

package si.asovic.backend.data.entity;


import si.asovic.backend.service.NewOrderListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@EntityListeners(NewOrderListener.class)
@Entity(name="purchases")
@Table(name="purchases")
public class OrderEntity implements Serializable {
    
	private static final long serialVersionUID = 8994124296617397940L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	@Column(name="status", columnDefinition="int default 0")
	private int status;
	@Column(columnDefinition="date")
	private LocalDate order_date;
	private String comment;
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="orderid")
	private List<BottleEntity> bottle;

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

	public LocalDate getOrder_date() {
		return order_date;
	}
	public void setOrder_date(LocalDate order_date) {
		this.order_date = order_date;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

    public List<BottleEntity> getBottle() {
    	return bottle;
    }
    public void setBottle(List<BottleEntity> bottle) {
    	this.bottle = bottle;
    }

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OrderEntity{" +
				"id=" + id +
				", username='" + username + '\'' +
				", status=" + status +
				", order_date=" + order_date +
				", comment='" + comment + '\'' +
				", bottle=" + bottle +
				'}';
	}
}
