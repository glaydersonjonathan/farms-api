package br.ufs.dcomp.farms.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.ufs.dcomp.farms.model.dao.CriteriaReviewJustificationDao;
import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dao.RatedContentDao;
import br.ufs.dcomp.farms.model.dao.ResearcherDao;
import br.ufs.dcomp.farms.model.dao.ReviewDao;
import br.ufs.dcomp.farms.model.dao.SelectionStepDao;
import br.ufs.dcomp.farms.model.dao.StudyDao;
import br.ufs.dcomp.farms.model.dto.RatedContentCreatedDto;
import br.ufs.dcomp.farms.model.dto.ReviewCreateDto;
import br.ufs.dcomp.farms.model.dto.ReviewCreatedDto;
import br.ufs.dcomp.farms.model.dto.SelectionCriteriaCreatedDto;
import br.ufs.dcomp.farms.model.dto.SelectionStepCreatedDto;
import br.ufs.dcomp.farms.model.entity.CriteriaReviewJustification;
import br.ufs.dcomp.farms.model.entity.CriteriaReviewJustificationPk;
import br.ufs.dcomp.farms.model.entity.RatedContent;
import br.ufs.dcomp.farms.model.entity.Review;
import br.ufs.dcomp.farms.model.entity.SelectionCriteria;
import br.ufs.dcomp.farms.model.entity.SelectionStep;
import br.ufs.dcomp.farms.model.enums.CriteriaEnum;
import br.ufs.dcomp.farms.model.enums.SelectionStatusEnum;
import br.ufs.dcomp.farms.model.enums.SelectionStepStatusEnum;

/**
 * @author farms
 *
 */
@Component
public class SelectionService {

	@Autowired
	SelectionStepDao selectionStepDao;
	@Autowired
	ProjectDao projectDao;
	@Autowired
	RatedContentDao ratedContentDao;
	@Autowired
	ReviewDao reviewDao;
	@Autowired
	ResearcherDao researcherDao;
	@Autowired
	StudyDao studyDao;
	@Autowired
	CriteriaReviewJustificationDao criteriaReviewJustificationDao;

	/**
	 * Save configuration of selection Step of a project
	 * 
	 * @param selectionCreateDto
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Boolean save(SelectionStepCreatedDto selectionCreateDto) {
		SelectionStep selectionStep = new SelectionStep();

		selectionStep.setDhConflictsSolvingEnd(selectionCreateDto.getDhConflictsSolvingEnd());
		selectionStep.setDhEndSelectionStep(selectionCreateDto.getDhEndSelectionStep());
		selectionStep.setDhReviewEnd(selectionCreateDto.getDhReviewEnd());
		selectionStep.setDhStartSelectionStep(selectionCreateDto.getDhStartSelectionStep());
		selectionStep.setQtReview(selectionCreateDto.getQtReview());
		// selectionStep.setProject(projectDao.getByDsKey(selectionCreateDto.getDsKey()));
		selectionStep.setProject(projectDao.get(selectionCreateDto.getIdProject()));

		// verificar inicio
		RatedContent ratedContent = new RatedContent();
		// ratedContent.setIdRatedContent(Long.parseLong(selectionCreateDto.getDsRatedContent()));
		ratedContent.setIdRatedContent(selectionCreateDto.getIdRatedContent());
		selectionStep.setRatedContent(ratedContent);
		selectionStep.setNrSerial(0);
		selectionStep.setVlLowerScore(1);
		selectionStep.setTpStatus(SelectionStepStatusEnum.ASSIGNED);
		// verificar fim

		selectionStepDao.delete(selectionCreateDto.getIdProject()); // delete if
																	// exists

		selectionStepDao.save(selectionStep);

		return true;
	}

	/**
	 * Get configuration of selection Step of a project
	 * 
	 * @param dsKey
	 * @return
	 */
	public SelectionStepCreatedDto getConfiguration(String dsKey) {
		SelectionStep selectionStep = selectionStepDao.getConfiguration(dsKey);
		SelectionStepCreatedDto selectionStepCreatedDto = new SelectionStepCreatedDto();
		selectionStepCreatedDto = new SelectionStepCreatedDto(selectionStep);
		return selectionStepCreatedDto;
	}

