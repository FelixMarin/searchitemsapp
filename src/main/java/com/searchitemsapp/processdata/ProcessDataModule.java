package com.searchitemsapp.processdata;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.searchitemsapp.commons.CommonsPorperties;
import com.searchitemsapp.dto.ResultadoDTO;
import com.searchitemsapp.dto.UrlDTO;

/**
 * Esta clase es la encargada de inicializar el proceso
 * de chequeo de los datos extraidos de las páginas web 
 * rastreadas. El proceso de consulta, extracción y 
 * refinamiento de datos se realiza en tiempo real, lo que
 * permite tener la información totalmente actualizada.
 * {@link ProcessDataAbstract}, {@link ProcessDataLogin}
 * 
 * @author Felix Marin Ramirez
 *
 */
public class ProcessDataModule extends ProcessDataLogin  implements Callable<List<ResultadoDTO>> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDataModule.class);  
	
	/*
	 * Constantes Globales
	 */		
	private static final String SEPARADOR_URL = "%20";
	private static final String SCRAP_NO_PATTERN = "SCRAP_NO_PATTERN";
	private static final String SCRAP_PATTERN = "SCRAP_PATTERN";
	
	/* 
	 * Variables Globales
	 */
	private static Map<Integer, Map<String, String>> mapaCookies = new HashMap<>(NumberUtils.INTEGER_ONE);
	private UrlDTO urlDto; 
	private String producto;
	private String didPais; 
	private String didCategoria;
	private String ordenacion;
	
	/*
	 * Constructor
	 */
	public ProcessDataModule(UrlDTO urlDto, String producto, 
			String didPais, String didCategoria, String ordenacion) {
		super();
		this.urlDto = urlDto;
		this.producto = producto;
		this.didPais = didPais;
		this.didCategoria = didCategoria;
		this.ordenacion = ordenacion;
	}
	
	/**
	 * Método que realiza el proceso de web para el procesamiento de datos.
	 * Primero obtiene los html de los sitios web, después
	 * Estrae la información relevante y la inserta en una 
	 * lista de objetos. Finalmente se devuelve una lista
	 * con todos los resultados obtenidos.
	 * 
	 * @return List<ResultadoDTO>
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 */
	public  List<ResultadoDTO> checkHtmlDocument() throws IOException, URISyntaxException, InterruptedException {
		
		/**
		 * Se validan los valores de entrada. Si
		 * el resultado false, la ejecucion termina
		 * devolviendo nulo.
		 */
		if(validaUrlDto()) {
			return new ArrayList<>(NumberUtils.INTEGER_ONE);
		}
		
		/*
		 * Variables
		 */
		List<ResultadoDTO> lResultadoDto;
		Elements entradas;
		ResultadoDTO resDto;
		
		/**
		 * Se extraen los valores principales para validarlos
		 * y procesar según proceda.
		 */
		boolean bStatus = urlDto.getBolStatus();
		String[] arProducto = producto.split(StringUtils.SPACE);
		int iIdEmpresa = urlDto.getDidEmpresa();
		Pattern pattern = createPatternProduct(arProducto);
		
		/**
		 * Si la página solicitada está disponible,
		 * se desencadenará el proceso, en otro
		 * caso la ejecución termina con nulo por
		 * respuesta.
		 */			
        if (getStatus(bStatus) == 200) {
        	
        	/**
        	 * Se obtienen los selectores que se usarán para
        	 * estraer la información de la página web.
        	 */
    		Map<String, String> mapLoginPageCookies = mapaCookies.get(iIdEmpresa);
        	
        	if(Objects.isNull(mapLoginPageCookies)) {
    			mapLoginPageCookies = checkingHtmlLoginDocument(didPais, didCategoria, iIdEmpresa, mapaCookies);
        	}
        	
        	/**
        	 * Se obtiene el listado de documentos de los que se
        	 * van a extraer los datos.
        	 */
        	List<Document> listDocuments = getHtmlDocument(urlDto, mapLoginPageCookies, producto);
        	
        	lResultadoDto = new ArrayList<>(NumberUtils.INTEGER_ONE);
        	
        	/**
        	 * Se itera sobre cada uno de los documentos
        	 * de los cuales se extraerá la información
        	 * que se necesita.
        	 */
        	for (Document document : listDocuments) {
        	
        		/**
        		 * Si el objeto es nulo se continua 
        		 * con la siguente iteración.
        		 */
	        	if(Objects.isNull(document)) {
	            	continue;
	            }

	        	/**
	        	 * Se comprueba que la lista no esté vacía y que
	        	 * la URL sea válida. En el caso de que no se 
	        	 * cumpla la condición, termina el proceso y 
	        	 * returna nulo.
	        	 */
	            if(listDocuments.size() == 1 && 
	            		!validaURL(document.baseUri(),urlDto.getNomUrl()
	            				.replace(StringUtils.SPACE, SEPARADOR_URL))) {
	            	return new ArrayList<>(NumberUtils.INTEGER_ONE);
	            }
	            
	            /**
	             * Se extraen de la página los elementos
	             * que contiene los datos.
	             */
	            entradas = selectScrapPattern(document,
	            		urlDto.getSelectores().get(SCRAP_PATTERN), 
	            		urlDto.getSelectores().get(SCRAP_NO_PATTERN));

	            /**
	             * De cada elemento se extrae la
	             * información y se añade a un 
	             * objeto de tipo resultado.
	             */
	    		for (Element elem : entradas) {
	    			
	    			/**
	    			 * Si el elemento actual supera las
	    			 * validaciones, la operación 
	    			 * continua, en otro caso termina
	    			 * la iteración.
	    			 */
	    			if(validaSelector(elem)) {
	    				continue;
	    			}
	    			
	    			/**
	    			 * En este punto se extraen los datos del objeto
	    			 * element y se añaden en un objeto DTO.
	    			 */
	    			resDto = fillDataResultadoDTO(elem, urlDto, ordenacion);
	    			
	    			/**
	    			 * Se realiza la última comprovación y 
	    			 * se añade el resultado a la lista de
	    			 * resultados que será retornada.
	    			 */
	    			if(validaYCargaResultado(iIdEmpresa, arProducto, resDto,  pattern)) {
	    				lResultadoDto.add(resDto);
	    			}
		        }
        	}	
        } else {
        	return new ArrayList<>(NumberUtils.INTEGER_ONE);
        }   
        
        return lResultadoDto;
	}

	/**
	 * Método publico desde el que se inicializa el proceso.
	 * Devuelve una lista con los resultado obtenidos.
	 * 
	 * @return 
	 */
	@Override
	public List<ResultadoDTO> call() throws IOException, URISyntaxException, InterruptedException {
		return checkHtmlDocument();
	}
	
	//-- Métodos privados --//
	
	/**
	 * Devuelve el código del estado de la conexion a un recurso web.
	 * 
	 * @param bStatus
	 * @return int
	 */
	private int getStatus(final boolean bStatus) {
		return bStatus?getStatusConnectionCode(urlDto.getNomUrl()):200;
	}
	
	/**
	 * Valida el parámetro de entrada de tipo UrlDTO.
	 * 
	 * @return boolean
	 */
	private boolean validaUrlDto() {
		return Objects.isNull(urlDto) ||
				Objects.isNull(urlDto.getDidEmpresa()) ||
				urlDto.getSelectores().isEmpty();
	}
		
	/**
	 * Método que realiza validaciones de los valores
	 * obtenidos del objeto element. 
	 * 
	 * @param iIdEmpresa
	 * @param arProducto
	 * @param resDto
	 * @param pattern
	 * @return boolean
	 */
	private boolean validaYCargaResultado(final int iIdEmpresa, 
			final String[] arProducto, 
			final ResultadoDTO resDto, 
			final Pattern pattern) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		/**
		 * Se comprueba que los principales valores
		 *  del producto no sean nulos.
		 */
		if(Objects.isNull(resDto.getNomProducto()) || iIdEmpresa == 0 ||
				StringUtils.isAllEmpty(resDto.getPrecio()) ||
				Objects.isNull(resDto.getPrecioKilo()) || 
				StringUtils.isAllEmpty(resDto.getPrecioKilo())) {
			return Boolean.FALSE;
		} 
		
		/**
		 * Se elimina la marca de la descripción del producto
		 */
		String strProducto = filtroMarca(iIdEmpresa, resDto.getNomProducto());
		
		if(StringUtils.isAllBlank(strProducto)) {
			return Boolean.FALSE;
		}
		
		if(arProducto.length == 1 &&
				eliminarTildes(strProducto)
				.toLowerCase().startsWith(
						eliminarTildes(arProducto[0].trim())
				.toLowerCase().concat(StringUtils.SPACE))) {
			return Boolean.TRUE;			
		} else if(arProducto.length > 1) {			
			
			return pattern.matcher(eliminarTildes(strProducto).toUpperCase()).find();
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * Método que valida una URL.
	 * 
	 * @param baseUri
	 * @param url
	 * @return boolean
	 */
	private boolean validaURL(final String baseUri,final String url) {
		return url.equalsIgnoreCase(baseUri);
	}
	
	private boolean validaSelector(Element elem) {
		return Objects.nonNull(elem.selectFirst(CommonsPorperties.getValue("flow.value.pagina.siguiente.carrefour"))) ||
		Objects.nonNull(elem.selectFirst(CommonsPorperties.getValue("flow.value.pagina.acceso.popup.peso")));
	}	
}