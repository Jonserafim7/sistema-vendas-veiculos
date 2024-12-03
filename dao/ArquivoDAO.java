package dao;

import model.Veiculo;
import model.Cliente;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoDAO {
    private static final String ARQUIVO_VEICULOS = "veiculos.txt";
    private static final String ARQUIVO_CLIENTES = "clientes.txt";

    public void salvarVeiculo(Veiculo veiculo) {
        try (FileWriter fw = new FileWriter(ARQUIVO_VEICULOS, true)) {
            fw.write(veiculo.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salvarCliente(Cliente cliente) {
        try (FileWriter fw = new FileWriter(ARQUIVO_CLIENTES, true)) {
            fw.write(cliente.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Veiculo> lerVeiculos() {
        List<Veiculo> veiculos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_VEICULOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                int id = Integer.parseInt(dados[0]);
                String marca = dados[1];
                String modelo = dados[2];
                double valor = Double.parseDouble(dados[3]);
                veiculos.add(new Veiculo(id, marca, modelo, valor));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return veiculos;
    }

    public List<Cliente> lerClientes() {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_CLIENTES))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                int id = Integer.parseInt(dados[0]);
                String nome = dados[1];
                String telefone = dados[2];
                String dataCompra = dados[3];
                int idVeiculo = Integer.parseInt(dados[4]);
                clientes.add(new Cliente(id, nome, telefone, dataCompra, idVeiculo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public void alterarVeiculo(Veiculo veiculoAtualizado) {
        List<Veiculo> veiculos = lerVeiculos();
        try (FileWriter fw = new FileWriter(ARQUIVO_VEICULOS)) {
            for (Veiculo veiculo : veiculos) {
                if (veiculo.getId() == veiculoAtualizado.getId()) {
                    fw.write(veiculoAtualizado.toString() + "\n");
                } else {
                    fw.write(veiculo.toString() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void excluirVeiculo(int id) {
        List<Veiculo> veiculos = lerVeiculos();
        try (FileWriter fw = new FileWriter(ARQUIVO_VEICULOS)) {
            for (Veiculo veiculo : veiculos) {
                if (veiculo.getId() != id) {
                    fw.write(veiculo.toString() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void alterarCliente(Cliente clienteAtualizado) {
        List<Cliente> clientes = lerClientes();
        try (FileWriter fw = new FileWriter(ARQUIVO_CLIENTES)) {
            for (Cliente cliente : clientes) {
                if (cliente.getId() == clienteAtualizado.getId()) {
                    fw.write(clienteAtualizado.toString() + "\n");
                } else {
                    fw.write(cliente.toString() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void excluirCliente(int id) {
        List<Cliente> clientes = lerClientes();
        try (FileWriter fw = new FileWriter(ARQUIVO_CLIENTES)) {
            for (Cliente cliente : clientes) {
                if (cliente.getId() != id) {
                    fw.write(cliente.toString() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 