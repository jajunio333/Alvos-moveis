package components;

import static java.lang.Math.sqrt;

public class Lancador extends  Thread{
    private Carregador carregador;

    public Lancador(Carregador carregador) {
        this.carregador = carregador;
    }

    public void carregar() throws InterruptedException {
        while (carregador.verificaMunicao()){
            sleep(30);
            carregador.retiraMunicao();
        }
    }
    public int[] preparar(int[] pontoAtualAlvo,int[]  pontoAtualTiro) throws InterruptedException {
        sleep(30);//mira
        return calculoTrajetoria(pontoAtualAlvo, pontoAtualTiro);
    }
    public int[] calculoTrajetoria(int[] pontoAtualAlvo,int[]  pontoAtualTiro) {
        double d = sqrt((pontoAtualTiro[0] - pontoAtualAlvo[0]) ^ 2 + (pontoAtualTiro[1] - pontoAtualAlvo[1]) ^ 2);
        int[] aux = new int[0];
        if (d > 0){//?? sempre vai ser maior q 0
            while(pontoAtualAlvo[1]>pontoAtualTiro[1])
            aux[0] = pontoAtualTiro[0] + 5;
            aux[1] = pontoAtualTiro[1] + 5;
        }
        return aux;
    }//teste
    public boolean atirar(){
        return true;
    }
}
