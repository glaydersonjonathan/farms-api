package br.ufs.dcomp.farms.rest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import br.ufs.dcomp.farms.core.FarmsResponse;
import br.ufs.dcomp.farms.model.dto.StudyCreateDto;
import br.ufs.dcomp.farms.model.dto.StudyCreatedDto;
import br.ufs.dcomp.farms.model.service.StudyService;

/**
 * @author farms
 *
 */
@Path("/studies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class StudyResource {
	@Autowired
	private StudyService studyService;
	final static Logger logger = Logger.getLogger(ProjectResource.class);

	/**
	 * Get studies of project.
	 * 
	 * @param dsKey
	 * @return
	 */
	@GET
	@Path("/{dsKey}")
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
	public Response createStudy(StudyCreateDto studycreateDto) {
		try {
			Boolean bool = studyService.save(studycreateDto);
			return FarmsResponse.ok(SuccessMessage.STUDY_CREATED, bool);
		} //catch (FarmsException fe){
			//return FarmsResponse.error(fe.getErrorMessage());
		//}
		catch (Exception ex) {
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}
	
	
	
	@GET
	@Path("/read/{cdCiteKey}")
	public Response readStudy(@PathParam("cdCiteKey") String cdCiteKey) {
		try {
			StudyCreatedDto study = studyService.getStudyByCdciteKey(cdCiteKey);
			return FarmsResponse.ok(study);
		} catch (Exception ex) {
			logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}
	
	
	

	@POST
	@Path("/upload-study")
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
