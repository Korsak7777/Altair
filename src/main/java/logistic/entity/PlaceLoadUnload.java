package logistic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;

@Entity
@Table(name = "PLACE_LOAD_UNLOAD",
		uniqueConstraints = @UniqueConstraint(columnNames = {"USER_NAME", "ADRESS_NAME"}) )
public class PlaceLoadUnload implements Serializable {

	private static final long serialVersionUID = 5857038542259301400L;

	@Id
	@Column(name = "ADRESS_ID", length = 256, nullable = false)
	private String adressId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_NAME", nullable = false, 
		foreignKey = @ForeignKey(name = "PLACE_LOAD_UNLOAD_FK_ACCOUNT"))
	private Account userName;
	
	@Column(name = "ADRESS_NAME", length = 256, nullable = false)
	private String adressName;
	
	@Column(name = "CITY", length = 128, nullable = false)	
	private String city;

	@Column(name = "FULL_ADRESS", length = 256, nullable = true)
	private String fullAdress;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTACT_ON_PLACE", nullable = false, 
		foreignKey = @ForeignKey(name = "PLACE_LOAD_UNLOAD_FK_CONTACT"))
	private ContactFace contactOnPlace;
	
	
	public String getAdressId() {
		return adressId;
	}
	public void setAdressId(String adressId) {
		this.adressId = adressId;
	}
	public Account getUserName() {
		return userName;
	}
	public void setUserName(Account userName) {
		this.userName = userName;
	}
	public String getAdressName() {
		return adressName;
	}
	public void setAdressName(String adressName) {
		this.adressName = adressName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getFullAdress() {
		return fullAdress;
	}
	public void setFullAdress(String fullAdress) {
		this.fullAdress = fullAdress;
	}
	public ContactFace getContactOnPlace() {
		return contactOnPlace;
	}
	public void setContactOnPlace(ContactFace contactOnPlace) {
		System.out.println("setContactOnPlace entity " + contactOnPlace);
		this.contactOnPlace = contactOnPlace;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
