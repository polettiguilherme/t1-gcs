public class Usuario {


    private int identificador;
    private String nome;

    //Tipo 1: Adm
    //Tipo 2: Paciente
    //Tipo 3: Médico
    private int tipo;

    public Usuario(int identificador, String nome, int tipo ){
        this.identificador = identificador;
        this.nome = nome;
        this.tipo = tipo;
    }

    public int getIdentificador() {
        return identificador;
    }

    public String getNome() {
        return nome;
    }

    public int getTipo() {
        return tipo;
    }

}

