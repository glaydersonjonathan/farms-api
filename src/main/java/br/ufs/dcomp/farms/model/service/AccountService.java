package br.ufs.dcomp.farms.model.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import br.ufs.dcomp.farms.common.message.ErrorMessage;
import br.ufs.dcomp.farms.core.FarmsCrypt;
import br.ufs.dcomp.farms.core.FarmsException;
import br.ufs.dcomp.farms.core.FarmsMail;
import br.ufs.dcomp.farms.model.dao.ResearcherDao;
import br.ufs.dcomp.farms.model.dto.ResearcherLoggedDto;
import br.ufs.dcomp.farms.model.dto.ResearcherLoginDto;
import br.ufs.dcomp.farms.model.dto.ResearcherRegisterDto;
import br.ufs.dcomp.farms.model.dto.ResearcherRegisteredDto;
import br.ufs.dcomp.farms.model.entity.Researcher;
import br.ufs.dcomp.farms.model.enums.StateEnum;
import br.ufs.dcomp.farms.model.enums.YesNoEnum;

/**
 * @author farms
 *
 */
@Component
public class AccountService {

    @Autowired
    private ResearcherService researcherService;
    @Autowired
    private ResearcherDao researcherDAO;

    /**
     * Register a researcher
     *
     * @param researcherRegisterDto
     * @return researcher
     * @throws FarmsException
     */
    @Transactional(rollbackFor = FarmsException.class)
    public Researcher register(ResearcherRegisterDto researcherRegisterDto) throws FarmsException {
        Researcher researcher = null;
        if (researcherService.save(researcherRegisterDto)) {
            researcher = researcherService.getByEmail(researcherRegisterDto.getDsEmail());
        }
        return researcher;
    }

    /**
     * Register a researcher and send email confirmation
     *
     * @param researcherRegisterDto
     * @return researcher
     * @throws FarmsException
     */
    public ResearcherRegisteredDto registerAndSendAccountConfirmationEmail(ResearcherRegisterDto researcherRegisterDto)
            throws FarmsException {
        ResearcherRegisteredDto researcherRegisteredDto = null;
        Researcher researcher = register(researcherRegisterDto);
        if (researcher != null) {
            FarmsMail.sendAccountConfirmationEmail(researcher.getNmResearcher(), researcher.getDsEmail(),
                    researcher.getCdUuid());
            researcherRegisteredDto = new ResearcherRegisteredDto(researcher);
        }
        return researcherRegisteredDto;
    }

    /**
     * Do login
     *
     * @param researcherLoginDto
     * @return ResearcherLoggedDto
     * @throws FarmsException
     */
    public ResearcherLoggedDto login(ResearcherLoginDto researcherLoginDto) throws FarmsException {
        ResearcherLoggedDto researcherLoggedDto = null;
        Researcher researcherLogged = researcherService.getByEmail(researcherLoginDto.getDsEmail());
        // search by username
        if (researcherLogged == null) {
            researcherLogged = researcherDAO.getByDsSSO(researcherLoginDto.getDsEmail());
        }

        if (researcherLogged != null
                && FarmsCrypt.checkPassword(researcherLoginDto.getDsPassword(), researcherLogged.getDsPassword())) {
            // verify account confirmed
            if (researcherLogged.getTpConfirmed() == YesNoEnum.N) {
                throw new FarmsException(ErrorMessage.ACCOUNT_NOT_CONFIRMED);
            }

            // turns active
            if (researcherLogged.getTpState() == StateEnum.I) {
                researcherDAO.active(researcherLogged.getIdResearcher());
            }
            researcherLoggedDto = new ResearcherLoggedDto(researcherLogged);
        } else {
            throw new FarmsException(ErrorMessage.LOGIN_INVALID);
        }
        return researcherLoggedDto;
    }

    /**
     * Confirm account
     *
     * @param cdUuid
     * @return ResearcherRegisteredDto
     * @throws FarmsException
     */
    public ResearcherRegisteredDto confirmAccount(String cdUuid) throws FarmsException {
        ResearcherRegisteredDto researcherRegisteredDto = null;
        Researcher researcherFound = researcherService.getByUuid(cdUuid);
        if (researcherFound == null) {
            throw new FarmsException(ErrorMessage.RESEARCHER_NOT_FOUND);
        } else {
            if (YesNoEnum.Y.equals(researcherFound.getTpConfirmed())) {
                throw new FarmsException(ErrorMessage.ACCOUNT_ALREADY_CONFIRMED);
            } else {
                researcherService.confirmAccount(researcherFound);
                researcherRegisteredDto = new ResearcherRegisteredDto(researcherFound);
            }
        }
        return researcherRegisteredDto;
    }

    /**
     * Resend email confirmation
     *
     * @param researcherLoginDto
     * @return
     */
    public Boolean resend(ResearcherLoginDto researcherLoginDto) {
        Researcher researcher = researcherDAO.getByDsEmail(researcherLoginDto.getDsEmail());
        FarmsMail.sendAccountConfirmationEmail(researcher.getNmResearcher(), researcher.getDsEmail(),
                researcher.getCdUuid());
        return true;
    }

    /**
     * Send email to new password
     *
     * @param email
     * @return
     */
    public Boolean sendEmailNewPassword(String email) {
        Researcher researcher = researcherDAO.getByDsEmail(email);
        FarmsMail.sendNewPasswordEmail(researcher.getNmResearcher(), researcher.getDsEmail(),
                researcher.getCdUuid());
        return true;
    }

    /**
     * Update Password of researcher
     *
     * @param u
     * @param researcherRegisterDto
     * @return
     * @throws FarmsException
     */
    public Boolean newPass(String u, ResearcherRegisterDto researcherRegisterDto) throws FarmsException {
        Researcher researcher = researcherDAO.getByUuid(u);
        if (researcher == null) {
            throw new FarmsException(ErrorMessage.RESEARCHER_NOT_FOUND_PASS);
        }
        researcher.setDsPassword(FarmsCrypt.hashPassword(researcherRegisterDto.getDsPassword()));
        UUID uuid = UUID.randomUUID();
        researcher.setCdUuid(uuid.toString());

        researcherDAO.updatePass(researcher);
        return true;
    }
}
