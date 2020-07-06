package com.searchitemsapp.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.searchitemsapp.services.IFService;
import com.searchitemsapp.services.ListadoProductosService;

/**
 * Clase Factory encargada de gestionar la creación de 
 * objetos de tipo service. Las peticiones a los services 
 * pasarán siempre por esta clase.
 *
 * @author Felix Marin Ramirez
 */
@Component
public class ServiceFactory {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceFactory.class);  
	
	/*
	 * Constantes Globales
	 */
	private static final String LISTA_PRODUCTOS = "LISTA_PRODUCTOS";

	/*
	 * VAriables Globales
	 */
	@Autowired
	private ListadoProductosService listadoProductosService;
	
	/*
	 * Constructor
	 */
	public ServiceFactory() {
		super();
	}
	
	/**
	 * Método de la clase factory que gestiona la creación 
	 * de instancias de servicios.
	 * 
	 * @param String
	 * @return IFService<String,String>
	 */
	public IFService<String,String> getService(final String nomService) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}

		if(nomService.equals(LISTA_PRODUCTOS)) {
			return listadoProductosService;
		}
		
		return null;
	}

}
