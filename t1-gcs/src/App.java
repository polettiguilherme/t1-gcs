import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Date;

public class App {

    private int continua;

    //variavel que armazena a tela atual

    //Adicionar ArrayLists de todos os tipos de dados e carregá-los no método carregaDadosIniciais
    private ArrayList<Usuario> users;
    private ArrayList<Exame> exames;
    private ArrayList<AutorizacaoExame> autorizacoes;
    private Usuario userAtual;

    public App(){
        users = new ArrayList<>();
        exames = new ArrayList<>();
        autorizacoes = new ArrayList<>();
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

    public void telaPaciente() {
        System.out.println("Bem-vindo a central do Paciente");

        System.out.println("Digite o número de uma opção:");
        System.out.println("1. Marcar exame como realizado");
        System.out.println("2. Listar autorizações de exames");

        Scanner escolha = new Scanner(System.in);
        int opcao = escolha.nextInt();

        switch (opcao) {
            case 1:
                marcarExameComoRealizado();
                break;
            case 2:
                listarAutorizacoesPaciente();
                break;
            case 0:
                telaInicial();
                break;
        }
    }

    public void telaMedico() {
        System.out.println("Bem-vindo a central do Paciente");

        System.out.println("Digite o número de uma opção:");
        System.out.println("1. Incluir nova autorização de exame");
        System.out.println("2. Listar autorizações de exame");

        Scanner escolha = new Scanner(System.in);
        int opcao = escolha.nextInt();

        switch (opcao) {
            case 1:
                telaCriarAutorizacao();
                break;
            case 2:
                listarAutorizacoesMedico();
                break;
            case 0:
                telaInicial();
                break;
        }
    }

    public void telaAdm() {
        System.out.println("Bem-vindo a central do Paciente");

        System.out.println("Digite o número de uma opção:");
        System.out.println("1. Incluir novo usuário");
        System.out.println("2. Buscar paciente");
        System.out.println("3. Buscar Médico");
        System.out.println("4. Ver estatísticas");

        Scanner escolha = new Scanner(System.in);
        int opcao = escolha.nextInt();

        switch (opcao) {
            case 1:
                telaCriarNovoUsuario();
                break;
            case 2:
                telaBuscarPaciente();
                break;
            case 3:
                telaBuscarMedico();
                break;
            case 4:
                verEstatisticasGerais();
                break;
            case 0:
                telaInicial();
                break;
        }
    }

    public void marcarExameComoRealizado() {
        System.out.println("Digite o código da autorização de exame que deseja marcar como realizado:");
        Scanner scanner = new Scanner(System.in);
        int codigoAutorizacao = scanner.nextInt();

        AutorizacaoExame autorizacao = autorizacoes.stream()
                .filter(a -> a.getPaciente().equals(userAtual) && a.getCodigo() == codigoAutorizacao)
                .findFirst()
                .orElse(null);

        if (autorizacao != null) {
            System.out.println("Digite a data de realização do exame (formato: dd/MM/yyyy):");
            String dataRealizacaoStr = scanner.next();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            try {
                Date dataRealizacao = sdf.parse(dataRealizacaoStr);
                Date dataSolicitacao = autorizacao.getDataCadastro();

                // Verifica se a data de realização é posterior à data de solicitação
                if (dataRealizacao.after(dataSolicitacao)) {
                    // Verifica se a data de realização não ultrapassa 30 dias após a data de
                    // solicitação
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dataSolicitacao);
                    cal.add(Calendar.DAY_OF_MONTH, 30);
                    Date dataLimite = cal.getTime();

                    if (dataRealizacao.before(dataLimite) || dataRealizacao.equals(dataLimite)) {
                        autorizacao.marcarExameRealizado(dataRealizacao);
                        System.out.println("Exame marcado como realizado com sucesso!");
                    } else {
                        System.out.println(
                                "A data de realização não pode ser mais de 30 dias após a data de solicitação.");
                    }
                } else {
                    System.out.println("A data de realização deve ser posterior à data de solicitação.");
                }
            } catch (ParseException e) {
                System.out.println("Formato de data inválido.");
            }
        } else {
            System.out.println("Autorização de exame não encontrada.");
        }
    }

