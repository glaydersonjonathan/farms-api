package br.ufs.dcomp.farms.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "search_engine")
@XmlRootElement
@SequenceGenerator(name = "SearchEngineSequenceGenerator", sequenceName = "sq_search_engine")
public class SearchEngine implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idSearchEngine;
	private String nmSearchEngine;

	// verificar
	// private Set<AdaptedQuery> adaptedQuerys = new HashSet<AdaptedQuery>(0);

	public SearchEngine() {
	}

	public SearchEngine(String nmSearchEngine) {
		this.nmSearchEngine = nmSearchEngine;
	}

	public SearchEngine(Long idSearchEngine, String nmSearchEngine) {
		super();
		this.idSearchEngine = idSearchEngine;
		this.nmSearchEngine = nmSearchEngine;
	}

	public SearchEngine(SearchEngine engine) {
		this.idSearchEngine = engine.getIdSearchEngine();
		this.nmSearchEngine = engine.getNmSearchEngine();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SearchEngineSequenceGenerator")
	@Column(name = "id_search_engine", nullable = false, unique = true)
	public Long getIdSearchEngine() {
		return idSearchEngine;
	}

	public void setIdSearchEngine(Long idSearchEngine) {
		this.idSearchEngine = idSearchEngine;
	}

	@Column(name = "nm_search_engine", nullable = false)
	public String getNmSearchEngine() {
		return nmSearchEngine;
	}

	public void setNmSearchEngine(String nmSearchEngine) {
		this.nmSearchEngine = nmSearchEngine;
	}

	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "searchEngine") public
	 * Set<AdaptedQuery> getAdaptedQuerys() { return this.adaptedQuerys; }
	 * 
	 * public void setAdaptedQuerys(Set<AdaptedQuery> adaptedQuerys) {
	 * this.adaptedQuerys = adaptedQuerys; }
	 */
}
