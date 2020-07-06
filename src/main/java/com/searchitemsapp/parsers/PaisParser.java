package com.searchitemsapp.parsers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.searchitemsapp.dto.PaisDTO;
import com.searchitemsapp.entities.TbSiaEmpresa;
import com.searchitemsapp.entities.TbSiaMarcas;
import com.searchitemsapp.entities.TbSiaNomProducto;
import com.searchitemsapp.entities.TbSiaPais;

/**
 * Es un componente analizador de software que 
 * toma datos de entrada y construye una 
 * estructura de datos. 
 * 
 * @author Felix Marin Ramirez
 *
 */
public class PaisParser implements IFParser<PaisDTO, TbSiaPais> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaisParser.class); 
	
	@Autowired
	PaisDTO paisPDto;
	
	@Autowired
	TbSiaPais tbSiaPPais;
		
	/*
	 * Constructor
	 */
	public PaisParser() {
		super();
	}
	
	/**
	 * Mapea los datos de un objeto de tipo Entity a un objeto de tipo DTO.
	 * 
	 * @param TbSiaPais
	 * @return PaisDTO
	 */
	public PaisDTO toDTO(TbSiaPais tbSiaPPais) {	
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		paisPDto.setBolActivo(tbSiaPPais.getBolActivo());
		paisPDto.setDesPais(tbSiaPPais.getDesPais());
		paisPDto.setDid(tbSiaPPais.getDid());
		paisPDto.setNomPais(tbSiaPPais.getNomPais());
		
		TbSiaEmpresa tbSiaEmpresa = tbSiaPPais.getTbSiaEmpresas().get(NumberUtils.INTEGER_ZERO);
		LinkedHashMap<Integer, String> mapEmpresa = new LinkedHashMap<>(NumberUtils.INTEGER_ONE);
		mapEmpresa.put(tbSiaEmpresa.getDid(), tbSiaEmpresa.getNomEmpresa());
		paisPDto.setEmpresas(mapEmpresa);
		
		TbSiaMarcas tbSiaMarcas = tbSiaPPais.getTbSiaMarcas().get(NumberUtils.INTEGER_ZERO);
		LinkedHashMap<Integer, String> mapMarcas = new LinkedHashMap<>(NumberUtils.INTEGER_ONE);
		mapMarcas.put(tbSiaMarcas.getDid(), tbSiaMarcas.getNomMarca());
		paisPDto.setMarcas(mapMarcas);
		
		
		TbSiaNomProducto tbSiaNomProductos = tbSiaPPais.getTbSiaNomProductos().get(NumberUtils.INTEGER_ZERO);
		LinkedHashMap<Integer, String> mapProductos = new LinkedHashMap<>(NumberUtils.INTEGER_ONE);
		mapProductos.put(tbSiaNomProductos.getDid(), tbSiaNomProductos.getNomProducto());
		paisPDto.setProductos(mapProductos);
		
		return paisPDto;
	}
	
	/**
	 * Mapea los datos de un objeto de tipo DTO a un objeto de tipo Entity.
	 * 
	 * @param PaisDTO
	 * @return TbSiaPais
	 */
	public TbSiaPais toTbSia(PaisDTO paisPDto) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		tbSiaPPais.setBolActivo(paisPDto.getBolActivo());
		tbSiaPPais.setDesPais(paisPDto.getDesPais());
		tbSiaPPais.setDid(paisPDto.getDid());
		tbSiaPPais.setNomPais(paisPDto.getNomPais());

		for (Map.Entry<Integer,String> e  : paisPDto.getEmpresas().entrySet()) {
			TbSiaEmpresa tbempresa = new TbSiaEmpresa();
			tbempresa.setDid(e.getKey());
			tbempresa.setNomEmpresa(e.getValue());
			tbSiaPPais.getTbSiaEmpresas().add(tbempresa);
		}
		
		for (Map.Entry<Integer,String> e  : paisPDto.getMarcas().entrySet()) {
			TbSiaMarcas tbmarcas = new TbSiaMarcas();
			tbmarcas.setDid(e.getKey());
			tbmarcas.setNomMarca(e.getValue());
			tbSiaPPais.getTbSiaMarcas().add(tbmarcas);
		}
		
		for (Map.Entry<Integer,String> e  : paisPDto.getProductos().entrySet()) {
			TbSiaNomProducto tbproductos = new TbSiaNomProducto();
			tbproductos.setDid(e.getKey());
			tbproductos.setNomProducto(e.getValue());
			tbSiaPPais.getTbSiaNomProductos().add(tbproductos);
		}
		
		return tbSiaPPais;
	}

	/**
	 * Método no implementado.
	 */
	@Override
	public List<PaisDTO> toListDTO(List<TbSiaPais> objeto) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return new ArrayList<>(NumberUtils.INTEGER_ONE);
	}
	
	/**
	 * Método no implementado.
	 */
	@Override
	public List<PaisDTO> toListODTO(List<Object[]> objeto) {
		
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info(Thread.currentThread().getStackTrace()[1].toString());
		}
		
		return new ArrayList<>(NumberUtils.INTEGER_ONE);
	}
}
