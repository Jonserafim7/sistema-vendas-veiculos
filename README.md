# Sistema de Vendas de Veículos

Um sistema de gerenciamento de vendas de veículos desenvolvido em Java com interface gráfica Swing.

## Funcionalidades

- Cadastro de veículos (marca, modelo, valor)
- Cadastro de clientes (nome, telefone, data da compra, veículo)
- Persistência de dados em arquivos texto
- Interface gráfica intuitiva
- Operações CRUD completas (Create, Read, Update, Delete)

## Estrutura do Projeto

sistema-vendas-veiculos/
├── gui/
│ ├── MainWindow.java
│ ├── VeiculosPanel.java
│ └── ClientesPanel.java
│ ├── model/
│ ├── Veiculo.java
│ └── Cliente.java
│ └── dao/
│ └── ArquivoDAO.java
├── veiculos.txt
├── clientes.txt
└── README.md

## Requisitos

- Java JDK 19 ou superior
- Sistema operacional compatível com Java (Windows, Linux, macOS)

## Como Executar

### Usando linha de comando

1. Clone o repositório:

   ```bash
   git clone https://github.com/Jonserafim7/sistema-vendas-veiculos.git
   cd sistema-vendas-veiculos
   ```

2. Compile o projeto:

   ```bash
   javac gui/*.java model/*.java dao/*.java
   ```

3. Execute o programa:
   ```bash
   java gui.MainWindow
   ```

### Usando uma IDE

1. Importe o projeto para sua IDE preferida (Eclipse, IntelliJ IDEA, NetBeans)
2. Localize a classe `gui.MainWindow`
3. Execute o método `main`

## Uso

1. **Cadastro de Veículos**:

   - Acesse a aba "Veículos"
   - Preencha marca, modelo e valor
   - Clique em "Incluir" para salvar

2. **Cadastro de Clientes**:
   - Acesse a aba "Clientes"
   - Preencha nome, telefone, data da compra
   - Selecione um veículo cadastrado
   - Clique em "Incluir" para salvar

## Armazenamento de Dados

Os dados são armazenados em arquivos texto:

- `veiculos.txt`: Registro de veículos
- `clientes.txt`: Registro de clientes

## Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
