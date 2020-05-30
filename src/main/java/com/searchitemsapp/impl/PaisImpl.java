package com.searchitemsapp.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.searchitemsapp.commons.CommonsPorperties;
import com.searchitemsapp.dao.PaisDao;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.PaisDTO;
import com.searchitemsapp.repository.IFPaisRepository;

/**
 * Implementación del dao {@link PaisDao}.
 * 
 * Esta clase ofrece los métodos que permiten interactuar con
 * la capa de persistencia. 
 * 
 * @author Felix Marin Ramirez
 *
 */
@Aspect
public class PaisImpl implements IFImplementacion<PaisDTO, CategoriaDTO> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaisImpl.class);  
	
	/*
	 * Variables Globales
	 */
	@Autowired
	private IFPaisRepository paisDao;
	
	/*
	 * Constructor
	 */
	public PaisImpl() {
		super();
	}
	
	/**
	 * Recupera un elemento de la tabla a partir
	 * de su identificador.
	 * 
	 * @param PaisDTO
	 * @return PaisDTO
	 * @exception IOException
	 */
	@Override
	public PaisDTO findByDid(PaisDTO paisDto) throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		/**
		 * Si el parametro de entrada es nulo, el proceso
		 * termina y retorna nulo.
		 */
		if(Objects.isNull(paisDto)) {
			return null;
		}
		
		/**
		 * Traza de log que escribe identificador del pais.
		 */
		final StringBuilder debugMessage = new StringBuilder(NumberUtils.INTEGER_ONE);
		debugMessage.append(CommonsPorperties.getValue("flow.value.pais.did.txt"));
		debugMessage.append(StringUtils.SPACE);
		debugMessage.append(paisDto.getDid());
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(debugMessage.toString(),this.getClass());
		}
		
		/**
		 * Devuelve un objeto con el valor solicitado.
		 */
		return paisDao.findByDid(paisDto.getDid());
	}

	/**
	 * Funcionalidad no implementada.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public List<PaisDTO> findAll() throws IOException {
		throw new UnsupportedOperationException(OPERACION_NO_SOPORTADA);
	}

	/**
	 * Funcionalidad no implementada.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public List<PaisDTO> findByTbSia(PaisDTO paisDTO, CategoriaDTO categoriaDTO) throws IOException {
		throw new UnsupportedOperationException(OPERACION_NO_SOPORTADA);
	}
}
