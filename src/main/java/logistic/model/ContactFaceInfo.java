package logistic.model;

import java.util.List;


/**
 * @author korsak7777
 * Содержит информацию о контактном лице: имя контакта
 */
public class ContactFaceInfo {

	private String contactName;
	
	private List<ContactFaceInfo> list;
	
	public ContactFaceInfo() {
		
	}
	
	public ContactFaceInfo(String contactName) {
		this.contactName = contactName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public List<ContactFaceInfo> getList() {
//		System.out.println("\n List<ContactFaceInfo> info get " + list.toString());
		
		return list;
	}

	public void setList(List<ContactFaceInfo> list) {
//		System.out.println("\n List<ContactFaceInfo> info set " + list.toString());
		this.list = list;
	}
}
