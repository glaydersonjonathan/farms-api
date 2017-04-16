package br.ufs.dcomp.farms.model.dto;

import br.ufs.dcomp.farms.model.entity.Search;
import br.ufs.dcomp.farms.model.entity.Study;
import br.ufs.dcomp.farms.model.enums.ReadingRateEnum;
import br.ufs.dcomp.farms.model.enums.StudyStatusEnum;
import br.ufs.dcomp.farms.model.enums.VenueEnum;

public class StudyCreatedDto {

	private String cdCiteKey;
	private String dsTitle;
	private String nmAuthor;
	private String dsAbstract;
	private String dsKeyword;
	private Integer nrYear;
	private String dsVolume;
	private String dsUrl;
	private String cdIssnIsbn;
	private String cdDoi;
	private String dsType;
	private String dsPage;
	private String dsComment;
	private String dsJournal;
	private VenueEnum tpVenue;
	private ReadingRateEnum tpReadingRate;
	private StudyStatusEnum tpStatus;

	private String dsKeyProject;
	private Long idSearch;
	private Integer tpSearch; //from search

	private Long idStudy;

	private Long accepted;
	private Long rejected;
	private Long unclassified;

	public StudyCreatedDto() {
	}

	public StudyCreatedDto(Study study) {
		super();
		this.cdCiteKey = study.getCdCiteKey();
		this.dsTitle = study.getDsTitle();
		this.nmAuthor = study.getNmAuthor();
		this.dsAbstract = study.getDsAbstract();
		this.dsKeyword = study.getDsKeyword();
		this.nrYear = study.getNrYear();
		this.dsVolume = study.getDsVolume();
		this.dsUrl = study.getDsUrl();
		this.cdIssnIsbn = study.getCdIssnIsbn();
		this.cdDoi = study.getCdDoi();
		this.dsType = study.getDsType();
		this.dsPage = study.getDsPage();
		this.dsComment = study.getDsComment();
		this.dsJournal = study.getDsJournal();
		this.tpVenue = study.getTpVenue();
		this.tpReadingRate = study.getTpReadingRate();
		this.tpStatus = study.getTpStatus();
		this.dsKeyProject = study.getProject().getDsKey();
		this.idSearch = study.getSearch().getIdSearch();
		this.idStudy = study.getIdStudy();
		this.tpSearch =study.getSearch().getTpSearch().getCode();
	}

	public StudyCreatedDto(Study study, Long accepted, Long rejected, Long unclassified) {
		super();
		this.cdCiteKey = study.getCdCiteKey();
		this.dsTitle = study.getDsTitle();
		this.nmAuthor = study.getNmAuthor();
		this.dsAbstract = study.getDsAbstract();
		this.dsKeyword = study.getDsKeyword();
		this.nrYear = study.getNrYear();
		this.dsVolume = study.getDsVolume();
		this.dsUrl = study.getDsUrl();
		this.cdIssnIsbn = study.getCdIssnIsbn();
		this.cdDoi = study.getCdDoi();
		this.dsType = study.getDsType();
		this.dsPage = study.getDsPage();
		this.dsComment = study.getDsComment();
		this.dsJournal = study.getDsJournal();
		this.tpVenue = study.getTpVenue();
		this.tpReadingRate = study.getTpReadingRate();
		this.tpStatus = study.getTpStatus();
		this.dsKeyProject = study.getProject().getDsKey();
		this.idSearch = study.getSearch().getIdSearch();
		this.idStudy = study.getIdStudy();
		this.accepted = accepted;
		this.rejected = rejected;
		this.unclassified = unclassified;
	}

	public StudyCreatedDto(String cdCiteKey, String dsTitle, String nmAuthor, String dsAbstract, String dsKeyword,
			Integer nrYear, String dsVolume, String dsUrl, String cdIssnIsbn, String cdDoi, String dsType,
			String dsPage, String dsComment, String dsJournal, VenueEnum tpVenue, ReadingRateEnum tpReadingRate,
			StudyStatusEnum tpStatus, Search search, Long idStudy) {
		super();
		this.cdCiteKey = cdCiteKey;
		this.dsTitle = dsTitle;
		this.nmAuthor = nmAuthor;
		this.dsAbstract = dsAbstract;
		this.dsKeyword = dsKeyword;
		this.nrYear = nrYear;
		this.dsVolume = dsVolume;
		this.dsUrl = dsUrl;
		this.cdIssnIsbn = cdIssnIsbn;
		this.cdDoi = cdDoi;
		this.dsType = dsType;
		this.dsPage = dsPage;
		this.dsComment = dsComment;
		this.dsJournal = dsJournal;
		this.tpVenue = tpVenue;
		this.tpReadingRate = tpReadingRate;
		this.tpStatus = tpStatus;
		this.idStudy = idStudy;
	}

