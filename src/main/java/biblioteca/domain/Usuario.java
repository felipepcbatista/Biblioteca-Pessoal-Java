package biblioteca.domain;

import java.util.regex.Pattern;

public class Usuario{

    private final long id;
    private final String nome;
    private String email;

    public Usuario (long id, String nome, String email){
        this.id = id;
        this.nome = nome;
        this.email = validarEmail (email);
    }

    private String validarEmail (String email){
        String regex = "[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
         if (Pattern.matches(regex, email)){
             return email;
         }
         throw new EmailInvalidoException ("E-mail invalido: " + email);

    }

    public void setEmail (String email) {
        this.email = validarEmail(email);
    }

    public long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
}