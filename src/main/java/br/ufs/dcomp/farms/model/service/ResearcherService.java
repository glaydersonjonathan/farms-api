package br.ufs.dcomp.farms.model.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.ufs.dcomp.farms.common.message.ErrorMessage;
import br.ufs.dcomp.farms.core.FarmsCrypt;
import br.ufs.dcomp.farms.core.FarmsException;
import br.ufs.dcomp.farms.model.dao.ResearcherDao;
import br.ufs.dcomp.farms.model.dto.ResearcherRegisterDto;
import br.ufs.dcomp.farms.model.entity.Researcher;
import br.ufs.dcomp.farms.model.enums.StateEnum;
import br.ufs.dcomp.farms.model.enums.YesNoEnum;

@Component
public class ResearcherService {

	@Autowired
	private ResearcherDao researcherDAO;

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
	
	//ok?
	@Transactional(rollbackFor = Exception.class)
	public boolean update(ResearcherRegisterDto researcherRegisterDto) throws FarmsException {
		//Researcher researcherFoundByEmail = this.getByEmail(researcherRegisterDto.getDsEmail());
		//if (researcherFoundByEmail != null && (!researcherFoundByEmail.getDsEmail().equals(researcherRegisterDto.getDsEmail()))) {
			//System.out.println(researcherFoundByEmail.getDsEmail() +" "+researcherRegisterDto.getDsEmail());
			//throw new FarmsException(ErrorMessage.EMAIL_ALREADY_IN_USE);
		//}
		
		//System.out.println(researcherFoundByEmail.getDsEmail() +" "+researcherRegisterDto.getDsEmail());
			
		Researcher researcher = new Researcher();
		researcher.setNmResearcher(researcherRegisterDto.getNmResearcher());
		researcher.setDsSSO(researcherRegisterDto.getDsSSO());
		researcher.setDsEmail(researcherRegisterDto.getDsEmail());
		
		
		researcher.setDsPassword(researcherRegisterDto.getDsPassword());
		
		researcher.setCdUuid(researcherRegisterDto.getCdUuid());
		researcher.setIdResearcher(researcherRegisterDto.getIdResearcher());
	    researcher.setTpConfirmed(researcherRegisterDto.getTpConfirmed());
		researcher.setTpState(researcherRegisterDto.getTpState());
		researcherDAO.update(researcher);
		return true;
	}
	
	
	
	
	public boolean delete(Long idResearcher) {
		return researcherDAO.delete(idResearcher);
		
	}
	
	
	
	
	@Transactional(rollbackFor = Exception.class)
	public boolean confirmAccount(Researcher researcher) {
		researcher.setTpConfirmed(YesNoEnum.Y);
		researcherDAO.update(researcher);
		return true;
	}
	
	// TODO Obter todos por projeto. Mover para ProjectService.
	public List<Researcher> getAll() {
		List<Researcher> researchers = researcherDAO.getAll();
		return researchers;
	}

	public List<Researcher> getByName(String nmResearcher) {
		return researcherDAO.getByNmResearcher(nmResearcher);
	}
	
	//ok, usado na hora de buscar perfil
	public ResearcherRegisterDto getBySSO(String dsSSO) {
		Researcher researcher = researcherDAO.getByDsSSO(dsSSO);
		ResearcherRegisterDto researcherCreatedDto = new ResearcherRegisterDto(researcher);
		return researcherCreatedDto;
	}
	
	//ok, usado na hora de registrar
	public Researcher getBySSOtoRegister(String dsSSO) {
		Researcher researcher = researcherDAO.getByDsSSO(dsSSO);
		return researcher;
}
	
	public Researcher getByEmail(String dsEmail) {
		Researcher researcher = researcherDAO.getByDsEmail(dsEmail);
		return researcher;
	}

	public Researcher getByUuid(String cdUuid) {
		Researcher researcher = null;
		if (cdUuid != null) {
			researcher = researcherDAO.getByUuid(cdUuid);
		}
		return researcher;
	}


}