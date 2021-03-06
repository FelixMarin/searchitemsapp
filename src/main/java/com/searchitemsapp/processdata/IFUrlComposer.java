package com.searchitemsapp.processdata;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.dto.SelectoresCssDTO;
import com.searchitemsapp.dto.UrlDTO;

public interface IFUrlComposer {

	abstract List<UrlDTO> replaceWildcardCharacter(final String didPais, 
			final String didCategoria, 
			final String producto,
			final String empresas,
			final List<SelectoresCssDTO> listTodosSelectoresCss,
			final Map<String,EmpresaDTO> mapEmpresas) throws IOException;
	
	abstract void applicationData(Map<String,EmpresaDTO> mapEmpresas, 
			Map<Integer,Boolean> mapDynEmpresas);
	
	abstract List<MarcasDTO> getListTodasMarcas() throws IOException;
	
	abstract List<SelectoresCssDTO> listSelectoresCssPorEmpresa(
			final String didEmpresas);
	
	abstract String getErrorJsonMessage();
	
}