    public void listarAutorizacoesPaciente() {
        List<AutorizacaoExame> autorizacoesPaciente = autorizacoes.stream()
                .filter(a -> a.getPaciente().equals(userAtual))
                .sorted(Comparator.comparing(AutorizacaoExame::getDataCadastro))
                .collect(Collectors.toList());

        if (!autorizacoesPaciente.isEmpty()) {
            System.out.println("Suas autorizações de exame:");

            for (AutorizacaoExame autorizacao : autorizacoesPaciente) {
                System.out.println("Código: " + autorizacao.getCodigo());
                System.out.println("Data de Cadastro: " + autorizacao.getDataCadastro());
                System.out.println("Tipo de Exame: " + autorizacao.getTipoExame().getNome());
                System.out.println("Data de Realização: "
                        + (autorizacao.getDataRealizacao() != null ? autorizacao.getDataRealizacao()
                                : "Não realizado"));
                System.out.println("-----------------------------------");
            }
        } else {
            System.out.println("Você não possui autorizações de exame.");
        }

        telaPaciente();
    }

    public void listarAutorizacoesMedico() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o filtro (nome do paciente ou tipo de exame) para listar as autorizações:");
        String filtro = scanner.nextLine();

        List<AutorizacaoExame> autorizacoesFiltradas = autorizacoes.stream()
                .filter(autorizacao -> {
                    String pacienteNome = autorizacao.getPaciente().getNome();
                    String tipoExameNome = autorizacao.getTipoExame().getNome();
                    return pacienteNome.equalsIgnoreCase(filtro) || tipoExameNome.equalsIgnoreCase(filtro);
                })
                .collect(Collectors.toList());

        if (!autorizacoesFiltradas.isEmpty()) {
            System.out.println("Autorizações encontradas:");
            for (AutorizacaoExame autorizacao : autorizacoesFiltradas) {
                System.out.println("Código da Autorização: " + autorizacao.getCodigo());
                System.out.println("Data de Cadastro: " + autorizacao.getDataCadastro());
                System.out.println("Paciente: " + autorizacao.getPaciente().getNome());
                System.out.println("Tipo de Exame: " + autorizacao.getTipoExame().getNome());
                System.out.println("Data de Realização: "
                        + (autorizacao.getDataRealizacao() != null ? autorizacao.getDataRealizacao()
                                : "Não realizada"));
                System.out.println("-------------------");
            }
        } else {
            System.out.println("Nenhuma autorização encontrada com o filtro fornecido.");
        }

