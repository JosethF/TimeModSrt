import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class timeSrt {
    public static void main(String[] args)throws IOException {
        try {
            String newLine = "";
            System.out.println("Hi there!\nThis is a program to add and substract milliseconds in your srt file.\nPlease put your srt file path in the next prompt or just the name file if is in the same directory as this project:");
            Scanner scanner = new Scanner(System.in);
            String nameFile = scanner.nextLine();
            nameFile = nameFile.contains(".srt") ? nameFile.replace(".srt", ""):nameFile; 
            File myObj = new File(nameFile+".srt");
            Scanner myReader = new Scanner(myObj);
            System.out.println("Do you want add or substract milliseconds?");
            String operationInput = scanner.nextLine();
            boolean operation = (operationInput.equals("add")) ? true:false;
            System.out.println("How many milliseconds?");
            int inputSeconds =  Integer.parseInt(scanner.nextLine());
            FileWriter fw = new FileWriter(nameFile+"Trasnformed.srt");
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
            e.printStackTrace();
        }
    }

    public static String customTime(String data, int inputMilliseconds,boolean operation){
        
        String primero = timeCalc(data.substring(0,12),inputMilliseconds, operation);
        String segundo = timeCalc(data.substring(17,29),inputMilliseconds, operation);

        return primero+" --> "+segundo;
    }

    public static String timeCalc(String data, int inputSeconds,boolean operation){
        int horas = Integer.parseInt(data.substring(0, 2));
        int minutos = Integer.parseInt(data.substring(3, 5));
        int segundos = Integer.parseInt(data.substring(6, 8));
        int milisegundos = Integer.parseInt(data.substring(9, 12));

        if(operation) return addSeconds(horas, minutos, segundos, milisegundos, inputSeconds);
        else return substractSeconds(horas, minutos, segundos, milisegundos, inputSeconds);
    }

    public static String addSeconds(int horas, int minutos, int segundos, int milisegundos, int inputMilisegundos){

        if(milisegundos<1000){
            milisegundos = milisegundos + inputMilisegundos;
            if(milisegundos>=1000){
                segundos++;
                milisegundos = milisegundos-1000;
                if(segundos>=60){
                    minutos++;
                    minutos=minutos-60;
                } if(minutos >= 60){
                    horas++;
                    minutos=minutos-60;
                }
            }
        }
        return returnTime(horas, minutos, segundos,milisegundos);
    }

    public static String substractSeconds(int horas, int minutos, int segundos, int milisegundos, int inputMilliseconds){
        
        milisegundos = Math.abs(milisegundos-inputMilliseconds);
        if(milisegundos<1){
            segundos--;
            milisegundos = 1000-milisegundos;
            if(segundos<=0){
                minutos--;
                segundos=60-segundos;
                if(minutos<=0){
                    horas--;
                    minutos=60-minutos;
                }
            }
        }
        if(segundos<1){
            minutos--;
            segundos = 60-segundos;
            if(minutos<=0){
                horas--;
                minutos=60-minutos;
            }
        }
        return returnTime(horas, minutos, segundos,milisegundos);
    }

    public static String returnTime(int horas, int minutos, int segundos,int milisegundos){

        String nhoras = ((horas<10) ? "0":"")+Integer.toString(horas);
        String nMinutos = ((minutos<10) ? "0":"")+Integer.toString(minutos);
        String nSegundos = ((segundos<10) ? "0":"")+Integer.toString(segundos);
        String nMilisegundos = ((milisegundos<100) ? "00":"")+Integer.toString(milisegundos);

        return nhoras+":"+nMinutos+":"+nSegundos+","+nMilisegundos;
    }

}