package br.ufs.dcomp.farms.rest;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.common.message.ErrorMessage;
import br.ufs.dcomp.farms.common.message.SuccessMessage;
import br.ufs.dcomp.farms.core.FarmsCrypt;
import br.ufs.dcomp.farms.core.FarmsResponse;
import br.ufs.dcomp.farms.model.dto.ResearcherRegisterDto;
import br.ufs.dcomp.farms.model.entity.Researcher;
import br.ufs.dcomp.farms.model.service.ResearcherService;

@Path("/researchers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class ResearcherResource {

	final static Logger logger = Logger.getLogger(ResearcherResource.class);

	@Autowired
	private ResearcherService researcherService;


	@GET
	@Path("/?dsEmail={dsEmail}")
	public Response getByDsEmail(@PathParam("dsEmail") String dsEmail) {
		try {
			Researcher researcher = researcherService.getByEmail(dsEmail);
			return FarmsResponse.ok(researcher);
		} catch (Exception ex) {
			logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	// OK!
	@GET
	@Path("/{dsSSO}")
	public Response getBydsSSO(@PathParam("dsSSO") String dsSSO) {
		try {
			ResearcherRegisterDto researcherCreatedDto = researcherService.getBySSO(dsSSO);
			return FarmsResponse.ok(researcherCreatedDto);
		} catch (Exception ex) {
			logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	// ok? falta verificar corretamente se email já existe
	@PUT
	public Response updateResearcher(ResearcherRegisterDto researcherRegisterDto) {
		try {
			Boolean researcherRegisteredDto = researcherService.update(researcherRegisterDto);
			return FarmsResponse.ok(SuccessMessage.RESEARCHER_UPDATED, researcherRegisteredDto);
		} catch (Exception fe) {
			return FarmsResponse.error(ErrorMessage.EMAIL_ALREADY_IN_USE);
		}
	}

	//mudança de senha necessito criptografar, por isso outro metodo
	@PUT
	@Path("/pass")
	public Response updatePassword(ResearcherRegisterDto researcherRegisterDto) {
		researcherRegisterDto.setDsPassword(FarmsCrypt.hashPassword(researcherRegisterDto.getDsPassword()));
		try {
			Boolean researcherRegisteredDto = researcherService.update(researcherRegisterDto);
			return FarmsResponse.ok(SuccessMessage.PASSWORD_CHANGED, researcherRegisteredDto);
		} catch (Exception ex) {
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	// testando
	@DELETE
	@Path("/{idResearcher}")
	public Response deleteResearcher(@PathParam("idResearcher") Long idResearcher) {
		try {
			Boolean researcherExcludedDto = researcherService.delete(idResearcher);
			return FarmsResponse.ok(SuccessMessage.RESEARCHER_EXCLUDED, researcherExcludedDto);
		} catch (Exception ex) {
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}


}