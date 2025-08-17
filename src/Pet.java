import java.util.Date;

class Pet {
    String nome;
    String especie;
    String raca;
    Date dataNascimento;

    Pet(String nome, String especie, String raca, Date dataNascimento) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
    }

    void editarPet(String novoNome, String novaEspecie, String novaRaca, Date novaDataNascimento) {
        this.nome = novoNome;
        this.especie = novaEspecie;
        this.raca = novaRaca;
        this.dataNascimento = novaDataNascimento;
        System.out.println("Pet atualizado com sucesso!");
    }

    void mostrarPet() {
        System.out.println("Nome do pet: " + nome);
        System.out.println("Espécie: " + especie);
        System.out.println("Raça: " + raca);
        System.out.println("Data de Nascimento: " + dataNascimento);
    }
}