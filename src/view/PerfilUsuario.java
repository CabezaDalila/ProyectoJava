package view;

import model.*;
import sistema.*;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.border.EmptyBorder;
import exception.*;
import java.util.*;


public class PerfilUsuario extends JFrame implements Serializable {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static PerfilInstagram perfilInstagram;
	private Sistema sistema;

	public static void main(String[] args) {
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            Sistema sistema = new Sistema();
	            perfilInstagram = PerfilInstagram.getInstance();
	            sistema.recupera();  // Deserializo aca
	            PerfilUsuario frame = new PerfilUsuario(sistema);
	            frame.setVisible(true);
	        }
	    });
	}


	public PerfilUsuario(Sistema sistema) {
	
		this.sistema = sistema;
		   
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
		this.addWindowListener((WindowListener) new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        sistema.serializa();//serializo aca
		        System.exit(0);
		    }
		});

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
					//System.out.println(nombreAlbum);
					//System.out.println(perfilInstagram.getListaAlbumes().size());
					JOptionPane.showMessageDialog(null, "El álbum fue agregado con éxito");
					sistema.serializa();  // serializa después de agregar un nuevo álbum
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
					e1.printStackTrace();
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
	public JMenu menuTOPestadisticas(){
		JMenu estadisticas = new JMenu("Estadísticas");
		estadisticas.setFont(new Font("Open Sans", Font.PLAIN, 15));	
		
		JMenuItem mntmVerEstadisticas = new JMenuItem("Ver estadisticas");
		estadisticas.add(mntmVerEstadisticas);
		estadisticas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				contentPane = new JPanel();
				contentPane.setBackground(Color.blue);
				
				contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
				setContentPane(contentPane);
			}
		});
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
		 * Setea el espacio donde apareceran las Publicaciones del Perfil
		 */
		
		JPanel jpPublicaciones = new JPanel();
		jpPublicaciones.setBackground(Color.LIGHT_GRAY);
		jpPublicaciones.setFont(new Font("Open Sans", Font.PLAIN, 20));
		
		try {
			Set<String> nombresP = perfilInstagram.getNombresPublicaciones();
			for (String nombre : nombresP) {
				JLabel publi = new JLabel();
				publi.setText(nombre);
				jpPublicaciones.add(publi);
				jpPublicaciones.setLayout(new GridLayout(1, 0, 0, 0));
				contentPane.add(jpPublicaciones, BorderLayout.CENTER);
	        }
		} catch (SinDatosException e) {
			jpPublicaciones.setVisible(false);
		}	
	}

}
