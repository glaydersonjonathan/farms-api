package br.ufs.dcomp.farms.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.ufs.dcomp.farms.common.message.ErrorMessage;
import br.ufs.dcomp.farms.core.FarmsException;
import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dao.SearchDao;
import br.ufs.dcomp.farms.model.dao.StudyDao;
import br.ufs.dcomp.farms.model.dto.StudyCreateDto;
import br.ufs.dcomp.farms.model.dto.StudyCreatedDto;
import br.ufs.dcomp.farms.model.entity.Project;
import br.ufs.dcomp.farms.model.entity.Search;
import br.ufs.dcomp.farms.model.entity.Study;
import br.ufs.dcomp.farms.model.enums.ReadingRateEnum;
import br.ufs.dcomp.farms.model.enums.SearchEnum;
import br.ufs.dcomp.farms.model.enums.StudyStatusEnum;
import br.ufs.dcomp.farms.model.enums.VenueEnum;

@Component
public class StudyService {

	@Autowired
	private StudyDao studyDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private SearchDao searchDao;

	/**
	 * Get all studies of project
	 * 
	 * @param dsKey
	 * @return
	 */
	public List<StudyCreatedDto> getByDsKeyProject(String dsKey) {
		List<StudyCreatedDto> studyCreatedDto = new ArrayList<StudyCreatedDto>();
		List<Study> studies = studyDao.getByDsKeyProject(dsKey);
		if (studies != null) {
			for (Study study : studies) {
				studyCreatedDto.add(new StudyCreatedDto(study));
			}
		}
		return studyCreatedDto;
	}

	public StudyCreatedDto getStudyByCdciteKey(String cdCiteKey) {
		Study study = studyDao.getByCdCiteKey(cdCiteKey);
		StudyCreatedDto studyCreatedDto = new StudyCreatedDto(study);
		return studyCreatedDto;
	}

	@Transactional(rollbackFor = Exception.class)
	public Boolean save(StudyCreateDto studycreateDto) throws FarmsException {

		Study study = new Study();
		study.setCdCiteKey(studycreateDto.getCdCiteKey());
		study.setCdDoi(studycreateDto.getCdDoi());
		study.setCdIssnIsbn(studycreateDto.getCdIssnIsbn());
		study.setDsAbstract(studycreateDto.getDsAbstract());
		study.setDsComment(studycreateDto.getDsComment());
		study.setDsJournal(studycreateDto.getDsJournal());
		study.setDsKeyword(studycreateDto.getDsKeyword());
		study.setDsPage(studycreateDto.getDsPage());
		study.setDsTitle(studycreateDto.getDsTitle());
		study.setDsType(studycreateDto.getDsType());
		study.setDsUrl(studycreateDto.getDsUrl());
		study.setDsVolume(studycreateDto.getDsVolume());
		study.setNmAuthor(studycreateDto.getNmAuthor());
		study.setNrYear(studycreateDto.getNrYear());
		study.setTpReadingRate(ReadingRateEnum.fromCode(studycreateDto.getTpReadingRate()));

		Project project = projectDao.getByDsKey(studycreateDto.getDsKey());

		study.setProject(project);
		study.setTpStatus(StudyStatusEnum.SETTING);
		study.setTpVenue(VenueEnum.fromCode(studycreateDto.getTpVenue()));

		Search search = new Search();
		search.setNmSearch("manual insert");
		search.setDsSearch("manual insert");
		search.setTpSearch(SearchEnum.MANUAL);
		search.setProject(project);
		Long i = (long) 1;
		search.setIdSearch(i);
		// searchDao.save(search);

		study.setSearch(search);

		if (studyDao.getByCdCiteKey(study.getCdCiteKey()) == null) {
			studyDao.save(study);
		} else {
			throw new FarmsException(ErrorMessage.CITEKEY_IN_USE);
		}

		return true;
	}

	@Transactional(rollbackFor = Exception.class)
	public Boolean editStudy(StudyCreatedDto studycreatedDto) {
		Study study = new Study();
		study.setCdCiteKey(studycreatedDto.getCdCiteKey());
		study.setCdDoi(studycreatedDto.getCdDoi());
		study.setCdIssnIsbn(studycreatedDto.getCdIssnIsbn());
		study.setDsAbstract(studycreatedDto.getDsAbstract());
		study.setDsComment(studycreatedDto.getDsComment());
		study.setDsJournal(studycreatedDto.getDsJournal());
		study.setDsKeyword(studycreatedDto.getDsKeyword());
		study.setDsPage(studycreatedDto.getDsPage());
		study.setDsTitle(studycreatedDto.getDsTitle());
		study.setDsType(studycreatedDto.getDsType());
		study.setDsUrl(studycreatedDto.getDsUrl());
		study.setDsVolume(studycreatedDto.getDsVolume());
		study.setNmAuthor(studycreatedDto.getNmAuthor());
		study.setNrYear(studycreatedDto.getNrYear());
		study.setTpReadingRate(studycreatedDto.getTpReadingRate());

		Project project = projectDao.getByDsKey(studycreatedDto.getDsKeyProject());

		study.setProject(project);
		study.setTpStatus(studycreatedDto.getTpStatus());
		study.setTpVenue(studycreatedDto.getTpVenue());

		Search search = new Search();
		search.setNrSearch(studycreatedDto.getNrSearch());
		study.setIdStudy(studycreatedDto.getIdStudy());
		study.setSearch(search);

		studyDao.update(study);

		return true;
	}

	public Boolean deleteStudy(Long idStudy) {
		studyDao.deleteStudy(idStudy);
		return true;
	}
}