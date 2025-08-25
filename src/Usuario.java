import java.util.ArrayList;
import java.util.List;

/**
 * Classe Usuario - representa funcionários do pet shop
 */
class Usuario {
    private String nome;
    private String cpf;
    private String telefone;
    private PerfilUsuario perfil;
    private boolean ativo;
    
    static List<Usuario> listaUsuarios = new ArrayList<>();
    
    /**
     * Construtor da classe Usuario
     */
    public Usuario(String nome, String cpf, String telefone, PerfilUsuario perfil) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.perfil = perfil;
        this.ativo = true;
    }
    
    /**
     * Cadastra o funcionário
     */
    public void cadastrarUsuario() {
        listaUsuarios.add(this);
        System.out.println("Funcionário " + nome + " cadastrado com sucesso!");
    }
    
    /**
     * Edita as informações do funcionário
     */
    public void editarUsuario(String novoNome, String novoTelefone, PerfilUsuario novoPerfil) {
        this.nome = novoNome;
        this.telefone = novoTelefone;
        this.perfil = novoPerfil;
        System.out.println("Dados do funcionário atualizados com sucesso!");
    }
    
    /**
     * Remove o funcionário do sistema
     */
    public void removerUsuario() {
        this.ativo = false;
        System.out.println("Funcionário " + nome + " removido do sistema!");
    }
    
    /**
     * Lista todos os funcionários
     */
    public static void listarUsuarios() {
        System.out.println("===== LISTA DE FUNCIONÁRIOS =====");
        for (Usuario u : listaUsuarios) {
            if (u.ativo) {
                System.out.println("Nome: " + u.nome);
                System.out.println("CPF: " + u.cpf);
                System.out.println("Telefone: " + u.telefone);
                System.out.println("Perfil: " + u.perfil);
                System.out.println("Status: " + (u.ativo ? "Ativo" : "Inativo"));
                System.out.println("-----------------------------");
            }
        }
    }
    
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public PerfilUsuario getPerfil() { return perfil; }
    public boolean isAtivo() { return ativo; }
    
    /**
     * Busca funcionário por CPF
     */
    public static Usuario buscarPorCpf(String cpf) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.cpf.equals(cpf) && usuario.ativo) {
                return usuario;
            }
        }
        return null;
    }
}