package com.searchitemsapp.scraping;

import java.net.MalformedURLException;
import java.util.List;

import org.jsoup.nodes.Document;

import com.searchitemsapp.dto.SelectoresCssDTO;
import com.searchitemsapp.dto.UrlDTO;

/**
 * Representa de forma abstracta el objeto que queremos crear, 
 * mediante esta interface se definen la estructura que tendrán
 * los objetos 'Scraping'. 
 * 
 * @author Felix Marin Ramirez
 *
 */
public interface IFScrapingEmpresas {

	public abstract List<String> getListaUrls(final Document document, final UrlDTO urlDto,
			final SelectoresCssDTO selectorCssDto) throws MalformedURLException;
}