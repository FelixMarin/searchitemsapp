package com.searchitemsapp.repository;
import java.io.IOException;
import java.util.List;

import org.springframework.data.repository.Repository;

import com.searchitemsapp.dto.EmpresaDTO;
import com.searchitemsapp.model.TbSiaCategoriasEmpresa;
import com.searchitemsapp.model.TbSiaEmpresa;

/**
 * Interfaz que se encarga de gestionar todas las 
 * operaciones de persistencia contra la tabla 
 * 'TbSiaEmpresa' de la base de datos.
 * 
 * @author Felix Marin Ramirez
 *
 */
public interface IFEmpresaRepository extends Repository<TbSiaEmpresa, Long>{
	List<EmpresaDTO> findAll() throws IOException;
	EmpresaDTO findByDid(Integer did) throws IOException;
	List<EmpresaDTO> findByDidAndTbSiaCategoriasEmpresa(Integer didEmpresa, TbSiaCategoriasEmpresa tbSiaCategoriasEmpresa) throws IOException;
	List<EmpresaDTO> findByTbSiaCategoriasEmpresa(TbSiaCategoriasEmpresa tbSiaCategoriasEmpresa) throws IOException;
}
