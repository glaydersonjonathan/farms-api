package br.ufs.dcomp.farms.model.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.hibernate.exception.ConstraintViolationException;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXParser;
import org.jbibtex.BibTeXString;
import org.jbibtex.Key;
import org.jbibtex.ParseException;
import org.jbibtex.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import br.ufs.dcomp.farms.common.message.ErrorMessage;
import br.ufs.dcomp.farms.core.FarmsException;
import br.ufs.dcomp.farms.model.dao.AdaptedQueryDao;
import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dao.SearchDao;
import br.ufs.dcomp.farms.model.dao.StandardQueryDao;
import br.ufs.dcomp.farms.model.dao.StudyDao;
import br.ufs.dcomp.farms.model.dto.StudyCreateDto;
import br.ufs.dcomp.farms.model.dto.StudyCreatedDto;
import br.ufs.dcomp.farms.model.entity.AdaptedQuery;
import br.ufs.dcomp.farms.model.entity.Project;
import br.ufs.dcomp.farms.model.entity.Search;
import br.ufs.dcomp.farms.model.entity.SearchEngine;
import br.ufs.dcomp.farms.model.entity.StandardQuery;
import br.ufs.dcomp.farms.model.entity.Study;
import br.ufs.dcomp.farms.model.enums.ReadingRateEnum;
import br.ufs.dcomp.farms.model.enums.SearchEnum;
import br.ufs.dcomp.farms.model.enums.StudyStatusEnum;
import br.ufs.dcomp.farms.model.enums.VenueEnum;

/**
 * @author farms
 *
 */
@Component
public class StudyService {

	@Autowired
	private StudyDao studyDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private SearchDao searchDao;
	@Autowired
	private StandardQueryDao standardDao;
	@Autowired
	private AdaptedQueryDao adaptedDao;

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

	/**
	 * Get Study by citekey
	 * 
	 * @param cdCiteKey
	 * @return
	 */
	public StudyCreatedDto getStudyByCdciteKey(String cdCiteKey) {
		Study study = studyDao.getByCdCiteKey(cdCiteKey);
		StudyCreatedDto studyCreatedDto = new StudyCreatedDto(study);
		return studyCreatedDto;
	}

	/**
	 * Save a standard query
	 * 
	 * @param studycreateDto
	 * @return
	 * @throws FarmsException
	 */
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
		study.setTpStatus(StudyStatusEnum.SETTING);
		study.setTpVenue(VenueEnum.fromCode(studycreateDto.getTpVenue()));

		Project project = projectDao.getByDsKey(studycreateDto.getDsKey());
		study.setProject(project);

		StandardQuery standard = new StandardQuery();
		if (standardDao.getByDsKeyProject(studycreateDto.getDsKey()).size() == 0) {
			throw new FarmsException(ErrorMessage.NO_STANDARD_QUERY);
		} else {
			standard = standardDao.getByDsKeyProject(studycreateDto.getDsKey()).get(0);
		}

		AdaptedQuery adaptedQuery = new AdaptedQuery();
		adaptedQuery.setDsObservation("MANUAL INSERT");
		adaptedQuery.setStandardQuery(standard);
		adaptedQuery.setDsAdaptedQuery("MANUAL INSERT");
		SearchEngine searchEngine = new SearchEngine();
		searchEngine.setNmSearchEngine("MANUAL INSERT");
		searchEngine.setIdSearchEngine(1L);
		adaptedQuery.setSearchEngine(searchEngine);
		adaptedDao.save(adaptedQuery);

		Search search = new Search();
		search.setNmSearch("MANUAL INSERT");
		search.setDsSearch("MANUAL INSERT");
		search.setTpSearch(SearchEnum.MANUAL);
		search.setProject(project);
		search.setDhSearch(new Date(System.currentTimeMillis()));
		// verificar
		Random rand = new Random();
		search.setNrSearch(rand.nextLong());
		search.setAdaptedQuery(adaptedQuery);
		searchDao.save(search);

		study.setSearch(search);

		if (studyDao.getByCdCiteKey(study.getCdCiteKey()) == null) {
			studyDao.save(study);
		} else {
			throw new FarmsException(ErrorMessage.CITEKEY_IN_USE);
		}

