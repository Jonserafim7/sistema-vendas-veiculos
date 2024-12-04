package gui;

import javax.swing.*;
import java.awt.*;
import model.Veiculo;
import dao.ArquivoDAO;
import javax.swing.table.DefaultTableModel;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class VeiculosPanel extends JPanel {
    private JTextField marcaField;
    private JTextField modeloField;
    private JFormattedTextField valorField;
    private JTable tabelaVeiculos;
    private ArquivoDAO arquivoDAO;
    private DefaultTableModel tableModel;
    private int nextId = 1;
    
    public VeiculosPanel() {
        arquivoDAO = new ArquivoDAO();
        setLayout(new BorderLayout());
        
        // Configurar formulário
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        formPanel.add(new JLabel("Marca:"));
        marcaField = new JTextField();
        formPanel.add(marcaField);
        
        formPanel.add(new JLabel("Modelo:"));
        modeloField = new JTextField();
        formPanel.add(modeloField);
        
        formPanel.add(new JLabel("Valor:"));
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));
        valorField = new JFormattedTextField(format);
        valorField.setValue(0.0);
        formPanel.add(valorField);
        
        // Configurar tabela
        String[] colunas = {"ID", "Marca", "Modelo", "Valor"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaVeiculos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaVeiculos);
        
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
        incluirButton.addActionListener(e -> incluirVeiculo());
        alterarButton.addActionListener(e -> alterarVeiculo());
        excluirButton.addActionListener(e -> excluirVeiculo());
        limparButton.addActionListener(e -> limparCampos());
        
        tabelaVeiculos.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tabelaVeiculos.getSelectedRow() != -1) {
                preencherCamposComVeiculoSelecionado();
            }
        });
        
        // Adicionar componentes ao painel
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Carregar dados existentes
        atualizarTabela();
        updateNextId();
    }

    private void updateNextId() {
        List<Veiculo> veiculos = arquivoDAO.lerVeiculos();
        if (!veiculos.isEmpty()) {
            nextId = veiculos.stream()
                    .mapToInt(Veiculo::getId)
                    .max()
                    .getAsInt() + 1;
        }
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Veiculo> veiculos = arquivoDAO.lerVeiculos();
        for (Veiculo veiculo : veiculos) {
            Object[] row = {
                veiculo.getId(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(veiculo.getValor())
            };
            tableModel.addRow(row);
        }
    }

    private void incluirVeiculo() {
        try {
            String marca = marcaField.getText();
            String modelo = modeloField.getText();
            
            Number number = (Number) valorField.getValue();
            double valor = number.doubleValue();

            if (marca.isEmpty() || modelo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            Veiculo veiculo = new Veiculo(nextId++, marca, modelo, valor);
            arquivoDAO.salvarVeiculo(veiculo);
            limparCampos();
            atualizarTabela();
            
            JOptionPane.showMessageDialog(this, "Veículo cadastrado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Valor inválido! Digite um número válido.\nExemplo: 10,00");
        }
    }

    private void alterarVeiculo() {
        int selectedRow = tabelaVeiculos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para alterar!");
            return;
        }

        try {
            int id = (int) tabelaVeiculos.getValueAt(selectedRow, 0);
            String marca = marcaField.getText();
            String modelo = modeloField.getText();
            Number number = (Number) valorField.getValue();
            double valor = number.doubleValue();

            if (marca.isEmpty() || modelo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            Veiculo veiculoAtualizado = new Veiculo(id, marca, modelo, valor);
            arquivoDAO.alterarVeiculo(veiculoAtualizado);
            atualizarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Veículo alterado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar veículo: " + e.getMessage());
        }
    }

    private void excluirVeiculo() {
        int selectedRow = tabelaVeiculos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para excluir!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja excluir este veículo?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tabelaVeiculos.getValueAt(selectedRow, 0);
            arquivoDAO.excluirVeiculo(id);
            atualizarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Veículo excluído com sucesso!");
        }
    }

    private void limparCampos() {
        marcaField.setText("");
        modeloField.setText("");
        valorField.setValue(0.0);
        marcaField.requestFocus();
    }

    private void preencherCamposComVeiculoSelecionado() {
        int selectedRow = tabelaVeiculos.getSelectedRow();
        if (selectedRow != -1) {
            try {
                // Pegar os valores da linha selecionada
                marcaField.setText((String) tabelaVeiculos.getValueAt(selectedRow, 1));
                modeloField.setText((String) tabelaVeiculos.getValueAt(selectedRow, 2));
                
                // Buscar o veículo original para pegar o valor correto
                int id = (int) tabelaVeiculos.getValueAt(selectedRow, 0);
                List<Veiculo> veiculos = arquivoDAO.lerVeiculos();
                for (Veiculo v : veiculos) {
                    if (v.getId() == id) {
                        valorField.setValue(v.getValor());
                        break;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao preencher campos: " + e.getMessage());
            }
        }
    }
}