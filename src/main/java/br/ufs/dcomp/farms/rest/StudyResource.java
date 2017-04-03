package br.ufs.dcomp.farms.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
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
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.ufs.dcomp.farms.common.message.ErrorMessage;
import br.ufs.dcomp.farms.common.message.SuccessMessage;
import br.ufs.dcomp.farms.core.FarmsException;
import br.ufs.dcomp.farms.core.FarmsMail;
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
	//final static Logger logger = Logger.getLogger(ProjectResource.class);

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
			//logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Add study manually.
	 * 
	 * @param studycreateDto
	 * @return
	 */
	@POST
	public Response createStudy(StudyCreateDto studycreateDto) {
		try {
			Boolean bool = studyService.save(studycreateDto);
			return FarmsResponse.ok(SuccessMessage.STUDY_CREATED, bool);
		} catch (FarmsException fe) {
			return FarmsResponse.error(fe.getErrorMessage());
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Update a study
	 * 
	 * @param studycreatedDto
	 * @return
	 */
	@PUT
	public Response editStudy(StudyCreatedDto studycreatedDto) {
		try {
			Boolean bool = studyService.editStudy(studycreatedDto);
			return FarmsResponse.ok(SuccessMessage.STUDY_CREATED, bool);
		} 
		catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Delete study
	 * 
	 * @param idStudy
	 * @return
	 */
	@DELETE
	@Path("/{idStudy}")
	public Response deleteStudy(@PathParam("idStudy") Long idStudy) {
		try {
			Boolean bool = studyService.deleteStudy(idStudy);
			return FarmsResponse.ok(SuccessMessage.STUDY_DELETED, bool);
		} catch (FarmsException fe) {
			return FarmsResponse.error(fe.getErrorMessage());
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}

	}

	/**
	 * Get details os study by citeKey
	 * 
	 * @param cdCiteKey
	 * @return
	 */
	@GET
	@Path("/read/{cdCiteKey}")
	public Response readStudy(@PathParam("cdCiteKey") String cdCiteKey) {
		try {
			StudyCreatedDto study = studyService.getStudyByCdciteKey(cdCiteKey);
			return FarmsResponse.ok(study);
		} catch (Exception ex) {
			//logger.error(ErrorMessage.OPERATION_NOT_RESPONDING, ex);
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Receive a file to import.
	 * @param dsKey
	 * @param uploadedInputStream
	 * @param fileDetail
	 * @return
	 */
	@POST
	@Path("/upload-study/{dsKey}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@PathParam("dsKey") String dsKey, @FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		
		// turn name unique
		String fileName = System.currentTimeMillis() + fileDetail.getFileName();
		String dir = "/farms/" + dsKey;
		
		saveFile(uploadedInputStream, dir, fileName);

		try {
			Integer total = studyService.importStudies(dir+ "/" + fileName, dsKey);
			return FarmsResponse.ok(SuccessMessage.STUDY_IMPORTED, total);
		} catch (Exception ex) {
			FarmsMail.sendMailText("contact.farms@gmail.com", "Erro", ex.getMessage() +" "+ ex.toString());
			return FarmsResponse.error(ErrorMessage.OPERATION_NOT_RESPONDING);
		}
	}

	/**
	 * Save file in system
	 * @param file
	 * @param dir
	 * @param filename
	 */
	private void saveFile(InputStream file, String dir, String filename) {
		try {
			// make dir
			new File(dir).mkdirs();
			// Change directory path
			java.nio.file.Path path = FileSystems.getDefault().getPath(dir+ "/" + filename);
			// Save InputStream as file
			Files.copy(file, path);
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

}
