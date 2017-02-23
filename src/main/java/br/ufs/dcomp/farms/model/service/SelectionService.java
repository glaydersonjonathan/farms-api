package br.ufs.dcomp.farms.model.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dao.SelectionStepDao;
import br.ufs.dcomp.farms.model.dto.SelectionStepCreatedDto;
import br.ufs.dcomp.farms.model.entity.RatedContent;
import br.ufs.dcomp.farms.model.entity.SelectionStep;
import br.ufs.dcomp.farms.model.enums.SelectionStepStatusEnum;

@Component
public class SelectionService {

	@Autowired
	SelectionStepDao selectionStepDao;
	@Autowired
	ProjectDao projectDao;

	/**
	 *  Save configuration of selection Step of a project
	 * @param selectionCreateDto
	 * @return
	 */
	public Boolean save(SelectionStepCreatedDto selectionCreateDto) {
		SelectionStep selectionStep = new SelectionStep();

		selectionStep.setDhConflictsSolvingEnd(selectionCreateDto.getDhConflictsSolvingEnd());
		selectionStep.setDhEndSelectionStep(selectionCreateDto.getDhEndSelectionStep());
		selectionStep.setDhReviewEnd(selectionCreateDto.getDhReviewEnd());
		selectionStep.setDhStartSelectionStep(selectionCreateDto.getDhStartSelectionStep());
		selectionStep.setQtReview(selectionCreateDto.getQtReview());
		//selectionStep.setProject(projectDao.getByDsKey(selectionCreateDto.getDsKey()));
		selectionStep.setProject(projectDao.get(selectionCreateDto.getIdProject()));

		// verificar inicio
		RatedContent ratedContent = new RatedContent();
		//ratedContent.setIdRatedContent(Long.parseLong(selectionCreateDto.getDsRatedContent()));
		ratedContent.setIdRatedContent(selectionCreateDto.getIdRatedContent());
		selectionStep.setRatedContent(ratedContent);
		selectionStep.setNrSerial(0);
		selectionStep.setVlLowerScore(1);
		selectionStep.setTpStatus(SelectionStepStatusEnum.ASSIGNED);
		// verificar fim

       selectionStepDao.delete(selectionCreateDto.getIdProject()); //delete if exists 
		
		selectionStepDao.save(selectionStep);

		return true;
	}

	/**
	 * Get configuration of selection Step of a project
	 * @param dsKey
	 * @return
	 */
	public SelectionStepCreatedDto getConfiguration(String dsKey) {
		SelectionStep selectionStep = selectionStepDao.getConfiguration(dsKey);
		SelectionStepCreatedDto selectionStepCreatedDto = new SelectionStepCreatedDto();
			selectionStepCreatedDto = new SelectionStepCreatedDto(selectionStep);
		return selectionStepCreatedDto;
	}

}
