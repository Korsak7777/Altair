package logistic.validator;

import logistic.dao.ContactFaceDAO;
import logistic.entity.ContactFace;
import logistic.form.ContactFaceForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ContactFaceFormValidator implements Validator {
	
	@Autowired
	private ContactFaceDAO contactFaceDAO;
	
	@Override
	public boolean supports(Class<?> clazz) {
//		System.out.println("validator Supports");
		return ContactFaceForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
//		System.out.println("validator Validate");

		ContactFaceForm contactFaceForm = (ContactFaceForm) target;
		
	    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactName", "NotEmpty.contactFaceForm.contactName");
	    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPhone", "NotEmpty.contactFaceForm.contactPhone");		
	
	    String contactName = contactFaceForm.getContactName();
	    if (contactName != null && contactName.length()>0) {
	    	if (contactName.matches("\\s+")) {
	    		errors.rejectValue("contactName", "Pattern.contactFaceForm.contactName");
	    	} else if (contactFaceForm.isNewContactFace()) {
	    		ContactFace contactFace = contactFaceDAO.findContactFace(contactName);
	    		if (contactFace != null) {
	    			errors.rejectValue("contactName", "Duplicate.contactFaceForm.contactName");
	    		}
	    	}
	    }
	}
}
