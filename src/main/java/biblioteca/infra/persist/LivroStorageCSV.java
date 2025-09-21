package biblioteca.infra.persist;

import biblioteca.domain.Livro;

import java.util.*;
import java.io.*;

public class LivroStorageCSV implements ArquivoStorage<Livro> {

    @Override
    public void salvar (List<Livro> livros, String caminho) throws IOException{
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))){
            for (Livro l : livros){
                writer.write(l.getId() + ";" + l.getTitulo() + ";" + l.getAutor() + ";" +
                        l.getAno() + ";" + l.getStatus());
                writer.newLine();
            }
        }
    }

    @Override
    public List<Livro> carregar (String caminho) throws IOException{
        List<Livro> livros = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))){
            String linha;
            while ((linha = reader.readLine()) != null){
                String[] partes = linha.split(";");
                long id = Long.parseLong(partes[0]);
                String titulo = partes[1];
                String autor = partes[2];
                int ano = Integer.parseInt(partes[3]);
                Livro.Status status = Livro.Status.valueOf(partes[4]);

                Livro l = new Livro (id, titulo, autor, ano);
                if (status == Livro.Status.EMPRESTADO){
                    l.marcarEmprestado();
                }
                livros.add(l);
            }
        }
        return livros;
    }
}