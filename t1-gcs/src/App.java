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

        Scanner escolha = new Scanner(System.in);
        int opção = escolha.nextInt();

        switch(opção){
            case 1:
                Scanner sc = new Scanner(System.in);
                System.out.println("Digite o nome do novo usuario: ");
                String nomeNovo = sc.nextLine();
                System.out.println("Digite o tipo do novo usuario: ");
                int tipoUsuario = sc.nextInt();
                Usuario nUsuario = null;
                switch(tipoUsuario) {
                    case 1:
                        nUsuario = new Admin(users.size() + 1, nomeNovo);
                        break;
                    case 2:
                        nUsuario = new Paciente(users.size() + 1, nomeNovo);
                        break;
                    case 3:
                        nUsuario = new Medico(users.size() + 1, nomeNovo);
                        break;
                    default: 
                        System.out.println("Tipo de usuario invalido");
                        telaAdm();
                }
                users.add(nUsuario);
                System.out.println("Novo usuario adicionado com sucesso!");
                telaAdm();
                break;
            case 2:
                Scanner scP = new Scanner(System.in);
                System.out.println("Digite o nome do paciente que deseja buscar: ");
                String nomePaciente = scP.nextLine();
                for (Usuario usuario : users) {
                    if (usuario instanceof Paciente && usuario.getNome().equalsIgnoreCase(nomePaciente)) {
                        System.out.println("Paciente encontrado: " + usuario.getNome());
                        break;
                    }
                }
                System.out.println("Paciente nao encontrado.");
                telaAdm();
                break;
            case 3:
                Scanner scM = new Scanner(System.in);
                System.out.println("Digite o nome do medico que deseja buscar: ");
                String nomeMedico = scM.nextLine();
                for (Usuario usuario : users) {
                    if (usuario instanceof Medico && usuario.getNome().equalsIgnoreCase(nomeMedico)) {
                        System.out.println("Medico encontrado: " + usuario.getNome());
                        break;
                    }
                }
                System.out.println("Medico nao encontrado.");
                telaAdm();
                break;
            case 4: 
                int numMedicos = 0;
                int numPacientes = 0;
                int numAutorizacoes = 0;
                int perncentualAutorizacoesRealizadas = 0;
                
                for (Usuario usuario : users) {
                    if (usuario instanceof Medico) {
                        numMedicos++;
                    } else if (usuario instanceof Paciente) {
                        numPacientes++;
                    }
                }

                System.out.println("Estastisticas: ");
                System.out.println("Numero de medicos: " + numMedicos);
                System.out.println("Numero de pacientes: " + numPacientes);
                System.out.println("Numero de autorizacoes emitidas: " + numAutorizacoes);
                System.out.println("Percentual de autorizacoes ja realizadas: " + perncentualAutorizacoesRealizadas + "%");
                telaAdm();
                break;
            case 0:
                telaMudaUsuario();
                break;
            default:
                System.out.println("Opcao invalida");
                telaAdm();
        }
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
