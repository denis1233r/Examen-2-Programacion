public class Pelicula {

    private int numeroID;
    private String titulo;
    private String director;
    private String genero;
    private int anio;
    private int meGusta;

    public Pelicula(int numeroID, String titulo,
                    String director, String genero, int anio) {

        this.numeroID = numeroID;
        this.titulo = titulo;
        this.director = director;
        this.genero = genero;
        this.anio = anio;
        this.meGusta = 0;
    }

    public int getNumeroID() {
        return numeroID;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDirector() {
        return director;
    }

    public String getGenero() {
        return genero;
    }

    public int getAnio() {
        return anio;
    }

    public int getMeGusta() {
        return meGusta;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void aumentarMeGusta() {
        meGusta++;
    }

    @Override
    public String toString() {
        return numeroID + " | " + titulo
                + " | Director: " + director
                + " | Género: " + genero
                + " | Año: " + anio
                + " | Me gusta: " + meGusta;
    }
}
