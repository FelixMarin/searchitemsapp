package com.searchitemsapp.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.searchitemsapp.commons.CommonsPorperties;
import com.searchitemsapp.dto.UrlDTO;
import com.searchitemsapp.entities.TbSiaUrl;
import com.searchitemsapp.parsers.IFParser;
import com.searchitemsapp.repository.IFUrlRepository;

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
public class UrlDao extends AbstractDao implements IFUrlRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlDao.class);  
	
	/*
	 * Variables Globales
	 */
	@Autowired
	private IFParser<UrlDTO, TbSiaUrl> parser;

	/*
	 * Constructor
	 */
	public UrlDao() {
		super();
	}
	
	/**
	 * Método que devuelve todos los elementos de una tabla.
	 * 
	 * @return List<EmpresaDTO>
	 */
	@Override
	public List<UrlDTO> findAll() throws IOException {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		List<UrlDTO> resultado = null;
		
		/**
		 * Se obtiene la query del fichero de propiedades.
		 */
		StringBuilder stringBuilder = new StringBuilder(1);
		stringBuilder.append(CommonsPorperties.getValue("flow.value.url.select.all"));		
		
		/**
		 * Se ejecuta la consulta y se almacena en objeto de tipo query
		 */
		Query q = entityManager.createQuery(stringBuilder.toString(), TbSiaUrl.class);
		
		/**
		 * Se recupera el resultado de la query y se mapea a un objeto de tipo DTO.
		 */
		try {
			resultado = parser.toListDTO(((List<TbSiaUrl>) q.getResultList()));
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		stringBuilder.setLength(0);
		
		return resultado;
	}
	
	/**
	 * Método que devuelve un elemento de la 
	 * tabla dependiendo del identificador
	 * 
	 * @return UrlDTO
	 */
	@Override
	public UrlDTO findByDid(Integer did) throws IOException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		/**
		 * Si el parametro de entrada es nulo, el proceso
		 * termina y retorna nulo.
		 */
		if (Objects.isNull(did)) {
			return new UrlDTO();
		}
		
		UrlDTO urlDto = null;
		
		/**
		 * Se compone el mensaje que se mostrará como unta traza
		 * en el fichero de logs. Pinta el identificador de la marca.
		 */
		StringBuilder stringBuilder = new StringBuilder(1);
		stringBuilder.append(CommonsPorperties.getValue("flow.value.empresa.did.txt"))
		.append(StringUtils.SPACE).append(did);

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(stringBuilder.toString(),this.getClass());
		}
		
		/**
		 * Se obtiene el resutlado y se mapea a un objeto de tipo DTO.
		 * Si no hay resultado la excepcion se traza en los logs.
		 */
		try {
			urlDto = parser.toDTO(entityManager.find(TbSiaUrl.class, did));
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		stringBuilder.setLength(0);

		return urlDto;
	}

	/**
	 * Devuelve una lista de URLs correspondientes
	 * a un pais y a una categoria.
	 * 
	 * @param didPais Integer
	 * @param didCategoria Integer
	 * @exception IOException
	 */
	@Override
	public List<UrlDTO> findByDidAndDesUrl(Integer didPais, String didCategoria) throws IOException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		/**
		 * Si el parametro de entrada es nulo, el proceso
		 * termina y retorna nulo.
		 */
		if (Objects.isNull(didCategoria)) {
			return new ArrayList<>(NumberUtils.INTEGER_ONE);
		}
		
		List<UrlDTO> listUrlDto = null;
		
		/**
		 * Se obtiene la query del fichero de propiedades.
		 */
		StringBuilder stringBuilder = new StringBuilder(1);
		stringBuilder.append(CommonsPorperties.getValue("flow.value.url.select.url.by.pais.categoria"));
				
		/**
		 * Se ejecuta la consulta y se almacena en ubjeto de tipo query.
		 * Se le asignan los parámetros de entrada.
		 */
		Query query = entityManager.createNativeQuery(stringBuilder.toString());
		query.setParameter(CommonsPorperties.getValue("flow.value.empresa.didCategoria.key"), Integer.parseInt(didCategoria));
		query.setParameter(CommonsPorperties.getValue("flow.value.categoria.didPais.key"), didPais);
		
		/**
		 * Se recupera el resultado de la query y se mapea a un objeto de tipo DTO.
		 */
		try {
			
			/**
			 * Como la lista de datos que devuelve la consulta no 
			 * son todos del mismo tipo, el resultado será una array 
			 * de objetos. 
			 */
			listUrlDto = parser.toListODTO((List<Object[]>) query.getResultList());
			
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		stringBuilder.setLength(0);
		
		return listUrlDto;
	}

	/**
	 * Devuelve una lista de URLs correspondientes a un país y auna categoría.
	 * 
	 * @param Integer didPais
	 * @param Integer didCategoria
	 * @return List<UrlDTO>
	 * @exception IOException
	 */
	@Override
	public List<UrlDTO> findByDidAndNomUrl(Integer didPais, String didCategoria) throws IOException {

		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		/**
		 * Si el parametro de entrada es nulo, el proceso
		 * termina y retorna nulo.
		 */
		if (Objects.isNull(didCategoria)) {
			return new ArrayList<>(NumberUtils.INTEGER_ONE);
		}
		
		List<UrlDTO> listUrlDto = null;
		
		/**
		 * Se obtiene la query del fichero de propiedades.
		 */
		StringBuilder stringBuilder = new StringBuilder(1);
		stringBuilder.append(CommonsPorperties.getValue("flow.value.url.select.url.by.bollogin"));
		
		/**
		 * Se ejecuta la consulta y se almacena en ubjeto de tipo query.
		 */
		Query query = entityManager.createNativeQuery(stringBuilder.toString());
		query.setParameter(CommonsPorperties.getValue("flow.value.empresa.didCategoria.key"), Integer.parseInt(didCategoria));
		query.setParameter(CommonsPorperties.getValue("flow.value.categoria.didPais.key"), didPais);

		/**
		 * Se recupera el resultado de la query y se mapea a un objeto de tipo DTO.
		 */
		try {
			
			/**
			 * Como la lista de datos que devuelve la consulta no 
			 * son todos del mismo tipo, el resultado será una array 
			 * de objetos. 
			 */
			listUrlDto = parser.toListODTO((List<Object[]>) query.getResultList());
			
		}catch(NoResultException e) {
			if(LOGGER.isErrorEnabled()) {
				LOGGER.error(Thread.currentThread().getStackTrace()[1].toString(),e);
			}
		}
		
		stringBuilder.setLength(0);
		
		return listUrlDto;
	}
}
