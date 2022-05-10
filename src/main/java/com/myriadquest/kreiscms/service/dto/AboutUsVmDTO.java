package com.myriadquest.kreiscms.service.dto;

import java.util.List;

public class AboutUsVmDTO {
	
	private List<AboutUsDTO> aboutUsDTOs;
	private List<VisionAndMissionDTO> vms;
	public List<AboutUsDTO> getAboutUsDTOs() {
		return aboutUsDTOs;
	}
	public void setAboutUsDTOs(List<AboutUsDTO> aboutUsDTOs) {
		this.aboutUsDTOs = aboutUsDTOs;
	}
	public List<VisionAndMissionDTO> getVms() {
		return vms;
	}
	public void setVms(List<VisionAndMissionDTO> vms) {
		this.vms = vms;
	}
	
	

}
