import java.util.Date;

public class Exame {
    private int exameID;

    private String nomeExame;

    private Date dataExame;

    

    public Exame(int exameID, String nomeExame, Date dataExame) {
        this.exameID = exameID;
        this.nomeExame = nomeExame;
        this.dataExame = dataExame;
    }

    public Date getDataExame() {
        return dataExame;
    }

    public void setDataExame(Date dataExame) {
        this.dataExame = dataExame;
    }

    public int getExameID() {
        return exameID;
    }

    public void setExameID(int exameID) {
        this.exameID = exameID;
    }

    public String getNomeExame() {
        return nomeExame;
    }

    public void setNomeExame(String nomeExame) {
        this.nomeExame = nomeExame;
    }

}
