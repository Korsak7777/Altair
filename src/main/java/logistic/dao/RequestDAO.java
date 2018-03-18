package logistic.dao;

import java.util.UUID;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	
	public Request findRequest(int requestNum) {
		
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
	public void saveRequestDetails(RequestDetailsForm requestDetailsForm) {
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
		
		requestDetails.setRequest(this.findRequest(1));
		
		session.persist(requestDetails);
		session.flush();		


	}

	public void saveRequest(RequestList requestList) {
		// TODO Auto-generated method stub
		
	}
}
