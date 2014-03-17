package com.projetocgt.core.petri;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.projetocgt.core.petri.entity.Arc;
import com.projetocgt.core.petri.entity.Place;
import com.projetocgt.core.petri.entity.Transition;

public class CpnXmlReader {
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	private Element elem;

	// elementos da rede
	Arc arc;
	Transition transition;

	// construtor que seta o caminho do XML
	public CpnXmlReader(InputStream xml) throws Exception {
		dbf = DocumentBuilderFactory.newInstance();

		db = dbf.newDocumentBuilder();

		// Funcionar sem internet: desabilitar verifica����o de pacotes
		db.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(String publicId, String systemId)
					throws SAXException, IOException {
				System.out.println("Ignoring " + publicId + ", " + systemId);
				return new InputSource(new StringReader(""));
			}
		});

		doc = db.parse(xml); // erro
		System.out.println("*** Passou ***");

		elem = doc.getDocumentElement();
		// pega todos os elementos place do XML
	}

	// ler do XML (CPN) e retorna um vector contendo os places
	public ArrayList<Place> lerPlaces() throws Exception {

		// pega todos os elementos place do XML
		NodeList nl = elem.getElementsByTagName("place");
		// teste
		// ClassesAuxiliares.Referencias.setRefPlaces(nl);

		// prepara o vetor
		ArrayList<Place> places = new ArrayList<Place>();

		// System.out.println("---- teste no cpnXmlReader");
		// percorre cada elemento place encontrado
		for (int i = 0; i < nl.getLength(); i++) {
			Element tagPlace = (Element) nl.item(i);
			NodeList nlInitmark = tagPlace.getElementsByTagName("initmark");
			NodeList nlType = tagPlace.getElementsByTagName("type");

			NodeList nlPosattr = tagPlace.getElementsByTagName("posattr");
			Element tagPosattr = (Element) nlPosattr.item(0);

			// ler: <text tool="CPN Tools" version="3.2.2">MyVar</text>
			// vari��vel: MyVar
			// dentro do marcador: <type id="ID1412319741">
			// se tiver dentro do marcador: <initmark id="ID1412319742">
			// - vai resultar na marca����o inicial (token inicial)
			NodeList tipoNoPNML_pai = tagPlace.getElementsByTagName("type"); // /mudei
																				// aqui
			// if ()
			// NodeList tipoNoPNML = tagPlace.getElementsByTagName( "text tool="
			// ); //erro!
			// String testeSeDarCerto = tagPlace.getAttribute( "text tool=" );
			// System.out.println("--> teste: "+testeSeDarCerto);

			// pega os dados do place atual
			String id = tagPlace.getAttribute("id");
			String text = getChildTagValue(tagPlace, "text");
			String initmark = "";
			String textType = "";
			String x = tagPosattr.getAttribute("x");
			String y = tagPosattr.getAttribute("y");

			// System.out.println("id: "+id+" || x: "+x+" || y: "+y);

			// // pega os dados do initmark que ��� filho do place atual
			for (int j = 0; j < nlInitmark.getLength(); j++) {
				Element tagInitmark = (Element) nlInitmark.item(j);
				if (tagInitmark != null)
					initmark = getChildTagValue(tagInitmark, "text");
				else
					initmark = null;
			}
			for (int j = 0; j < nlType.getLength(); j++) {
				Element tagType = (Element) nlType.item(j);
				textType = getChildTagValue(tagType, "text");
			}

			// cria uma nova instancia do Place com os dados do xml
			int xi = (int) Double.parseDouble(x);
			int yi = (int) Double.parseDouble(y);
			Place place = new Place(id, text, initmark, textType, xi, yi);

			// adiciona o place na colecao (vector) de places
			places.add(place);
		}

		return places;
	}

	// ler do XML (CPN) e retorna um vector contendo as transitions
	public ArrayList<Transition> lerTransitions() throws Exception {

		// pega todos os elementos place do XML
		NodeList nl = elem.getElementsByTagName("trans");

		// prepara o vetor
		ArrayList<Transition> transitions = new ArrayList<Transition>();

		// percorre cada elemento place encontrado

		for (int i = 0; i < nl.getLength(); i++) {
			Element tagTransition = (Element) nl.item(i);

			// pega os dados do place atual
			String id = tagTransition.getAttribute("id");
			String text = getChildTagValue(tagTransition, "text");

			// cria uma nova instancia do Place com os dados do xml
			transition = new Transition(id, text);

			// adiciona o place na cole������o (vector) de places
			transitions.add(transition);
		}

		return transitions;
	}

	// retorna as transi����es que levam a outras pages (redes) - indicativo de
	// Hierarquia
	public ArrayList<Transition> lerTransitionsHierarquicas() throws Exception {

		// pega todos os elementos transitions do XML
		NodeList nl = elem.getElementsByTagName("trans");

		// System.out.println("has attributes? "+ elem.hasAttributes());
		// System.out.println("has childe nodes? "+ elem.hasChildNodes());
		// System.out.println("textcontent: "+ elem.getTextContent());

		// prepara o vetor
		ArrayList<Transition> transitionsHierarquia = new ArrayList<Transition>();

		// System.out.println("tamanho nl: " + nl.getLength());

		// for (int i = 0; i < nl.getLength(); i++) {
		// System.out.println("item " + i + " do nl: " + nl.item(i));
		// System.out.println("content: " + nl.item(i).getTextContent());
		// }

		// percorre cada elemento transition encontrado
		for (int i = 0; i < nl.getLength(); i++) {
			// System.out.println("item "+i+" do nl: "+ nl.item( i ));
			Element tagTransition = (Element) nl.item(i);

			System.out.println("elemento posicao " + i
					+ " possui subpageinfo? "
					+ tagTransition.hasAttribute("subpageinfo"));

			if (tagTransition.hasAttribute("subst")) {

				// System.out.println("has attributes? "+
				// tagTransition.hasAttributes());
				// System.out.println("has childe nodes? "+
				// tagTransition.hasChildNodes());
				// System.out.println("textcontent: "+
				// tagTransition.getTextContent());

				// pega os dados da transicao atual
				String subst = tagTransition.getAttribute("subst");
				String text = getChildTagValue(tagTransition, "subpageinfo");

				// cria uma nova instancia do Transition com os dados do xml
				Transition mytransition = new Transition(subst, text);

				System.out.println("new transition: " + mytransition.getId());

				// adiciona o place na colecao (vector) de transicoes
				transitionsHierarquia.add(mytransition);
			}
		}

		return transitionsHierarquia;
	}

	// ler do XML (CPN) e retorna um vector contendo os arcs
	public ArrayList<Arc> lerArcs() throws Exception {

		// pega todos os elementos arc do XML
		NodeList nl = elem.getElementsByTagName("arc");

		// prepara o vetor
		ArrayList<Arc> arcs = new ArrayList<Arc>();

		// percorre cada elemento arc encontrado
		for (int i = 0; i < nl.getLength(); i++) {
			Element tagArc = (Element) nl.item(i);

			// transend s��� tem um elemento por isso nl.item(0)
			NodeList nlTransend = tagArc.getElementsByTagName("transend");
			Element tagTransend = (Element) nlTransend.item(0);

			// placeend s��� tem um elemento por isso nl.item(0)
			NodeList nlPlaceend = tagArc.getElementsByTagName("placeend");
			Element tagPlaceend = (Element) nlPlaceend.item(0);

			NodeList nlAnnot = tagArc.getElementsByTagName("annot");
			String text = "";

			// // pega os dados do text do annot filho que ��� filho do arc atual
			for (int j = 0; j < nlAnnot.getLength(); j++) {
				Element tagText = (Element) nlAnnot.item(j);
				text = getChildTagValue(tagText, "text");
			}

			// System.out.println("texto elemento: "+text); //texto ok!

			// pega os dados do arc atual
			String id = tagArc.getAttribute("id");

			int orientation = Arc.T_TO_P;
			if (tagArc.getAttribute("orientation").equals("TtoP")) {
				orientation = Arc.T_TO_P;
			} else
				orientation = Arc.P_TO_T;

			String transend = tagTransend.getAttribute("idref");
			String placeend = tagPlaceend.getAttribute("idref");

			// cria uma nova instancia do Place com os dados do xml
			arc = new Arc(id, orientation, transend, placeend, text);

			// adiciona o place na cole������o (vector) de places
			arcs.add(arc);
		}

		return arcs;
	}

	// este m���todo l��� e retorna o conte���do (texto) de uma tag (elemento)
	// filho da tag informada como par���metro. A tag filho a ser pesquisada
	// ��� a tag informada pelo nome (string)
	private String getChildTagValue(Element elem, String tagName)
			throws Exception {
		NodeList children = elem.getElementsByTagName(tagName);
		if (children == null)
			return null;

		Element child = (Element) children.item(0);

		if (child == null)
			return null;

		if (child.getFirstChild() == null)
			return null;

		return child.getFirstChild().getNodeValue();

	}
}
