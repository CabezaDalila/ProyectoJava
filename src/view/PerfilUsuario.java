package view;

import model.*;
import sistema.*;
import utils.AssetsUtils;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;
import exception.*;
import java.util.*;
import java.util.List;


public class PerfilUsuario extends JFrame {
	
	private JPanel contentPane;
	private static PerfilInstagram perfilInstagram;
	

	public static void main(String[] args) {
		perfilInstagram = PerfilInstagram.getInstance();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				PerfilUsuario frame = new PerfilUsuario();
				frame.setVisible(true);
			}
		});
	}

	public PerfilUsuario() {
		setTitle("Perfil del Usuario");
		//setSize(787,401);
		//setSize(787,801);
		// setMaximumSize(getMaximumSize());
		setForeground(Color.DARK_GRAY);
		setBackground(Color.GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 607, 401);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		
		menuTop();
	}
	
public void menuTop() {
		
		/**
		 * Setea el Menu Principal de la Interfaz
		 */
		
		JMenuBar menuPrincipal = new JMenuBar();
		
		menuPrincipal.setOpaque(false);
		menuPrincipal.setBackground(Color.LIGHT_GRAY);
		menuPrincipal.setFont(new Font("Open Sans", Font.PLAIN, 20));
		menuPrincipal.setBorderPainted(true);
		
		menuPrincipal.add(menuTOPalbumes());
		menuPrincipal.add(menuTOPreportes());
		menuPrincipal.add(menuTOPopciones());
		menuPrincipal.add(menuTOPestadisticas());

		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(menuPrincipal, BorderLayout.NORTH);
       
	}
	
	public JMenu menuTOPalbumes() {
		JMenu albumes = new JMenu("Álbumes");
		albumes.setFont(new Font("Open Sans", Font.PLAIN, 15));
		
		JMenuItem crearAlbum = new JMenuItem ("Crear álbum");
		
		crearAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreAlbum = JOptionPane.showInputDialog(null, "Ingrese el nombre del nuevo Album");
				if (nombreAlbum.isEmpty())
					nombreAlbum = JOptionPane.showInputDialog(null, "Ingrese el nombre del nuevo Album");
				else {
					Album nuevoAlbum = new Album(nombreAlbum);
					PerfilInstagram.getInstance().addAlbum(nuevoAlbum);				
					JOptionPane.showMessageDialog(null, "El álbum fue agregado con éxito");
				}
			}
		});
		
		/**
		 * Configura Gestionar Albumes
		 */
		JMenuItem gestionaAlbum = new JMenu ("Gestionar álbumes");
		
		JMenuItem gaAgregaPubli = new JMenuItem("Agregar Publicación a un Album");
		gaAgregaPubli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame();
		        frame.setAlwaysOnTop(true);
				String nombreAlbum = JOptionPane.showInputDialog("Ingrese el nombre del Album");
				String nombrePubli = JOptionPane.showInputDialog("Ingrese el nombre de la Publicación");
				try {
					Album album =perfilInstagram.buscaAlbum(nombreAlbum);
					Publicacion publicacion = perfilInstagram.buscaPubli(nombrePubli);
					perfilInstagram.addPubliDentroAlbum(album, publicacion);
					JOptionPane.showMessageDialog(null, "La publicación fue agregada con éxito");
				} catch (AlbumNoEncontradoException e1) {
					JOptionPane.showMessageDialog(null, "El álbum NO existe. Intente de nuevo.");
				}
				catch (PublicacionNoEncontradaException e1) {
					JOptionPane.showMessageDialog(null, "La publicación NO existe. Intente de nuevo.");
				}
			}
		});
		gestionaAlbum.add(gaAgregaPubli);
		
		JMenuItem gaEliminaPubli = new JMenuItem("Eliminar Publicación");
		gaEliminaPubli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombrePublicacion = JOptionPane.showInputDialog("Ingrese el nombre de la Publicacion");
				try {
					Publicacion publicacionAEliminar =perfilInstagram.buscaPubli(nombrePublicacion);
					perfilInstagram.eliminarPublicacion(publicacionAEliminar);
				}catch (PublicacionNoEncontradaException e1){
					JOptionPane.showMessageDialog(null, "La publicación NO existe. Intente de nuevo.");
				} catch (AlbumNoEncontradoException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Album no encontrado. Intente de nuevo.");
				}
			}
		});
		
		gestionaAlbum.add(gaEliminaPubli);
		
		JMenuItem gaSacarPubli = new JMenuItem("Sacar publicación de un Album");
		gaSacarPubli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombrePublicacion = JOptionPane.showInputDialog("Ingrese el nombre de la Publicacion");
				String nombreAlbum = JOptionPane.showInputDialog("Ingrese el nombre del Album");
				try {
					Publicacion publicacionAEliminar =perfilInstagram.buscaPubli(nombrePublicacion);
					Album album = perfilInstagram.buscaAlbum(nombreAlbum);
					perfilInstagram.sacarPublicacionDelAlbum(publicacionAEliminar, album);
					JOptionPane.showMessageDialog(null, "Publicacion sacada del album con exito");
				}catch (PublicacionNoEncontradaException e1){
					JOptionPane.showMessageDialog(null, "La publicación NO existe. Intente de nuevo.");
				} catch (AlbumNoEncontradoException e1) {
					JOptionPane.showMessageDialog(null, "El album NO existe. Intente de nuevo.");
				}
			}
		});
		gestionaAlbum.add(gaSacarPubli);
		
		JMenuItem eliminaAlbum = new JMenuItem ("Eliminar álbum");
		eliminaAlbum.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String nombreAlbum = JOptionPane.showInputDialog("Ingrese el nombre del Album a eliminar");
		        if (nombreAlbum != null && !nombreAlbum.isEmpty()) {
		            try {
		                Album albumAEliminar = perfilInstagram.buscaAlbum(nombreAlbum);
		                perfilInstagram.eliminaAlbum(albumAEliminar);
		                JOptionPane.showMessageDialog(null, "El álbum fue eliminado con éxito");
		            } catch (AlbumNoEncontradoException e2) {
		                JOptionPane.showMessageDialog(null, "El álbum NO existe. Intente de nuevo.");
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Debe ingresar un nombre de álbum válido.");
		        }
		    }
		});
		
		albumes.add(crearAlbum);
		albumes.add(gestionaAlbum);
		albumes.add(eliminaAlbum);
		
		return albumes;
	}
	public JMenu menuTOPreportes() {
		JMenu reportes = new JMenu("Reportes");
		reportes.setFont(new Font("Open Sans", Font.PLAIN, 15));
		
		JMenuItem generaTXT = new JMenuItem ("Generar TXT");
		generaTXT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sistema.generarReporteEnArchivo(perfilInstagram.cantidadYpromedioDeMg());
				File reporte = new File("reporte.txt");
				if (reporte.exists())
					JOptionPane.showMessageDialog(null, "El archivo TXT fue generado con éxito");
				else 
					JOptionPane.showMessageDialog(null, "El archivo NO fue generado");		
			
			} 
		});
		JMenuItem ReportePublicaciones = new JMenuItem("Reporte de publicaciones");
		ReportePublicaciones.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        ReportesPublicaciones ventanaReportesPublicaciones = new ReportesPublicaciones();
		        ventanaReportesPublicaciones.setVisible(true);
		    }
		});
		JMenuItem ReporteDeAlbumes = new JMenuItem("Reporte de Albumes");
		ReporteDeAlbumes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		reportes.add(ReporteDeAlbumes);
		reportes.add(ReportePublicaciones);	
		reportes.add(generaTXT);
		return reportes;
	}
	public JMenu menuTOPestadisticas() {
	    JMenu estadisticas = new JMenu("Estadísticas");
	    estadisticas.setFont(new Font("Open Sans", Font.PLAIN, 15));

	    JMenuItem VerEstadisticas = new JMenuItem("Histograma de Publicaciones");
	    VerEstadisticas.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            // Crear una nueva ventana JFrame
	            JFrame ventanaEstadisticas = new JFrame("Estadística: Histograma Publicaciones");
	            ventanaEstadisticas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	            ventanaEstadisticas.setSize(400, 400);

	            // Obtener la lista de reportes de cantidad de "Me gusta" por tipo de publicación
	            Map<String, List<Publicacion>> listaPublicacionesPorTipo = perfilInstagram.agruparPublicacionesPorTipo();

	            // Crear el arreglo de datos para el histograma
	            int[] data = new int[listaPublicacionesPorTipo.size()];
	            String[] labels = new String[listaPublicacionesPorTipo.size()];
	            int index = 0;
	            for (Map.Entry<String, List<Publicacion>> entry : listaPublicacionesPorTipo.entrySet()) {
	                String tipoPublicacion = entry.getKey();
	                List<Publicacion> publicaciones = entry.getValue();
	                int cantidadPublicaciones = publicaciones.size();
	                data[index] = cantidadPublicaciones;
	                labels[index] = tipoPublicacion;
	                index++;
	            }

	            HistogramaPublicaciones estadisticasPanel = new HistogramaPublicaciones();
	            estadisticasPanel.setHistogramData(data, labels);
	            ventanaEstadisticas.getContentPane().add(estadisticasPanel, BorderLayout.CENTER);

	            ventanaEstadisticas.setVisible(true);
	        }
	    });

	    estadisticas.add(VerEstadisticas);
	    return estadisticas;
	}




	public JMenu menuTOPopciones() {
		JMenu opciones = new JMenu("Opciones");
		opciones.setFont(new Font("Open Sans", Font.PLAIN, 15));
		
		JMenuItem cargaDatos = new JMenuItem ("Cargar datos desde XML");
		cargaDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				perfilInstagram.cargarPublicaciones();				
				JOptionPane.showMessageDialog(null, "Los datos fueron agregados con éxito");
				publicacionesActuales();
			}
		});
		
		JMenuItem filtraPublicaciones = new JMenuItem("Filtrar publicaciones");
		filtraPublicaciones.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				FiltraPublicaciones ventanaFiltros = new FiltraPublicaciones();
				ventanaFiltros.setVisible(true);
			}
		});
		
		opciones.add(cargaDatos);
		opciones.add(filtraPublicaciones);
		return opciones;
	}
		
	public void publicacionesActuales() {
	    /**
	     * Setea el espacio donde aparecerán las Publicaciones del Perfil
	     */

	    JPanel jpPublicaciones = new JPanel();
	    jpPublicaciones.setBackground(Color.LIGHT_GRAY);
	    jpPublicaciones.setFont(new Font("Open Sans", Font.PLAIN, 20));
	    jpPublicaciones.setLayout(new GridLayout(0, 3, 10, 10)); // Configura el GridLayout con 3 columnas y espacios de 10 píxeles

	    try {
	        Set<Publicacion> listaPublicaciones = perfilInstagram.getPublicaciones();
	        for (Publicacion publicacion : listaPublicaciones) {
	            JPanel panel = new JPanel(); // Crea un JPanel para cada publicación
	            panel.setBackground(Color.WHITE);
	            panel.setLayout(new GridLayout(2, 1)); // Configura un GridLayout para el panel interno

	            String tipoPublicacion = publicacion.getTipoPublicacion();
	            JLabel imageLabel = new JLabel(); // JLabel que muestra el icono
	            if (tipoPublicacion.equals("Audio")) {
	                imageLabel.setIcon(AssetsUtils.obtenerIcono("audio"));
	                panel.setBackground(Color.GRAY);
	            } else if (tipoPublicacion.equals("Imagen")) {
	                imageLabel.setIcon(AssetsUtils.obtenerIcono("image"));
	                panel.setBackground(Color.GRAY);
	            } else {
	                imageLabel.setIcon(AssetsUtils.obtenerIcono("video"));
	                panel.setBackground(Color.GRAY);
	            }
	            imageLabel.setHorizontalAlignment(JLabel.CENTER);

	            JLabel nameLabel = new JLabel(publicacion.getNombrePublicacion()); // JLabel para mostrar el nombre de la publicación
	            nameLabel.setHorizontalAlignment(JLabel.CENTER);

	            panel.add(imageLabel);
	            panel.add(nameLabel);

	            jpPublicaciones.add(panel);
	        }
	        contentPane.add(jpPublicaciones, BorderLayout.CENTER);
	        contentPane.revalidate();
	        contentPane.repaint();
	    } catch (SinDatosException e) {
	        jpPublicaciones.setVisible(false);
	    }
	}
}
