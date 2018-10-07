package logistic.dao;

import java.util.ListIterator;
import java.util.UUID;

import javax.persistence.NoResultException;

import org.apache.commons.collections.iterators.ArrayListIterator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.threeten.bp.LocalDateTime;

import logistic.entity.Account;
import logistic.entity.ContactFace;
import logistic.entity.PlaceLoadUnload;
import logistic.entity.Request;
import logistic.entity.RequestDetails;
import logistic.form.RequestDetailsForm;
import logistic.model.RequestList;

@Transactional
@Repository
public class RequestDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private ContactFaceDAO contactFaceDAO;

	@Autowired
	private PlaceLoadUnloadDAO placeLoadUnloadDAO;
	
	private int getMaxRequestNum() {
		String sql = "Select max(r.requestNum) from " + Request.class.getName() + " r ";
		Session session = this.sessionFactory.getCurrentSession();
		Query<Integer> query = session.createQuery(sql, Integer.class);
		Integer value = (Integer) query.getSingleResult();
		if (value == null) {
			return 0;
		}
		return value;
	}
	
	private Request findRequest(int requestNum) {
		
		try {
			String sql = "Select e from " + Request.class.getName() + 
					" e Where e.requestNum =:requestNum and e.userName =:userName ";
			
			Session session = this.sessionFactory.getCurrentSession();
			Query<Request> query = session.createQuery(sql, Request.class);
			query.setParameter("requestNum", requestNum);
            query.setParameter("userName", accountDAO.findUserAccount());
            return (Request) query.getSingleResult();

		} catch (NoResultException e) {
        	return null;
        }
}
	
	public RequestDetails findRequestDetails(String requestId) {
		return null;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private void saveRequestDetails(RequestDetailsForm requestDetailsForm, int requestNum) {
		System.out.println("DAO saveRequestDetails");

		Session session = this.sessionFactory.getCurrentSession();
		
		RequestDetails requestDetails = new RequestDetails();
		
		ContactFace contactFace = contactFaceDAO.
				findContactFace(requestDetailsForm.getContactFace());
		PlaceLoadUnload placeLoadUnload = placeLoadUnloadDAO.
				findPlaceLoadUnload(requestDetailsForm.getPlaceLoadUnload());	
		
		requestDetails.setRequestId(UUID.randomUUID().toString());
		requestDetails.setContactFace(contactFace);
		requestDetails.setPlaceLoadUnload(placeLoadUnload);
		requestDetails.setDateOfEvent(requestDetailsForm.getDateOfEvent());
		requestDetails.setMass(requestDetailsForm.getMass());
		requestDetails.setPalletQuantity(requestDetailsForm.getPalletQuantity());
		requestDetails.setLoadingUnloading(requestDetailsForm.isLoadingUnloading());
		
		requestDetails.setRequest(this.findRequest(requestNum));
		
		session.persist(requestDetails);
		session.flush();		


	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void saveRequest(RequestList requestList) {
		System.out.println("DAO saveRequest");

		Session session = this.sessionFactory.getCurrentSession();
		
		Request request = new Request();
		
		int amount = 0;
		int requestNum = getMaxRequestNum() + 1;
		LocalDateTime creationDate = LocalDateTime.now();
		
		request.setRequestId(UUID.randomUUID().toString());
		request.setUserName(accountDAO.findUserAccount());
		request.setRequestNum(requestNum);
		request.setCreationDate(creationDate.toString());
	
		ListIterator<RequestDetailsForm> requestListIter = requestList.getList().listIterator();
		while (requestListIter.hasNext()) {
			amount += requestListIter.next().getPalletQuantity();
			System.out.println("amount: "+ amount);
		}
		
		request.setAmount(amount);
		
		session.persist(request);
		
		while (requestListIter.hasPrevious()) {
			RequestDetailsForm R = requestListIter.previous();
			this.saveRequestDetails(R, requestNum);
		}
		session.flush();

	}
}
