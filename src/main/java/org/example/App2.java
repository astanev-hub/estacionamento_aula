package org.example;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * <b>Entrada:</b> Placa do carro
 * <ul>
 *   <li>Verifica se já houve entrada </li>
 *   <ul>
 *       <li>
 *           Se não entrou 'Entrada do veículo de placa: <b><placa></b>'
 *           <ul><li>Verificar se existe vaga disponível</li></ul>
 *       </li>
 *   <li>Se já entrou 'Saída do veículo de placa  <b><placa></b> . Tempo
 *       no estabelecimento . Valor a ser cobrado:'
 *   </li>
 *   </ul>
 * <p>
 *   Tempo a ser Cobrado:
 *
 *   <ul>
 *
 *       <li>0 - 5 minutos - 0 reais</li>
 *       <li>5 - 60 minutos - 4 reais</li>
 *       <li>Acima de 60 - cobrado o valor de 6 reais por hora adicional</li>
 *   </ul>
 *
 *   <em>Verificar vaga disponível (V2).</em>
 *   <em>Adicionar número de vagas  (V2).</em>
 * </ul>
 */
public class App2 {
    public static final Integer NUMERO_VAGAS = 5;


    public static void main(String[] args) {

        try (Scanner ler = new Scanner(System.in)) {
            StringBuilder saidas = new StringBuilder();
            List<String> placas = new ArrayList<>();
            List<Long> tempos = new ArrayList<>();
            int option = 0;
            do {
                System.out.println("Options \n\t 1 - Adicionar número da placa \n\t 0 - Sair do programa");
                option = ler.nextInt();

                if (option != 0) {
                    // Entrada: Placa do carro
                    // array das placas
                    // array das entradas

                    System.out.println("Informe o número de placa");
                    String placaVeiculo = ler.next();
                    // Verifica se já houve entrada
                    int indice = -1;
                    if ((indice = verificaPlaca(placas, placaVeiculo)) >= 0) {
                        //placa existe
                        saidas.append(liberarVaga(placas, tempos, indice));
                        // Se já entrou 'Saída do veículo de placa <placa> . Tempo
                        //no estabelecimento . Valor a ser cobrado:'
                    } else {
                        // placa não existe
                        //Se não houve entrada
                        //Verificar vaga disponível (V1)
                        if (placas.size() < NUMERO_VAGAS) {
                            alocaVaga(placas, tempos, placaVeiculo);
                        } else {
                            System.err.println("Não tem mais vaga");
                        }

                    }
                    imprimirRelatorio(placas, tempos, saidas);
                }
            } while (option != 0);


        }


    }

    private static void imprimirRelatorio(List<String> placas, List<Long> tempos, StringBuilder saida) {
        System.out.println("Veiculos no patio");
        GregorianCalendar gc = new GregorianCalendar();
        for (int i = 0; i < placas.size(); i++) {

            gc.setTimeInMillis(tempos.get(i));
            System.out.printf("Placa %s \t Hora de entrada: %tD %tl:%tM %n ", placas.get(i), gc, gc, gc);

        }
        System.out.printf("%n Relatório de veículos %n %s ", saida.toString());

    }

    private static String liberarVaga(List<String> placas, List<Long> tempos, int indice) {
        Long tempoInicial = tempos.get(indice);
        Long tempoFinal = System.currentTimeMillis();
        Long tempoMinutos = TimeUnit.MILLISECONDS
                .toMinutes(tempoFinal - tempoInicial);

        double valorCobrado = getValorCobrado(tempoMinutos);

        // Se não entrou 'Entrada do veículo de placa: <placa>'
        System.out.printf("Saída do veículo de placa %s . " +
                "Tempo no estabelecimento . Valor a ser cobrado: %.2f %n", placas.get(indice), valorCobrado);


        String retorno = String.format("Placa %s - tempo permanencia: %d minutos - valor cobrado: %.2f %n",
                placas.get(indice), tempoMinutos, valorCobrado);
        placas.remove(indice);
        tempos.remove(indice);
        return retorno;
    }

    /***
     * <ul>
     * <li>0 - 5 minutos - 0 reais</li>
     * <li>5 - 60 minutos - 4 reais</li>
     * <li>Acima de 60 - cobrado o valor de 6 reais por hora adicional</li>
     * </ul>
     *
     * @param tempoMinutos
     * @return
     */
    private static double getValorCobrado(Long tempoMinutos) {

        double horas = Math.ceil(tempoMinutos / 60d);

        double valorCobrado = (tempoMinutos > 5) ? 4.0d : 0.0d;

        if (horas > 1) {
            valorCobrado += (horas - 1) * 6.0d;
        }


        return valorCobrado;
    }

    private static void alocaVaga(List<String> placas, List<Long> tempos, String placaVeiculo) {
        placas.add(placaVeiculo);
        tempos.add(System.currentTimeMillis());
        System.out.printf("Entrada do veículo de placa: %s %n", placaVeiculo);
    }

    private static int verificaPlaca(List<String> placas, String placaVeiculo) {
        for (int i = 0; i < placas.size(); i++) {
            String placa = placas.get(i);
            if (null != placa && placa.equals(placaVeiculo)) {
                return i;
            }
        }
        return -1;
    }
}