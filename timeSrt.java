import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class timeSrt {
    public static void main(String[] args)throws IOException {
        try {
            String newLine = "";
            System.out.println("Hi there!\nThis is a program to add and substract seconds in your srt file.\nPlease put your srt file path in the next prompt or just the name file if is in the same directory as this project:");
            Scanner scanner = new Scanner(System.in);
            String nameFile = scanner.nextLine();
            nameFile = nameFile.contains(".srt") ? nameFile.replace(".srt", ""):nameFile; 
            File myObj = new File(nameFile+".srt");
            Scanner myReader = new Scanner(myObj);
            System.out.println("Do you want add or substract seconds?");
            String operationInput = scanner.nextLine();
            boolean operation = (operationInput=="add") ? false:true;
            System.out.println("How many seconds do you want add?");
            int inputSeconds =  Integer.parseInt(scanner.nextLine());
            FileWriter fw = new FileWriter(nameFile+"Trasnformed.srt");
            System.out.println(inputSeconds);
            scanner.close();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.contains("-->")) {
                    newLine = customTime(data,inputSeconds,operation);
                    data = data.replace(data, newLine);
                    fw.write(data+"\n");
                } else {
                    fw.write(data+"\n");
                }
            }
            myReader.close();
            fw.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public static String customTime(String data, int inputSeconds,boolean operation){
        String primero = "";
        String segundo = "";

        primero = timeCalc(data.substring(0,8),inputSeconds, operation);
        segundo = timeCalc(data.substring(17,25),inputSeconds, operation);

        return primero+" --> "+segundo;
    }

    public static String timeCalc(String data, int inputSeconds,boolean operation){

        int horas = Integer.parseInt(data.substring(0, 2));
        int minutos = Integer.parseInt(data.substring(3, 5));
        int segundos = Integer.parseInt(data.substring(6, 8));

        if(operation) return addSeconds(horas, minutos, segundos, inputSeconds);
        else return "";        
    }

    public static String addSeconds(int horas, int minutos, int segundos,int inputSeconds){

        if(segundos<60){
            segundos = segundos+inputSeconds;
            if(segundos >= 60){
                minutos++;
                segundos=segundos-60;
                if(minutos >= 60){
                    horas++;
                    minutos=minutos-60;
                }
            }
        }

        String nhoras = ((horas<10) ? "0":"")+Integer.toString(horas);
        String nMinutos = ((minutos<10) ? "0":"")+Integer.toString(minutos);
        String nSegundos = ((segundos<10) ? "0":"")+Integer.toString(segundos)+",000";

        return nhoras+":"+nMinutos+":"+nSegundos;
    }

}