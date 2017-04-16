package br.ufs.dcomp.farms.rest;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import br.ufs.dcomp.farms.core.FarmsMail;
import br.ufs.dcomp.farms.core.FarmsResponse;
import br.ufs.dcomp.farms.model.dto.BaseUseCriteriaCreatedDto;
import br.ufs.dcomp.farms.model.dto.LanguageCreatedDto;
import br.ufs.dcomp.farms.model.dto.MainQuestionCreatedDto;
import br.ufs.dcomp.farms.model.dto.ObjectiveCreateDto;
import br.ufs.dcomp.farms.model.dto.ObjectiveCreatedDto;
import br.ufs.dcomp.farms.model.dto.SearchEngineCreatedDto;
import br.ufs.dcomp.farms.model.dto.SearchKeywordCreatedDto;
import br.ufs.dcomp.farms.model.dto.SecondaryQuestionCreatedDto;
import br.ufs.dcomp.farms.model.dto.SelectionCriteriaCreatedDto;
import br.ufs.dcomp.farms.model.dto.StandardQueryCreatedDto;
import br.ufs.dcomp.farms.model.dto.StudyLanguageCreatedDto;
import br.ufs.dcomp.farms.model.entity.Language;
import br.ufs.dcomp.farms.model.entity.SearchEngine;
import br.ufs.dcomp.farms.model.service.LanguageService;
import br.ufs.dcomp.farms.model.service.MainQuestionService;
import br.ufs.dcomp.farms.model.service.ObjectiveService;
import br.ufs.dcomp.farms.model.service.SearchEngineService;
import br.ufs.dcomp.farms.model.service.SearchKeywordService;
import br.ufs.dcomp.farms.model.service.SecondaryQuestionService;
import br.ufs.dcomp.farms.model.service.SelectionCriteriaService;
import br.ufs.dcomp.farms.model.service.StandardQueryService;

/**
 * @author farms
 *
 */
