package com.searchitemsapp.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.searchitemsapp.config.IFCommonsProperties;
import com.searchitemsapp.dao.repository.IFUrlRepository;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.PaisDTO;
import com.searchitemsapp.dto.UrlDTO;



/**
 * Implementación del dao.
 * 
 * Esta clase ofrece los métodos que permiten interactuar con
 * la capa de persistencia. 
 * 
 * @author Felix Marin Ramirez
 *
 */
@Component
public class UrlImpl implements IFUrlImpl, IFImplementacion<UrlDTO, CategoriaDTO> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UrlImpl.class);  
	
	private static final String COMA = ",";
	private static final String ALL = "ALL";

	@Autowired
	private IFUrlRepository urlDao;
	
	@Autowired
	private IFCommonsProperties iFCommonsProperties;
	
	public UrlImpl() {
		super();
	}

	/**
	 * Recupera un elemento de la tabla a partir de su identificador.
	 * 
	 * @param UrlDTO
	 * @return UrlDTO
	 * @exception IOException
	 */
	@Override
	public UrlDTO findByDid(final UrlDTO urlDTO) throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
			
		return urlDao.findByDid(urlDTO.getDid());
	}
	
	public List<UrlDTO> obtenerUrls(PaisDTO paisDto, CategoriaDTO categoriaDto) throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return urlDao.findByDidAndDesUrl(paisDto.getDid(), String.valueOf(categoriaDto.getDid()));
	}
	
	public List<UrlDTO> obtenerUrlsLogin(PaisDTO paisDto, CategoriaDTO categoriaDto) throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return urlDao.findByDidAndNomUrl(paisDto.getDid(), String.valueOf(categoriaDto.getDid()));
	}
	
	public List<UrlDTO> obtenerUrlsPorIdEmpresa(final PaisDTO paisDto, 
			final CategoriaDTO categoriaDto,
			final String idsEmpresas) 
			throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		String strIdsEmpresas;
		
		if(ALL.equalsIgnoreCase(idsEmpresas)) {
			strIdsEmpresas = iFCommonsProperties.getValue("flow.value.all.id.empresa");
		} else {
			strIdsEmpresas = idsEmpresas;
		}
		
		String[] arIdsEpresas = tokenizeString(strIdsEmpresas, COMA);
		List<UrlDTO> lsIdsEmpresas = Lists.newArrayList();
				
		List<UrlDTO> listUrlDTO = urlDao.findByDidAndDesUrl(paisDto.getDid(), String.valueOf(categoriaDto.getDid()));
		
		if(Objects.isNull(listUrlDTO)) {
			return lsIdsEmpresas;
		}
		
		for (String id : arIdsEpresas) {
			for (UrlDTO urlDTO : listUrlDTO) {
				if(Integer.parseInt(id) == urlDTO.getDidEmpresa()) {
					lsIdsEmpresas.add(urlDTO);
				}
			}
		}
		
		return lsIdsEmpresas;
	}
	
	private String[] tokenizeString(final String cadena, final String token) {
		
		StringTokenizer st = new StringTokenizer(cadena, token); 		
		List<String> listaAux = Lists.newArrayList();
		
		while (st.hasMoreElements()) {
			listaAux.add((String) st.nextElement());
		}
		
		return listaAux.toArray(new String[0]);
	}

	@Override
	public List<UrlDTO> findAll() throws IOException {
		throw new NotImplementedException("Funcionalidad no implementada");
	}

	@Override
	public List<UrlDTO> findByTbSia(UrlDTO r, CategoriaDTO t) throws IOException {
		throw new NotImplementedException("Funcionalidad no implementada");
	}
}
