package logistic.form;


import java.util.Date;

import org.threeten.bp.LocalDate;

import logistic.entity.RequestDetails;
import logistic.model.RequestDetailInfo;


public class RequestDetailsForm {
	
	private String placeLoadUnload;
	private String contactFace;
	private int mass;
	private int palletQuantity;
	private boolean loadingUnloading;
	
	private String dateOfEvent;
    private boolean valid;
	
    
    
	public RequestDetailsForm() {
		
	}
	
	public RequestDetailsForm(RequestDetails requestDetails) {
		this.placeLoadUnload = requestDetails.getPlaceLoadUnload().getFullAdress();
		this.contactFace = requestDetails.getContactFace().getContactName();
		this.dateOfEvent = requestDetails.getDateOfEvent().toString();
		this.mass = requestDetails.getMass();
		this.palletQuantity = requestDetails.getPalletQuantity();
		this.loadingUnloading = requestDetails.isLoadingUnloading();
	}
	
	public RequestDetailsForm(RequestDetailInfo requestDetailsForm) {
		if(requestDetailsForm != null) {
			this.placeLoadUnload = requestDetailsForm.getPlaceLoadUnload();
			this.contactFace = requestDetailsForm.getContactFace();
			this.dateOfEvent = requestDetailsForm.getDateOfEvent().toString();
			this.mass = requestDetailsForm.getMass();
			this.palletQuantity = requestDetailsForm.getPalletQuantity();
			this.loadingUnloading = requestDetailsForm.isLoadingUnloading();
		}
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
