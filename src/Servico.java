import java.util.ArrayList;
import java.util.List;

class Servico {
    String nome;
    String descricao;
    float preco;
    CategoriaServico categoria;

    static List<Servico> listaServicos = new ArrayList<>();

    Servico(String nome, String descricao, float preco, CategoriaServico categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
    }

    void cadastrarServico() {
        listaServicos.add(this);
        System.out.println("Serviço cadastrado com sucesso!");
    }

    void editarServico(String novoNome, String novaDescricao, float novoPreco, CategoriaServico novaCategoria) {
        this.nome = novoNome;
        this.descricao = novaDescricao;
        this.preco = novoPreco;
        this.categoria = novaCategoria;
        System.out.println("Serviço atualizado com sucesso!");
    }

    void removerServico() {
        listaServicos.remove(this);
        System.out.println("Serviço removido com sucesso!");
    }

    static void listarServicos() {
        System.out.println("===== LISTA DE SERVIÇOS =====");
        for (Servico s : listaServicos) {
            System.out.println("Nome: " + s.nome);
            System.out.println("Descrição: " + s.descricao);
            System.out.println("Preço: R$ " + s.preco);
            System.out.println("Categoria: " + s.categoria);
            System.out.println("-----------------------------");
        }
    }
}
