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

/**
 * @author farms
 *
 */
@Component
public class SelectionCriteriaService {

	@Autowired
	private SelectionCriteriaDao selectionCriteriaDao;
	@Autowired
	private ProjectDao projectDao;

	/**
	 * Get selection criterias of protocol project
	 * 
	 * @param dsKey
	 * @return List<SelectionCriteriaCreatedDto>
	 */
	public List<SelectionCriteriaCreatedDto> getByDsKeyProject(String dsKey) {
		List<SelectionCriteriaCreatedDto> selectionCriteriaCreatedDtos = new ArrayList<SelectionCriteriaCreatedDto>();
		List<SelectionCriteria> selectionCriterias = selectionCriteriaDao.getByDsKeyProject(dsKey);
		if (selectionCriterias != null) {
			for (SelectionCriteria selectionCriteria : selectionCriterias) {
				selectionCriteriaCreatedDtos.add(new SelectionCriteriaCreatedDto(selectionCriteria));
			}
		}
		return selectionCriteriaCreatedDtos;
	}

	/**
	 * Save selection criteria of protocol project
	 * 
	 * @param selectionCriteriaCreatedDto
	 * @return boolean
	 */
	public Boolean saveCriteria(SelectionCriteriaCreatedDto selectionCriteriaCreatedDto) {
		Project project = projectDao.getByDsKey(selectionCriteriaCreatedDto.getDsProjectKey());

		SelectionCriteria criteria = new SelectionCriteria(selectionCriteriaCreatedDto.getDsSelectionCriteria(),
				CriteriaEnum.fromCode(selectionCriteriaCreatedDto.getTpCriteria()), project);

		selectionCriteriaDao.save(criteria);
		return true;
	}

	/**
	 * Delete criteria of protocol project
	 * @param dsProjectKey
	 * @param idSelectionCriteria
	 * @return
	 */
	public Boolean deleteCriteria(String dsProjectKey, String dsSelectionCriteria, Long tpCriteria) {
		selectionCriteriaDao.deleteCriteria(projectDao.getByDsKey(dsProjectKey).getIdProject(), dsSelectionCriteria, tpCriteria);
		return true;
	}

	/**
	 * Edit selection criteria of protocol project
	 * @param selectionCriteriaCreatedDto
	 * @return
	 */
	public Boolean editCriteria(SelectionCriteriaCreatedDto selectionCriteriaCreatedDto) {
		selectionCriteriaDao.editCriteria(projectDao.getByDsKey(selectionCriteriaCreatedDto.getDsProjectKey()).getIdProject(),selectionCriteriaCreatedDto);
		return true;
	}
}