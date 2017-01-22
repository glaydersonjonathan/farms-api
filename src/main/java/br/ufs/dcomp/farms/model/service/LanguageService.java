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

@Component
public class LanguageService {

	@Autowired
	private LanguageDao languageDao;
	@Autowired
	private ProjectDao projectDao;

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

	
	
	
	
	
	public List<Language> getAllLanguages() {
		return languageDao.getAllLanguages();
	}
	
	
	
	
	
	

	public Boolean saveLanguage(StudyLanguageCreatedDto languages) {
		StudyLanguage studyLanguage = new StudyLanguage();
		
		Language language  = new Language(languages.getLanguage());
		Project project =  projectDao.getByDsKey(languages.getDsProjectKey());
		
		
		studyLanguage.setLanguage(language);
		studyLanguage.setProject(project);		
		
		languageDao.saveStudyLanguage(studyLanguage);
		return true;
	}
}