		return true;
	}

	/**
	 * Update a study
	 * 
	 * @param studycreatedDto
	 * @return
	 */
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

		Search search = searchDao.get(studycreatedDto.getIdSearch());
		study.setIdStudy(studycreatedDto.getIdStudy());
		study.setSearch(search);

		studyDao.update(study);

		return true;
	}

	/**
	 * Delete a Study if is not in review
	 * 
	 * @param idStudy
	 * @return
	 * @throws FarmsException
	 */
	public Boolean deleteStudy(Long idStudy) throws FarmsException {
		Study s = studyDao.get(idStudy);
		Search searc = s.getSearch();
		AdaptedQuery a = searc.getAdaptedQuery();
		searchDao.delete(searc);
		adaptedDao.delete(a);
		try {
			studyDao.deleteStudy(idStudy);
			searchDao.deleteSearch(searc);
			adaptedDao.deleteAdapted(a);
			return true;
		} catch (ConstraintViolationException c) {
			throw new FarmsException(ErrorMessage.STUDY_IN_REVIEW);
		}

	}

	/**
	 * Import studies from bibtex
	 * 
	 * @param dir
	 * @param dsKey
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws FarmsException
	 * @throws NullPointerException
	 */
	public int importStudies(String dir, String dsKey)
			throws IOException, ParseException, FarmsException, NullPointerException {
		File input = new File(dir);
		int result = 0;
		BibTeXDatabase database = parseBibTeX(input);
		List<Study> studies = new ArrayList<Study>();
		Collection<BibTeXEntry> entries = (database.getEntries()).values();

		Project project = projectDao.getByDsKey(dsKey);
		StandardQuery standard = new StandardQuery();
		if (standardDao.getByDsKeyProject(dsKey).size() == 0) {
			throw new FarmsException(ErrorMessage.NO_STANDARD_QUERY);
		} else {
			standard = standardDao.getByDsKeyProject(dsKey).get(0);
		}

		for (BibTeXEntry entry : entries) {

			Study study = new Study();

			Key title_key = new Key("title");
			Value title = entry.getField(title_key);
			if (title != null)
				study.setDsTitle(title.toUserString());

			Key author_key = new Key("author");
			Value author = entry.getField(author_key);
			if (author != null)
				study.setNmAuthor(author.toUserString());

			Key abstract_key = new Key("abstract");
			Value abstrac = entry.getField(abstract_key);
			if (abstrac != null)
				study.setDsAbstract(abstrac.toUserString());

			Key doi_key = new Key("doi");
			Value doi = entry.getField(doi_key);
			if (doi != null)
				study.setCdDoi(doi.toUserString());

			Key year_key = new Key("year");
			Value year = entry.getField(year_key);
			if (year != null)
				study.setNrYear(Integer.parseInt(year.toUserString()));

			Key journal_key = new Key("journal");
			Value journal = entry.getField(journal_key);
			if (journal != null)
				study.setDsJournal(journal.toUserString());

			Key pages_key = new Key("pages");
			Value pages = entry.getField(pages_key);
			if (pages != null)
				study.setDsPage(pages.toUserString());

			Key url_key = new Key("url");
			Value url = entry.getField(url_key);
			if (url != null)
				study.setDsUrl(url.toUserString());

			Key keywords_key = new Key("keywords");
			Value keywords = entry.getField(keywords_key);
			if (keywords != null)
				study.setDsKeyword(keywords.toUserString());

			Key volume_key = new Key("volume");
			Value volume = entry.getField(volume_key);
			if (volume != null)
				study.setDsVolume(volume.toUserString());

			Key issn_key = new Key("issn");
			Value issn = entry.getField(issn_key);
			if (issn != null)
				study.setCdIssnIsbn(issn.toUserString());

			Key type_key = new Key("type");
			Value type = entry.getField(type_key);
			if (type != null)
				study.setDsType(type.toUserString());

			Random rand = new Random();
			String x = dsKey + rand.nextInt();
			study.setCdCiteKey(x);
			study.setTpReadingRate(ReadingRateEnum.HIGH);

			study.setProject(project);

			AdaptedQuery adaptedQuery = new AdaptedQuery();
			adaptedQuery.setDsObservation("MANUAL INSERT");
			adaptedQuery.setStandardQuery(standard);
			adaptedQuery.setDsAdaptedQuery("MANUAL INSERT");
			SearchEngine searchEngine = new SearchEngine();
			searchEngine.setNmSearchEngine("MANUAL INSERT");
			searchEngine.setIdSearchEngine(1L);
			adaptedQuery.setSearchEngine(searchEngine);
			adaptedDao.save(adaptedQuery);

			Search search = new Search();
			search.setNmSearch("MANUAL INSERT");
			search.setDsSearch("MANUAL INSERT");
			search.setTpSearch(SearchEnum.IMPORTED);
			search.setProject(project);
			search.setDhSearch(new Date(System.currentTimeMillis()));
			// verificar
			search.setNrSearch(rand.nextLong());

			search.setAdaptedQuery(adaptedQuery);
			searchDao.save(search);

			study.setSearch(search);
			study.setTpStatus(StudyStatusEnum.SETTING);

			// studyDao.save(study);
			studies.add(study);
			result++;
		}

		for (Study s : studies) {
			studyDao.save(s);
		}

		return result;
	}

	static public BibTeXDatabase parseBibTeX(File file) throws IOException, ParseException {
		Reader reader = new FileReader(file);

		try {
			BibTeXParser parser = new BibTeXParser() {

				@Override
				public void checkStringResolution(Key key, BibTeXString string) {

					if (string == null) {
						System.err.println("Unresolved string: \"" + key.getValue() + "\"");
					}
				}

				@Override
				public void checkCrossReferenceResolution(Key key, BibTeXEntry entry) {

					if (entry == null) {
						System.err.println("Unresolved cross-reference: \"" + key.getValue() + "\"");
					}
				}
			};

			return parser.parse(reader);
		} finally {
			reader.close();
		}
	}
}