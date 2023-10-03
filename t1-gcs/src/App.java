import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    private int continua;

    // variavel que armazena a tela atual

    // Adicionar ArrayLists de todos os tipos de dados e carregá-los no método
    // carregaDadosIniciais
    private ArrayList<Usuario> users;
    private Usuario userAtual;

    public App() {
        users = new ArrayList<>();
        this.carregaDadosIniciais();
    }

    public void run() {
        telaInicial();
    }

    public void telaInicial() {
        System.out.println("Bem-vindo ao Sistema de Autorização de Exames Médicos!");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Digite o número de uma opção:");
            System.out.println("1. Selecionar usuário");
            System.out.println("0. Sair");

            try {
                int opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        this.telaMudaUsuario();
                        break;
                    case 0:
                        System.out.println("Obrigado por usar o sistema. Até logo!");
                        System.exit(0);
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, insira um número válido.");
                scanner.next(); // Limpa o buffer do scanner
            }
        }
    }

    public void telaMudaUsuario() {
        System.out.println("Digite o id do seu usuário");
        for (Usuario u : users) {
            System.out.println(u.getIdentificador() + ". " + u.getNome() + " "
                    + (u.getTipo() == 1 ? "(Admin) " : u.getTipo() == 2 ? "(Paciente)" : "(Médico)"));
        }
        System.out.println("0. Voltar ao menu inicial");

        Scanner scanner = new Scanner(System.in);

        try {
            int escolheUsuario = scanner.nextInt();

            if (escolheUsuario == 0) {
                telaInicial();
                return;
            }

            Usuario usuarioSelecionado = null;
            for (Usuario u : users) {
                if (escolheUsuario == u.getIdentificador()) {
                    usuarioSelecionado = u;
                    break;
                }
            }

            if (usuarioSelecionado != null) {
                userAtual = usuarioSelecionado;
                System.out.println("Tipo de usuário atual: " + userAtual.getClass().getSimpleName());
                if (userAtual instanceof Admin || userAtual.getTipo() == 1) {
                    telaAdm();
                } else if (userAtual instanceof Paciente || userAtual.getTipo() == 2) {
                    telaPaciente();
                } else if (userAtual instanceof Medico || userAtual.getTipo() == 3) {
                    telaMedico();
                } else {
                    System.out.println("Tipo de usuário inválido.");
                    telaMudaUsuario();
                }
            } else {
                System.out.println("Usuário não encontrado.");
                telaMudaUsuario();
            }
        } catch (InputMismatchException e) {
            System.out.println("Por favor, insira um número válido.");
            scanner.next(); // Limpa o buffer do scanner
        }
    }
    
    
    

/* 
    public void telaMudaUsuario() {

        System.out.println("Digite o id do seu usuario");
        for (Usuario u : users) {
            System.out.println(u.getIdentificador() + ". " + u.getNome() + " "
                    + (u.getTipo() == 1 ? "(Admin) " : u.getTipo() == 2 ? "(Paciente)" : "(Médico)"));
        }
        System.out.println("0. Voltar ao menu inicial");

        Scanner escolha = new Scanner(System.in);
        int escolheUsuario = escolha.nextInt();
        if (escolheUsuario == 0) {
            telaInicial();
        }
        for (Usuario u : users) {
            if (escolheUsuario == u.getIdentificador()) {
                userAtual = u;
                if (userAtual.getTipo() == 1) {
                    telaAdm();
                } else if (userAtual.getTipo() == 2) {
                    telaPaciente();
                } else if (userAtual.getTipo() == 3) {
                    telaMedico();
                } else {
                    System.out.println("Tipo de usuário inválido.");
                    telaMudaUsuario(); 
                }
            }
        }
    }
*/

    public void telaCadastro() {

    }

    public void telaPaciente() {
        System.out.println("Bem-vindo a central do Paciente");

        System.out.println("Digite o número de uma opção:");
        System.out.println("1. Marcar exame como realizado");
        System.out.println("2. Listar autorizações de exames");

    }

    public void telaMedico() {
        System.out.println("Bem-vindo a central do Paciente");

        System.out.println("Digite o número de uma opção:");
        System.out.println("1. Incluir nova autorização de exame");
        System.out.println("2. Listar autorizações de exame");

    }

    public void telaAdm() {
        if (userAtual instanceof Admin) {
            Admin admin = (Admin) userAtual;
            Scanner escolha = new Scanner(System.in);

            while (true) {
                System.out.println("Bem-vindo à central do Administrador");
                System.out.println("Digite o número de uma opção:");
                System.out.println("1. Incluir novo usuário");
                System.out.println("2. Buscar paciente");
                System.out.println("3. Buscar Médico");
                System.out.println("4. Ver estatísticas");
                System.out.println("0. Voltar ao menu inicial");

                int opcao = escolha.nextInt();

                switch (opcao) {
                    case 1:
                        Scanner sc = new Scanner(System.in);
                        System.out.println("Digite o nome do novo usuário: ");
                        String nomeNovo = sc.nextLine();
                        System.out.println(
                                "Digite o tipo do novo usuário (1 para Admin, 2 para Paciente, 3 para Médico): ");
                        int tipoUsuario = sc.nextInt();
                        Usuario nUsuario = null;
                        switch (tipoUsuario) {
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
                                System.out.println("Tipo de usuário inválido.");
                                break;
                        }
                        if (nUsuario != null) {
                            users.add(nUsuario);
                            System.out.println("Novo usuário adicionado com sucesso!");
                        }
                        break;
                    case 2:
                        Scanner scP = new Scanner(System.in);
                        System.out.println("Digite o nome do paciente que deseja buscar: ");
                        String nomePaciente = scP.nextLine();
                        boolean pacienteEncontrado = false;
                        for (Usuario usuario : users) {
                            if (usuario instanceof Paciente && usuario.getNome().equalsIgnoreCase(nomePaciente)) {
                                System.out.println("Paciente encontrado: " + usuario.getNome());
                                pacienteEncontrado = true;
                                break;
                            }
                        }
                        if (!pacienteEncontrado) {
                            System.out.println("Paciente não encontrado.");
                        }
                        break;
                    case 3:
                        Scanner scM = new Scanner(System.in);
                        System.out.println("Digite o nome do médico que deseja buscar: ");
                        String nomeMedico = scM.nextLine();
                        boolean medicoEncontrado = false;
                        for (Usuario usuario : users) {
                            if (usuario instanceof Medico && usuario.getNome().equalsIgnoreCase(nomeMedico)) {
                                System.out.println("Médico encontrado: " + usuario.getNome());
                                medicoEncontrado = true;
                                break;
                            }
                        }
                        if (!medicoEncontrado) {
                            System.out.println("Médico não encontrado.");
                        }
                        break;
                    case 4:
                        admin.verEstatisticasGerais();
                        break;
                    case 0:
                        telaMudaUsuario();
                        //return; // Sair da função
                    default:
                        System.out.println("Opção inválida.");
                }
            }
        }
    }

    public void carregaDadosIniciais() {
        // carregar dados inciais de usuários, exames e demais informações necessárias
        users.add(new Usuario(1, "Maria", 1));
        users.add(new Usuario(2, "João", 1));
        users.add(new Usuario(3, "Pedro", 1));
        users.add(new Usuario(4, "Flávia", 1));
        users.add(new Usuario(5, "Paulo", 2));
        users.add(new Usuario(6, "Mario", 3));
    }

}
