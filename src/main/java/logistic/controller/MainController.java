package logistic.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import logistic.dao.ContactFaceDAO;
import logistic.dao.PlaceLoadUnloadDAO;
import logistic.dao.RequestDAO;
import logistic.validator.ContactFaceFormValidator;
import logistic.validator.PlaceLoadUnloadFormValidator;
import logistic.validator.RequestDetailsFormValidator;
import logistic.entity.ContactFace;
import logistic.entity.PlaceLoadUnload;
import logistic.form.ContactFaceForm;
import logistic.form.PlaceLoadUnloadForm;
import logistic.form.RequestDetailsForm;
import logistic.model.ContactFaceInfo;
import logistic.model.PlaceLoadUnloadInfo;
import logistic.model.RequestList;
import logistic.utils.Utils;


@Controller
public class MainController {
	
	@Autowired
	private ContactFaceDAO contactFaceDAO;
	
	@Autowired
	private PlaceLoadUnloadDAO placeLoadUnloadDAO;
	
	@Autowired
	private RequestDAO requestDAO;
	
	@Autowired
	private ContactFaceFormValidator contactFaceFormValidator;
	
	@Autowired
	private PlaceLoadUnloadFormValidator placeLoadUnloadFormValidator;
	
	@Autowired
	private RequestDetailsFormValidator requestDetailsFormValidator;
	
	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);
		
		if (target.getClass() == ContactFaceForm.class) {
			dataBinder.setValidator(contactFaceFormValidator);
		}
		
		if (target.getClass() == PlaceLoadUnloadForm.class) {
			dataBinder.setValidator(placeLoadUnloadFormValidator);
		}
		
		if (target.getClass() == RequestDetailsForm.class) {
			dataBinder.setValidator(requestDetailsFormValidator);
		}
	}
	
	@RequestMapping("/403")
	public String accessDenied() {
		return "/403";
	}

	@RequestMapping("/")
	public String home() {
	   return "index";
	}
	
	// GET: Show Login Page
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = { "/accountInfo" }, method = RequestMethod.GET)
	public String accountInfo(Model model) {

	   UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   System.out.println(userDetails.getPassword());
	   System.out.println(userDetails.getUsername());
	   System.out.println(userDetails.isEnabled());

	   model.addAttribute("userDetails", userDetails);
	   return "accountInfo";
	}
	
	//извлекает даные с сервера
	@RequestMapping(value = {"/addContact"}, method = RequestMethod.GET)
	public String contactFace(Model model, @RequestParam(value = "contactName", 
			defaultValue = "") String contactName) {
//		System.out.println("controller GET");
		ContactFaceForm contactFaceForm = null;
		
		if (contactName != null && contactName.length()>0) {
			ContactFace contactFace = contactFaceDAO.findContactFace(contactName);
			if (contactFace != null) {
				contactFaceForm = new ContactFaceForm(contactFace);
			}
		}
		if (contactFaceForm == null) {
			contactFaceForm = new ContactFaceForm();
			contactFaceForm.setNewContactFace(true);
		}
		model.addAttribute("contactFaceForm", contactFaceForm);
		

		return "addContactFace";
	}
	
	//отправляет данные на сервер
	@RequestMapping(value = {"/addContact"}, method = RequestMethod.POST)
	public String contactFaceSave(Model model, @ModelAttribute("contactFaceForm") 
			@Validated ContactFaceForm contactFaceForm, BindingResult result,
			final RedirectAttributes redirectAttributes) {
//		System.out.println("controller POST");
		
		if (result.hasErrors()) {
			return "addContactFace";
		}
		try {
			contactFaceDAO.save(contactFaceForm);
		} catch (Exception e) {
	         Throwable rootCause = ExceptionUtils.getRootCause(e);
	         String message = rootCause.getMessage();
	         model.addAttribute("errorMessage", message);
	         // Show product form.
	         return "addContactFace";
	    }
		return "redirect:/";
	}

	@RequestMapping(value = {"/addPlaceLoadUnload"}, method = RequestMethod.GET)
	public String placeLoadUnload(Model model, @RequestParam(value = "adressName", 
			defaultValue = "") String adressName) {
//		System.out.println("controller Place Load Unload GET");
		PlaceLoadUnloadForm placeLoadUnloadForm = null;
		
		if (adressName != null && adressName.length()>0) {
			PlaceLoadUnload placeLoadUnload = placeLoadUnloadDAO.findPlaceLoadUnload(adressName);
			if (placeLoadUnload != null) {
				placeLoadUnloadForm = new PlaceLoadUnloadForm(placeLoadUnload);
			}
		}
		if (placeLoadUnloadForm == null) {
			placeLoadUnloadForm = new PlaceLoadUnloadForm();
			placeLoadUnloadForm.setNewPlaceLoadUnload(true);
		}
		model.addAttribute("placeLoadUnloadForm", placeLoadUnloadForm);
		
		ContactFaceInfo contactInfo = new ContactFaceInfo();
//		System.out.println("\n List<ContactFaceInfo> controller " + list.toString());
		contactInfo.setList(contactFaceDAO.queryListContactFace());
		model.addAttribute("contactList", contactInfo);
		
		return "addPlaceLoadUnload";
		
	}
	
	@RequestMapping(value = {"/addPlaceLoadUnload"}, method = RequestMethod.POST)
	public String placeLoadUnloadSave(Model model, @ModelAttribute("placeLoadUnloadForm") 
			@Validated PlaceLoadUnloadForm placeLoadUnloadForm, BindingResult result,
			final RedirectAttributes redirectAttributes) {
//		System.out.println("controller Place Load Unload POST");
		
		ContactFaceInfo contactInfo = new ContactFaceInfo();
		contactInfo.setList(contactFaceDAO.queryListContactFace());
		model.addAttribute("contactList", contactInfo);
		
		if (result.hasErrors()) {
	        System.out.println("save hasErrors: " + result.getAllErrors().toString() );
			return "addPlaceLoadUnload";
		}
		try {
			placeLoadUnloadDAO.save(placeLoadUnloadForm);
	        System.out.println("save" );

		} catch (Exception e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
	        String message = rootCause.getMessage();
	        System.out.println("\nException e: " + message);
	        model.addAttribute("errorMessage", message);
	        // Show product form.
	        return "addPlaceLoadUnload";
	    }
		return "redirect:/";
	}
	
	//
	/**
	 * извлекает даные с сервера
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/addRequest"}, method = RequestMethod.GET)
	public String addRequestGet(HttpServletRequest request, Model model) {
		System.out.println("addRequestGet");
		
		//создание и добавление атрибута requestDetailsForm
		RequestDetailsForm requestDetailsForm = new RequestDetailsForm();
		model.addAttribute("requestDetailsForm", requestDetailsForm);

		//добавление списка контактов в выпадающее окно
		ContactFaceInfo contactInfo = new ContactFaceInfo();
		contactInfo.setList(contactFaceDAO.queryListContactFace());
		model.addAttribute("contactList", contactInfo);

		//добавление мест погрузок в выпадающее окно
		PlaceLoadUnloadInfo placeLoadUnloadInfo = new PlaceLoadUnloadInfo();
		placeLoadUnloadInfo.setList(placeLoadUnloadDAO.queryListPlaceLoadUnload());
		model.addAttribute("placeLoadUnloadList", placeLoadUnloadInfo);
		
		//добавление списка подзаказов, при его существовании
		RequestList requestList = Utils.getRequestListInSession(request);
		model.addAttribute("requestList", requestList);
		
		System.out.println("\n" + "add Request Get " + requestList.getList().toString() + "\n");
		
		return "request";
	}
	
	/**
	 * 
	 * Отправляет данные на сервер
	 * @param request
	 * @param model
	 * @param requestDetailsForm
	 * @param result
	 * @return
	 */
	@RequestMapping(value = {"/addRequest"}, method = RequestMethod.POST)
	public String addRequestPost(HttpServletRequest request, Model model,
			@ModelAttribute("requestDetailsForm") @Validated RequestDetailsForm requestDetailsForm,
			BindingResult result) {
		
		//добавление списка контактов в выпадающее окно
		ContactFaceInfo contactInfo = new ContactFaceInfo();
		contactInfo.setList(contactFaceDAO.queryListContactFace());
		model.addAttribute("contactList", contactInfo);

		PlaceLoadUnloadInfo placeLoadUnloadInfo = new PlaceLoadUnloadInfo();
		placeLoadUnloadInfo.setList(placeLoadUnloadDAO.queryListPlaceLoadUnload());
		model.addAttribute("placeLoadUnloadList", placeLoadUnloadInfo);
		
		RequestList requestList = Utils.getRequestListInSession(request);
		
		if (result.hasErrors()) {
	        System.out.println("save hasErrors: " + result.getAllErrors().toString() );
			return "request";
		}
		
		try {
			requestList.getList().add(requestDetailsForm);
			model.addAttribute("requestList", requestList);
			System.out.println("\n" + "add Request Post " + requestList.getList().toString() + "\n");
		} catch (Exception e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
	        String message = rootCause.getMessage();
	        System.out.println("\n" + "Exception e: " + message);
	        model.addAttribute("errorMessage", message);
	        // Show product form.
	        return "request";
	    }
		return "request";
	}
	
	@RequestMapping(value = {"/requestConfirmation"}, method = RequestMethod.POST)
	public String requestSave(HttpServletRequest request, Model model,
			final RedirectAttributes redirectAttributes) {
		RequestList requestList = Utils.getRequestListInSession(request);
		
	    try {
	    	  requestDAO.saveRequest(requestList);
		      System.out.println("save" );
	      } catch (Exception e) {
	    	  Throwable rootCause = ExceptionUtils.getRootCause(e);
		      String message = rootCause.getMessage();
		      System.out.println("\nException e: " + message);
	          return "request";
	      }
	      // Remove Request from Session.
	      Utils.removeRequestInSession(request);

	      // Store last Request.
	      Utils.storeLastRequestInSession(request, requestList);
	      return "redirect:/";
	}	
	//сохраняет RequestDetails
