package gui;

import javax.swing.*;
import java.awt.*;
import model.Cliente;
import model.Veiculo;
import dao.ArquivoDAO;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ClientesPanel extends JPanel {
    private JTextField nomeField;
    private JFormattedTextField telefoneField;
    private JFormattedTextField dataCompraField;
    private JComboBox<String> veiculoComboBox;
    private JTable tabelaClientes;
    private ArquivoDAO arquivoDAO;
    private DefaultTableModel tableModel;
    private Map<String, Integer> veiculosMap;
    private int nextId = 1;
    
    public ClientesPanel() {
        arquivoDAO = new ArquivoDAO();
        veiculosMap = new HashMap<>();
        setLayout(new BorderLayout());
        
        // Configurar formulário
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        formPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        formPanel.add(nomeField);
        
        formPanel.add(new JLabel("Telefone:"));
        try {
            telefoneField = new JFormattedTextField(new javax.swing.text.MaskFormatter("(##) #####-####"));
        } catch (java.text.ParseException e) {
            telefoneField = new JFormattedTextField();
        }
        formPanel.add(telefoneField);
        
        formPanel.add(new JLabel("Data da Compra:"));
        try {
            dataCompraField = new JFormattedTextField(new javax.swing.text.MaskFormatter("##/##/####"));
        } catch (java.text.ParseException e) {
            dataCompraField = new JFormattedTextField();
        }
        formPanel.add(dataCompraField);
        
        formPanel.add(new JLabel("Veículo:"));
        veiculoComboBox = new JComboBox<>();
        formPanel.add(veiculoComboBox);
        
        // Configurar tabela
        String[] colunas = {"ID", "Nome", "Telefone", "Data Compra", "Veículo"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaClientes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        
        // Configurar botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton incluirButton = new JButton("Incluir");
        JButton alterarButton = new JButton("Alterar");
        JButton excluirButton = new JButton("Excluir");
        JButton limparButton = new JButton("Limpar");
        
        buttonPanel.add(incluirButton);
        buttonPanel.add(alterarButton);
        buttonPanel.add(excluirButton);
        buttonPanel.add(limparButton);
        
        // Adicionar listeners
        incluirButton.addActionListener(e -> incluirCliente());
        alterarButton.addActionListener(e -> alterarCliente());
        excluirButton.addActionListener(e -> excluirCliente());
        limparButton.addActionListener(e -> limparCampos());
        
        // Adicionar componentes ao painel
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Carregar dados iniciais
        atualizarComboBoxVeiculos();
        atualizarTabela();
        updateNextId();
        
        tabelaClientes.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tabelaClientes.getSelectedRow() != -1) {
                preencherCamposComClienteSelecionado();
            }
        });
    }

    private void atualizarComboBoxVeiculos() {
        veiculoComboBox.removeAllItems();
        veiculosMap.clear();
        List<Veiculo> veiculos = arquivoDAO.lerVeiculos();
        for (Veiculo veiculo : veiculos) {
            String item = veiculo.getMarca() + " " + veiculo.getModelo();
            veiculoComboBox.addItem(item);
            veiculosMap.put(item, veiculo.getId());
        }
    }

    private void incluirCliente() {
        try {
            String nome = nomeField.getText();
            String telefone = telefoneField.getText();
            String dataCompra = dataCompraField.getText();
            String veiculoSelecionado = (String) veiculoComboBox.getSelectedItem();
            
            if (nome.isEmpty() || telefone.isEmpty() || dataCompra.isEmpty() || veiculoSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            int idVeiculo = veiculosMap.get(veiculoSelecionado);
            Cliente cliente = new Cliente(nextId++, nome, telefone, dataCompra, idVeiculo);
            arquivoDAO.salvarCliente(cliente);
            
            limparCampos();
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    private void alterarCliente() {
        int selectedRow = tabelaClientes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para alterar!");
            return;
        }

        try {
            int id = (int) tabelaClientes.getValueAt(selectedRow, 0);
            String nome = nomeField.getText();
            String telefone = telefoneField.getText();
            String dataCompra = dataCompraField.getText();
            String veiculoSelecionado = (String) veiculoComboBox.getSelectedItem();
            
            if (nome.isEmpty() || telefone.isEmpty() || dataCompra.isEmpty() || veiculoSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            int idVeiculo = veiculosMap.get(veiculoSelecionado);
            Cliente clienteAtualizado = new Cliente(id, nome, telefone, dataCompra, idVeiculo);
            
            arquivoDAO.alterarCliente(clienteAtualizado);
            atualizarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Cliente alterado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar cliente: " + e.getMessage());
        }
    }

    private void excluirCliente() {
        int selectedRow = tabelaClientes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja excluir este cliente?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tabelaClientes.getValueAt(selectedRow, 0);
            arquivoDAO.excluirCliente(id);
            atualizarTabela();
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        telefoneField.setText("");
        dataCompraField.setText("");
        veiculoComboBox.setSelectedIndex(-1);
        nomeField.requestFocus();
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Cliente> clientes = arquivoDAO.lerClientes();
        Map<Integer, Veiculo> veiculosById = new HashMap<>();
        arquivoDAO.lerVeiculos().forEach(v -> veiculosById.put(v.getId(), v));
        
        for (Cliente cliente : clientes) {
            Veiculo veiculo = veiculosById.get(cliente.getIdVeiculo());
            String veiculoNome = veiculo != null ? 
                veiculo.getMarca() + " " + veiculo.getModelo() : 
                "Veículo não encontrado";
                
            Object[] row = {
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getDataCompra(),
                veiculoNome
            };
            tableModel.addRow(row);
        }
    }

    private void updateNextId() {
        List<Cliente> clientes = arquivoDAO.lerClientes();
        if (!clientes.isEmpty()) {
            nextId = clientes.stream()
                    .mapToInt(Cliente::getId)
                    .max()
                    .getAsInt() + 1;
        }
    }

    private void preencherCamposComClienteSelecionado() {
        int selectedRow = tabelaClientes.getSelectedRow();
        if (selectedRow != -1) {
            String nome = (String) tabelaClientes.getValueAt(selectedRow, 1);
            String telefone = (String) tabelaClientes.getValueAt(selectedRow, 2);
            String dataCompra = (String) tabelaClientes.getValueAt(selectedRow, 3);
            String veiculoNome = (String) tabelaClientes.getValueAt(selectedRow, 4);

            nomeField.setText(nome);
            telefoneField.setText(telefone);
            dataCompraField.setText(dataCompra);
            
            // Selecionar o veículo correto no combobox
            for (int i = 0; i < veiculoComboBox.getItemCount(); i++) {
                if (veiculoComboBox.getItemAt(i).equals(veiculoNome)) {
                    veiculoComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
}