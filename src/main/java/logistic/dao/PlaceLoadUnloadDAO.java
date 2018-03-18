package logistic.dao;

import java.util.List;
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
import logistic.form.PlaceLoadUnloadForm;
import logistic.model.PlaceLoadUnloadInfo;

@Transactional
@Repository
public class PlaceLoadUnloadDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private ContactFaceDAO contactFaceDAO;
	
	public PlaceLoadUnload findPlaceLoadUnload(String adressName) {
		System.out.println("DAO findPlaceLoadUnload");

        try {
            String sql = "Select e from " + PlaceLoadUnload.class.getName() + 
            		" e Where e.adressName =:adressName and e.userName =:userName ";

            Session session = this.sessionFactory.getCurrentSession();
            Query<PlaceLoadUnload> query = session.createQuery(sql, PlaceLoadUnload.class);
            query.setParameter("adressName", adressName);
            query.setParameter("userName", accountDAO.findUserAccount());
            return (PlaceLoadUnload) query.getSingleResult();
        } catch (NoResultException e) {
        	return null;
        }
	}


	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void save(PlaceLoadUnloadForm placeLoadUnloadForm) {
		System.out.println("DAO save PlaceLoadUnload");

		Session session = this.sessionFactory.getCurrentSession();
		String adressName = placeLoadUnloadForm.getAdressName();
		
		PlaceLoadUnload placeLoadUnload = null;
		
		ContactFace contactFace = contactFaceDAO.findContactFace(placeLoadUnloadForm.getContactOnPlace());
		
		boolean isNew = false;
		if (adressName != null) {
			placeLoadUnload = this.findPlaceLoadUnload(adressName);
		}
		if (placeLoadUnload == null) {
			isNew = true;
			placeLoadUnload = new PlaceLoadUnload();
		}		
		placeLoadUnload.setAdressId(UUID.randomUUID().toString());//not guarantee of unique
		placeLoadUnload.setAdressName(placeLoadUnloadForm.getAdressName());
		placeLoadUnload.setCity(placeLoadUnloadForm.getCity());
		placeLoadUnload.setUserName(accountDAO.findUserAccount());
		//System.out.println("setContactOnPlace before " + contactFace.getContactName());
		placeLoadUnload.setContactOnPlace(contactFace);
		placeLoadUnload.setFullAdress(placeLoadUnloadForm.getFullAdress());
		
		if (isNew) {
			session.persist(placeLoadUnload);
		}
		session.flush();		
	}

	public List<PlaceLoadUnloadInfo> queryListPlaceLoadUnload() {
		
		String sql = "Select new " + PlaceLoadUnloadInfo.class.getName()
				+ " (p.adressName) " + " from "
				+ PlaceLoadUnload.class.getName() + " p "
				+ "where p.userName =:userName";
		
		Session session = this.sessionFactory.getCurrentSession();
		Query<PlaceLoadUnloadInfo> query = session.createQuery(sql, PlaceLoadUnloadInfo.class);
        query.setParameter("userName", accountDAO.findUserAccount());
		return query.getResultList();
	}

	
	
	
	

}