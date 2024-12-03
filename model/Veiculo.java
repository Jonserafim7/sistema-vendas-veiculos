package model;

public class Veiculo {
    private String marca;
    private String modelo;
    private double valor;
    private int id;

    // Construtor
    public Veiculo(int id, String marca, String modelo, double valor) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.valor = valor;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return id + "," + marca + "," + modelo + "," + valor;
    }
} 