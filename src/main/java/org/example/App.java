package org.example;

import java.util.GregorianCalendar;
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
 *
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
 *</ul>

 */
public class App 
{
    public static final Integer NUMERO_VAGAS = 5;
    public static Integer vagasDisponiveis=NUMERO_VAGAS;
    public static void main( String[] args )
    {

        try(Scanner ler = new Scanner(System.in)){
            String saidas = "";
            String[] placas = new String[NUMERO_VAGAS];

            Long[] tempos = new Long[NUMERO_VAGAS];
            int option =0;
            do{
                System.out.println("Options \n\t 1 - Adicionar número da placa \n\t 0 - Sair do programa");
                option = ler.nextInt();

                if(option!=0) {
                    // Entrada: Placa do carro
                    // array das placas
                    // array das entradas

                    System.out.println("Informe o número de placa");
                    String placaVeiculo = ler.next();
                    // Verifica se já houve entrada
                    int indice = -1;
                    if ((indice = verificaPlaca(placas, placaVeiculo)) >= 0) {
                        //placa existe
                        saidas += liberarVaga(placas, tempos, indice);
                        // Se já entrou 'Saída do veículo de placa <placa> . Tempo
                        //no estabelecimento . Valor a ser cobrado:'
                    } else {
                        // placa não existe
                        //Se não houve entrada
                        //Verificar vaga disponível (V1)
                        if (vagasDisponiveis > 0) {
                            alocaVaga(placas, tempos, placaVeiculo);
                        } else {
                            System.err.println("Não tem mais vaga");
                        }

                    }
                    imprimirRelatorio(placas, tempos, saidas);
                }
            }while(option != 0);



        }

        // Verificar vaga disponível (V2).
    }

    private static void imprimirRelatorio(String[] placas, Long[] tempos, String saida) {
        System.out.println("Veiculos no patio");
        GregorianCalendar gc = new GregorianCalendar();
        for (int i = 0; i < placas.length; i++) {
            if(null!=placas[i]){
                gc.setTimeInMillis(tempos[i]);
                System.out.printf("Placa %s \t Hora de entrada: %tD %tl:%tM  ", placas[i],gc,gc,gc);
            }
        }
        System.out.printf("Relatório de veículos %n %s ",saida);

    }

    private static String liberarVaga(String[] placas, Long[] tempos, int indice) {
        Long tempoInicial = tempos[indice];
        Long tempoFinal = System.currentTimeMillis();
        Long horas = TimeUnit.MILLISECONDS
                .toHours(tempoFinal-tempoInicial);
        Long tempoMinutos = TimeUnit.MILLISECONDS
                .toMinutes(tempoFinal-tempoInicial);
        // 0 - 5 minutos - 0 reais
        // 5 - 60 minutos - 4 reais
        // Acima de 60 - cobrado o valor de 6 reais por hora adicional

        double valorCobrado = 0.0d;


        if(tempoMinutos>5){
            valorCobrado = 4.0d;
            if(horas>=1) {
                valorCobrado += (horas - 1) * 6.0d;
                if (tempoMinutos % 60 > 0) {
                    valorCobrado += 6.0d;
                }
            }
        }

        // Se não entrou 'Entrada do veículo de placa: <placa>'
        System.out.printf("Saída do veículo de placa %s . " +
                "Tempo no estabelecimento . Valor a ser cobrado: %.2f %n", placas[indice],valorCobrado);

        String retorno = String.format("Placa %s - tempo permanencia: %d minutos - valor cobrado: %.2f %n",
                placas[indice],tempoMinutos,valorCobrado);
        placas[indice] = null;
        tempos[indice] = null;
        vagasDisponiveis++;

        return retorno;
    }

    private static void alocaVaga(String[] placas,Long[] tempos, String placaVeiculo) {
        for (int i = 0; i < placas.length; i++) {
            if(null==placas[i]){
                placas[i] = placaVeiculo;
                tempos[i] = System.currentTimeMillis();
                vagasDisponiveis--;
                System.out.printf("Entrada do veículo de placa: %s %n", placaVeiculo);
                break;
            }

        }
    }

    private static int verificaPlaca(String[] placas, String placaVeiculo) {
        for (int i = 0; i < placas.length; i++) {
            String placa = placas[i];
            if(null!=placa && placa.equals(placaVeiculo)){
                return i;
            }
        }
        return -1;
    }
}