	public String getCdCiteKey() {
		return cdCiteKey;
	}

	public void setCdCiteKey(String cdCiteKey) {
		this.cdCiteKey = cdCiteKey;
	}

	public String getDsTitle() {
		return dsTitle;
	}

	public void setDsTitle(String dsTitle) {
		this.dsTitle = dsTitle;
	}

	public String getNmAuthor() {
		return nmAuthor;
	}

	public void setNmAuthor(String nmAuthor) {
		this.nmAuthor = nmAuthor;
	}

	public String getDsAbstract() {
		return dsAbstract;
	}

	public void setDsAbstract(String dsAbstract) {
		this.dsAbstract = dsAbstract;
	}

	public String getDsKeyword() {
		return dsKeyword;
	}

	public void setDsKeyword(String dsKeyword) {
		this.dsKeyword = dsKeyword;
	}

	public Integer getNrYear() {
		return nrYear;
	}

	public void setNrYear(Integer nrYear) {
		this.nrYear = nrYear;
	}

	public String getDsVolume() {
		return dsVolume;
	}

	public void setDsVolume(String dsVolume) {
		this.dsVolume = dsVolume;
	}

	public String getDsUrl() {
		return dsUrl;
	}

	public void setDsUrl(String dsUrl) {
		this.dsUrl = dsUrl;
	}

	public String getCdIssnIsbn() {
		return cdIssnIsbn;
	}

	public void setCdIssnIsbn(String cdIssnIsbn) {
		this.cdIssnIsbn = cdIssnIsbn;
	}

	public String getCdDoi() {
		return cdDoi;
	}

	public void setCdDoi(String cdDoi) {
		this.cdDoi = cdDoi;
	}

	public String getDsType() {
		return dsType;
	}

	public void setDsType(String dsType) {
		this.dsType = dsType;
	}

	public String getDsPage() {
		return dsPage;
	}

	public void setDsPage(String dsPage) {
		this.dsPage = dsPage;
	}

	public String getDsComment() {
		return dsComment;
	}

	public void setDsComment(String dsComment) {
		this.dsComment = dsComment;
	}

	public String getDsJournal() {
		return dsJournal;
	}

	public void setDsJournal(String dsJournal) {
		this.dsJournal = dsJournal;
	}

	public VenueEnum getTpVenue() {
		return tpVenue;
	}

	public void setTpVenue(VenueEnum tpVenue) {
		this.tpVenue = tpVenue;
	}

	public ReadingRateEnum getTpReadingRate() {
		return tpReadingRate;
	}

	public void setTpReadingRate(ReadingRateEnum tpReadingRate) {
		this.tpReadingRate = tpReadingRate;
	}

	public StudyStatusEnum getTpStatus() {
		return tpStatus;
	}

	public void setTpStatus(StudyStatusEnum tpStatus) {
		this.tpStatus = tpStatus;
	}

	public String getDsKeyProject() {
		return dsKeyProject;
	}

	public void setDsKeyProject(String dsKeyProject) {
		this.dsKeyProject = dsKeyProject;
	}

	public Long getIdSearch() {
		return idSearch;
	}

	public void setIdSearch(Long idSearch) {
		this.idSearch = idSearch;
	}

	public Long getIdStudy() {
		return idStudy;
	}

	public void setIdStudy(Long idStudy) {
		this.idStudy = idStudy;
	}

	public Long getAccepted() {
		return accepted;
	}

	public void setAccepted(Long accepted) {
		this.accepted = accepted;
	}

	public Long getRejected() {
		return rejected;
	}

	public void setRejected(Long rejected) {
		this.rejected = rejected;
	}

	public Long getUnclassified() {
		return unclassified;
	}

	public void setUnclassified(Long unclassified) {
		this.unclassified = unclassified;
	}

	public Integer getTpSearch() {
		return tpSearch;
	}

	public void setTpSearch(Integer tpSearch) {
		this.tpSearch = tpSearch;
	}
	
	

}