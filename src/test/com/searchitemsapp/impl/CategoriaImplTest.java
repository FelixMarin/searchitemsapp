package com.searchitemsapp.impl;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.searchitemsapp.config.CommonsPorperties;
import com.searchitemsapp.dto.CategoriaDTO;
import com.searchitemsapp.dto.EmpresaDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/resources/context-servicefactory-test.xml")
@WebAppConfiguration
public class CategoriaImplTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CategoriaImplTest.class); 
	
    @Autowired
    private ServletContext context;
    
    @Autowired
    CategoriaImpl categoriaImpl;
	
    @BeforeClass
    public static void setLogger() throws MalformedURLException {
    	org.apache.log4j.BasicConfigurator.configure();
        System.setProperty("log4j.properties","log4j.properties");
        System.setProperty("db.properties","db.properties");
        System.setProperty("flow.properties","flow.properties");
    }
    
    @Before
    public void init() throws IOException {
    	ServletContextEvent sve =  new ServletContextEvent(context);
		
		CommonsPorperties.loadPropertiesFile(
				"E:\\workspace_sts\\properties\\flow.properties",
				"flow.properties", sve);
		
		CommonsPorperties.loadPropertiesFile(
				"E:\\workspace_sts\\properties\\db.properties",
				"db.properties", sve);
    }

	@Test
	public void testFindAll() throws IOException {

		LOGGER.debug(Thread.currentThread().getStackTrace()[1].toString());
		
		List<CategoriaDTO> cdto = categoriaImpl.findAll();
		
		assertNotNull(cdto);
	}

	@Test
	public void testFindByDid() throws IOException {
		
		LOGGER.debug(Thread.currentThread().getStackTrace()[1].toString());
		
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setDid(101);
		CategoriaDTO cdto = categoriaImpl.findByDid(categoriaDTO);
		
		assertNotNull(cdto);
		
	}

	@Test
	public void testFindByTbSia() throws IOException {

		LOGGER.debug(Thread.currentThread().getStackTrace()[1].toString());
		
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		EmpresaDTO empresaDTO = new EmpresaDTO();
		categoriaDTO.setDid(101);
		empresaDTO.setDid(101);
		List<CategoriaDTO> cdto = categoriaImpl.findByTbSia(categoriaDTO, empresaDTO);
		
		assertNotNull(cdto);
		
	}

}
