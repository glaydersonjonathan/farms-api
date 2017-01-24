package br.ufs.dcomp.farms.rest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.common.message.ErrorMessage;
import br.ufs.dcomp.farms.common.message.SuccessMessage;
import br.ufs.dcomp.farms.core.FarmsException;
import br.ufs.dcomp.farms.core.FarmsResponse;
import br.ufs.dcomp.farms.model.dto.InstitutionCreatedDto;
import br.ufs.dcomp.farms.model.dto.ProjectCreateDto;
import br.ufs.dcomp.farms.model.dto.ProjectCreatedDto;
import br.ufs.dcomp.farms.model.dto.ProjectMemberDto;
import br.ufs.dcomp.farms.model.dto.ProjectMemberInviteDto;
import br.ufs.dcomp.farms.model.dto.StudyCreatedDto;
import br.ufs.dcomp.farms.model.service.InstitutionService;
import br.ufs.dcomp.farms.model.service.ProjectMemberService;
import br.ufs.dcomp.farms.model.service.ProjectService;
import br.ufs.dcomp.farms.model.service.StudyService;

/**
 * @author farms
 *
 */
@Path("/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class ProjectResource {

	final static Logger logger = Logger.getLogger(ProjectResource.class);

	@Autowired
	private ProjectService projectService;
	@Autowired
	private StudyService studyService;
	@Autowired
	private ProjectMemberService projectMemberService;
	@Autowired
	private InstitutionService institutuionService;

	/**
	 * Receive a request from client to create a project.
	 * 
	 * @param projectCreateDto
	 * @return Response
	 */
	@POST
	public Response createProject(ProjectCreateDto projectCreateDto) {
		projectCreateDto.setTpReview((Integer) projectCreateDto.getTpReview());
		try {
			ProjectCreatedDto projectCreatedDto = projectService.save(projectCreateDto);
			return FarmsResponse.ok(SuccessMessage.PROJECT_REGISTERED, projectCreatedDto);
		} catch (FarmsException fe){
			return FarmsResponse.error(fe.getErrorMessage());
		}
		catch (Exception ex) {
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Receive a request from client to update a project.
	 * 
	 * @param projectCreatedDto
	 * @return Response
	 */
	@PUT
	public Response updateProject(ProjectCreatedDto projectCreatedDto) {
		try {
			Boolean bool = projectService.update(projectCreatedDto);
			return FarmsResponse.ok(SuccessMessage.PROJECT_UPDATED, bool);
		}catch (FarmsException fe){
			return FarmsResponse.error(fe.getErrorMessage());
		}
		
		catch (Exception ex) {
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Receive a request from client to get a project.
	 * 
	 * @param String
	 *            dsKey
	 * @return Response
	 */
	@GET
	@Path("/{dsKey}")
	public Response getProjectByDsKey(@PathParam("dsKey") String dsKey) {
		try {
			ProjectCreatedDto projectCreatedDto = projectService.getByDsKey(dsKey);
			return FarmsResponse.ok(projectCreatedDto);
		} catch (Exception ex) {
			logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Get all projects of researcher.
	 * 
	 * @param dsSSO
	 * @return Response
	 */
	@GET
	@Path("/{dsSSO}/projects")
	public Response GetByDsSsoResearcher(@PathParam("dsSSO") String dsSSO) {
		try {
			List<ProjectCreatedDto> projectCreatedDtos = projectService.GetByDsSsoResearcher(dsSSO);
			return FarmsResponse.ok(projectCreatedDtos);
		} catch (Exception ex) {
			logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Get all institutions of a project.
	 * 
	 * @param dsKey
	 * @return Response
	 */
	@GET
	@Path("/{dsKey}/institutions")
	public Response getInstitutionsByDsKeyProject(@PathParam("dsKey") String dsKey) {
		if (dsKey.equals("null")) {
			return FarmsResponse.error(ErrorMessage.NO_PROJECT_OPEN);
		}

		try {
			List<InstitutionCreatedDto> institutionCreatedDtos = institutuionService.getByDsKeyProject(dsKey);
			return FarmsResponse.ok(institutionCreatedDtos);
		} catch (Exception ex) {
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Get all members of project.
	 * 
	 * @param dsKey
	 * @return Response
	 */
	@GET
	@Path("/{dsKey}/members")
	public Response getMembersByDsKeyProject(@PathParam("dsKey") String dsKey) {
		if (dsKey.equals("null")) {
			return FarmsResponse.error(ErrorMessage.NO_PROJECT_OPEN);
		}

		try {
			List<ProjectMemberDto> projectMemberDtos = projectMemberService.getByDsKeyProject(dsKey);
			return FarmsResponse.ok(projectMemberDtos);
		} catch (Exception ex) {
			logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Invite a member to a project.
	 * 
	 * @param ProjectMemberInviteDto
	 * @return Response
	 */
	@POST
	@Path("/members/invite")
	public Response createProject(ProjectMemberInviteDto pm) {
		try {
			Boolean bool = projectMemberService.invite(pm);
			return FarmsResponse.ok(SuccessMessage.MEMBER_ADDED, bool);
		} catch (FarmsException fe) {
			return FarmsResponse.error(ErrorMessage.MEMBER_NOT_FOUND);
		} catch (Exception ex) {
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	//verificar
	@GET
	@Path("/{dsKey}/role/{dsUserName}")
	public Response getRoleResearcher(@PathParam("idProject") String dsKey, @PathParam ("dsUserName") String dsUserName) {
		try {
			int roleCode = projectMemberService.getRole(dsKey, dsUserName);
			return FarmsResponse.ok(roleCode);
		} catch (Exception ex) {
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	// ***** DAQUI PRA BAIXO NECESSITA VERIFICAR *****
	// verificar
	// projects/{dsKey}/studies
	@GET
	@Path("/{dsKey}/studies")
	public Response getStudiesByDsKeyProject(@PathParam("dsKey") String dsKey) {
		try {
			List<StudyCreatedDto> studyCreatedDtos = studyService.getByDsKeyProject(dsKey);
			return FarmsResponse.ok(studyCreatedDtos);
		} catch (Exception ex) {
			logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	@POST
	@Path("/{dsKey}/upload-study")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream file,
			@FormDataParam("file") FormDataContentDisposition fileDisposition) {

		String fileName = fileDisposition.getFileName();

		saveFile(file, fileName);

		String fileDetails = "File saved at /Volumes/Drive2/temp/file/" + fileName;

		System.out.println(fileDetails);

		return Response.ok(fileDetails).build();
	}

	private void saveFile(InputStream file, String name) {
		try {
			// Change directory path
			java.nio.file.Path path = FileSystems.getDefault().getPath("/Volumes/Drive2/temp/file/" + name);
			// Save InputStream as file
			Files.copy(file, path);
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}
}