package parser;

import java.io.File;
//import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import model.*;
import java.time.LocalDate;

public class CargaXML {
	public void cargarPublicacionesXML(PerfilInstagram perfil) {
		try {
			File archivoXML = new File("src/xml/publicaciones.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(archivoXML);
			doc.getDocumentElement().normalize();

			NodeList listaPublicaciones = doc.getElementsByTagName("publicacion");

			for (int i = 0; i < listaPublicaciones.getLength(); i++) {
				Node nodoPublicacion = listaPublicaciones.item(i);

				if (nodoPublicacion.getNodeType() == Node.ELEMENT_NODE) {
					Element elementoPublicacion = (Element) nodoPublicacion;

					Element elementoAudio = (Element) elementoPublicacion.getElementsByTagName("audio").item(0);
					Element elementoImagen = (Element) elementoPublicacion.getElementsByTagName("imagen").item(0);
					Element elementoVideo = (Element) elementoPublicacion.getElementsByTagName("video").item(0);

					String nombrePublicacion = elementoPublicacion.getElementsByTagName("nombrePublicacion").item(0)
							.getTextContent();
					String fechaSubida = elementoPublicacion.getElementsByTagName("fechaSubida").item(0)
							.getTextContent();
					LocalDate fechaSubidaLocalDate = LocalDate.parse(fechaSubida);
					int cantMG = Integer
							.parseInt(elementoPublicacion.getElementsByTagName("cantMG").item(0).getTextContent());

					if (elementoAudio != null) {
						// Leer y procesar datos del audio
						int velocidadBits = Integer
								.parseInt(elementoAudio.getElementsByTagName("velocidadBits").item(0).getTextContent());
						Audio audio = new Audio(nombrePublicacion, fechaSubidaLocalDate, cantMG, velocidadBits);
						cargaEtiqComentAlbum(elementoAudio, audio);
						perfil.addPublicacion(audio);
					} else if (elementoImagen != null) {
						// Leer y procesar datos de la imagen
						String resolucion = elementoImagen.getElementsByTagName("resolucion").item(0).getTextContent();
						int ancho = Integer
								.parseInt(elementoImagen.getElementsByTagName("ancho").item(0).getTextContent());
						int alto = Integer
								.parseInt(elementoImagen.getElementsByTagName("alto").item(0).getTextContent());
						Imagen imagen = new Imagen(nombrePublicacion, fechaSubidaLocalDate, cantMG, resolucion, ancho,
								alto);
						cargaEtiqComentAlbum(elementoImagen, imagen);
						perfil.addPublicacion(imagen);
					} else if (elementoVideo != null) {
						// Leer y procesar datos del video
						String resolucion = elementoVideo.getElementsByTagName("resolucion").item(0).getTextContent();
						int cantCuadros = Integer
								.parseInt(elementoVideo.getElementsByTagName("cantCuadros").item(0).getTextContent());
						Video video = new Video(nombrePublicacion, fechaSubidaLocalDate, cantMG, resolucion,
								cantCuadros);
						cargaEtiqComentAlbum(elementoVideo, video);
						perfil.addPublicacion(video);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cargaEtiqComentAlbum(Element elementoPublicacion, Publicacion publicacion) {
		NodeList listaEtiquetas = elementoPublicacion.getElementsByTagName("etiqueta");
		for (int i = 0; i < listaEtiquetas.getLength(); i++) {
			String etiqueta = listaEtiquetas.item(i).getTextContent();
			publicacion.agregarEtiqueta(etiqueta);
		}

		NodeList listaComentarios = elementoPublicacion.getElementsByTagName("comentario");
		for (int i = 0; i < listaComentarios.getLength(); i++) {
			String comentario = listaComentarios.item(i).getTextContent();
			publicacion.agregarComentario(comentario);
		}

		NodeList listaAlbumes = elementoPublicacion.getElementsByTagName("album");
		for (int i = 0; i < listaAlbumes.getLength(); i++) {
			String albumNombre = listaAlbumes.item(i).getTextContent();
			Album album = new Album(albumNombre);
			publicacion.agregarAlbum(album);
		}
	}
}
