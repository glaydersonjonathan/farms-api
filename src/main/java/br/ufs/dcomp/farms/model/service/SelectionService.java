package br.ufs.dcomp.farms.model.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.ufs.dcomp.farms.common.message.ErrorMessage;
import br.ufs.dcomp.farms.core.FarmsException;
import br.ufs.dcomp.farms.model.dao.CriteriaReviewJustificationDao;
import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dao.ProjectMemberDao;
import br.ufs.dcomp.farms.model.dao.RatedContentDao;
import br.ufs.dcomp.farms.model.dao.ResearcherDao;
import br.ufs.dcomp.farms.model.dao.ReviewDao;
import br.ufs.dcomp.farms.model.dao.SelectionStepDao;
import br.ufs.dcomp.farms.model.dao.StudyDao;
import br.ufs.dcomp.farms.model.dto.ProjectMemberDto;
import br.ufs.dcomp.farms.model.dto.RatedContentCreatedDto;
import br.ufs.dcomp.farms.model.dto.ReviewCreateDto;
import br.ufs.dcomp.farms.model.dto.SelectionCriteriaCreatedDto;
import br.ufs.dcomp.farms.model.dto.SelectionStepCreatedDto;
import br.ufs.dcomp.farms.model.dto.StudyCreatedDto;
import br.ufs.dcomp.farms.model.entity.CriteriaReviewJustification;
import br.ufs.dcomp.farms.model.entity.CriteriaReviewJustificationPk;
import br.ufs.dcomp.farms.model.entity.ProjectMember;
import br.ufs.dcomp.farms.model.entity.RatedContent;
import br.ufs.dcomp.farms.model.entity.Review;
import br.ufs.dcomp.farms.model.entity.SelectionCriteria;
import br.ufs.dcomp.farms.model.entity.SelectionStep;
import br.ufs.dcomp.farms.model.entity.Study;
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
	@Autowired
	ProjectMemberDao pmd;

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
	 * @throws FarmsException
	 */
	public Boolean assignManual(ReviewCreateDto reviewCreateDto) throws FarmsException {
		boolean verify_already_assigned = false;
		for (Long idStudy : reviewCreateDto.getStudies()) {
			Review review = new Review();
			review.setResearcher(researcherDao.getByDsSSO(reviewCreateDto.getDsSSO()));
			review.setDhAssign(new Date(System.currentTimeMillis()));
			review.setTpStatus(SelectionStatusEnum.fromCode(0)); // assigned
			review.setStudy(studyDao.get(idStudy));

			List<Review> list = reviewDao.getReviewByStudyAndResearcher(idStudy,
					review.getResearcher().getIdResearcher());
			if (list.size() > 0) {
				verify_already_assigned = true;
			} else {
				reviewDao.save(review);
			}
		}

		if (verify_already_assigned) {
			throw new FarmsException(ErrorMessage.STUDY_ALREADY_ASSIGNED);
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
	public List<ReviewCreateDto> getReviews(String dsKey, String dsSSO) {
		List<ReviewCreateDto> reviewCreatedDto = new ArrayList<ReviewCreateDto>();
		List<Review> reviews = reviewDao.getStudiesToReview(dsKey, dsSSO);

		if (reviews != null) {
			for (Review review : reviews) {
				List<CriteriaReviewJustification> criterias = criteriaReviewJustificationDao
						.getByIdReview(review.getIdReview());
				List<SelectionCriteriaCreatedDto> ids = new ArrayList<SelectionCriteriaCreatedDto>();
				for (CriteriaReviewJustification criteria : criterias) {
					ids.add(new SelectionCriteriaCreatedDto(
							criteria.getCriteriaReviewJustificationPk().getSelectionCriteria()));
				}

				if (criterias.size() > 0) {
					reviewCreatedDto.add(new ReviewCreateDto(review, criterias.get(0).getDsJustification(), ids));
				} else {
					reviewCreatedDto.add(new ReviewCreateDto(review, null, ids));
				}
			}
		}
		return reviewCreatedDto;
	}

	/**
	 * Save a realized review
	 * 
	 * @param reviewCreateDto
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Boolean realizeReview(ReviewCreateDto reviewCreateDto) {
		Review review = new Review();
		review.setIdReview(reviewCreateDto.getIdReview());
		review.setDhAssign(reviewCreateDto.getDhAssign());
		review.setDhReview(new Date(System.currentTimeMillis()));
		review.setTpStatus(SelectionStatusEnum.fromCode(Integer.parseInt(reviewCreateDto.getTpStatus())));
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

			criteriaReviewJustificationDao.deleteCriteriaJustificationReview(reviewCreateDto.getIdReview(),
					criteria.getIdSelectionCriteria());

			criteriaReviewJustificationDao.save(criteriaReviewJustification);
		}
		return true;
	}

	/**
	 * Get studies in conflict by dsKey project
	 * 
	 * @param dsKey
	 * @return
	 */
	public List<StudyCreatedDto> getStudiesInConflict(String dsKey) {
		List<StudyCreatedDto> result = new ArrayList<StudyCreatedDto>();
		for (BigInteger id : reviewDao.reviewsConflicts(dsKey)) {
			Study study = studyDao.get(id.longValue());
			result.add(new StudyCreatedDto(study, reviewDao.scoreAccepted(study.getIdStudy()).longValue(),
					reviewDao.scoreRejected(study.getIdStudy()).longValue(),
					reviewDao.scoreUnclassified(study.getIdStudy()).longValue()));
		}
		return result;
	}

	public Boolean assignAuto(String dsKey) throws FarmsException {
		List<ProjectMember> members = pmd.getByDsKeyProject(dsKey);
		List<Study> studies = studyDao.getByDsKeyProject(dsKey);

		boolean verify_already_assigned = false;
		for (Study s : studies) {

			for (int x = 0; x < members.size(); x++) {
				Review review = new Review();

				review.setResearcher(members.get(x).getResearcher());
				review.setDhAssign(new Date(System.currentTimeMillis()));
				review.setTpStatus(SelectionStatusEnum.fromCode(0)); // assigned
				review.setStudy(s);

				List<Review> list = reviewDao.getReviewByStudyAndResearcher(review.getStudy().getIdStudy(),
						review.getResearcher().getIdResearcher());
				if (list.size() > 0) {
					verify_already_assigned = true;
				} else {
					reviewDao.save(review);
				}
			}

		}

		if (verify_already_assigned)
		{
			throw new FarmsException(ErrorMessage.STUDY_ALREADY_ASSIGNED);
		}
		return true;

	}

}
