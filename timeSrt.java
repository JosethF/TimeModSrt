import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class timeSrt {
    public static void main(String[] args) {
        try {
            File myObj = new File("Outrage.srt");
            Scanner myReader = new Scanner(myObj);
            String newLine = "";
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.contains("-->")) {
                    newLine = customTime(data);
                    System.out.println(newLine);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public static String customTime(String data){
        String primero = "";
        String segundo = "";

        primero = timeCalc(data.substring(0,8));
        segundo = timeCalc(data.substring(17,25));

        return primero+" --> "+segundo;
    }

    public static String timeCalc(String data){

        int horas = Integer.parseInt(data.substring(0, 2));
        int minutos = Integer.parseInt(data.substring(3, 5));
        int segundos = Integer.parseInt(data.substring(6, 8));

        if(segundos+1<=60){ 
            segundos++;
            if(segundos == 60){ 
                minutos++;
                segundos=0;
                if(minutos == 60){
                    horas++;
                    minutos=0;
                }
            }
        }

        String nhoras = ((horas<10) ? "0":"")+Integer.toString(horas);
        String nMinutos = ((minutos<10) ? "0":"")+Integer.toString(minutos);
        String nSegundos = ((segundos<10) ? "0":"")+Integer.toString(segundos);

        data = nhoras+":"+nMinutos+":"+nSegundos;
        return data;
    }
}