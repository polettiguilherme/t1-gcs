import java.util.Date;

public class AutorizacaoExame {
    private static int codigoSequencial = 1;
    private int codigo;
    private Date dataCadastro;
    private Usuario medicoSolicitante;
    private Usuario paciente;
    private Exame tipoExame;
    private Date dataRealizacao;

    public AutorizacaoExame(int codigo, Usuario medicoSolicitante, Usuario paciente, Exame tipoExame) {
        this.codigo = codigoSequencial++;
        this.dataCadastro = new Date();
        this.medicoSolicitante = medicoSolicitante;
        this.paciente = paciente;
        this.tipoExame = tipoExame;
    }

    public int getCodigo() {
        return codigo;
    }

    public static int getCodigoSequencial() {
        return codigoSequencial;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public Usuario getMedicoSolicitante() {
        return medicoSolicitante;
    }

    public Usuario getPaciente() {
        return paciente;
    }

    public Exame getTipoExame() {
        return tipoExame;
    }

    public Date getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(Date dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }
}
