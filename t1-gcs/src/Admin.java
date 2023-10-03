import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Admin extends Usuario {
    private List<Usuario> usuarios;
    private List<AutorizacaoExame> autorizacoes;

    public Admin(int identificador, String nome){
        super(identificador, nome, 1);
        usuarios = new ArrayList<>();
        autorizacoes = new ArrayList<>();
    }

    public void addNovoUsuario(int identificador, String nome, int tipo) {
        Usuario nUsuario = new Usuario(identificador, nome, tipo);
        usuarios.add(nUsuario);
        System.out.println("Novo usuario adicionado com sucesso! ");
    }

    public void buscarPaciente(String nome) {
        List<Usuario> pacientesEncontrados = usuarios.stream()
                .filter(usuario -> usuario.getTipo() == 2 && usuario.getNome().equalsIgnoreCase(nome))
                .collect(Collectors.toList());
        if(!pacientesEncontrados.isEmpty()) {
            for (Usuario paciente : pacientesEncontrados) {
                System.out.println(paciente);
            }
        } else {
            System.out.println("Nenhum paciente encontrado com o nome informado. ");
        }
    }

    public void buscarMedico(String nome) {
        List<Usuario> medicosEncontrados = usuarios.stream()
                .filter(usuario -> usuario.getTipo() == 3 && usuario.getNome().equalsIgnoreCase(nome))
                .collect(Collectors.toList());
        if (!medicosEncontrados.isEmpty()) {
            for (Usuario medico : medicosEncontrados) {
                System.out.println(medico);
            }
        } else {
            System.out.println("Nenhum medico encontrado com o nome informado.");
        }
    }
    
    public void verEstatisticasGerais(){
        int numMedicos = (int) usuarios.stream().filter(usuario -> usuario.getTipo() == 3).count();
        int numPacientes = (int) usuarios.stream().filter(usuario -> usuario.getTipo() == 2).count(); 
        int numAutorizacoesEmitidas = autorizacoes.size();
        long numAutorizacoesRealizadas = autorizacoes.stream().filter(autorizacao -> autorizacao.getDataRealizacao() != null).count(); //Erro getDataRealizacao()
        double perncentualAutorizacoesRealizadas = (double) numAutorizacoesRealizadas / numAutorizacoesEmitidas * 100;
    
        System.out.println("Numero de Medicos: " + numMedicos);
        System.out.println("Numero de Pacientes: " + numPacientes);
        System.out.println("Numero de Autorizacoes Emitidas: " + numAutorizacoesEmitidas);
        System.out.println("Percentual de Autorizacoes Realizadas: " + perncentualAutorizacoesRealizadas + "%");
    }
}
