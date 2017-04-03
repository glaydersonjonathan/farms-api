package br.ufs.dcomp.farms.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.ufs.dcomp.farms.common.message.ErrorMessage;
import br.ufs.dcomp.farms.common.message.SuccessMessage;
import br.ufs.dcomp.farms.core.FarmsException;
import br.ufs.dcomp.farms.core.FarmsMail;
import br.ufs.dcomp.farms.core.FarmsResponse;
import br.ufs.dcomp.farms.model.dto.CountryCreatedDto;
import br.ufs.dcomp.farms.model.dto.InstitutionCreateDto;
import br.ufs.dcomp.farms.model.dto.InstitutionCreatedDto;
import br.ufs.dcomp.farms.model.service.InstitutionService;

/**
 * @author farms
 *
 */
@Path("/institutions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class InstitutionResource {

	// final static Logger logger = Logger.getLogger(InstitutionResource.class);

	@Autowired
	private InstitutionService institutionService;

	/**
	 * Receive a request from client to get all countries registered.
	 *
	 * @return Response.
	 */
	@GET
	@Path("/countries")
	public Response getAllCountries() {
		try {
			List<CountryCreatedDto> countryCreatedDto = institutionService.getAllCountries();
			return FarmsResponse.ok(countryCreatedDto);
		} catch (Exception ex) {
			// logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Receive request from client to Add institution a project.
	 *
	 * @param InstitutionCreateDto.
	 * @return Response.
	 */
	@POST
	@Path("/addInstitution")
	public Response addInstitutionProject(InstitutionCreateDto institutionCreateDto) {
		try {
			Boolean bool = institutionService.save(institutionCreateDto);
			return FarmsResponse.ok(SuccessMessage.INSTITUTION_ADDED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Receive a request from client to update a institution.
	 * 
	 * @param projectCreatedDto
	 * @return Response
	 */
	@PUT
	public Response updateProject(InstitutionCreatedDto institutionCreatedDto) {
		try {
			Boolean bool = institutionService.update(institutionCreatedDto);
			return FarmsResponse.ok(SuccessMessage.INSTITUTION_UPDATED, bool);
		} catch (FarmsException fe) {
			return FarmsResponse.error(fe.getErrorMessage());
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Receive a request from client to get a institution.
	 * 
	 * @param dsKey
	 * @param nmInstitution
	 * @return Response
	 */
	@GET
	@Path("/{nmInstitution}/{dsKey}")
	public Response getInstitutionByName(@PathParam("nmInstitution") String nmInstitution,
			@PathParam("dsKey") String dsKey) {
		try {
			InstitutionCreatedDto institutionCreatedDto = institutionService.getInstitutionByName(nmInstitution, dsKey);
			return FarmsResponse.ok(institutionCreatedDto);
		} catch (Exception ex) {
			// logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Delete institution from project
	 * 
	 * @param institutionCreatedDto
	 * @return
	 */
	@DELETE
	@Path("/delete/{dsKey}/{idInstitution}")
	public Response deleteInstitution(@PathParam("dsKey") String dsKey,
			@PathParam("idInstitution") Long idInstitution) {
		try {
			Boolean bool = institutionService.deleteInstitution(dsKey, idInstitution);
			return FarmsResponse.ok(SuccessMessage.INSTITUTION_DELETED, bool);
		} catch (FarmsException fe) {
			return FarmsResponse.error(fe.getErrorMessage());
		}

		catch (Exception ex) {
			// logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

}