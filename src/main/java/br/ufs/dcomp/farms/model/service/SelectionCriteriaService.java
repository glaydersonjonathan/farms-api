package br.ufs.dcomp.farms.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.ufs.dcomp.farms.model.enums.CriteriaEnum;

import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dao.SelectionCriteriaDao;
import br.ufs.dcomp.farms.model.dto.SelectionCriteriaCreatedDto;
import br.ufs.dcomp.farms.model.entity.Project;
import br.ufs.dcomp.farms.model.entity.SelectionCriteria;

@Component
public class SelectionCriteriaService {

	@Autowired
	private SelectionCriteriaDao selectionCriteriaDao;
	@Autowired
	private ProjectDao projectDao;

	public List<SelectionCriteriaCreatedDto> getByDsKeyProject(String dsKey) {
		List<SelectionCriteriaCreatedDto> selectionCriteriaCreatedDtos = new ArrayList<SelectionCriteriaCreatedDto>();
		List<SelectionCriteria> selectionCriterias = selectionCriteriaDao.getByDsKeyProject(dsKey);
		if (selectionCriterias != null) {
			for(SelectionCriteria selectionCriteria : selectionCriterias) {
				selectionCriteriaCreatedDtos.add(new SelectionCriteriaCreatedDto(selectionCriteria));
			}
		}
		return selectionCriteriaCreatedDtos;
	}

	public Boolean saveCriteria(SelectionCriteriaCreatedDto sc) {
		Project project = projectDao.getByDsKey(sc.getDsProjectKey());

		SelectionCriteria criteria = new SelectionCriteria(sc.getDsSelectionCriteria(), CriteriaEnum.fromCode(sc.getTpCriteria()), project);

		selectionCriteriaDao.save(criteria);
		return true;
	}
}