package logistic.form;

import logistic.entity.ContactFace;

public class ContactFaceForm {
	
	private String contactName;
	private String contactPhone;
	
	
	private boolean newContactFace = false;
	
	public ContactFaceForm(ContactFace contactFace) {
		this.contactName = contactFace.getContactName();
		this.contactPhone = contactFace.getContactPhone();
	}
	
	public ContactFaceForm() {
		this.newContactFace = true;
	}
	
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public boolean isNewContactFace() {
		return newContactFace;
	}

	public void setNewContactFace(boolean newContactFace) {
		this.newContactFace = newContactFace;
	}


}
