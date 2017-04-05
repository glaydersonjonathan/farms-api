package br.ufs.dcomp.farms.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import br.ufs.dcomp.farms.model.dto.RatedContentCreatedDto;
import br.ufs.dcomp.farms.model.dto.ReviewCreateDto;
import br.ufs.dcomp.farms.model.dto.SelectionStepCreatedDto;
import br.ufs.dcomp.farms.model.dto.StudyCreatedDto;
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

	//final static Logger logger = Logger.getLogger(SelectionResource.class);

	@Autowired
	private SelectionService selectionService;

	/**
	 * Get configuration of selection Step of a project
	 * 
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
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Save configuration of selection Step of a project
	 * 
	 * @param selectionCreatedDto
	 * @return
	 */
	@POST
	public Response saveSelectionStepConfiguration(SelectionStepCreatedDto selectionCreatedDto) {
		try {
			Boolean bool = selectionService.save(selectionCreatedDto);
			return FarmsResponse.ok(SuccessMessage.SELECTION_STEP_REGISTERED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Receive a request from client to get all rated content registered.
	 *
	 * @return Response.
	 */
	@GET
	@Path("/rated")
	public Response getAllRated() {
		try {
			List<RatedContentCreatedDto> ratedContents = selectionService.getAllRated();
			return FarmsResponse.ok(ratedContents);
		} catch (Exception ex) {
			//logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			//FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Assign studies to researcher
	 * 
	 * @param reviewCreateDto
	 * @return
	 */
	@POST
	@Path("/assignManual")
	public Response assignManual(ReviewCreateDto reviewCreateDto) {
		try {
			Boolean bool = selectionService.assignManual(reviewCreateDto);
			return FarmsResponse.ok(SuccessMessage.STUDIES_MANUAL_ASSIGNED, bool);
		} catch (FarmsException fe) {
			return FarmsResponse.error(fe.getErrorMessage());
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Get reviews by project and researcher
	 * 
	 * @param dsKey
	 * @param dsSSO
	 * @return
	 */
	@GET
	@Path("/review/{dsKey}/{dsSSO}")
	public Response getReviews(@PathParam("dsKey") String dsKey, @PathParam("dsSSO") String dsSSO) {
		try {
			List<ReviewCreateDto> reviewCreatedDto = selectionService.getReviews(dsKey, dsSSO);
			return FarmsResponse.ok(reviewCreatedDto);
		} catch (Exception ex) {
			//FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Update state of review.
	 * 
	 * @param reviewCreateDto
	 * @return
	 */
	@POST
	@Path("/realizeReview")
	public Response realizeReview(ReviewCreateDto reviewCreateDto) {
		try {
			Boolean bool = selectionService.realizeReview(reviewCreateDto);
			return FarmsResponse.ok(SuccessMessage.REVIEW_FINALIZED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Get studies in conflict
	 * 
	 * @param dsKey
	 * @return
	 */
	@GET
	@Path("/conflicts/{dsKey}")
	public Response getStudiesInConflicts(@PathParam("dsKey") String dsKey) {
		try {
			List<StudyCreatedDto> studyCreatedDto = selectionService.getStudiesInConflict(dsKey);
			return FarmsResponse.ok(studyCreatedDto);
		} catch (Exception ex) {
			//FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Assign Studies automatically
	 * 
	 * @param dsKey
	 * @return
	 */
	@POST
	@Path("/assignAuto/{dsKey}")
	public Response assignAuto(@PathParam("dsKey") String dsKey) {
		try {
			Boolean bool = selectionService.assignAuto(dsKey);
			return FarmsResponse.ok(SuccessMessage.STUDIES_AUTO_ASSIGNED, bool);
		} catch (FarmsException fe) {
			return FarmsResponse.error(fe.getErrorMessage());
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

}
