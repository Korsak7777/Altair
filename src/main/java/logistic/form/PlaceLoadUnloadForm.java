package logistic.form;

import logistic.entity.PlaceLoadUnload;

public class PlaceLoadUnloadForm {
	
	private String adressName;
	private String city;
	private String fullAdress;
	private String contactOnPlace;
	
	private boolean newPlaceLoadUnload = false;

	public PlaceLoadUnloadForm(PlaceLoadUnload placeLoadUnload) {
		this.adressName = placeLoadUnload.getAdressName();
		this.city = placeLoadUnload.getCity();
		this.fullAdress = placeLoadUnload.getFullAdress();
		this.contactOnPlace = placeLoadUnload.getContactOnPlace().getContactName();//продумать
	}
	
	public PlaceLoadUnloadForm() {
		this.newPlaceLoadUnload = true;
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

	public String getContactOnPlace() {
//		System.out.println("Get 12345"+ contactOnPlace);
		return contactOnPlace;
	}

	public void setContactOnPlace(String contactOnPlace) {
//		System.out.println("Set 12345"+ contactOnPlace.toString());
		this.contactOnPlace = contactOnPlace;
	}

	public boolean isNewPlaceLoadUnload() {
		return newPlaceLoadUnload;
	}

	public void setNewPlaceLoadUnload(boolean newPlaceLoadUnload) {
		this.newPlaceLoadUnload = newPlaceLoadUnload;
	}		
}
