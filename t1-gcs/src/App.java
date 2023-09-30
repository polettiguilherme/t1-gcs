import java.util.ArrayList;
import java.util.Scanner;

public class App {

    private int continua;

    //variavel que armazena a tela atual

    //Adicionar ArrayLists de todos os tipos de dados e carregá-los no método carregaDadosIniciais
    private ArrayList<Usuario> users;
    private Usuario userAtual;

    public App(){
        users = new ArrayList<>();
        this.carregaDadosIniciais();
    }

    public void run(){
        telaInicial();
    }

    public void telaInicial(){
        System.out.println("Bem-vindo ao Sistema de Autorização de Exames Médicos!");

        System.out.println("Digite o número de uma opção:");
        System.out.println("1. Selecionar usuário");


        Scanner escolha  = new Scanner(System.in);

        switch(escolha.nextInt()){
            case 1:
                this.telaMudaUsuario();
                break;
//            case 2:
//
//                break;
        }
    }

    public void telaMudaUsuario(){

        System.out.println("Digite o id do seu usuario");
        for (Usuario u : users) {
            System.out.println(u.getIdentificador() + ". " + u.getNome() + " " + (u.getTipo() == 1 ? "(Admin) " : u.getTipo() == 2 ? "(Paciente)" : "(Médico)"));
        }
        System.out.println("0. Voltar ao menu inicial");

        Scanner escolha  = new Scanner(System.in);
        int escolheUsuario = escolha.nextInt();
        if(escolheUsuario == 0){
            telaInicial();
        }
        for (Usuario u : users) {
            if(escolheUsuario == u.getIdentificador()){
                userAtual = u;
                if(userAtual.getTipo() == 1) telaAdm();
                if(userAtual.getTipo() == 2) telaPaciente();
                if(userAtual.getTipo() == 3) telaMedico();
            }
        }
    }

    public void telaCadastro(){

    }
    public void telaPaciente(){
        System.out.println("Bem-vindo a central do Paciente");

        System.out.println("Digite o número de uma opção:");
        System.out.println("1. Marcar exame como realizado");
        System.out.println("2. Listar autorizações de exames");

    }

    public void telaMedico(){
        System.out.println("Bem-vindo a central do Paciente");

        System.out.println("Digite o número de uma opção:");
        System.out.println("1. Incluir nova autorização de exame");
        System.out.println("2. Listar autorizações de exame");

    }

    public void telaAdm(){
        System.out.println("Bem-vindo a central do Paciente");

        System.out.println("Digite o número de uma opção:");
        System.out.println("1. Incluir novo usuário");
        System.out.println("2. Buscar paciente");
        System.out.println("3. Buscar Médico");
        System.out.println("4. Ver estatísticas");

    }

    public void carregaDadosIniciais(){
        //carregar dados inciais de usuários, exames e demais informações necessárias
        users.add(new Usuario(1,"Maria", 1));
        users.add(new Usuario(2,"João", 2));
        users.add(new Usuario(3,"Pedro", 2));
        users.add(new Usuario(4,"Flávia", 3));
        users.add(new Usuario(5,"Paulo", 2));

    }

}