/*	@RequestMapping(value = {"/addRequest"}, method = RequestMethod.POST)
	public String requesDetailsSave(Model model, @ModelAttribute("requestDetailsForm")
			@Validated RequestDetailsForm requestDetailsForm, BindingResult result,
			final RedirectAttributes redirectAttributes) {
		System.out.println("saveRequesDetails " + model.toString() + "\n");

		ContactFaceInfo contactInfo = new ContactFaceInfo();
		contactInfo.setList(contactFaceDAO.queryListContactFace());
		model.addAttribute("contactList", contactInfo);

		PlaceLoadUnloadInfo placeLoadUnloadInfo = new PlaceLoadUnloadInfo();
		placeLoadUnloadInfo.setList(placeLoadUnloadDAO.queryListPlaceLoadUnload());
		model.addAttribute("placeLoadUnloadList", placeLoadUnloadInfo);
		
		if (result.hasErrors()) {
			return "request";
		}		
		try {
			requestDAO.saveRequestDetails(requestDetailsForm);
		} catch (Exception e) {
	         Throwable rootCause = ExceptionUtils.getRootCause(e);
	         String message = rootCause.getMessage();
	         model.addAttribute("errorMessage", message);
	         // Show product form.
	         return "request";
	    }
		return "redirect:/";
	}*/
	
	//пока не реализованно сохранение в таблицу ниже, сперва сохраню 
	//в БД, проверю как работает добавление RequestDetailsForm, затем 
	//буду думать о переносе в форму RequestList
/*	@RequestMapping(value = {"/addRequest"}, method = RequestMethod.POST)
	public String addRequestPost(HttpServletRequest request, Model model,
			@ModelAttribute("requestDetailsForm") RequestDetailsForm requestDetailsForm,
			BindingResult result) {
		if (result.hasErrors()) {
			return "request";
		}
		
		RequestList requestList = Utils.getRequestListInSession(request);
		requestList.getList().add(requestDetailsForm);
		
		return "request";
	}*/
	
/*	@RequestMapping(value = {"/requestConfirmation"}, method = RequestMethod.GET)
	public String requestList(HttpServletRequest request, Model model) {
		RequestList requestList = Utils.getRequestListInSession(request);
		
		model.addAttribute("requestList",requestList);
		

		return "request";
	}
*/	
	
	
}
