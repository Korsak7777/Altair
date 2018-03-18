package logistic.utils;

import javax.servlet.http.HttpServletRequest;

import logistic.model.RequestList;

public class Utils {

	public static RequestList getRequestListInSession(HttpServletRequest request) {
		
		RequestList requestList = (RequestList) request.getSession().getAttribute("requestList");
		
		if (requestList == null) {
			requestList = new RequestList();
			
			request.getSession().setAttribute("requestList", requestList);
		}
		return requestList;
	}
	
	public static void removeRequestInSession(HttpServletRequest request) {
		request.getSession().removeAttribute("requestList");
	}

	public static void storeLastOrderedCartInSession(HttpServletRequest request, RequestList requestList) {
		request.getSession().setAttribute("lastRequestList", requestList);		
	}
}
