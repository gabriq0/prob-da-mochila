import java.util.Arrays;

public class BruteForce{
    public static int brute(int i, int capacidade, int[] valores, int[] pesos) {
        int n = valores.length;
        if (i == n || capacidade == 0) return 0;

        int i_peso_atual = pesos[i];
        int i_valor_atual = valores[i];

        // 1. se o peso do item for maior que a capacidade total, não coloca na mochila. ou seja, ignora/pula.
        if (i_peso_atual > capacidade) return brute(i+1, capacidade, valores, pesos);
        else {

            // opção 1: não pega o item, mesmo cálculo de cima
            int valor_sem_item = brute(i+1, capacidade, valores, pesos);
            // opção 2: pega o item, o cálculo é: peso do item - capacidade total + valor do item atual.
            int valor_com_item = i_valor_atual + brute(i+1, capacidade - i_peso_atual, valores, pesos);
        
            // vai calcular qual dessas duas opções é a que o maior (melhor) valor.
            return Math.max(valor_sem_item, valor_com_item);
        }
    }

    public static void main(String[] args) {
        int n = 20;
        int capacidade = 500;

        int[] valores = new int[n];
        int[] pesos = new int[n];
        java.util.Random rand = new java.util.Random(40);

        for (int i = 0; i < n; i++) {
            pesos[i] = rand.nextInt(100) + 1; 
            valores[i] = rand.nextInt(500) + 10;
        }

        long startTime = System.nanoTime();
        int maxValor = brute(0, capacidade, valores, pesos);
        long endTime = System.nanoTime();
        
        System.out.println("Capacidade da Mochila: " + capacidade);
        System.out.println("Pesos dos Itens:   " + Arrays.toString(pesos));
        System.out.println("Valores dos Itens: " + Arrays.toString(valores));
        System.out.println("-------------------------------------------------------");
        System.out.println("Valor Máximo (Força Bruta): " + maxValor);
        System.out.println("-------------------------------------------------------");
        System.out.printf("Tempo de Execução: %.6f ms%n", (endTime - startTime) / 1_000_000.0);
    }
}