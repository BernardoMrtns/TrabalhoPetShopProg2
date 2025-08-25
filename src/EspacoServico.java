import java.util.ArrayList;
import java.util.List;

/**
 * Classe EspacoServico - espaços físicos do pet shop
 */
class EspacoServico {
    private String nome;
    private String descricao;
    private TipoEspaco tipo;
    private boolean disponivel;
    private boolean ativo;
    
    static List<EspacoServico> listaEspacos = new ArrayList<>();
    
    /**
     * Construtor da classe EspacoServico
     */
    public EspacoServico(String nome, String descricao, TipoEspaco tipo) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.disponivel = true;
        this.ativo = true;
    }
    
    /**
     * Cadastra o espaço
     */
    public void cadastrarEspaco() {
        listaEspacos.add(this);
        System.out.println("Espaço " + nome + " cadastrado com sucesso!");
    }
    
    /**
     * Edita as informações do espaço
     * 
     * @param novoNome Novo nome do espaço
     * @param novaDescricao Nova descrição
     * @param novoTipo Novo tipo de espaço
     */
    public void editarEspaco(String novoNome, String novaDescricao, TipoEspaco novoTipo) {
        this.nome = novoNome;
        this.descricao = novaDescricao;
        this.tipo = novoTipo;
        System.out.println("Dados do espaço atualizados com sucesso!");
    }
    
    /**
     * Remove o espaço do sistema (inativa)
     */
    public void removerEspaco() {
        this.ativo = false;
        System.out.println("Espaço " + nome + " removido do sistema!");
    }
    
    /**
     * Verifica se o espaço está disponível para uso
     * 
     * @return true se disponível, false caso contrário
     */
    public boolean verificarDisponibilidade() {
        return disponivel && ativo;
    }
    
    /**
     * Define o status de disponibilidade do espaço
     * 
     * @param disponivel true para disponível, false para ocupado
     */
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
    
    /**
     * Lista todos os espaços cadastrados
     */
    public static void listarEspacos() {
        System.out.println("===== LISTA DE ESPAÇOS =====");
        for (EspacoServico e : listaEspacos) {
            if (e.ativo) {
                System.out.println("Nome: " + e.nome);
                System.out.println("Descrição: " + e.descricao);
                System.out.println("Tipo: " + e.tipo);
                System.out.println("Status: " + (e.disponivel ? "Disponível" : "Ocupado"));
                System.out.println("-----------------------------");
            }
        }
    }
    
    // Getters necessários para outras classes
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public TipoEspaco getTipo() { return tipo; }
    public boolean isDisponivel() { return disponivel; }
    public boolean isAtivo() { return ativo; }
    
    /**
     * Busca espaço por nome
     * 
     * @param nome Nome do espaço a ser buscado
     * @return EspacoServico encontrado ou null se não encontrar
     */
    public static EspacoServico buscarPorNome(String nome) {
        for (EspacoServico espaco : listaEspacos) {
            if (espaco.nome.equalsIgnoreCase(nome) && espaco.ativo) {
                return espaco;
            }
        }
        return null;
    }
    
    /**
     * Lista espaços disponíveis por tipo
     * 
     * @param tipo Tipo de espaço desejado
     * @return Lista de espaços disponíveis do tipo especificado
     */
    public static List<EspacoServico> buscarEspacosDisponiveis(TipoEspaco tipo) {
        List<EspacoServico> espacosDisponiveis = new ArrayList<>();
        for (EspacoServico espaco : listaEspacos) {
            if (espaco.tipo == tipo && espaco.verificarDisponibilidade()) {
                espacosDisponiveis.add(espaco);
            }
        }
        return espacosDisponiveis;
    }
}