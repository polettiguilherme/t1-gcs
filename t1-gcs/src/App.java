import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    private int continua;
    private ArrayList<Usuario> users;
    private ArrayList<AutorizacaoExame> autorizacoes;
    private ArrayList<Exame> exames;
    private Usuario userAtual;

    public App() {
        users = new ArrayList<>();
        autorizacoes = new ArrayList<>();
        exames = new ArrayList<>();
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

    public void telaPaciente() {
        Scanner escolha = new Scanner(System.in);

        while (true) {
            System.out.println("Bem-vindo a central do Paciente");
            System.out.println("Digite o número de uma opção:");
            System.out.println("1. Marcar exame como realizado");
            System.out.println("2. Listar autorizações de exames");
            System.out.println("0. Voltar ao menu inicial");

            int opcao = escolha.nextInt();

            switch (opcao) {
                case 1:
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    System.out.println("Digite o Id do exame: ");
                    int idExame = escolha.nextInt();
                    boolean achou = false;
                    for(Exame e : exames){
                        if(idExame == e.getExameID()){
                            achou = true;

                            System.out.println("Digite a data em que o exame foi realizado (DD/MM/AAAA):");

                            try {
                                Date data = formato.parse(escolha.next());
                                for(AutorizacaoExame a : autorizacoes) {
                                    if (a.getTipoExame().getNomeExame() == e.getNomeExame()) {
                                        if (data.compareTo(a.getDataCadastro()) > 0 && a.getDataCadastro().compareTo(data) < 30) {
                                            System.out.println("Exame marcado como realizado!");
                                            a.setDataRealizacao(data);
                                        }
                                        else{
                                            System.out.println("Data inválida");
                                            telaPaciente();
                                        }
                                    }
                                }
                            } catch (ParseException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                    if(!achou){
                        System.out.println("Exame não encontrado.");
                        telaPaciente();
                    }
                    break;
                case 2:
                    for(AutorizacaoExame a : autorizacoes){
                        if(a.getPaciente().getIdentificador() == userAtual.getIdentificador()){
                            System.out.println("----------------------");
                            System.out.println("Código: " + a.getCodigo() + "\nData de cadastro: " + a.getDataCadastro() + "\nMédico: "+ a.getMedicoSolicitante().getNome()
                                    + "\nPaciente: " +a.getPaciente().getNome() + "\nExame: " + a.getTipoExame().getNomeExame()
                                    + (a.getDataRealizacao() != null ? "\nData realizado: " + a.getDataRealizacao() : ""));
                            System.out.println("----------------------");
                        }
                    }
                    break;
                case 0:
                    telaMudaUsuario();
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    public void telaMedico() {
        Scanner escolha = new Scanner(System.in);

        while (true) {
            System.out.println("Bem-vindo a central do Médico");
            System.out.println("Digite o número de uma opção:");
            System.out.println("1. Incluir nova autorização de exame");
            System.out.println("2. Listar autorizações de exame");
            System.out.println("0. Voltar ao menu inicial");

            int opcao = escolha.nextInt();

            switch (opcao) {
                case 1:
                    novaAutorizacao();
                    break;
                case 2:
                    for(AutorizacaoExame a : autorizacoes){
                        if(a.getMedicoSolicitante().getIdentificador() == userAtual.getIdentificador()){
                            System.out.println("----------------------");
                            System.out.println("Código: " + a.getCodigo() + "\nData de cadastro: " + a.getDataCadastro() + "\nMédico: "+ a.getMedicoSolicitante().getNome()
                                    + "\nPaciente: " +a.getPaciente().getNome() + "\nExame: " + a.getTipoExame().getNomeExame()
                                    + (a.getDataRealizacao() != null ? "\nData realizado: " + a.getDataRealizacao() : ""));
                            System.out.println("----------------------");
                        }
                    }
                    break;
                case 0:
                    telaMudaUsuario();
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    public void novaAutorizacao(){
        Scanner escolha = new Scanner(System.in);
        boolean achouMedico = false;
        boolean achouPaciente = false;
        boolean achouExame = false;

        while(true){

            Medico medicoSolicitante = null;
            Paciente paciente = null;
            Exame exame = null;

            System.out.println("Incluir nova autorização:");
            System.out.println("Digite o Id do médico solicitante:");
            int idMedico = escolha.nextInt();

            for (Usuario u: users) {
                if(u instanceof Medico){
                    if(idMedico == u.getIdentificador()){
                        medicoSolicitante = (Medico) u;
                        achouMedico = true;
                    }
                }
            }

            if(!achouMedico) {
                System.out.println("Médico não encontrado!");
                continue;
            }

            System.out.println("Digite o Id do paciente:");
            int idPaciente = escolha.nextInt();
            for (Usuario u: users) {
                if(u instanceof Paciente){
                    if(idPaciente == u.getIdentificador()){
                        paciente = (Paciente) u;
                        achouPaciente = true;
                        break;
                    }
                }
            }

            if(!achouPaciente) {
                System.out.println("Paciente não encontrado!");
                continue;
            }

            System.out.println("Digite o Id do Exame:");
            int idExame = escolha.nextInt();
            for (Exame e: exames) {
                if(idExame == e.getExameID()){
                    exame = e;
                    achouExame = true;

                }
            }

            if(!achouExame) {
                System.out.println("Exame não encontrado!");
                continue;
            }

            autorizacoes.add(new AutorizacaoExame(medicoSolicitante, paciente, exame));
            System.out.println("Autorização adicionada com sucesso!");
            telaMedico();
        }
    }
    public void telaAdm() {
        Admin admin = new Admin(userAtual.getIdentificador(), userAtual.getNome());
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
                    verEstatisticasGerais();
                    break;
                case 0:
                    telaMudaUsuario();
                default:
                    System.out.println("Opção inválida.");
            }
        }

    }

    public void verEstatisticasGerais(){
        Admin adm = (Admin) userAtual;
        int numMedicos = (int) users.stream().filter(usuario -> usuario.getTipo() == 3).count();
        int numPacientes = (int) users.stream().filter(usuario -> usuario.getTipo() == 2).count();
        int numAutorizacoesEmitidas = autorizacoes.size();
        long numAutorizacoesRealizadas = autorizacoes.stream().filter(autorizacao -> autorizacao.getDataRealizacao() != null).count(); //Erro getDataRealizacao()
        double perncentualAutorizacoesRealizadas = (double) numAutorizacoesRealizadas / numAutorizacoesEmitidas * 100;

        System.out.println("Numero de Medicos: " + numMedicos);
        System.out.println("Numero de Pacientes: " + numPacientes);
        System.out.println("Numero de Autorizacoes Emitidas: " + numAutorizacoesEmitidas);
        System.out.println("Percentual de Autorizacoes Realizadas: " + perncentualAutorizacoesRealizadas + "%");
    }

    public void carregaDadosIniciais() {
        // carregar dados inciais de usuários, exames e demais informações necessárias
        users.add(new Admin(1, "Maria"));
        users.add(new Admin(2, "João"));
        users.add(new Paciente(3, "Pedro"));
        users.add(new Paciente(4, "Flávia"));
        users.add(new Medico(5, "Paulo"));
        users.add(new Medico(6, "Mario"));

        exames.add(new Exame(1, "Hemograma", new Date()));
        exames.add(new Exame(2, "Urina", new Date()));
        exames.add(new Exame(3, "Fezes", new Date()));
        exames.add(new Exame(4, "Sangue", new Date()));
        exames.add(new Exame(5, "Acido Úrico", new Date()));
        exames.add(new Exame(6, "Colesterol", new Date()));
        exames.add(new Exame(7, "TSH", new Date()));
        exames.add(new Exame(8, "Creatinina", new Date()));
        exames.add(new Exame(9, "Papanicolau", new Date()));
        exames.add(new Exame(10, "Pressão Arterial", new Date()));

        autorizacoes.add(new AutorizacaoExame((Medico) users.get(4), (Paciente) users.get(2), exames.get(0)));
        autorizacoes.add(new AutorizacaoExame((Medico) users.get(5), (Paciente) users.get(2), exames.get(1)));
        autorizacoes.add(new AutorizacaoExame((Medico) users.get(4), (Paciente) users.get(3), exames.get(2)));
        autorizacoes.add(new AutorizacaoExame((Medico) users.get(5), (Paciente) users.get(3), exames.get(3)));

    }

}
