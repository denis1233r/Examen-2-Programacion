import java.util.ArrayList;
import java.util.Comparator;

public class CineClub {

    private ArrayList<Pelicula> peliculas;

    public CineClub() {
        peliculas = new ArrayList<>();
        cargarPeliculas();
    }

    private void cargarPeliculas() {

        peliculas.add(new Pelicula(1020, "Interestelar", "Christopher Nolan", "Drama", 2014));
        peliculas.add(new Pelicula(1019, "Coco", "Lee Unkrich", "Animación", 2017));
        peliculas.add(new Pelicula(1018, "Gladiador", "Ridley Scott", "Aventura", 2000));
        peliculas.add(new Pelicula(1017, "Up", "Pete Docter", "Animación", 2009));
        peliculas.add(new Pelicula(1016, "Titanic", "James Cameron", "Drama", 1997));
        peliculas.add(new Pelicula(1015, "Shrek", "Andrew Adamson", "Comedia", 2001));
        peliculas.add(new Pelicula(1014, "Avatar", "James Cameron", "Aventura", 2009));
        peliculas.add(new Pelicula(1013, "Joker", "Todd Phillips", "Drama", 2019));
        peliculas.add(new Pelicula(1012, "Toy Story", "John Lasseter", "Animación", 1995));
        peliculas.add(new Pelicula(1011, "Náufrago", "Robert Zemeckis", "Drama", 2000));
        peliculas.add(new Pelicula(1010, "Madagascar", "Eric Darnell", "Comedia", 2005));
        peliculas.add(new Pelicula(1009, "El viaje de Chihiro", "Hayao Miyazaki", "Animación", 2001));
        peliculas.add(new Pelicula(1008, "Jumanji", "Joe Johnston", "Aventura", 1995));
        peliculas.add(new Pelicula(1007, "Roma", "Alfonso Cuarón", "Drama", 2018));
        peliculas.add(new Pelicula(1006, "Buscando a Nemo", "Andrew Stanton", "Animación", 2003));
        peliculas.add(new Pelicula(1005, "Los Increíbles", "Brad Bird", "Aventura", 2004));
        peliculas.add(new Pelicula(1004, "El Rey León", "Roger Allers", "Animación", 1994));
        peliculas.add(new Pelicula(1003, "La vida es bella", "Roberto Benigni", "Drama", 1997));
        peliculas.add(new Pelicula(1002, "Mi pobre angelito", "Chris Columbus", "Comedia", 1990));
        peliculas.add(new Pelicula(1001, "March of the Penguins", "Luc Jacquet", "Documental", 2005));

        ordenarPorNumero();
    }

    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public int cantidadPeliculas() {
        return peliculas.size();
    }

    public void ordenarPorNumero() {
        peliculas.sort((a, b) ->
                Integer.compare(b.getNumeroID(), a.getNumeroID()));
    }

    private boolean generoValido(String genero) {
        return genero != null &&
                (genero.equals("Drama")
                        || genero.equals("Aventura")
                        || genero.equals("Comedia")
                        || genero.equals("Animación")
                        || genero.equals("Documental"));
    }

    public String registrar(int codigo, String titulo,
                            String director, String genero, int anio) {

        if (codigo <= 0) return "El código debe ser positivo.";

        if (buscarBinaria(codigo) != -1)
            return "El código ya existe.";

        if (titulo == null || titulo.trim().isEmpty())
            return "El título es obligatorio.";

        if (director == null || director.trim().isEmpty())
            return "El director es obligatorio.";

        if (!generoValido(genero))
            return "Seleccione un género válido.";

        int actual = java.time.Year.now().getValue();

        if (anio < 1930 || anio > actual)
            return "El año no es válido.";

        peliculas.add(new Pelicula(
                codigo, titulo.trim(), director.trim(), genero, anio));

        ordenarPorNumero();

        return "OK";
    }

    public int buscarBinaria(int codigo) {

        if (codigo <= 0) return -1;

        int inicio = 0;
        int fin = peliculas.size() - 1;

        while (inicio <= fin) {

            int medio = (inicio + fin) / 2;

            int actual = peliculas.get(medio).getNumeroID();

            if (actual == codigo) return medio;

            if (actual < codigo) {
                fin = medio - 1;
            } else {
                inicio = medio + 1;
            }
        }

        return -1;
    }

    public Pelicula obtenerPorCodigo(int codigo) {

        int indice = buscarBinaria(codigo);

        if (indice == -1) return null;

        return peliculas.get(indice);
    }

    public ArrayList<Pelicula> buscarTexto(String texto) {

        ArrayList<Pelicula> resultados = new ArrayList<>();

        if (texto == null || texto.trim().isEmpty())
            return resultados;

        for (Pelicula p : peliculas) {

            if (p.getTitulo().contains(texto)
                    || p.getDirector().contains(texto)) {

                resultados.add(p);
            }
        }

        return resultados;
    }

    public String editar(int codigo, String titulo,
                         String director, String genero, int anio) {

        int indice = buscarBinaria(codigo);

        if (indice == -1) return "Película no encontrada.";

        if (titulo == null || titulo.trim().isEmpty())
            return "El título es obligatorio.";

        if (director == null || director.trim().isEmpty())
            return "El director es obligatorio.";

        if (!generoValido(genero))
            return "Género incorrecto.";

        int actual = java.time.Year.now().getValue();

        if (anio < 1930 || anio > actual)
            return "Año incorrecto.";

        Pelicula p = peliculas.get(indice);

        p.setTitulo(titulo.trim());
        p.setDirector(director.trim());
        p.setGenero(genero);
        p.setAnio(anio);

        return "OK";
    }

    public boolean eliminar(int codigo) {

        int indice = buscarBinaria(codigo);

        if (indice == -1) return false;

        peliculas.remove(indice);

        return true;
    }

    public boolean darMeGusta(int codigo) {

        Pelicula p = obtenerPorCodigo(codigo);

        if (p == null) return false;

        p.aumentarMeGusta();

        return true;
    }

    public ArrayList<Pelicula> obtenerRanking() {

        ArrayList<Pelicula> ranking =
                new ArrayList<>(peliculas);

        ranking.sort(
                Comparator.comparingInt(
                        Pelicula::getMeGusta
                ).reversed());

        return ranking;
    }
}
