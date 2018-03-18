package logistic.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import logistic.form.RequestDetailsForm;

@Component
public class RequestDetailsFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return RequestDetailsForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "placeLoadUnload", "NotEmpty.requestDetailsForm.placeLoadUnload");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactFace", "NotEmpty.requestDetailsForm.contactFace");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfEvent", "NotEmpty.requestDetailsForm.dateOfEvent");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mass", "NotEmpty.requestDetailsForm.mass");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "palletQuantity", "NotEmpty.requestDetailsForm.palletQuantity");
	}
}