@Path("/protocol")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class ProtocolResource {

	// final static Logger logger = Logger.getLogger(ProtocolResource.class);
	@Autowired
	private ObjectiveService objectiveService;
	@Autowired
	private MainQuestionService mainQuestionService;
	@Autowired
	private SecondaryQuestionService secondaryQuestionService;
	@Autowired
	private SearchKeywordService searchKeywordService;
	@Autowired
	private LanguageService languageService;
	@Autowired
	private StandardQueryService standardQueryService;
	@Autowired
	private SelectionCriteriaService selectionCriteriaService;
	@Autowired
	private SearchEngineService searchEngineService;

	/**
	 * Get objectives, part of protocol of a project.
	 *
	 * @param dsKey
	 * @return Response
	 */
	@GET
	@Path("/{dsKey}/objectives")
	public Response getObjectivesByDsKeyProject(@PathParam("dsKey") String dsKey) {
		try {
			List<ObjectiveCreatedDto> objectiveCreatedDtos = objectiveService.getByDsKeyProject(dsKey);
			return FarmsResponse.ok(objectiveCreatedDtos);
		} catch (Exception ex) {
			// logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Get main question, part of protocol of a project.
	 *
	 * @param dsKey
	 * @return Response
	 */
	@GET
	@Path("/{dsKey}/main-question")
	public Response getMainQuestionByDsKeyProject(@PathParam("dsKey") String dsKey) {
		try {
			List<MainQuestionCreatedDto> mainQuestionCreatedDtos = mainQuestionService.getByDsKeyProject(dsKey);
			return FarmsResponse.ok(mainQuestionCreatedDtos);
		} catch (Exception ex) {
			// logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Get secondary question, part of protocol of a project.
	 *
	 * @param dsKey
	 * @return Response
	 */
	@GET
	@Path("/{dsKey}/secondary-question")
	public Response getSecondaryQuestionByDsKeyProject(@PathParam("dsKey") String dsKey) {
		try {
			List<SecondaryQuestionCreatedDto> secondaryQuestionCreatedDtos = secondaryQuestionService
					.getByDsKeyProject(dsKey);
			return FarmsResponse.ok(secondaryQuestionCreatedDtos);
		} catch (Exception ex) {
			// logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Get keywords, part of protocol of a project.
	 *
	 * @param dsKey
	 * @return Response
	 */
	@GET
	@Path("/{dsKey}/search-keywords")
	public Response getSearchKeywordsByDsKeyProject(@PathParam("dsKey") String dsKey) {
		try {
			List<SearchKeywordCreatedDto> searchKeywordCreatedDtos = searchKeywordService.getByDsKeyProject(dsKey);
			return FarmsResponse.ok(searchKeywordCreatedDtos);
		} catch (Exception ex) {
			// logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Get standard query, part of protocol of a project.
	 *
	 * @param dsKey
	 * @return Response
	 */
	@GET
	@Path("/{dsKey}/standard-query")
	public Response getStandardQueryByDsKeyProject(@PathParam("dsKey") String dsKey) {
		try {
			List<StandardQueryCreatedDto> standardQueryCreatedDtos = standardQueryService.getByDsKeyProject(dsKey);
			return FarmsResponse.ok(standardQueryCreatedDtos);
		} catch (Exception ex) {
			// logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Get selection studies criterias, part of protocol of a project.
	 *
	 * @param dsKey
	 * @return Response
	 */
	@GET
	@Path("/{dsKey}/selection-criterias")
	public Response getSelectionCriteriasByDsKeyProject(@PathParam("dsKey") String dsKey) {
		try {
			List<SelectionCriteriaCreatedDto> selectionCriteriaCreatedDtos = selectionCriteriaService
					.getByDsKeyProject(dsKey);
			return FarmsResponse.ok(selectionCriteriaCreatedDtos);
		} catch (Exception ex) {
			// logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Get languages of selection studies, part of protocol of a project.
	 *
	 * @param dsKey
	 * @return Response
	 */
	@GET
	@Path("/{dsKey}/languages")
	public Response getLanguagesByDsKeyProject(@PathParam("dsKey") String dsKey) {
		try {
			List<LanguageCreatedDto> languageCreatedDtos = languageService.getByDsKeyProject(dsKey);
			return FarmsResponse.ok(languageCreatedDtos);
		} catch (Exception ex) {
			// logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Get search engines selection, part of protocol of a project.
	 *
	 * @param dsKey
	 * @return Response
	 */
	@GET
	@Path("/{dsKey}/search-engine")
	public Response getEnginesByDsKeyProject(@PathParam("dsKey") String dsKey) {
		try {
			List<SearchEngineCreatedDto> searchEngineCreatedDtos = searchEngineService.getByDsKeyProject(dsKey);
			return FarmsResponse.ok(searchEngineCreatedDtos);
		} catch (Exception ex) {
			// logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Get all languages registered.
	 *
	 * @return Response
	 */
	@GET
	@Path("/allLanguages")
	public Response getAllLanguages() {
		try {
			List<Language> languageCreatedDtos = languageService.getAllLanguages();
			return FarmsResponse.ok(languageCreatedDtos);
		} catch (Exception ex) {
			// logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Get all search engines registered.
	 *
	 * @return Response
	 */
	@GET
	@Path("/allEngines")
	public Response getAllEngines() {
		try {
			List<SearchEngine> engines = searchEngineService.getAllEngines();
			return FarmsResponse.ok(engines);
		} catch (Exception ex) {
			// logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	// **********************************POST's*****************//
	/**
	 * Save a objective of protocol project.
	 *
	 * @param obcd
	 * @return Response
	 */
	@POST
	@Path("/objectives")
	public Response saveObjective(ObjectiveCreateDto obcd) {
		try {
			Boolean bool = objectiveService.saveObjective(obcd);
			return FarmsResponse.ok(SuccessMessage.OBJECTIVE_SAVED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Save a main question of protocol project.
	 *
	 * @param mqcd
	 * @return Response
	 */
	@POST
	@Path("/mainQuestion")
	public Response saveMainQuestion(MainQuestionCreatedDto mqcd) {
		try {
			Boolean bool = mainQuestionService.saveMainQuestion(mqcd);
			return FarmsResponse.ok(SuccessMessage.MAIN_QUESTION_SAVED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Save a secondary question of protocol project.
	 *
	 * @param sqcd
	 * @return Response
	 */
	@POST
	@Path("/secondaryQuestion")
	public Response saveSecondaryQuestion(SecondaryQuestionCreatedDto sqcd) {
		try {
			Boolean bool = secondaryQuestionService.saveSecondaryQuestion(sqcd);
			return FarmsResponse.ok(SuccessMessage.SECONDARY_QUESTION_SAVED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Save standard query of protocol project.
	 *
	 * @param stqcd
	 * @return Response
	 */
	@POST
	@Path("/standardQuery")
	public Response saveStandardQuery(StandardQueryCreatedDto stqcd) {
		try {
			Boolean bool = standardQueryService.saveStandardQuery(stqcd);
			return FarmsResponse.ok(SuccessMessage.STANDARD_QUERY_SAVED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Save keyword of protocol project.
	 *
	 * @param searchKeywords
	 * @return Response
	 */
	@POST
	@Path("/searchKeywords")
	public Response saveKeyword(SearchKeywordCreatedDto searchKeywords) {
		try {
			Boolean bool = searchKeywordService.saveKeyword(searchKeywords);
			return FarmsResponse.ok(SuccessMessage.KEYWORD_SAVED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Save selections criterias of protocol project.
	 *
	 * @param selectionCriterias
	 * @return Response
	 */
	@POST
	@Path("/selectionCriterias")
	public Response saveCriteria(SelectionCriteriaCreatedDto selectionCriterias) {
		try {
			Boolean bool = selectionCriteriaService.saveCriteria(selectionCriterias);
			return FarmsResponse.ok(SuccessMessage.CRITERIA_SAVED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Save languages of protocol project.
	 *
	 * @param languages
	 * @return Response
	 */
	@POST
	@Path("/languages")
	public Response saveLanguage(StudyLanguageCreatedDto languages) {
		try {
			Boolean bool = languageService.saveLanguage(languages);
			return FarmsResponse.ok(SuccessMessage.LANGUAGE_ADDED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Save engines of protocol project.
	 *
	 * @param baseUseCriteriaCreatedDto
	 * @return Response
	 */
	@POST
	@Path("/engines")
	public Response saveEngine(BaseUseCriteriaCreatedDto baseUseCriteriaCreatedDto) {
		try {
			Boolean bool = searchEngineService.saveEngine(baseUseCriteriaCreatedDto);
			return FarmsResponse.ok(SuccessMessage.ENGINE_ADDED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Create engines
	 *
	 * @param engine
	 *            name
	 * @return Response
	 */
	@POST
	@Path("/createEngine/{engine}")
	public Response createEngine(@PathParam("engine") String engine) {
		try {
			Boolean bool = searchEngineService.createEngine(engine);
			return FarmsResponse.ok(SuccessMessage.ENGINE_CREATED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Delete keyword of protocol project
	 *
	 * @param dsProjectKey
	 * @param dsSearchKeyword
	 * @param idSearchKeyword
	 * @return
	 */
	@DELETE
	@Path("/deleteKeyword/{dsProjectKey}/{dsSearchKeyword}/{idSearchKeyword}")
	public Response deleteKeyword(@PathParam("dsProjectKey") String dsProjectKey,
			@PathParam("dsSearchKeyword") String dsSearchKeyword, @PathParam("idSearchKeyword") Long idSearchKeyword) {
		try {
			Boolean bool = searchKeywordService.deleteKeyword(dsProjectKey, dsSearchKeyword, idSearchKeyword);
			return FarmsResponse.ok(SuccessMessage.KEYWORD_DELETED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Delete language of protocol project
	 *
	 * @param dsProjectKey
	 * @param idLanguage
	 * @return
	 */
	@DELETE
	@Path("/deleteLanguage/{dsProjectKey}/{idLanguage}")
	public Response deleteLanguage(@PathParam("dsProjectKey") String dsProjectKey,
			@PathParam("idLanguage") Long idLanguage) {
		try {
			Boolean bool = languageService.deleteLanguage(dsProjectKey, idLanguage);
			return FarmsResponse.ok(SuccessMessage.LANGUAGE_DELETED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Delete engine of protocol project
	 *
	 * @param dsProjectKey
	 * @param idEngine
	 * @return
	 */
	@DELETE
	@Path("/deleteEngine/{dsProjectKey}/{idEngine}")
	public Response deleteEngine(@PathParam("dsProjectKey") String dsProjectKey, @PathParam("idEngine") Long idEngine) {
		try {
			Boolean bool = searchEngineService.deleteEngine(dsProjectKey, idEngine);
			return FarmsResponse.ok(SuccessMessage.ENGINE_DELETED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Delete selection criteria of protocol project
	 *
	 * @param dsProjectKey
	 * @param dsSelectionCriteria
	 * @param tpCriteria
	 * @return
	 */
	@DELETE
	@Path("/deleteSelectionCriteria/{dsProjectKey}/{dsSelectionCriteria}/{tpCriteria}")
	public Response deleteSelectionCriteria(@PathParam("dsProjectKey") String dsProjectKey,
			@PathParam("dsSelectionCriteria") String dsSelectionCriteria, @PathParam("tpCriteria") Long tpCriteria) {
		try {
			Boolean bool = selectionCriteriaService.deleteCriteria(dsProjectKey, dsSelectionCriteria, tpCriteria);
			return FarmsResponse.ok(SuccessMessage.CRITERIA_DELETED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Edit keyword
	 *
	 * @param searchKeywordCreatedDto
	 * @return
	 */
	@PUT
	@Path("/editKeyword")
	public Response editKeyword(SearchKeywordCreatedDto searchKeywordCreatedDto) {
		try {
			Boolean bool = searchKeywordService.editKeyword(searchKeywordCreatedDto);
			return FarmsResponse.ok(SuccessMessage.KEYWORD_SAVED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Edit engine of protocol project
	 *
	 * @param searchEngineCreatedDto
	 * @return
	 */
	@PUT
	@Path("/editEngine")
	public Response editEngine(SearchEngineCreatedDto searchEngineCreatedDto) {
		try {
			Boolean bool = searchEngineService.editEngine(searchEngineCreatedDto);
			return FarmsResponse.ok(SuccessMessage.ENGINE_ADDED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Edit selection criteria of protocol project
	 *
	 * @param selectionCriteriaCreatedDto
	 * @return
	 */
	@PUT
	@Path("/editCriteria")
	public Response editCriteria(SelectionCriteriaCreatedDto selectionCriteriaCreatedDto) {
		try {
			Boolean bool = selectionCriteriaService.editCriteria(selectionCriteriaCreatedDto);
			return FarmsResponse.ok(SuccessMessage.CRITERIA_SAVED, bool);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() + " " + ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

}
