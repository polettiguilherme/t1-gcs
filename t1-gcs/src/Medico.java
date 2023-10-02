import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

public class Medico extends Usuario {
    private List<AutorizacaoExame> autorizacoes;

    public Medico(int identificador, String nome) {
        super(identificador, nome, 3);
        autorizacoes = new ArrayList<>();
    }

    public void addAutorizacao(Paciente paciente, Exame exame, Date dataRealizacao) {
        AutorizacaoExame autorizacao = new AutorizacaoExame(this, paciente, exame, dataRealizacao);
        autorizacoes.add(autorizacao);
    }

    public List<AutorizacaoExame> listarAutoPorPaciente(Paciente paciente) {
        List<AutorizacaoExame> autorizacoesPaciente = autorizacoes.stream()
                .filter(autorizacao -> autorizacao.getPaciente().equals(paciente))
                .collect(Collectors.toList());
    
        for (AutorizacaoExame autorizacao : autorizacoesPaciente) {
            System.out.println(autorizacao);
        }
    
        return autorizacoesPaciente;
    }

    public List<AutorizacaoExame> listarAutoPorTipoExame(String tipoExame) {
        List<AutorizacaoExame> autorizacoesPorExame = autorizacoes.stream()
                .filter(autorizacao -> autorizacao.getTipoExame().equals(tipoExame))
                .collect(Collectors.toList());
    
        for (AutorizacaoExame autorizacao : autorizacoesPorExame) {
            System.out.println(autorizacao);
        }
    
        return autorizacoesPorExame;
    }
}
