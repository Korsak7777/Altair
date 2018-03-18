package logistic.model;

import java.util.ArrayList;
import java.util.List;

import logistic.form.RequestDetailsForm;

public class RequestList {
	
	private List<RequestDetailsForm> list = new ArrayList<RequestDetailsForm>();

	public RequestList() {
		
	}

	public List<RequestDetailsForm> getList() {
		return list;
	}

}
