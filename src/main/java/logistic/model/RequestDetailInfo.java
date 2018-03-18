package logistic.model;


import org.threeten.bp.LocalDate;

import logistic.form.RequestDetailsForm;

public class RequestDetailInfo {

	private String placeLoadUnload;
	private String contactFace;
	private String dateOfEvent;
	private int mass;
	private int palletQuantity;
	private boolean loadingUnloading;
	
    private boolean valid;
	
	public RequestDetailInfo() {
		
	}
	
	public RequestDetailInfo(RequestDetailsForm requestDetailsForm) {
		this.placeLoadUnload = requestDetailsForm.getPlaceLoadUnload();
		this.contactFace = requestDetailsForm.getContactFace();
		this.dateOfEvent = requestDetailsForm.getDateOfEvent();
		this.mass = requestDetailsForm.getMass();
		this.palletQuantity = requestDetailsForm.getPalletQuantity();
		this.loadingUnloading = requestDetailsForm.isLoadingUnloading();
		this.valid = requestDetailsForm.isValid();
	}
	
	public String getPlaceLoadUnload() {
		return placeLoadUnload;
	}
	public void setPlaceLoadUnload(String placeLoadUnload) {
		this.placeLoadUnload = placeLoadUnload;
	}
	public String getContactFace() {
		return contactFace;
	}
	public void setContactFace(String contactFace) {
		this.contactFace = contactFace;
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
	public boolean isLoadingUnloading() {
		return loadingUnloading;
	}
	public void setLoadingUnloading(boolean loadingUnloading) {
		this.loadingUnloading = loadingUnloading;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
}
