package br.ufs.dcomp.farms.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.dao.LanguageDao;
import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dto.LanguageCreatedDto;
import br.ufs.dcomp.farms.model.dto.StudyLanguageCreatedDto;
import br.ufs.dcomp.farms.model.entity.Language;
import br.ufs.dcomp.farms.model.entity.Project;
import br.ufs.dcomp.farms.model.entity.StudyLanguage;

/**
 * @author farms
 *
 */
@Component
public class LanguageService {

	@Autowired
	private LanguageDao languageDao;
	@Autowired
	private ProjectDao projectDao;

	/**
	 * Get all languages of protocol project
	 * 
	 * @param dsKey
	 * @return List<LanguageCreatedDto>
	 */
	public List<LanguageCreatedDto> getByDsKeyProject(String dsKey) {
		List<LanguageCreatedDto> languageCreatedDtos = new ArrayList<LanguageCreatedDto>();
		List<StudyLanguage> studylanguages = languageDao.getByDsKeyProject(dsKey);
		if (studylanguages != null) {
			for (StudyLanguage language : studylanguages) {
				languageCreatedDtos.add(new LanguageCreatedDto(language));
			}
		}
		return languageCreatedDtos;
	}

	/**
	 * Get all languages registered
	 * 
	 * @return
	 */
	public List<Language> getAllLanguages() {
		return languageDao.getAllLanguages();
	}

	/**
	 * Add language a project
	 * @param languages
	 * @return boolean
	 */
	public Boolean saveLanguage(StudyLanguageCreatedDto studyLanguageCreatedDto) {
		StudyLanguage studyLanguage = new StudyLanguage();

		Language language = new Language(studyLanguageCreatedDto.getLanguage());
		Project project = projectDao.getByDsKey(studyLanguageCreatedDto.getDsProjectKey());

		studyLanguage.setLanguage(language);
		studyLanguage.setProject(project);

		languageDao.saveStudyLanguage(studyLanguage);
		return true;
	}

	/**
	 * Delete language of protocol project
	 * @param dsProjectKey
	 * @param idLanguage
	 * @return
	 */
	public Boolean deleteLanguage(String dsProjectKey, Long idLanguage) {
		
		languageDao.deleteLanguage(projectDao.getByDsKey(dsProjectKey).getIdProject(), idLanguage);
		return true;
	}
}