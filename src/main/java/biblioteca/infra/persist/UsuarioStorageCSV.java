package biblioteca.infra.persist;

import biblioteca.domain.Usuario;

import java.util.*;
import java.io.*;

public class UsuarioStorageCSV implements ArquivoStorage<Usuario> {

    @Override
    public void salvar (List<Usuario> usuarios, String caminho) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))){
            for (Usuario u : usuarios){
                writer.write(u.getId() + ";" + u.getNome() + ";" + u.getEmail());
                writer.newLine();
            }
        }
    }

    @Override
    public List<Usuario> carregar (String caminho) throws IOException {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))){
            String linha;
            while ((linha = reader.readLine()) != null){
                String[] partes = linha.split(";");
                long id = Long.parseLong(partes[0]);
                String nome = partes[1];
                String email = partes[2];

                Usuario u = new Usuario (id, nome, email);
                usuarios.add(u);
            }
        }
        return usuarios;
    }
}