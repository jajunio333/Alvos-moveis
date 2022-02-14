package components;

public class Municao {
    private  long Id;
    private boolean utilizada;

    public Municao(long id) {
        Id = id;
        this.utilizada = false;
    }

    public long getId() {
        return Id;
    }

    public boolean isUtilizada() {
        return utilizada;
    }

    public void setUtilizada(boolean utilizada) {
        this.utilizada = utilizada;
    }
}
