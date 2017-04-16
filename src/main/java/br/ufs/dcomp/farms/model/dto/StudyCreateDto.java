package br.ufs.dcomp.farms.model.dto;

public class StudyCreateDto {

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
	private Integer tpVenue;
	private Integer tpReadingRate;
	// private StudyStatusEnum tpStatus;
	private String dsKey;

	// private Search search;
	public StudyCreateDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudyCreateDto(String cdCiteKey, String dsTitle, String nmAuthor, String dsAbstract, String dsKeyword,
			Integer nrYear, String dsVolume, String dsUrl, String cdIssnIsbn, String cdDoi, String dsType,
			String dsPage, String dsComment, String dsJournal, Integer tpVenue, Integer tpReadingRate, String dsKey) {
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
		this.dsKey = dsKey;
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

	public Integer getTpVenue() {
		return tpVenue;
	}

	public void setTpVenue(Integer tpVenue) {
		this.tpVenue = tpVenue;
	}

	public Integer getTpReadingRate() {
		return tpReadingRate;
	}

	public void setTpReadingRate(Integer tpReadingRate) {
		this.tpReadingRate = tpReadingRate;
	}

	public String getDsKey() {
		return dsKey;
	}

	public void setDsKey(String dsKey) {
		this.dsKey = dsKey;
	}

}
