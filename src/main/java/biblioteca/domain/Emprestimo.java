package biblioteca.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprestimo{

    private final long id;
    private final long livroId;
    private final long usuarioId;
    private final LocalDate dataEmprestimo;
    private final LocalDate dataPrevista;
    private LocalDate dataDevolucao;

    public Emprestimo (long id, long livroId, long usuarioId, LocalDate dataEmprestimo){
        this.id = id;
        this.livroId = livroId;
        this.usuarioId = usuarioId;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevista = dataEmprestimo.plusDays(7);
    }

    public boolean isAberto() { return dataDevolucao == null; }
    public boolean isDevolvido() { return dataDevolucao != null; }

    public void marcarDevolvido (LocalDate dataDevolucao){
        this.dataDevolucao = dataDevolucao;
    }

    public long diasDeAtraso (LocalDate referencia){
        if (!isAberto()) { return 0; }
        if (!referencia.isAfter(dataPrevista)) { return 0; }
        return ChronoUnit.DAYS.between(dataPrevista, referencia);
    }

    @Override
    public String toString() {
        return String.format (
                "Emprestimo: %d | Livro: %d | Usuario: %d | Emprestado: %s | Previsto: %s | Devolvido: %s",
                id, livroId, usuarioId, dataEmprestimo, dataPrevista,
                (dataDevolucao != null ? dataDevolucao : "EM ABERTO")
        );
    }

    public long getId() { return id; }
    public long getLivroId() { return livroId; }
    public long getUsuarioId() { return usuarioId; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataPrevista() { return dataPrevista; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
}