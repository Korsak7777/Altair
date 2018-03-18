package logistic.dao;


import java.util.List;
import java.util.UUID;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import logistic.entity.Account;
import logistic.entity.ContactFace;
import logistic.form.ContactFaceForm;
import logistic.model.ContactFaceInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ContactFaceDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private AccountDAO accountDAO;
		
	public ContactFace findContactFace(String contactName) {
//		System.out.println("fin contact face");

		try {
            String sql = "Select e from " + ContactFace.class.getName() + 
            		" e Where e.contactName =:contactName and e.userName =:userName ";

            Session session = this.sessionFactory.getCurrentSession();
            Query<ContactFace> query = session.createQuery(sql, ContactFace.class);
            query.setParameter("contactName", contactName);
            query.setParameter("userName", accountDAO.findUserAccount());
            return (ContactFace) query.getSingleResult();
        } catch (NoResultException e) {
//        	System.out.println("contactDAO find need help");
        	return null;
        }
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void save(ContactFaceForm contactFaceForm) {
		
		Session session = this.sessionFactory.getCurrentSession();
		String contactName = contactFaceForm.getContactName();
		
		ContactFace contactFace = null;
		
		boolean isNew = false;
		if (contactName != null) {
			contactFace = this.findContactFace(contactName);
		}
		if (contactFace == null) {
			isNew = true;
			contactFace = new ContactFace();
		}		
		contactFace.setContactId(UUID.randomUUID().toString());//not guarantee of unique
		contactFace.setContactName(contactFaceForm.getContactName());
		contactFace.setContactPhone(contactFaceForm.getContactPhone());
		contactFace.setUserName(accountDAO.findUserAccount());
		
		if (isNew) {
			session.persist(contactFace);
		}
		session.flush();		
	}

	public List<ContactFaceInfo> queryListContactFace() {
		
		String sql = "Select new " + ContactFaceInfo.class.getName()
				+ " (p.contactName) " + " from "
				+ ContactFace.class.getName() + " p "
				+ "where p.userName =:userName";
				
		Session session = this.sessionFactory.getCurrentSession();
        Query<ContactFaceInfo> query = session.createQuery(sql, ContactFaceInfo.class);
        query.setParameter("userName", accountDAO.findUserAccount());
        System.out.println("\n List<ContactFaceInfo> " + query.getResultList().toString());
		return query.getResultList();
	}

}
