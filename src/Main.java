import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;

/**
 * Classe Main - Sistema Interativo de Agendamento Pet Shop
 * 
 * Sistema completo com menu interativo que permite ao usuário:
 * - Gerenciar clientes, pets, funcionários, serviços e espaços
 * - Realizar agendamentos com validações em tempo real
 * - Gerar relatórios avançados personalizados
 * - Consultar informações de forma dinâmica
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        carregarDadosIniciais();
        menuPrincipal();
        scanner.close();
    }
    
    /**
     * Carrega dados iniciais para demonstração do sistema
     */
    private static void carregarDadosIniciais() {
        System.out.println("🐾 SISTEMA DE AGENDAMENTO PET SHOP 🐾");
        System.out.println("=====================================");
        System.out.println("🔄 Carregando sistema...");
        
        new Servico("Banho e Tosa Completo", "Banho, tosa higiênica e secagem", 60.0f, CategoriaServico.BANHO_E_TOSA).cadastrarServico();
        new Servico("Consulta Veterinária", "Avaliação de rotina com veterinário", 150.0f, CategoriaServico.CONSULTA_VETERINARIA).cadastrarServico();
        new Servico("Diária no Hotelzinho", "Hospedagem com alimentação e recreação", 100.0f, CategoriaServico.HOTELARIA_E_CRECHE).cadastrarServico();
        
        new Usuario("Dr. Roberto Silva", "123.456.789-00", "3199999-8888", PerfilUsuario.VETERINARIO).cadastrarUsuario();
        new Usuario("Maria Santos", "987.654.321-11", "3188888-7777", PerfilUsuario.TOSADOR).cadastrarUsuario();
        new Usuario("João Oliveira", "456.789.123-22", "3177777-6666", PerfilUsuario.ATENDENTE).cadastrarUsuario();
        
        new EspacoServico("Sala de Banho 1", "Sala equipada para banho e tosa", TipoEspaco.SALA_BANHO).cadastrarEspaco();
        new EspacoServico("Consultório Veterinário", "Consultório para exames e consultas", TipoEspaco.CONSULTORIO_VETERINARIO).cadastrarEspaco();
        new EspacoServico("Quarto Premium", "Quarto de hospedagem com ar condicionado", TipoEspaco.QUARTO_HOTELARIA).cadastrarEspaco();
        
        System.out.println("✅ Sistema carregado com sucesso!");
    }
    
    /**
     * Menu principal do sistema
     */
    private static void menuPrincipal() {
        int opcao;
        do {
            System.out.println("\n╔══════════════════════════════════════════════════════════╗");
            System.out.println("║                    MENU PRINCIPAL                       ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");
            System.out.println("1️⃣  - Gerenciar Clientes e Pets");
            System.out.println("2️⃣  - Gerenciar Funcionários");
            System.out.println("3️⃣  - Gerenciar Serviços");
            System.out.println("4️⃣  - Gerenciar Espaços");
            System.out.println("5️⃣  - 📅 AGENDAMENTOS ");
            System.out.println("6️⃣  - 📊 RELATÓRIOS ");
            System.out.println("7️⃣  - 🔎 Consultar Informações");
            System.out.println("0️⃣  - Sair do Sistema");
            System.out.print("\n🔹 Escolha uma opção: ");
            
            opcao = lerInteiro();
            
            switch (opcao) {
                case 1 -> menuClientesPets();
                case 2 -> menuFuncionarios();
                case 3 -> menuServicos();
                case 4 -> menuEspacos();
                case 5 -> menuAgendamentos();
                case 6 -> menuRelatorios();
                case 7 -> menuConsultas();
                case 0 -> {
                    System.out.println("\n👋 Obrigado por usar o sistema! Até logo!");
                    return;
                }
                default -> System.out.println("❌ Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
    }
    
    /**
     * Menu de agendamentos
     */
    private static void menuAgendamentos() {
        int opcao;
        do {
            System.out.println("\n╔══════════════════════════════════════════════════════════╗");
            System.out.println("║                 📅 MENU AGENDAMENTOS                    ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");
            System.out.println("1️⃣  - ➕ Criar Novo Agendamento");
            System.out.println("2️⃣  - ❌ Cancelar Agendamento");
            System.out.println("3️⃣  - ✅ Marcar Serviço como Realizado");
            System.out.println("4️⃣  - 📋 Listar Todos os Agendamentos");
            System.out.println("5️⃣  - 🔎 Buscar Agendamento por ID");
            System.out.println("6️⃣  - 📅 Próximos Agendamentos (24h)");
            System.out.println("0️⃣  - ← Voltar ao Menu Principal");
            System.out.print("\n🔹 Escolha uma opção: ");
            
            opcao = lerInteiro();
            
            switch (opcao) {
                case 1 -> criarNovoAgendamento();
                case 2 -> cancelarAgendamento();
                case 3 -> marcarServicoRealizado();
                case 4 -> Agendamento.listarTodosAgendamentos();
                case 5 -> buscarAgendamentoPorId();
                case 6 -> GerenciadorRelatorios.listarProximosAgendamentos();
                case 0 -> System.out.println("↩️  Voltando ao menu principal...");
                default -> System.out.println("❌ Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
    }
    
    /**
     * Menu de relatórios
     */
    private static void menuRelatorios() {
        int opcao;
        do {
            System.out.println("\n╔══════════════════════════════════════════════════════════╗");
            System.out.println("║                  📊 MENU RELATÓRIOS                     ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");
            System.out.println("1️⃣  - 📅 Agenda Diária");
            System.out.println("2️⃣  - 👨‍💼 Relatório por Funcionário");
            System.out.println("3️⃣  - 🏢 Relatório por Espaço");
            System.out.println("4️⃣  - 📈 Relatório Geral do Sistema");
            System.out.println("0️⃣  - ← Voltar ao Menu Principal");
            System.out.print("\n🔹 Escolha uma opção: ");
            
            opcao = lerInteiro();
            
            switch (opcao) {
                case 1 -> gerarAgendaDiaria();
                case 2 -> gerarRelatorioPorFuncionario();
                case 3 -> gerarRelatorioPorEspaco();
                case 4 -> GerenciadorRelatorios.gerarRelatorioGeral();
                case 0 -> System.out.println("↩️  Voltando ao menu principal...");
                default -> System.out.println("❌ Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
    }
    
    /**
     * Cria um novo agendamento através de entrada interativa do usuário
     */
    private static void criarNovoAgendamento() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                ➕ NOVO AGENDAMENTO                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
        try {
            Cliente.listarClientes();
            System.out.print("\n🔹 Digite o CPF do cliente: ");
            String cpfCliente = scanner.nextLine();
            Cliente clienteSelecionado = buscarClientePorCpf(cpfCliente);
            if (clienteSelecionado == null) {
                System.out.println("❌ Cliente não encontrado!");
                return;
            }
            
            clienteSelecionado.listarPetsDoCliente();
            System.out.print("\n🔹 Digite o nome do pet: ");
            String nomePet = scanner.nextLine();
            Pet petSelecionado = buscarPetDoCliente(clienteSelecionado, nomePet);
            if (petSelecionado == null) {
                System.out.println("❌ Pet não encontrado!");
                return;
            }
            
            Servico.listarServicos();
            System.out.print("\n🔹 Digite o nome do serviço: ");
            String nomeServico = scanner.nextLine();
            Servico servicoSelecionado = buscarServicoPorNome(nomeServico);
            if (servicoSelecionado == null) {
                System.out.println("❌ Serviço não encontrado!");
                return;
            }
            
            Usuario.listarUsuarios();
            System.out.print("\n🔹 Digite o CPF do funcionário: ");
            String cpfFuncionario = scanner.nextLine();
            Usuario funcionarioSelecionado = Usuario.buscarPorCpf(cpfFuncionario);
            if (funcionarioSelecionado == null) {
                System.out.println("❌ Funcionário não encontrado!");
                return;
            }
            
            EspacoServico.listarEspacos();
            System.out.print("\n🔹 Digite o nome do espaço: ");
            String nomeEspaco = scanner.nextLine();
            EspacoServico espacoSelecionado = EspacoServico.buscarPorNome(nomeEspaco);
            if (espacoSelecionado == null) {
                System.out.println("❌ Espaço não encontrado!");
                return;
            }
            
            System.out.print("\n🔹 Data do agendamento (dd/MM/yyyy): ");
            String dataStr = scanner.nextLine();
            System.out.print("🔹 Hora do agendamento (HH:mm): ");
            String horaStr = scanner.nextLine();
            
            LocalDateTime dataHora = LocalDateTime.parse(dataStr + " " + horaStr, 
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            
            System.out.print("🔹 Observações (opcional): ");
            String observacoes = scanner.nextLine();
            
            Agendamento novoAgendamento = new Agendamento(clienteSelecionado, petSelecionado, 
                                        servicoSelecionado, funcionarioSelecionado, 
                                        espacoSelecionado, dataHora, observacoes);
            
            if (novoAgendamento.agendarServico()) {
                System.out.println("🎉 Agendamento criado com sucesso!");
            }
            
        } catch (DateTimeParseException e) {
            System.out.println("❌ Formato de data/hora inválido! Use dd/MM/yyyy e HH:mm");
        } catch (Exception e) {
            System.out.println("❌ Erro ao criar agendamento: " + e.getMessage());
        }
    }
    
    /**
     * Cancela um agendamento
     */
    private static void cancelarAgendamento() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║               ❌ CANCELAR AGENDAMENTO                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
        Agendamento.listarTodosAgendamentos();
        
        System.out.print("\n🔹 Digite o ID do agendamento para cancelar: ");
        int id = lerInteiro();
        
        Agendamento agendamento = Agendamento.buscarPorId(id);
        if (agendamento == null) {
            System.out.println("❌ Agendamento não encontrado!");
            return;
        }
        
        System.out.print("🔹 Motivo do cancelamento: ");
        String motivo = scanner.nextLine();
        
        agendamento.cancelarAgendamento(motivo);
    }
    
    /**
     * Marca um serviço como realizado
     */
    private static void marcarServicoRealizado() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║            ✅ MARCAR SERVIÇO REALIZADO                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
        Agendamento.listarTodosAgendamentos();
        
        System.out.print("\n🔹 Digite o ID do agendamento realizado: ");
        int id = lerInteiro();
        
        Agendamento agendamento = Agendamento.buscarPorId(id);
        if (agendamento == null) {
            System.out.println("❌ Agendamento não encontrado!");
            return;
        }
        
        agendamento.marcarComoRealizado();
    }
    
    /**
     * Busca um agendamento por ID
     */
    private static void buscarAgendamentoPorId() {
        System.out.print("\n🔹 Digite o ID do agendamento: ");
        int id = lerInteiro();
        
        Agendamento agendamento = Agendamento.buscarPorId(id);
        if (agendamento == null) {
            System.out.println("❌ Agendamento não encontrado!");
        } else {
            agendamento.exibirDetalhes();
        }
    }
    
    /**
     * Gera agenda diária
     */
    private static void gerarAgendaDiaria() {
        System.out.print("\n🔹 Digite a data para consulta (dd/MM/yyyy): ");
        String dataStr = scanner.nextLine();
        
        try {
            LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            GerenciadorRelatorios.gerarAgendaDiaria(data);
        } catch (DateTimeParseException e) {
            System.out.println("❌ Formato de data inválido! Use dd/MM/yyyy");
        }
    }
    
    /**
     * Gera relatório por funcionário
     */
    private static void gerarRelatorioPorFuncionario() {
        Usuario.listarUsuarios();
        System.out.print("\n🔹 Digite o CPF do funcionário: ");
        String cpf = scanner.nextLine();
        
        Usuario funcionario = Usuario.buscarPorCpf(cpf);
        if (funcionario == null) {
            System.out.println("❌ Funcionário não encontrado!");
            return;
        }
        
        System.out.print("🔹 Data inicial (dd/MM/yyyy): ");
        String dataInicialStr = scanner.nextLine();
        System.out.print("🔹 Data final (dd/MM/yyyy): ");
        String dataFinalStr = scanner.nextLine();
        
        try {
            LocalDate dataInicial = LocalDate.parse(dataInicialStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate dataFinal = LocalDate.parse(dataFinalStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            
            GerenciadorRelatorios.gerarRelatorioPorFuncionario(funcionario, dataInicial, dataFinal);
        } catch (DateTimeParseException e) {
            System.out.println("❌ Formato de data inválido! Use dd/MM/yyyy");
        }
    }
    
    /**
     * Gera relatório por espaço
     */
    private static void gerarRelatorioPorEspaco() {
        EspacoServico.listarEspacos();
        System.out.print("\n🔹 Digite o nome do espaço: ");
        String nome = scanner.nextLine();
        
        EspacoServico espaco = EspacoServico.buscarPorNome(nome);
        if (espaco == null) {
            System.out.println("❌ Espaço não encontrado!");
            return;
        }
        
        System.out.print("🔹 Data inicial (dd/MM/yyyy): ");
        String dataInicialStr = scanner.nextLine();
        System.out.print("🔹 Data final (dd/MM/yyyy): ");
        String dataFinalStr = scanner.nextLine();
        
        try {
            LocalDate dataInicial = LocalDate.parse(dataInicialStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate dataFinal = LocalDate.parse(dataFinalStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            
            GerenciadorRelatorios.gerarRelatorioPorEspaco(espaco, dataInicial, dataFinal);
        } catch (DateTimeParseException e) {
            System.out.println("❌ Formato de data inválido! Use dd/MM/yyyy");
        }
    }
    
    private static void menuClientesPets() {
        int opcao;
        do {
            System.out.println("\n╔══════════════════════════════════════════════════════════╗");
            System.out.println("║               👥 CLIENTES E PETS                        ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");
            System.out.println("1️⃣  - Cadastrar Cliente");
            System.out.println("2️⃣  - Listar Clientes");
            System.out.println("3️⃣  - Adicionar Pet a Cliente");
            System.out.println("0️⃣  - ← Voltar");
            System.out.print("\n🔹 Escolha uma opção: ");
            
            opcao = lerInteiro();
            
            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> Cliente.listarClientes();
                case 3 -> adicionarPet();
                case 0 -> System.out.println("↩️  Voltando...");
                default -> System.out.println("❌ Opção inválida!");
            }
        } while (opcao != 0);
    }
    
    private static void menuFuncionarios() {
        int opcao;
        do {
            System.out.println("\n╔══════════════════════════════════════════════════════════╗");
            System.out.println("║                👨‍💼 FUNCIONÁRIOS                         ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");
            System.out.println("1️⃣  - Cadastrar Funcionário");
            System.out.println("2️⃣  - Listar Funcionários");
            System.out.println("0️⃣  - ← Voltar");
            System.out.print("\n🔹 Escolha uma opção: ");
            
            opcao = lerInteiro();
            
            switch (opcao) {
                case 1 -> cadastrarFuncionario();
                case 2 -> Usuario.listarUsuarios();
                case 0 -> System.out.println("↩️  Voltando...");
                default -> System.out.println("❌ Opção inválida!");
            }
        } while (opcao != 0);
    }
    
    private static void menuServicos() {
        int opcao;
        do {
            System.out.println("\n╔══════════════════════════════════════════════════════════╗");
            System.out.println("║                  🛍️  SERVIÇOS                          ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");
            System.out.println("1️⃣  - Cadastrar Serviço");
            System.out.println("2️⃣  - Listar Serviços");
            System.out.println("0️⃣  - ← Voltar");
            System.out.print("\n🔹 Escolha uma opção: ");
            
            opcao = lerInteiro();
            
            switch (opcao) {
                case 1 -> cadastrarServico();
                case 2 -> Servico.listarServicos();
                case 0 -> System.out.println("↩️  Voltando...");
                default -> System.out.println("❌ Opção inválida!");
            }
        } while (opcao != 0);
    }
    
    private static void menuEspacos() {
        int opcao;
        do {
            System.out.println("\n╔══════════════════════════════════════════════════════════╗");
            System.out.println("║                   🏢 ESPAÇOS                           ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");
            System.out.println("1️⃣  - Cadastrar Espaço");
            System.out.println("2️⃣  - Listar Espaços");
            System.out.println("0️⃣  - ← Voltar");
            System.out.print("\n🔹 Escolha uma opção: ");
            
            opcao = lerInteiro();
            
            switch (opcao) {
                case 1 -> cadastrarEspaco();
                case 2 -> EspacoServico.listarEspacos();
                case 0 -> System.out.println("↩️  Voltando...");
                default -> System.out.println("❌ Opção inválida!");
            }
        } while (opcao != 0);
    }
    
    private static void menuConsultas() {
        int opcao;
        do {
            System.out.println("\n╔══════════════════════════════════════════════════════════╗");
            System.out.println("║                  🔎 CONSULTAS                          ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");
            System.out.println("1️⃣  - Agendamentos por Cliente");
            System.out.println("2️⃣  - Agendamentos por Pet");
            System.out.println("0️⃣  - ← Voltar");
            System.out.print("\n🔹 Escolha uma opção: ");
            
            opcao = lerInteiro();
            
            switch (opcao) {
                case 1 -> consultarPorCliente();
                case 2 -> consultarPorPet();
                case 0 -> System.out.println("↩️  Voltando...");
                default -> System.out.println("❌ Opção inválida!");
            }
        } while (opcao != 0);
    }
    
    private static void cadastrarCliente() {
        System.out.println("\n➕ CADASTRAR CLIENTE");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        
        Cliente cliente = new Cliente(nome, telefone, cpf, endereco);
        cliente.cadastrarCliente();
    }
    
    private static void adicionarPet() {
        Cliente.listarClientes();
        System.out.print("\nCPF do cliente: ");
        String cpf = scanner.nextLine();
        
        Cliente cliente = buscarClientePorCpf(cpf);
        if (cliente == null) {
            System.out.println("❌ Cliente não encontrado!");
            return;
        }
        
        System.out.print("Nome do pet: ");
        String nome = scanner.nextLine();
        System.out.print("Espécie: ");
        String especie = scanner.nextLine();
        System.out.print("Raça: ");
        String raca = scanner.nextLine();
        
        Pet pet = new Pet(nome, especie, raca, new Date());
        cliente.adicionarPet(pet);
    }
    
    private static void cadastrarFuncionario() {
        System.out.println("\n➕ CADASTRAR FUNCIONÁRIO");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        
        System.out.println("Perfis disponíveis:");
        for (PerfilUsuario perfil : PerfilUsuario.values()) {
            System.out.println("- " + perfil);
        }
        System.out.print("Perfil: ");
        String perfilStr = scanner.nextLine();
        
        try {
            PerfilUsuario perfil = PerfilUsuario.valueOf(perfilStr.toUpperCase());
            Usuario funcionario = new Usuario(nome, cpf, telefone, perfil);
            funcionario.cadastrarUsuario();
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Perfil inválido!");
        }
    }
    
    private static void cadastrarServico() {
        System.out.println("\n➕ CADASTRAR SERVIÇO");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Preço: R$ ");
        float preco = Float.parseFloat(scanner.nextLine());
        
        System.out.println("Categorias disponíveis:");
        for (CategoriaServico categoria : CategoriaServico.values()) {
            System.out.println("- " + categoria);
        }
        System.out.print("Categoria: ");
        String categoriaStr = scanner.nextLine();
        
        try {
            CategoriaServico categoria = CategoriaServico.valueOf(categoriaStr.toUpperCase());
            Servico servico = new Servico(nome, descricao, preco, categoria);
            servico.cadastrarServico();
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Categoria inválida!");
        }
    }
    
    private static void cadastrarEspaco() {
        System.out.println("\n➕ CADASTRAR ESPAÇO");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        
        System.out.println("Tipos disponíveis:");
        for (TipoEspaco tipo : TipoEspaco.values()) {
            System.out.println("- " + tipo);
        }
        System.out.print("Tipo: ");
        String tipoStr = scanner.nextLine();
        
        try {
            TipoEspaco tipo = TipoEspaco.valueOf(tipoStr.toUpperCase());
            EspacoServico espaco = new EspacoServico(nome, descricao, tipo);
            espaco.cadastrarEspaco();
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Tipo inválido!");
        }
    }
    
    private static void consultarPorCliente() {
        Cliente.listarClientes();
        System.out.print("\nCPF do cliente: ");
        String cpf = scanner.nextLine();
        
        Cliente cliente = buscarClientePorCpf(cpf);
        if (cliente == null) {
            System.out.println("❌ Cliente não encontrado!");
        } else {
            Agendamento.consultarAgendamentosPorCliente(cliente);
        }
    }
    
    private static void consultarPorPet() {
        System.out.print("Nome do pet: ");
        String nome = scanner.nextLine();
        
        Pet pet = buscarPetPorNome(nome);
        if (pet == null) {
            System.out.println("❌ Pet não encontrado!");
        } else {
            Agendamento.consultarAgendamentosPorPet(pet);
        }
    }
    
    private static int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static Cliente buscarClientePorCpf(String cpf) {
        for (Cliente cliente : Cliente.listaClientes) {
            if (cliente.cpf.equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }
    
    private static Pet buscarPetDoCliente(Cliente cliente, String nome) {
        for (Pet pet : cliente.listaPets) {
            if (pet.nome.equalsIgnoreCase(nome)) {
                return pet;
            }
        }
        return null;
    }
    
    private static Pet buscarPetPorNome(String nome) {
        for (Cliente cliente : Cliente.listaClientes) {
            for (Pet pet : cliente.listaPets) {
                if (pet.nome.equalsIgnoreCase(nome)) {
                    return pet;
                }
            }
        }
        return null;
    }
    
    private static Servico buscarServicoPorNome(String nome) {
        for (Servico servico : Servico.listaServicos) {
            if (servico.nome.equalsIgnoreCase(nome)) {
                return servico;
            }
        }
        return null;
    }
}
