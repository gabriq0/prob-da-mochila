import java.util.Arrays;

public class Dinamica {
    
    static int mochila_prog_dinamica(int i, int cap, int pesos[], int valores[], int tabela[][]){
        int n = valores.length;
        if(i == n || cap == 0) return 0;

        //se não tiver -1, signifca que o elemento já foi preenchido na tabela, então não calcula de novo.
        if(tabela[i][cap] != -1) return tabela[i][cap]; 

        int i_peso_atual = pesos[i];
        int i_valor_atual = valores[i];
        int resultado = 0;

        // 1. se o peso do item for maior que a capacidade total, não coloca na mochila. ou seja, ignora/pula.
        if(i_peso_atual > cap) resultado = mochila_prog_dinamica(i+1, cap, pesos, valores, tabela);
        else {
            
            // opção 1: não pega o item, mesmo cálculo de cima
            int valor_sem_item = mochila_prog_dinamica(i+1, cap, pesos, valores, tabela);
            // opção 2: pega o item, o cálculo é: peso do item - capacidade total + valor do item atual.
            int valor_com_item = i_valor_atual + mochila_prog_dinamica(i+1, cap - i_peso_atual, pesos, valores, tabela);
            
            //vai calcular qual dessas duas opções é a que o maior (melhor) valor.
            resultado = Math.max(valor_sem_item, valor_com_item);
        }

        tabela[i][cap] = resultado; //guarda o resultado na tabela, para evitar de recalcular elementos anteriores.
        return resultado;
    }

    public static void main(String[] args) {
        int n = 20;
        int capacidadeTotal = 500; 

        int[] valores = new int[n];
        int[] pesos = new int[n];
        java.util.Random rand = new java.util.Random(40);

        for (int i = 0; i < n; i++) {
            pesos[i] = rand.nextInt(100) + 1; 
            valores[i] = rand.nextInt(500) + 10;
        }

        int[][] tabela = new int [n + 1][capacidadeTotal + 1];
        //preenche a tabela com -1, para servir como "elemento ainda não preenchido".
        for(int i=0;i<n;i++) Arrays.fill(tabela[i], -1);
        
        long startTime = System.nanoTime();
        int valorOtimo = mochila_prog_dinamica(0, capacidadeTotal, pesos, valores, tabela);
        long endTime = System.nanoTime();
        
        System.out.println("Capacidade da Mochila: " + capacidadeTotal);
        System.out.println("Pesos dos Itens:   " + Arrays.toString(pesos));
        System.out.println("Valores dos Itens: " + Arrays.toString(valores));
        System.out.println("-------------------------------------------------------");
        System.out.println("Valor Máximo (Dinâmica): " + valorOtimo);
        System.out.println("-------------------------------------------------------");
        System.out.printf("Tempo de Execução: %.6f ms%n", (endTime - startTime) / 1_000_000.0);
    }
}
