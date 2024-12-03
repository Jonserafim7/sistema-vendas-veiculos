package model;

public class Cliente {
    private String nome;
    private String telefone;
    private String dataCompra;
    private int idVeiculo;
    private int id;

    // Construtor
    public Cliente(int id, String nome, String telefone, String dataCompra, int idVeiculo) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.dataCompra = dataCompra;
        this.idVeiculo = idVeiculo;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    @Override
    public String toString() {
        return id + "," + nome + "," + telefone + "," + dataCompra + "," + idVeiculo;
    }
} 