package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.searchitemsapp.commons.CommonsPorperties;
import com.searchitemsapp.dto.MarcasDTO;
import com.searchitemsapp.model.TbSiaMarcas;
import com.searchitemsapp.repository.IFMarcasRepository;


/**
 * Encapsula el acceso a la base de datos. Por lo que cuando la capa 
 * de lógica de negocio necesite interactuar con la base de datos, va 
 * a hacerlo a través de la API que le ofrece el DAO.
 * 
 * @author Felix Marin Ramirez
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class MarcasDao extends AbstractDao<MarcasDTO, TbSiaMarcas> implements IFMarcasRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MarcasDao.class);     
	
	/*
	 * Constantes Globales
	 */
	private static final String MARCAS_PARSER = "MARCAS_PARSER";

	public MarcasDao() {
		super();
	}
	
	/**
	 * Método que devuelve todos los elementos de una tabla.
	 * 
	 * @return List<LoginDTO>
	 */
	@Override
	public List<MarcasDTO> findAll() throws IOException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		List<MarcasDTO> resultado = null;	
		
		/**
		 * Se obtiene la query del fichero de propiedades.
		 */
		StringBuilder queryBuilder = new StringBuilder(NumberUtils.INTEGER_ONE);
		queryBuilder.append(CommonsPorperties.getValue("flow.value.marcas.select.all"));
		
		/**
		 * Se comprueba que el entity manager esté activado.
		 */
		isEntityManagerOpen(this.getClass());
		
		/**
		 * Se ejecuta la consulta y se almacena en ubjeto de tipo query
		 */
		Query q = getEntityManager().createQuery(queryBuilder.toString(), TbSiaMarcas.class);
		
		try {
			/**
			 * Se recupera el resultado de la query y se mapea a un objeto de tipo DTO.
			 */
			resultado = getParser(MARCAS_PARSER).toListDTO(((List<TbSiaMarcas>) q.getResultList()));
		}catch(NoResultException e) {			
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
			}
		}		
		
		return resultado;
	}

	/**
	 * A partir de un indentifcador se obtiene un elemento de la tabla.
	 * 
	 * @return MarcasDTO
	 */
	@Override
	public MarcasDTO findByDid(Integer did) throws IOException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		/**
		 * Si el parametro de entrada es nulo, el proceso
		 * termina y retorna nulo.
		 */
		if(Objects.isNull(did)) {
			return null;
		}		
		
		MarcasDTO resultado = null;
		
		/**
		 * Se compone el mensaje que se mostrará como unta traza
		 * en el fichero de logs. Pinta el identificador de la marca.
		 */
		final StringBuilder debugMessage = new StringBuilder(NumberUtils.INTEGER_ONE);
		debugMessage.append(CommonsPorperties.getValue("flow.value.marcas.did.txt"));
		debugMessage.append(StringUtils.SPACE);
		debugMessage.append(did);	
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(debugMessage.toString(),this.getClass());
		}
		
		/**
		 * Se obtiene el resutlado y se mapea a un objeto de tipo DTO.
		 * Si no hay resultado la excepcion se traza en los logs.
		 */
		try {
			resultado = getParser(MARCAS_PARSER).toDTO(getEntityManager().find(TbSiaMarcas.class, did));
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		return resultado;
	}	
}
