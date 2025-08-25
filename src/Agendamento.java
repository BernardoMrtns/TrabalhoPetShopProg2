import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Agendamento - núcleo do sistema de agendamentos do pet shop
 */
class Agendamento {
    private static int proximoId = 1;
    private int id;
    private Cliente cliente;
    private Pet pet;
    private Servico servico;
    private Usuario funcionario;
    private EspacoServico espaco;
    private LocalDateTime dataHora;
    private StatusAgendamento status;
    private String observacoes;
    
    static List<Agendamento> listaAgendamentos = new ArrayList<>();
    
    /**
     * Construtor da classe Agendamento
     */
    public Agendamento(Cliente cliente, Pet pet, Servico servico, Usuario funcionario, 
                      EspacoServico espaco, LocalDateTime dataHora, String observacoes) {
        this.id = proximoId++;
        this.cliente = cliente;
        this.pet = pet;
        this.servico = servico;
        this.funcionario = funcionario;
        this.espaco = espaco;
        this.dataHora = dataHora;
        this.status = StatusAgendamento.AGENDADO;
        this.observacoes = observacoes != null ? observacoes : "";
    }
    
    /**
     * Agenda um novo serviço com validações essenciais
     */
    public boolean agendarServico() {
        // Validação 1: Verificar se funcionário está disponível no horário
        if (!validarDisponibilidadeFuncionario()) {
            System.out.println("❌ ERRO: Funcionário " + funcionario.getNome() + 
                             " já possui agendamento neste horário!");
            return false;
        }
        
        // Validação 2: Verificar se espaço está disponível no horário
        if (!validarDisponibilidadeEspaco()) {
            System.out.println("❌ ERRO: Espaço " + espaco.getNome() + 
                             " já está ocupado neste horário!");
            return false;
        }
        
        // Validação 3: Verificar se a data/hora é futura
        if (!validarDataHora()) {
            System.out.println("❌ ERRO: Não é possível agendar para datas passadas!");
            return false;
        }
        
        // Se todas as validações passaram, confirma o agendamento
        listaAgendamentos.add(this);
        System.out.println("✅ Agendamento realizado com sucesso!");
        System.out.println("ID do Agendamento: " + id);
        System.out.println("Cliente: " + cliente.nome);
        System.out.println("Pet: " + pet.nome);
        System.out.println("Serviço: " + servico.nome);
        System.out.println("Funcionário: " + funcionario.getNome());
        System.out.println("Espaço: " + espaco.getNome());
        System.out.println("Data/Hora: " + dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        
        return true;
    }
    
    /**
     * Cancela um agendamento
     */
    public boolean cancelarAgendamento(String motivo) {
        if (status == StatusAgendamento.CANCELADO) {
            System.out.println("❌ Este agendamento já foi cancelado!");
            return false;
        }
        
        if (status == StatusAgendamento.REALIZADO) {
            System.out.println("❌ Não é possível cancelar um serviço já realizado!");
            return false;
        }
        
        this.status = StatusAgendamento.CANCELADO;
        this.observacoes += " [CANCELADO: " + motivo + "]";
        
        System.out.println("✅ Agendamento ID " + id + " cancelado com sucesso!");
        System.out.println("Motivo: " + motivo);
        
        return true;
    }
    
    /**
     * Marca um agendamento como realizado
     */
    public void marcarComoRealizado() {
        if (status == StatusAgendamento.AGENDADO) {
            this.status = StatusAgendamento.REALIZADO;
            System.out.println("✅ Serviço ID " + id + " marcado como realizado!");
        }
    }
    
    /**
     * Consulta agendamentos por cliente
     */
    public static void consultarAgendamentosPorCliente(Cliente cliente) {
        System.out.println("===== AGENDAMENTOS DE " + cliente.nome.toUpperCase() + " =====");
        boolean encontrou = false;
        
        for (Agendamento agendamento : listaAgendamentos) {
            if (agendamento.cliente.equals(cliente)) {
                agendamento.exibirDetalhes();
                encontrou = true;
            }
        }
        
        if (!encontrou) {
            System.out.println("Nenhum agendamento encontrado para este cliente.");
        }
    }
    
    /**
     * Consulta agendamentos por pet
     */
    public static void consultarAgendamentosPorPet(Pet pet) {
        System.out.println("===== AGENDAMENTOS DO PET " + pet.nome.toUpperCase() + " =====");
        boolean encontrou = false;
        
        for (Agendamento agendamento : listaAgendamentos) {
            if (agendamento.pet.equals(pet)) {
                agendamento.exibirDetalhes();
                encontrou = true;
            }
        }
        
        if (!encontrou) {
            System.out.println("Nenhum agendamento encontrado para este pet.");
        }
    }
    
    /**
     * Lista todos os agendamentos
     */
    public static void listarTodosAgendamentos() {
        System.out.println("===== TODOS OS AGENDAMENTOS =====");
        if (listaAgendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento cadastrado no sistema.");
            return;
        }
        
        for (Agendamento agendamento : listaAgendamentos) {
            agendamento.exibirDetalhes();
        }
    }
    
    /**
     * Exibe os detalhes do agendamento
     */
    public void exibirDetalhes() {
        System.out.println("────────────────────────────────");
        System.out.println("ID: " + id);
        System.out.println("Status: " + status);
        System.out.println("Cliente: " + cliente.nome);
        System.out.println("Pet: " + pet.nome + " (" + pet.especie + " - " + pet.raca + ")");
        System.out.println("Serviço: " + servico.nome);
        System.out.println("Preço: R$ " + servico.preco);
        System.out.println("Funcionário: " + funcionario.getNome());
        System.out.println("Espaço: " + espaco.getNome());
        System.out.println("Data/Hora: " + dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        if (!observacoes.isEmpty()) {
            System.out.println("Observações: " + observacoes);
        }
        System.out.println("────────────────────────────────");
    }
    
    /**
     * Verifica se funcionário está disponível no horário
     */
    private boolean validarDisponibilidadeFuncionario() {
        for (Agendamento agendamento : listaAgendamentos) {
            if (agendamento.status != StatusAgendamento.CANCELADO &&
                agendamento.funcionario.equals(this.funcionario) &&
                agendamento.dataHora.equals(this.dataHora)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Verifica se espaço está disponível no horário
     */
    private boolean validarDisponibilidadeEspaco() {
        for (Agendamento agendamento : listaAgendamentos) {
            if (agendamento.status != StatusAgendamento.CANCELADO &&
                agendamento.espaco.equals(this.espaco) &&
                agendamento.dataHora.equals(this.dataHora)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Valida se a data/hora é futura
     */
    private boolean validarDataHora() {
        return dataHora.isAfter(LocalDateTime.now());
    }
    
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Pet getPet() { return pet; }
    public Servico getServico() { return servico; }
    public Usuario getFuncionario() { return funcionario; }
    public EspacoServico getEspaco() { return espaco; }
    public LocalDateTime getDataHora() { return dataHora; }
    public StatusAgendamento getStatus() { return status; }
    public String getObservacoes() { return observacoes; }
    
    /**
     * Busca agendamento por ID
     */
    public static Agendamento buscarPorId(int id) {
        for (Agendamento agendamento : listaAgendamentos) {
            if (agendamento.id == id) {
                return agendamento;
            }
        }
        return null;
    }
}