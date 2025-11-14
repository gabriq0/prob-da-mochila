import java.util.ArrayList;
import java.util.List;

class Item {
    int peso;
    int valor;
    double razao;

    public Item(int peso, int valor) {
        this.peso = peso;
        this.valor = valor;
        this.razao = (double) valor / peso;
    }

    @Override
    public String toString() {
        return "Item [V: " + this.valor + " / P: " + this.peso + "]";
    }
}

public class Gulosa {
    private static int encontra_melhor_i(List<Item> itens) {
        if (itens.isEmpty()) { return -1; }

        int indiceMelhor = 0;
        double melhorRazao = itens.get(0).razao;

        //esse loop compara a razão entre itens, o item com a melhor razão vai retornar seu indice.
        for (int i = 1; i < itens.size(); i++) {
            if (itens.get(i).razao > melhorRazao) {
                melhorRazao = itens.get(i).razao;
                indiceMelhor = i;
            }
        }
        return indiceMelhor;
    }

    public static double resolve01(List<Item> itensDisponiveis, int capacidade) {
        if (itensDisponiveis.isEmpty() || capacidade <= 0) return 0;

        int indiceMelhor = encontra_melhor_i(itensDisponiveis);
        Item melhorItem = itensDisponiveis.get(indiceMelhor);

        List<Item> proximosItens = new ArrayList<>();
        for (int i = 0; i < itensDisponiveis.size(); i++) {
            if (i != indiceMelhor) {
                proximosItens.add(itensDisponiveis.get(i));
            }
        }
        //essa lista de próximos itens, é uma lista dos itens restantes que podem ir à mochila.

        //1. item cabe, ou seja, vamos pegá-lo.
        if (melhorItem.peso <= capacidade) return melhorItem.valor + resolve01(proximosItens, capacidade - melhorItem.peso);
        //2. item não cabe, ou seja, vamos ignorá-lo.
        else return resolve01(proximosItens, capacidade);
    }

    public static double resolveF(List<Item> itensDisponiveis, int capacidade) {
        if (itensDisponiveis.isEmpty() || capacidade <= 0) return 0;

        int indiceMelhor = encontra_melhor_i(itensDisponiveis);
        Item melhorItem = itensDisponiveis.get(indiceMelhor);

        List<Item> proximosItens = new ArrayList<>();
        for (int i = 0; i < itensDisponiveis.size(); i++) {
            if (i != indiceMelhor) {
                proximosItens.add(itensDisponiveis.get(i));
            }
        }

        // 1. item cabe, ou seja, vamos pegá-lo.
        if (melhorItem.peso <= capacidade) return melhorItem.valor + resolveF(proximosItens, capacidade - melhorItem.peso);
        // 2. item não cabe. PORÉM, nesse caso a gente pode pegar uma fração do item.
        else {
            double fracao = (double) capacidade / melhorItem.peso; //ou seja, quanto desse item pode ser fracionado até a mochila encher.
            double valorFracao = fracao * melhorItem.valor;

            return valorFracao;
        }
    }

    public static void main(String[] args) {
        List<Item> itens = new ArrayList<>();
        itens.add(new Item(10, 60));
        itens.add(new Item(20, 100)); 
        itens.add(new Item(30, 120)); 

        int capacidade = 50;
        
        long startTime = System.nanoTime();
        double valorFracionaria = resolveF(new ArrayList<>(itens), capacidade);
        long endTime = System.nanoTime();

        System.out.println("Capacidade da Mochila: " + capacidade);
        System.out.println(itens.toString());
        System.out.println("-------------------------------------------------------");
        System.out.printf("Valor Ótimo (fracionária): %.1f\n", valorFracionaria);
        System.out.printf("Tempo de Execução: %.6f ms%n", (endTime - startTime) / 1_000_000.0);

        long startTime2 = System.nanoTime();
        double valor01 = resolve01(new ArrayList<>(itens), capacidade);
        long endTime2 = System.nanoTime();

        System.out.println("-------------------------------------------------------");
        System.out.printf("Valor Ótimo (0/1): %.1f\n", valor01);
        System.out.printf("Tempo de Execução: %.6f ms%n", (endTime2 - startTime2) / 1_000_000.0);
    }
}