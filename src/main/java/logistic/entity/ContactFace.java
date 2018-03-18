package logistic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CONTACT_FACE")
public class ContactFace implements Serializable {

	private static final long serialVersionUID = -6179745227674836565L;
	
	@Id
	@Column(name = "CONTACT_ID", length = 256, nullable = false)
	private String contactId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_NAME", nullable = false,
			foreignKey = @ForeignKey(name = "CONTACT_FACE_FK"))
	private Account userName;
	
	@Column(name = "CONTACT_NAME", length = 128, nullable = false)
	private String contactName;
	
	@Column(name = "CONTACT_PHONE", length = 128, nullable = false)
	private String contactPhone;
	
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public Account getUserName() {
		return userName;
	}
	public void setUserName(Account userName) {
		this.userName = userName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
}
