package br.ufs.dcomp.farms.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import br.ufs.dcomp.farms.core.FarmsResponse;
import br.ufs.dcomp.farms.model.dto.SelectionStepCreatedDto;
import br.ufs.dcomp.farms.model.service.SelectionService;

/**
 * @author farms
 *
 */
@Path("/selections")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class SelectionResource {

	final static Logger logger = Logger.getLogger(SelectionResource.class);

	@Autowired
	private SelectionService selectionService;

	/**
	 * Get configuration of selection Step of a project
	 * @param dsKey
	 * @return
	 */
	@GET
	@Path("/{dsKey}")
	public Response getSelectionStepConfiguration(@PathParam("dsKey") String dsKey) {
		try {
			SelectionStepCreatedDto selectionStepCreatedDto = selectionService.getConfiguration(dsKey);
			return FarmsResponse.ok(selectionStepCreatedDto);
		} catch (Exception ex) {
			return FarmsResponse.error(null);
		}
	}

	/**
	 * Save configuration of selection Step of a project
	 * @param selectionCreatedDto
	 * @return
	 */
	@POST
	public Response saveSelectionStepConfiguration(SelectionStepCreatedDto selectionCreatedDto) {
		try {
			Boolean bool = selectionService.save(selectionCreatedDto);
			return FarmsResponse.ok(SuccessMessage.SELECTION_STEP_REGISTERED, bool);
		} catch (Exception ex) {
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

}
