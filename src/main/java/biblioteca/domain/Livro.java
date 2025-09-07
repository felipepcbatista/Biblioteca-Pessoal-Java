package biblioteca.domain;

public class Livro{

    private final long id;
    private final String titulo;
    private final String autor;
    private final int ano;
    private Status status;

    public enum Status{
        DISPONIVEL,
        EMPRESTADO
    }

    public Livro (long id, String titulo, String autor, int ano){
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.status = Status.DISPONIVEL;
    }

    public void marcarEmprestado(){
        this.status = Status.EMPRESTADO;
    }

    public void marcarDisponivel() {
        this.status = Status.DISPONIVEL;
    }

    @Override
    public String toString(){
        return String.format (
                "ID: %d | Titulo: %s | Autor: %s | Ano: %d | Status: %s",
                id, titulo, autor, ano, status
        );

    }

    public long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAno() { return ano; }
    public Status getStatus() { return status; }
}