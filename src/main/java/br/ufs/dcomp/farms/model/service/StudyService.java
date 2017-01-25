package br.ufs.dcomp.farms.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.dao.StudyDao;
import br.ufs.dcomp.farms.model.dto.StudyCreatedDto;
import br.ufs.dcomp.farms.model.entity.Study;

@Component
public class StudyService {

	@Autowired
	private StudyDao studyDao;

	public List<StudyCreatedDto> getByDsKeyProject(String dsKey) {
		List<StudyCreatedDto> studyCreatedDto = new ArrayList<StudyCreatedDto>();
		List<Study> studies = studyDao.getByDsKeyProject(dsKey);
		if (studies != null) {
			for(Study study : studies) {
				studyCreatedDto.add(new StudyCreatedDto(study));
				System.out.println(study.getCdCiteKey());
				System.out.println(study.getCdDoi());
				System.out.println(study.getCdIssnIsbn());
				System.out.println(study.getDsAbstract());
				System.out.println(study.getDsComment());
				System.out.println(study.getDsJournal());
				System.out.println(study.getDsKeyword());
				System.out.println(study.getDsPage());
				System.out.println(study.getDsTitle());
				System.out.println(study.getDsType());
				System.out.println(study.getDsUrl());
				System.out.println(study.getDsVolume());
				System.out.println(study.getNmAuthor());
				System.out.println(study.getIdStudy());
				System.out.println(study.getNrYear());
				System.out.println(study.getProject());
				System.out.println(study.getSearch());
				System.out.println(study.getTpReadingRate());
				System.out.println(study.getTpStatus());
				System.out.println(study.getTpVenue());
			}
		}
		return studyCreatedDto;
	}
}