package br.ufs.dcomp.farms.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.Language;
import br.ufs.dcomp.farms.model.entity.StudyLanguage;

@Component
@SuppressWarnings("unchecked")
public class LanguageDao extends HibernateDao<Language> {

	public LanguageDao() {
		super(Language.class);
	}

	/**
	 * Returns all languages from the specified project.
	 *
	 * @param dsKey
	 *            the identifier of the project.
	 * @return a list of all the languages of the specified project.
	 */
	public List<StudyLanguage> getByDsKeyProject(String dsKey) {

		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from StudyLanguage sl");
		sbHql.append(" join fetch sl.language l");
		sbHql.append(" join fetch sl.project p");
		sbHql.append(" where p.dsKey = :dsKey");

		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("dsKey", dsKey);

		List<StudyLanguage> languages = query.list();

		return languages;
	}

	public List<Language> getAllLanguages() {

		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from Language");

		Query query = getSession().createQuery(sbHql.toString());

		List<Language> languages = query.list();

		return languages;
	}

	public void saveStudyLanguage(StudyLanguage studyLanguage) {
		Query query = getSession().createSQLQuery("INSERT INTO study_language (id_project, id_language) VALUES (:valor1, :valor2)");
		query.setParameter("valor1", studyLanguage.getProject().getIdProject());
		query.setParameter("valor2", studyLanguage.getLanguage().getIdLanguage());
		query.executeUpdate();
		
	}


}
