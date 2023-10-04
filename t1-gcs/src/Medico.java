import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Medico extends Usuario {
    private List<AutorizacaoExame> autorizacoes;

    public Medico(int identificador, String nome) {
        super(identificador, nome, 3);
        autorizacoes = new ArrayList<>();
    }

    public void incluirNovaAutorizacaoExame(Paciente paciente, Exame exame) {
        AutorizacaoExame autorizacao = new AutorizacaoExame(this, paciente, exame);
        autorizacoes.add(autorizacao);
        System.out.println("Nova autorizacao de exame adicionada com sucesso!");
    }

    public List<AutorizacaoExame> listarAutorizacoes(String filtro) {
        return autorizacoes.stream()
               .filter(autorizacao -> autorizacao.getPaciente().getNome().equalsIgnoreCase(filtro) ||
                       autorizacao.getTipoExame().getNomeExame().equalsIgnoreCase(filtro))
               .collect(Collectors.toList());
    }

}
