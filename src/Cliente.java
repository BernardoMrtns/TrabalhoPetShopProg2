import java.util.ArrayList;
import java.util.List;

class Cliente {
    String nome;
    String telefone;
    String cpf;
    String endereco;
    List<Pet> listaPets = new ArrayList<>();

    static List<Cliente> listaClientes = new ArrayList<>();

    // Construtor
    Cliente(String nome, String telefone, String cpf, String endereco) {
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    void cadastrarCliente() {
        listaClientes.add(this);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    void editarCliente(String novoNome, String novoTelefone, String novoCpf, String novoEndereco) {
        this.nome = novoNome;
        this.telefone = novoTelefone;
        this.cpf = novoCpf;
        this.endereco = novoEndereco;
        System.out.println("Cliente atualizado com sucesso!");
    }

    void removerCliente() {
        listaClientes.remove(this);
        System.out.println("Cliente removido com sucesso!");
    }

    void adicionarPet(Pet pet) {
        listaPets.add(pet);
        System.out.println("Pet adicionado ao cliente " + nome);
    }

    void removerPet(Pet pet) {
        listaPets.remove(pet);
        System.out.println("Pet removido do cliente " + nome);
    }

    void listarPetsDoCliente() {
        System.out.println("===== Pets do cliente " + nome + " =====");
        for (Pet p : listaPets) {
            p.mostrarPet();
            System.out.println("------------------");
        }
    }

    static void listarClientes() {
        System.out.println("===== LISTA DE CLIENTES =====");
        for (Cliente c : listaClientes) {
            System.out.println("Nome: " + c.nome);
            System.out.println("Telefone: " + c.telefone);
            System.out.println("CPF: " + c.cpf);
            System.out.println("Endereço: " + c.endereco);
            c.listarPetsDoCliente();
            System.out.println("-----------------------------");
        }
    }
}