	/**
	 * Get all rated content
	 * 
	 * @return
	 */
	public List<RatedContentCreatedDto> getAllRated() {
		List<RatedContentCreatedDto> ratedContentCreated = new ArrayList<RatedContentCreatedDto>();
		List<RatedContent> rateds = ratedContentDao.getAllRated();
		if (rateds != null) {
			for (RatedContent rated : rateds) {
				ratedContentCreated.add(new RatedContentCreatedDto(rated));
			}
		}
		return ratedContentCreated;
	}

	/**
	 * Assign studies to researcher
	 * 
	 * @param reviewCreateDto
	 * @return
	 */
	public Boolean assignManual(ReviewCreateDto reviewCreateDto) {
		for (Long idStudy : reviewCreateDto.getStudies()) {
			Review review = new Review();
			review.setResearcher(researcherDao.getByDsSSO(reviewCreateDto.getDsSSO()));
			review.setDhAssign(reviewCreateDto.getDhAssign());
			review.setTpStatus(SelectionStatusEnum.fromCode(0)); // assigned
			review.setStudy(studyDao.get(idStudy));
			reviewDao.save(review);
		}
		return true;
	}

	/**
	 * Get reviews by project and researcher
	 * 
	 * @param dsKey
	 * @param dsSSO
	 * @return
	 */
	public List<ReviewCreatedDto> getReviews(String dsKey, String dsSSO) {
		List<ReviewCreatedDto> reviewCreatedDto = new ArrayList<ReviewCreatedDto>();
		List<Review> reviews = reviewDao.getStudiesToReview(dsKey, dsSSO);
		if (reviews != null) {
			for (Review review : reviews) {
				reviewCreatedDto.add(new ReviewCreatedDto(review));
			}
		}
		return reviewCreatedDto;
	}

	/**
	 * Save a realized review
	 * @param reviewCreateDto
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Boolean realizeReview(ReviewCreateDto reviewCreateDto) {
		Review review = new Review();
		review.setIdReview(reviewCreateDto.getIdReview());
		review.setDhAssign(reviewCreateDto.getDhAssign());
		review.setDhReview(new Date(System.currentTimeMillis()));
		review.setTpStatus(SelectionStatusEnum.fromCode(reviewCreateDto.getTpStatus()));
		review.setResearcher(researcherDao.get(reviewCreateDto.getIdResearcher()));
		review.setStudy(studyDao.getByCdCiteKey(reviewCreateDto.getStudy().getCdCiteKey()));

		reviewDao.update(review);

		for (SelectionCriteriaCreatedDto sc : reviewCreateDto.getCriterias()) {
			CriteriaReviewJustification criteriaReviewJustification = new CriteriaReviewJustification();
			criteriaReviewJustification.setDsJustification(reviewCreateDto.getDsCommentary());

			CriteriaReviewJustificationPk pk = new CriteriaReviewJustificationPk();
			pk.setReview(review);

			SelectionCriteria criteria = new SelectionCriteria();
			criteria.setDsSelectionCriteria(sc.getDsSelectionCriteria());
			criteria.setIdSelectionCriteria(sc.getIdSelectionCriteria());
			criteria.setProject(projectDao.getByDsKey(sc.getDsProjectKey()));
			criteria.setTpCriteria(CriteriaEnum.fromCode(sc.getTpCriteria()));
			pk.setSelectionCriteria(criteria);

			criteriaReviewJustification.setCriteriaReviewJustificationPk(pk);
			
			criteriaReviewJustificationDao.deleteCriteriaJustificationReview(reviewCreateDto.getIdReview(), criteria.getIdSelectionCriteria());

			criteriaReviewJustificationDao.save(criteriaReviewJustification);
		}
		return true;
	}

}
