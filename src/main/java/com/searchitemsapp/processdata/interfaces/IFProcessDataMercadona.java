package com.searchitemsapp.processdata.interfaces;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.searchitemsapp.dto.ResultadoDTO;

public interface IFProcessDataMercadona extends IFProcessDataEmpresas {
	
	String getResult(final Element elem, final String cssSelector);
	Document getDocument(final String url, final String body);
	Connection getConnection(final String strUrl, final String producto);
	String getUrlAll(final ResultadoDTO resDto);
}