package logistic.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import logistic.dao.PlaceLoadUnloadDAO;
import logistic.entity.PlaceLoadUnload;
import logistic.form.PlaceLoadUnloadForm;

@Component
public class PlaceLoadUnloadFormValidator implements Validator {
	
	@Autowired
	private PlaceLoadUnloadDAO placeLoadUnloadDAO;

	@Override
	public boolean supports(Class<?> clazz) {
		System.out.println("validator supports PlaceLoadUnload");
		return PlaceLoadUnloadForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		System.out.println("validator validate PlaceLoadUnload");

		PlaceLoadUnloadForm placeLoadUnloadForm = (PlaceLoadUnloadForm) target;
		
	    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "adressName", "NotEmpty.placeLoadUnloadForm.adressName");
	    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "NotEmpty.placeLoadUnloadForm.city");		
	
	    String adressName = placeLoadUnloadForm.getAdressName();
	    if (adressName != null && adressName.length()>0) {
	    	if (adressName.matches("\\s+")) {
	    		errors.rejectValue("adressName", "Pattern.placeLoadUnloadForm.adressName");
	    	} else if (placeLoadUnloadForm.isNewPlaceLoadUnload()) {
	    		PlaceLoadUnload placeLoadUnload = placeLoadUnloadDAO.findPlaceLoadUnload(adressName);
	    		if (placeLoadUnload != null) {
	    			errors.rejectValue("adressName", "Duplicate.placeLoadUnloadForm.adressName");
	    		}
	    	}
	    }
	}
}