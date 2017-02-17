package br.ufs.dcomp.farms.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dao.SelectionStepDao;
import br.ufs.dcomp.farms.model.dto.SelectionStepCreateDto;
import br.ufs.dcomp.farms.model.entity.RatedContent;
import br.ufs.dcomp.farms.model.entity.SelectionStep;
import br.ufs.dcomp.farms.model.enums.RatedContentEnum;


@Component
public class SelectionService {

	@Autowired
	SelectionStepDao selectionStepDao;
	@Autowired
	ProjectDao projectDao;
	
	public Boolean save(SelectionStepCreateDto selectionCreateDto) {
		SelectionStep selectionStep =  new SelectionStep();
		
		selectionStep.setDhConflictsSolvingEnd(selectionCreateDto.getDhConflictsSolvingEnd());
		selectionStep.setDhEndSelectionStep(selectionCreateDto.getDhEndSelectionStep());
		selectionStep.setDhReviewEnd(selectionCreateDto.getDhReviewEnd());
		selectionStep.setDhStartSelectionStep(selectionCreateDto.getDhStartSelectionStep());
		selectionStep.setQtReview(selectionCreateDto.getQtReview());
		selectionStep.setProject(projectDao.getByDsKey(selectionCreateDto.getDsKey()));
		
		RatedContent ratedContent =  new  RatedContent(RatedContentEnum.fromCode(selectionCreateDto.getDsRatedContent()));
		ratedContent.setIdRatedContent(Long.parseLong(selectionCreateDto.getDsRatedContent())); //pog
		selectionStep.setRatedContent(ratedContent);
		
		selectionStepDao.save(selectionStep);
		
		return true;
	}

}
