import java.util.Date;

public class Main {
    public static void main(String[] args) {
        
        Servico servico1 = new Servico("Banho e Tosa Completo", "Banho, tosa higiênica e secagem", 60.0f, CategoriaServico.BANHO_E_TOSA);
        Servico servico2 = new Servico("Consulta Veterinária", "Avaliação de rotina com veterinário", 150.0f, CategoriaServico.CONSULTA_VETERINARIA);
        Servico servico3 = new Servico("Diária no Hotelzinho", "Hospedagem com alimentação e recreação", 100.0f, CategoriaServico.HOTELARIA_E_CRECHE);

        servico1.cadastrarServico();
        servico2.cadastrarServico();
        servico3.cadastrarServico();

        Servico.listarServicos();

        servico1.editarServico("Banho Premium", "Banho com hidratação + perfume", 80.0f, CategoriaServico.BANHO_E_TOSA);
        Servico.listarServicos();

        Cliente cliente1 = new Cliente("Ana Souza", "3198765-4321", "111.222.333-44", "Rua das Flores, 120");
        Cliente cliente2 = new Cliente("Carlos Pereira", "3198888-7777", "555.666.777-88", "Av. Brasil, 450");
        Cliente cliente3 = new Cliente("Juliana Oliveira", "3194444-3333", "999.888.777-66", "Rua Minas Gerais, 99");

        cliente1.cadastrarCliente();
        cliente2.cadastrarCliente();
        cliente3.cadastrarCliente();

        Pet pet1 = new Pet("Thor", "Cachorro", "Pitbull", new Date());
        Pet pet2 = new Pet("Luna", "Gato", "Siamês", new Date());
        Pet pet3 = new Pet("Belinha", "Cachorro", "Shih-tzu", new Date());

        cliente1.adicionarPet(pet1);
        cliente1.adicionarPet(pet2);
        cliente2.adicionarPet(pet3);

        Cliente.listarClientes();

        cliente1.editarCliente("Ana Souza Santos", "3192222-1111", "111.222.333-44", "Rua Nova Esperança, 300");
        Cliente.listarClientes();

        pet1.editarPet("Thor Boladão", "Cachorro", "Pitbull Red Nose", new Date());
        cliente1.listarPetsDoCliente();

        cliente1.removerPet(pet2);
        cliente1.listarPetsDoCliente();

        cliente3.removerCliente();
        Cliente.listarClientes();

        servico2.removerServico();
        Servico.listarServicos();
    }
}
