
import java.util.Date;
import java.util.List;


public class Paciente extends Usuario {
    private List<Exame> exames;

    public Paciente(int identificador, String nome) {
        super(identificador, nome, 2);

    }

    public List<Exame> getExames() {
        return exames;
    }

    public void setExames(List<Exame> exames) {
        this.exames = exames;
    }

    public void marcarExame(int id, Date dataExame) {
        Date data = new Date();
        if(dataExame.compareTo(data)<0){
            System.out.println("A data do exame invalida. ");
            return;
        }

        switch (id) {
            case 1:
                Exame hemograma = new Exame(1, "Hemograma", dataExame);
                exames.add(hemograma);

                break;
            case 2:
                Exame urina = new Exame(2, "Urina", dataExame);
                exames.add(urina);

                break;
            case 3:
                Exame fezes = new Exame(3, "Fezes", dataExame);
                exames.add(fezes);

                break;
            case 4:
                Exame sangue = new Exame(4, "Sangue", dataExame);
                exames.add(sangue);

                break;
            case 5:
                Exame acidoUrico = new Exame(5, "Acido Úrico", dataExame);
                exames.add(acidoUrico);

                break;
            case 6:
                Exame colesterol = new Exame(6, "Colesterol", dataExame);
                exames.add(colesterol);

                break;
            case 7:
                Exame glicerina = new Exame(7, "Glicerina", dataExame);
                exames.add(glicerina);

                break;
            case 8:
                Exame tsh = new Exame(8, "TSH", dataExame);
                exames.add(tsh);

                break;
            case 9:
                Exame creatinina = new Exame(9, "Creatinina", dataExame);
                exames.add(creatinina);

                break;
            case 10:
                Exame papanicolau = new Exame(10, "Papanicolau", dataExame);
                exames.add(papanicolau);

                break;
        }

    }

}
