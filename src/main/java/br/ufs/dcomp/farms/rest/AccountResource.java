package br.ufs.dcomp.farms.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.ufs.dcomp.farms.common.message.ErrorMessage;
import br.ufs.dcomp.farms.common.message.SuccessMessage;
import br.ufs.dcomp.farms.core.FarmsException;
import br.ufs.dcomp.farms.core.FarmsMail;
import br.ufs.dcomp.farms.core.FarmsResponse;
import br.ufs.dcomp.farms.model.dto.ResearcherLoggedDto;
import br.ufs.dcomp.farms.model.dto.ResearcherLoginDto;
import br.ufs.dcomp.farms.model.dto.ResearcherRegisterDto;
import br.ufs.dcomp.farms.model.dto.ResearcherRegisteredDto;
import br.ufs.dcomp.farms.model.service.AccountService;

/**
 * @author farms
 *
 */
@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class AccountResource {

	//final static Logger logger = Logger.getLogger(AccountResource.class);

	@Autowired
	private AccountService accountService;

	/**
	 * Receive request from client to login.
	 *
	 * @param ResearcherLoginDto.
	 * @return Response.
	 */
	@POST
	@Path("/login")
	public Response login(ResearcherLoginDto researcherLoginDto) {
		try {
			//logger.info("Starting login.");
			ResearcherLoggedDto researcherLoggedDto = accountService.login(researcherLoginDto);
			//logger.info(SuccessMessage.RESEARCHER_LOGGED);
			return FarmsResponse.ok(SuccessMessage.RESEARCHER_LOGGED, researcherLoggedDto);
		} catch (FarmsException fe) {
			//logger.error(fe.getErrorMessage());
			return FarmsResponse.error(fe.getErrorMessage());
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Receive request from client to register researcher.
	 *
	 * @param ResearcherRegisterDto
	 *            .
	 * @return Response.
	 */
	@POST
	@Path("/register")
	public Response register(ResearcherRegisterDto researcherRegisterDto) {
		try {
			ResearcherRegisteredDto researcherRegisteredDto = accountService
					.registerAndSendAccountConfirmationEmail(researcherRegisterDto);
			return FarmsResponse.ok(SuccessMessage.RESEARCHER_REGISTERED, researcherRegisteredDto);
		} catch (FarmsException fe) {
			return FarmsResponse.error(fe.getErrorMessage());
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Verify account registration
	 * 
	 * @param cdUuid
	 * @return Response
	 */
	@GET
	@Path("/confirmation")
	public Response verifyAccount(@QueryParam("u") String cdUuid) {
		try {
			//logger.info("Starting account confirmation.");
			ResearcherRegisteredDto researcherRegisteredDto = accountService.confirmAccount(cdUuid);
			//logger.info(SuccessMessage.ACCOUNT_CONFIRMED);
			return FarmsResponse.ok(SuccessMessage.ACCOUNT_CONFIRMED, researcherRegisteredDto);
		} catch (FarmsException fe) {
			return FarmsResponse.error(fe.getErrorMessage());
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Resend email confirmation
	 * 
	 * @param researcherLoginDto
	 * @return
	 */
	@POST
	@Path("/resend")
	public Response resendConfirmation(ResearcherLoginDto researcherLoginDto) {
		try {
			Boolean bool = accountService.resend(researcherLoginDto);
			return FarmsResponse.ok(SuccessMessage.RESEND_EMAIL_CONFIRMATION, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Send email to new password
	 * 
	 * @param email
	 * @return
	 */
	@GET
	@Path("/requestPass/{email}")
	public Response sendEmailNewPassword(@PathParam("email") String email) {
		try {
			Boolean bool = accountService.sendEmailNewPassword(email);
			return FarmsResponse.ok(SuccessMessage.EMAIL_NEW_PASSWORD, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Update Password of researcher
	 * 
	 * @param u
	 * @param researcherRegisterDto
	 * @return
	 */
	@POST
	@Path("/newPass/{u}")
	public Response newPass(@PathParam("u") String u, ResearcherRegisterDto researcherRegisterDto) {
		try {
			Boolean bool = accountService.newPass(u, researcherRegisterDto);
			return FarmsResponse.ok(SuccessMessage.PASSWORD_CHANGED, bool);
		}  catch (FarmsException fe) {
			return FarmsResponse.error(fe.getErrorMessage());
		}
		catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}
}