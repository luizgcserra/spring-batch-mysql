package com.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BeneficiaryKey implements Serializable {

	private static final long serialVersionUID = 5558824374644672397L;

	@Column
	private String nisBeneficiario;

	@Column
	private String mesCompetencia;
	
	public BeneficiaryKey() {
		
	}

	public BeneficiaryKey(String nisBeneficiario, String mesCompetencia) {
		super();
		this.nisBeneficiario = nisBeneficiario;
		this.mesCompetencia = mesCompetencia;
	}

	public String getNisBeneficiario() {
		return nisBeneficiario;
	}

	public void setNisBeneficiario(String nisBeneficiario) {
		this.nisBeneficiario = nisBeneficiario;
	}

	public String getMesCompetencia() {
		return mesCompetencia;
	}

	public void setMesCompetencia(String mesCompetencia) {
		this.mesCompetencia = mesCompetencia;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
