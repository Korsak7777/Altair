package logistic.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.threeten.bp.LocalDate;

@Entity
@Table(name = "REQUEST_DETAILS")
public class RequestDetails implements Serializable {

	private static final long serialVersionUID = -7625270267926821937L;
	
	@Id
	@Column(name = "REQUEST_ID", length = 256, nullable = false)
	private String requestId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTACT_FACE", nullable = false, 
		foreignKey = @ForeignKey(name = "CONTACT_FACE_FK_CONTACT"))
	private ContactFace contactFace;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLACE_LOAD_UNLOAD", nullable = false, 
		foreignKey = @ForeignKey(name = "PLACE_LOAD_UNLOAD_FK_CONTACT"))
	private PlaceLoadUnload placeLoadUnload;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REQUEST", nullable = false, 
		foreignKey = @ForeignKey(name = "REQUESTD_FK_REQUEST"))
	private Request request;
	
	@Column(name = "LOADING_UNLOADING", nullable = false)
	private boolean loadingUnloading;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DATE_OF_EVENT", nullable = false)
	private String dateOfEvent;
	
	@Column(name = "MASS", nullable = false)
	private int mass;
	
	@Column(name = "PALLET_QUANTITY", nullable = false)
	private int palletQuantity;
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public ContactFace getContactFace() {
		return contactFace;
	}
	public void setContactFace(ContactFace contactFace) {
		this.contactFace = contactFace;
	}
	public PlaceLoadUnload getPlaceLoadUnload() {
		return placeLoadUnload;
	}
	public void setPlaceLoadUnload(PlaceLoadUnload placeLoadUnload) {
		this.placeLoadUnload = placeLoadUnload;
	}
	public boolean isLoadingUnloading() {
		return loadingUnloading;
	}
	public void setLoadingUnloading(boolean loadingUnloading) {
		this.loadingUnloading = loadingUnloading;
	}
	public String getDateOfEvent() {
		return dateOfEvent;
	}
	public void setDateOfEvent(String dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}
	public int getMass() {
		return mass;
	}
	public void setMass(int mass) {
		this.mass = mass;
	}
	public int getPalletQuantity() {
		return palletQuantity;
	}
	public void setPalletQuantity(int palletQuantity) {
		this.palletQuantity = palletQuantity;
	}
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
}
