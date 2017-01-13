package br.ufs.dcomp.farms.rest;

import javax.ws.rs.Consumes;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.common.message.ErrorMessage;
import br.ufs.dcomp.farms.common.message.SuccessMessage;
import br.ufs.dcomp.farms.core.FarmsResponse;
import br.ufs.dcomp.farms.model.dto.CountryCreatedDto;
import br.ufs.dcomp.farms.model.dto.InstitutionCreateDto;
import br.ufs.dcomp.farms.model.dto.InstitutionCreatedDto;
import br.ufs.dcomp.farms.model.dto.ProjectCreateDto;
import br.ufs.dcomp.farms.model.dto.ProjectCreatedDto;
import br.ufs.dcomp.farms.model.service.InstitutionService;

@Path("/institutions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class InstitutionResource {

	final static Logger logger = Logger.getLogger(InstitutionResource.class);

	@Autowired
	private InstitutionService institutionService;

	@GET
	@Path("/countries")
	public Response getAllCountries() {
		try {
			List<CountryCreatedDto> countryCreatedDto = institutionService.getAllCountries();
			return FarmsResponse.ok(countryCreatedDto);
		} catch (Exception ex) {
			logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	// ok?
	@POST
	public Response createInstitution(InstitutionCreateDto institutionCreateDto) {
		try {
			Boolean bool = institutionService.save(institutionCreateDto);
			return FarmsResponse.ok(SuccessMessage.INSTITUTION_REGISTERED, bool);
		} catch (Exception ex) {
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

}