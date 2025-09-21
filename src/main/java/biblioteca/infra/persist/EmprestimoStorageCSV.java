package biblioteca.infra.persist;

import biblioteca.domain.Emprestimo;

import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class EmprestimoStorageCSV implements ArquivoStorage<Emprestimo> {

    @Override
    public void salvar (List<Emprestimo> emprestimos, String caminho) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))){
            for (Emprestimo e : emprestimos){
                writer.write (e.getId() + ";" + e.getLivroId() + ";" + e.getUsuarioId() + ";" +
                        e.getDataEmprestimo() + ";" + (e.getDataDevolucao() != null ? e.getDataDevolucao() : ""));
                writer.newLine();
            }
        }
    }

    @Override
    public List<Emprestimo> carregar (String caminho) throws IOException{
        List<Emprestimo> emprestimos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))){
            String linha;
            while ((linha = reader.readLine()) != null){
                String[] partes = linha.split(";");
                long id = Long.parseLong(partes[0]);
                long livroId = Long.parseLong(partes[1]);
                long usuarioId = Long.parseLong(partes[2]);
                LocalDate dataEmprestimo = LocalDate.parse(partes[3]);
                LocalDate dataDevolucao = partes[4].isBlank() ? null : LocalDate.parse(partes[4]);

                Emprestimo e = new Emprestimo (id, livroId, usuarioId, dataEmprestimo);
                if (dataDevolucao != null){
                    e.marcarDevolvido(dataDevolucao);
                }
                emprestimos.add(e);
            }
        }
        return emprestimos;
    }
}
