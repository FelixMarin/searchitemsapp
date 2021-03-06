package com.searchitemsapp.dao.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.data.repository.Repository;

import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.entities.TbSiaCategoriasEmpresa;
 
/**
 * Representa de forma abstracta el objeto que 
 * se encarga de gestionar todas las operaciones 
 * de persistencia contra la tabla 'TbSiaCategoriasEmpresa' 
 * de la base de datos.
 * 
 * @author Felix Marin Ramirez
 *
 */
public interface IFCategoriaRepository extends Repository<TbSiaCategoriasEmpresa, Long> {
	List<CategoriaDTO> findAll() throws IOException;
	CategoriaDTO findByDid(Integer did);
}
