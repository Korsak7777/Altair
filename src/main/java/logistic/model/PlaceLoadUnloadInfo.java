package logistic.model;

import java.util.List;

public class PlaceLoadUnloadInfo {
	
	private String adressName;
	
	private List<PlaceLoadUnloadInfo> list;
	
	public PlaceLoadUnloadInfo() {
		
	}
	
	public PlaceLoadUnloadInfo(String adressName) {
		this.adressName = adressName;
	}

	public String getAdressName() {
		return adressName;
	}

	public void setAdressName(String adressName) {
		this.adressName = adressName;
	}

	public List<PlaceLoadUnloadInfo> getList() {
		return list;
	}

	public void setList(List<PlaceLoadUnloadInfo> list) {
		this.list = list;
	}
	
	

}
