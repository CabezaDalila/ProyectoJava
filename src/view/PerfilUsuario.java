package view;

import model.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import exception.*;
import java.util.*;

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
		setSize(420,250);
		setForeground(Color.DARK_GRAY);
		setBackground(Color.GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 607, 401);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		
		menuTop();
		publicacionesActuales();
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
		
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(menuPrincipal, BorderLayout.NORTH);
	}
	
	public JMenu menuTOPalbumes() {
		JMenu albumes = new JMenu("Álbumes");
		albumes.setFont(new Font("Open Sans", Font.PLAIN, 15));
		
		JMenuItem crearAlbum = new JMenuItem ("Crear álbum");
		
		crearAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreAlbum = JOptionPane.showInputDialog("Ingrese el nombre del nuevo Album");
				Album nuevoAlbum = new Album(nombreAlbum);
				PerfilInstagram.getInstance().addAlbum(nuevoAlbum);				
				JOptionPane.showMessageDialog(null, "El álbum fue agregado con éxito");
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
					PerfilInstagram.getInstance().buscaAlbum(nombreAlbum);
					PerfilInstagram.getInstance().buscaPubli(nombrePubli);
					PerfilInstagram.getInstance().addPubliDentroAlbum(nombreAlbum, nombrePubli);
					JOptionPane.showMessageDialog(null, "La publicación fue eliminada con éxito");
				} catch (AlbumNoEncontradoException e1) {
					JOptionPane.showMessageDialog(null, "El álbum NO existe. Intente de nuevo.");
				}
				catch (PublicacionNoEncontradaException e1) {
					JOptionPane.showMessageDialog(null, "La publicación NO existe. Intente de nuevo.");
				}
			}
		});
		gestionaAlbum.add(gaAgregaPubli);
		
		JMenuItem gaEliminaPubli = new JMenuItem("Eliminar Publicación de un Album");
		gaEliminaPubli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		gestionaAlbum.add(gaEliminaPubli);
		
		JMenuItem eliminaAlbum = new JMenuItem ("Eliminar álbum");
		eliminaAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreAlbum = JOptionPane.showInputDialog("Ingrese el nombre del Album a eliminar");
				try {
					Album albumAEliminar = perfilInstagram.buscaAlbum(nombreAlbum);
					perfilInstagram.eliminaAlbum(albumAEliminar);
					JOptionPane.showMessageDialog(null, "El álbum fue eliminado con éxito");
				} catch (AlbumNoEncontradoException e1) {
					JOptionPane.showMessageDialog(null, "El álbum NO existe. Intente de nuevo.");
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
		
		return reportes;
	}
	
	public JMenu menuTOPopciones() {
		JMenu opciones = new JMenu("Opciones");
		opciones.setFont(new Font("Open Sans", Font.PLAIN, 15));
		
		JMenuItem cargaDatos = new JMenuItem ("Cargar datos desde XML");
		cargaDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PerfilInstagram.getInstance().cargarPublicaciones();				
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
		
		//PerfilInstagram.getInstance().getPublicaciones();
		//int i=0;
		//for (Publicacion p : publicaciones) {
		//	i++;
		//	JLabel publi = new JLabel();
		//	publi.setText("SOY LA PUBLICACION " + i);
		//	jpPublicaciones.add(publi);
		//}
		//jpPublicaciones.setLayout(new GridLayout(1, 0, 0, 0));
		//contentPane.add(jpPublicaciones, BorderLayout.CENTER);
	}
}
