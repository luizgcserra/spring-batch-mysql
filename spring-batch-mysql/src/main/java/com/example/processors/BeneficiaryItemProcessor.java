package com.example.processors;

import org.springframework.batch.item.ItemProcessor;

import com.example.model.Beneficiary;
import com.example.model.BeneficiaryKey;

public class BeneficiaryItemProcessor
		implements ItemProcessor<com.example.contracts.Beneficiary, com.example.model.Beneficiary> {

	@Override
	public com.example.model.Beneficiary process(final com.example.contracts.Beneficiary beneficiary) throws Exception {
		com.example.model.Beneficiary model = new Beneficiary();

		model.setKey(
				new BeneficiaryKey(beneficiary.getNisBeneficiario(), beneficiary.getMesCompetencia().replace("/", "")));
		model.setCodigoAcao(beneficiary.getCodigoAcao());
		model.setCodigoFuncao(beneficiary.getCodigoFuncao());
		model.setCodigoMunicipio(beneficiary.getCodigoMunicipio());
		model.setCodigoPrograma(beneficiary.getCodigoPrograma());
		model.setCodigoSubFuncao(beneficiary.getCodigoSubFuncao());
		model.setDataSaque(beneficiary.getDataSaque());
		model.setFonteFinalidade(beneficiary.getFonteFinalidade());
		model.setMesReferencia(beneficiary.getMesReferencia());
		model.setNomeBeneficiario(beneficiary.getNomeBeneficiario());
		model.setNomeMunicipio(beneficiary.getNomeMunicipio());
		model.setUf(beneficiary.getUf());
		model.setValorParcela(Double.parseDouble(beneficiary.getValorParcela().replace(",", "")));

		return model;
	}

}
