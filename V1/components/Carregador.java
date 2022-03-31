package components;

public class Carregador {
    private Municao municao;

    public Carregador(Municao municao) {
        this.municao = municao;
    }

    public boolean verificaMunicao(){
        if (municao.isUtilizada())
            return false;
        else
            return true;
    }
    public void retiraMunicao() {
        municao.setUtilizada(true);
    }
}
