import java.util.Date;

public class AutorizacaoExame {
    private static int codigoSequencial = 1;
    private int codigo;
    private Date dataCadastro;
    private Medico medicoSolicitante;
    private Paciente paciente;
    private Exame tipoExame;
    private Date dataRealizacao;

    public AutorizacaoExame(Medico medicoSolicitante, Paciente paciente, Exame tipoExame) {
        this.codigo = codigoSequencial++;
        this.dataCadastro = new Date();
        this.medicoSolicitante = medicoSolicitante;
        this.paciente = paciente;
        this.tipoExame = tipoExame;
    }

    public int getCodigo() {
        return codigo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public Medico getMedicoSolicitante() {
        return medicoSolicitante;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Exame getTipoExame() {
        return tipoExame;
    }

    public Date getDataRealizacao() {
        return dataRealizacao;
    }

    public void marcarExameRealizado(Date dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }
}