        telaMedico();
    }

    public void telaCriarNovoUsuario() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome do novo usuário: ");
        String nomeNovo = sc.nextLine();
        System.out.println("Digite o tipo do novo usuário (1 para Admin, 2 para Paciente, 3 para Médico): ");
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
                System.out.println("Tipo de usuário inválido");
                telaAdm();
        }
        users.add(nUsuario);
        System.out.println("Novo usuário adicionado com sucesso!");
        telaAdm();
    }

    public void telaBuscarPaciente() {
        Scanner scP = new Scanner(System.in);
        System.out.println("Digite o nome do paciente que deseja buscar: ");
        String nomePaciente = scP.nextLine();
        List<Usuario> pacientesEncontrados = users.stream()
                .filter(usuario -> usuario.getTipo() == 2 && usuario.getNome().equalsIgnoreCase(nomePaciente))
                .collect(Collectors.toList());
        if (!pacientesEncontrados.isEmpty()) {
            for (Usuario paciente : pacientesEncontrados) {
                System.out.println("Paciente encontrado: " + paciente.getNome());
            }
        } else {
            System.out.println("Paciente não encontrado.");
        }
        telaAdm();
    }

    public void telaBuscarMedico() {
        Scanner scM = new Scanner(System.in);
        System.out.println("Digite o nome do médico que deseja buscar: ");
        String nomeMedico = scM.nextLine();
        List<Usuario> medicosEncontrados = users.stream()
                .filter(usuario -> usuario.getTipo() == 3 && usuario.getNome().equalsIgnoreCase(nomeMedico))
                .collect(Collectors.toList());
        if (!medicosEncontrados.isEmpty()) {
            for (Usuario medico : medicosEncontrados) {
                System.out.println("Médico encontrado: " + medico.getNome());
            }
        } else {
            System.out.println("Médico não encontrado.");
        }
        telaAdm();
    }

    public void telaCriarAutorizacao() {
        System.out.println("Digite o nome do paciente:");
        Scanner scanner = new Scanner(System.in);
        String nomePaciente = scanner.nextLine();

        // Verifique se o paciente existe
        Paciente pacienteEncontrado = null;
        for (Usuario usuario : users) {
            if (usuario instanceof Paciente && usuario.getNome().equalsIgnoreCase(nomePaciente)) {
                pacienteEncontrado = (Paciente) usuario;
                break;
            }
        }

        if (pacienteEncontrado != null) {
            System.out.println("Escolha um tipo de exame:");
            for (int i = 0; i < exames.size(); i++) {
                System.out.println(i + 1 + ". " + exames.get(i).getNome());
            }

            Scanner escolha = new Scanner(System.in);
            int escolhaExame = escolha.nextInt();

            if (escolhaExame >= 1 && escolhaExame <= exames.size()) {
                Exame exameEscolhido = exames.get(escolhaExame - 1);

                // Solicite a data de solicitação da autorização
                System.out.println("Digite a data de solicitação (formato: dd/MM/yyyy):");
                String dataSolicitacaoStr = escolha.next();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date dataSolicitacao = sdf.parse(dataSolicitacaoStr);

                    // Crie uma nova autorização
                    AutorizacaoExame novaAutorizacao = new AutorizacaoExame((Medico) userAtual, pacienteEncontrado,
                            exameEscolhido, dataSolicitacao);

                    // Adicione a nova autorização à lista de autorizações
                    autorizacoes.add(novaAutorizacao);

                    System.out.println("Nova autorização de exame criada com sucesso!");
                } catch (ParseException e) {
                    System.out.println("Data de solicitação inválida.");
                }
            } else {
                System.out.println("Escolha de exame inválida.");
            }
        } else {
            System.out.println("Paciente não encontrado.");
        }
    }

    public void verEstatisticasGerais() {
        int numMedicos = (int) users.stream().filter(usuario -> usuario.getTipo() == 3).count();
        int numPacientes = (int) users.stream().filter(usuario -> usuario.getTipo() == 2).count();
        int numAutorizacoesEmitidas = autorizacoes.size();
        long numAutorizacoesRealizadas = autorizacoes.stream()
                .filter(autorizacao -> autorizacao.getDataRealizacao() != null).count();
        double perncentualAutorizacoesRealizadas = (double) numAutorizacoesRealizadas / numAutorizacoesEmitidas * 100;

        System.out.println("Estatísticas: ");
        System.out.println("Número de Médicos: " + numMedicos);
        System.out.println("Número de Pacientes: " + numPacientes);
        System.out.println("Número de Autorizações Emitidas: " + numAutorizacoesEmitidas);
        System.out.println("Percentual de Autorizações Realizadas: " + perncentualAutorizacoesRealizadas + "%");
        telaAdm();
    }

    public void carregaDadosIniciais(){
        //carregar dados inciais de usuários, exames e demais informações necessárias
        users.add(new Usuario(1,"Maria", 1));
        users.add(new Usuario(2,"João", 2));
        users.add(new Usuario(3,"Pedro", 2));
        users.add(new Usuario(4,"Flávia", 3));
        users.add(new Usuario(5,"Paulo", 2));

        exames.add(new Exame("Raio-X"));
        exames.add(new Exame("Tomografia"));
        exames.add(new Exame("Ressonância Magnética"));
        exames.add(new Exame("Hemograma"));
        exames.add(new Exame("Ultrassonografia"));
        exames.add(new Exame("Eletrocardiograma"));
        exames.add(new Exame("Colonoscopia"));
        exames.add(new Exame("Endoscopia"));
        exames.add(new Exame("Ecocardiograma"));
        exames.add(new Exame("Densitometria Óssea"));

    }

}
