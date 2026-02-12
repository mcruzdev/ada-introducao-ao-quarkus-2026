package tech.ada;

public class Pessoa {

    private String senha;
    private String nome;
    private Integer idade;

    public Pessoa() {}

    public Pessoa(String nome, Integer idade) {
        this.nome = nome;
        this.idade = idade;
        this.senha = "123456";
    }

    public Integer getIdade() {
        return idade;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }
}
