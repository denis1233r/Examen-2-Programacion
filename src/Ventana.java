import javax.swing.*;
import java.util.ArrayList;

public class Ventana extends JFrame {

    private CineClub cine = new CineClub();

    private JPanel panel1;
    private JButton btnRegistrar;
    private JButton btnLimpiar;
    private JButton btnBuscar;
    private JButton btnBuscarTexto;
    private JButton btnCargar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnMeGusta;
    private JButton btnRanking;
    private JButton btnMostrar;

    private JTextField txtCodigo;
    private JTextField txtTitulo;
    private JTextField txtDirector;
    private JTextField txtAnio;
    private JTextField txtBuscarCodigo;
    private JTextField txtBuscar;
    private JTextField txtCodigoEditar;
    private JTextField txtTituloEditar;
    private JTextField txtDirectorEditar;
    private JTextField txtDIrectorEditar;
    private JTextField txtAnioEditar;
    private JTextField txtCodigoLike;

    private JComboBox<String> cboGenero;
    private JComboBox<String> cboGeneroEditar;

    private JTextArea txtResultados;
    private JTextArea txtResultadosRanking;
    private JTabbedPane tabbedPane1;
    private JLabel lblCodigo;
    private JLabel lblTitulo;
    private JLabel lblDirector;
    private JLabel lblAnio;
    private JLabel lbLBuscarCodigo;
    private JLabel lblBuscarTexto;
    private JScrollPane scrollResultados;
    private JLabel lblCodigoEditar;
    private JLabel lblGeneroEditar;
    private JLabel lblDirectorEditar;
    private JLabel lblTituloEditar;
    private JScrollPane scrollRanking;
    private JLabel lblCodigoLike;
    private JLabel lblAnioEditar;
    private JLabel lblGenero;

    public Ventana() {
        setContentPane(panel1);
        setTitle("Cine Club");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cargarGeneros();
        pack();
        setLocationRelativeTo(null);

        // REGISTRAR
        btnRegistrar.addActionListener(e -> {
            try {
                Object generoSel = cboGenero.getSelectedItem();
                String genero = (generoSel != null) ? generoSel.toString() : "";

                String resultado = cine.registrar(
                        Integer.parseInt(txtCodigo.getText().trim()),
                        txtTitulo.getText().trim(),
                        txtDirector.getText().trim(),
                        genero,
                        Integer.parseInt(txtAnio.getText().trim())
                );

                JOptionPane.showMessageDialog(this, resultado);

                if ("OK".equals(resultado)) {
                    limpiarRegistro();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese datos numéricos válidos.");
            }
        });

        // LIMPIAR
        btnLimpiar.addActionListener(e -> limpiarRegistro());

        // BUSCAR
        btnBuscar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(txtBuscarCodigo.getText().trim());
                Pelicula p = cine.obtenerPorCodigo(codigo);

                if (p != null) {
                    txtResultados.setText(p.toString());
                } else {
                    txtResultados.setText("Película no encontrada.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Código inválido.");
            }
        });

        btnBuscarTexto.addActionListener(e ->
                mostrarLista(cine.buscarTexto(txtBuscar.getText().trim()), txtResultados)
        );

        btnCargar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(txtCodigoEditar.getText().trim());
                Pelicula p = cine.obtenerPorCodigo(codigo);

                if (p != null) {
                    txtTituloEditar.setText(p.getTitulo());
                    if (txtDirectorEditar != null) {
                        txtDirectorEditar.setText(p.getDirector());
                    }
                    if (txtDIrectorEditar != null) {
                        txtDIrectorEditar.setText(p.getDirector());
                    }
                    txtAnioEditar.setText(String.valueOf(p.getAnio()));
                    cboGeneroEditar.setSelectedItem(p.getGenero());
                } else {
                    JOptionPane.showMessageDialog(this, "Película no encontrada.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Código inválido.");
            }
        });

        btnEditar.addActionListener(e -> {
            try {
                Object generoSel = cboGeneroEditar.getSelectedItem();
                String genero = (generoSel != null) ? generoSel.toString() : "";

                String directorEditado = "";
                if (txtDirectorEditar != null && !txtDirectorEditar.getText().trim().isEmpty()) {
                    directorEditado = txtDirectorEditar.getText().trim();
                } else if (txtDIrectorEditar != null) {
                    directorEditado = txtDIrectorEditar.getText().trim();
                }

                String resultado = cine.editar(
                        Integer.parseInt(txtCodigoEditar.getText().trim()),
                        txtTituloEditar.getText().trim(),
                        directorEditado,
                        genero,
                        Integer.parseInt(txtAnioEditar.getText().trim())
                );

                JOptionPane.showMessageDialog(this, resultado);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Datos incorrectos.");
            }
        });

        btnEliminar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(txtCodigoEditar.getText().trim());

                int confirmar = JOptionPane.showConfirmDialog(
                        this,
                        "¿Desea eliminar esta película?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmar == JOptionPane.YES_OPTION) {
                    if (cine.eliminar(codigo)) {
                        JOptionPane.showMessageDialog(this, "Película eliminada.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Película no encontrada.");
                    }
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Código inválido.");
            }
        });

        btnMeGusta.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(txtCodigoLike.getText().trim());

                if (cine.darMeGusta(codigo)) {
                    JOptionPane.showMessageDialog(this, "Me gusta registrado.");
                } else {
                    JOptionPane.showMessageDialog(this, "Película no encontrada.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Código inválido.");
            }
        });

        btnRanking.addActionListener(e ->
                mostrarLista(cine.obtenerRanking(), txtResultadosRanking)
        );

        btnMostrar.addActionListener(e ->
                mostrarLista(cine.getPeliculas(), txtResultadosRanking)
        );
    }

    private void cargarGeneros() {
        String[] generos = {"Acción", "Comedia", "Drama", "Terror", "Ciencia Ficción", "Romance", "Animación"};
        if (cboGenero != null) {
            cboGenero.setModel(new DefaultComboBoxModel<>(generos));
        }
        if (cboGeneroEditar != null) {
            cboGeneroEditar.setModel(new DefaultComboBoxModel<>(generos));
        }
    }

    private void limpiarRegistro() {
        txtCodigo.setText("");
        txtTitulo.setText("");
        txtDirector.setText("");
        txtAnio.setText("");
        if (cboGenero != null && cboGenero.getItemCount() > 0) {
            cboGenero.setSelectedIndex(0);
        }
    }

    private void mostrarLista(ArrayList<Pelicula> lista, JTextArea area) {
        area.setText("");

        if (lista == null || lista.isEmpty()) {
            area.setText("No se encontraron películas.");
            return;
        }

        for (Pelicula p : lista) {
            area.append(p.toString() + "\n\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ventana().setVisible(true));
    }
}