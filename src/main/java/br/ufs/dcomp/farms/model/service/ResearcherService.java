package br.ufs.dcomp.farms.model.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.ufs.dcomp.farms.common.message.ErrorMessage;
import br.ufs.dcomp.farms.core.FarmsCrypt;
import br.ufs.dcomp.farms.core.FarmsException;
import br.ufs.dcomp.farms.core.FarmsMail;
import br.ufs.dcomp.farms.model.dao.ResearcherDao;
import br.ufs.dcomp.farms.model.dto.ResearcherRegisterDto;
import br.ufs.dcomp.farms.model.entity.Researcher;
import br.ufs.dcomp.farms.model.enums.StateEnum;
import br.ufs.dcomp.farms.model.enums.YesNoEnum;

/**
 * @author farms
 *
 */
@Component
public class ResearcherService {

	@Autowired
	private ResearcherDao researcherDAO;

	/**
	 * Register a researcher
	 * 
	 * @param researcherRegisterDto
	 * @return boolean
	 * @throws FarmsException
	 */
	@Transactional(rollbackFor = FarmsException.class)
	public boolean save(ResearcherRegisterDto researcherRegisterDto) throws FarmsException {

		Researcher researcherFoundByDsSSO = this.getBySSOtoRegister(researcherRegisterDto.getDsSSO());
		if (researcherFoundByDsSSO != null) {
			throw new FarmsException(ErrorMessage.USERNAME_ALREADY_IN_USE);
		}

		Researcher researcherFoundByEmail = this.getByEmail(researcherRegisterDto.getDsEmail());
		if (researcherFoundByEmail != null) {
			throw new FarmsException(ErrorMessage.EMAIL_ALREADY_IN_USE);
		}

		Researcher researcher = new Researcher();
		UUID uuid = UUID.randomUUID();
		researcher.setCdUuid(uuid.toString());
		researcher.setNmResearcher(researcherRegisterDto.getNmResearcher());
		researcher.setDsSSO(researcherRegisterDto.getDsSSO());
		researcher.setDsEmail(researcherRegisterDto.getDsEmail());
		researcher.setDsPassword(FarmsCrypt.hashPassword(researcherRegisterDto.getDsPassword()));
		researcher.setTpState(StateEnum.A);
		researcher.setTpConfirmed(YesNoEnum.N);
		researcherDAO.save(researcher);
		return true;
	}

	/**
	 * Update a researcher
	 * 
	 * @param researcherRegisterDto
	 * @return boolean
	 * @throws FarmsException
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean update(ResearcherRegisterDto researcherRegisterDto) throws FarmsException {

		Researcher researcher = new Researcher();

		researcher.setIdResearcher(researcherRegisterDto.getIdResearcher());
		researcher.setNmResearcher(researcherRegisterDto.getNmResearcher());
		researcher.setDsSSO(researcherRegisterDto.getDsSSO());
		researcher.setDsEmail(researcherRegisterDto.getDsEmail());
		researcher.setDsPassword(researcherRegisterDto.getDsPassword());
		researcher.setCdUuid(researcherRegisterDto.getCdUuid());
		researcher.setTpConfirmed(researcherRegisterDto.getTpConfirmed());
		researcher.setTpState(researcherRegisterDto.getTpState());

		researcherDAO.update(researcher);
		return true;
	}

	/**
	 * Update a researcher email
	 * 
	 * @param researcherRegisterDto
	 * @return boolean
	 * @throws FarmsException
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updateEmail(ResearcherRegisterDto researcherRegisterDto) throws FarmsException {

		Researcher researcherFoundByEmail = this.getByEmail(researcherRegisterDto.getDsEmail());
		if (researcherFoundByEmail != null) {
			throw new FarmsException(ErrorMessage.EMAIL_ALREADY_IN_USE);
		}

		Researcher researcher = new Researcher();

		researcher.setIdResearcher(researcherRegisterDto.getIdResearcher());
		researcher.setNmResearcher(researcherRegisterDto.getNmResearcher());
		researcher.setDsSSO(researcherRegisterDto.getDsSSO());
		researcher.setDsEmail(researcherRegisterDto.getDsEmail());
		researcher.setDsPassword(researcherRegisterDto.getDsPassword());
		UUID uuid = UUID.randomUUID();
		researcher.setCdUuid(uuid.toString());
		researcher.setTpConfirmed(YesNoEnum.N);
		researcher.setTpState(researcherRegisterDto.getTpState());

		FarmsMail.sendAccountConfirmationEmail(researcher.getNmResearcher(), researcher.getDsEmail(),
				researcher.getCdUuid().toString());

		researcherDAO.update(researcher);
		return true;
	}

	/**
	 * Set state of researcher to inactive.
	 * 
	 * @param researcherRegisterDto
	 * @return boolean
	 */
	public boolean inactive(Long idResearcher) {
		return researcherDAO.inactive(idResearcher);
	}

	/**
	 * Confirm register of researcher
	 * 
	 * @param researcher
	 * @return boolean
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean confirmAccount(Researcher researcher) {
		researcher.setTpConfirmed(YesNoEnum.Y);
		researcherDAO.update(researcher);
		return true;
	}

	/**
	 * Returns all researchers.
	 *
	 * @return a list of all the researchers.
	 */
	public List<Researcher> getAll() {
		List<Researcher> researchers = researcherDAO.getAll();
		return researchers;
	}

	/**
	 * Search a researcher by username.
	 * 
	 * @param dsSSO
	 * @return researcherCreatedDto
	 */
	public ResearcherRegisterDto getBySSO(String dsSSO) {
		Researcher researcher = researcherDAO.getByDsSSO(dsSSO);
		ResearcherRegisterDto researcherCreatedDto = new ResearcherRegisterDto(researcher);
		return researcherCreatedDto;
	}

	/**
	 * Search a researcher by username.
	 * 
	 * @param dsSSO
	 * @return researcher
	 */
	public Researcher getBySSOtoRegister(String dsSSO) {
		Researcher researcher = researcherDAO.getByDsSSO(dsSSO);
		return researcher;
	}

	/**
	 * Search a researcher by email.
	 * 
	 * @param dsEmail
	 * @return researcher
	 */
	public Researcher getByEmail(String dsEmail) {
		Researcher researcher = researcherDAO.getByDsEmail(dsEmail);
		return researcher;
	}

	/**
	 * Search a researcher by cdUuid
	 * 
	 * @param cdUuid
	 * @return researcher
	 */
	public Researcher getByUuid(String cdUuid) {
		Researcher researcher = null;
		if (cdUuid != null) {
			researcher = researcherDAO.getByUuid(cdUuid);
		}
		return researcher;
	}

}