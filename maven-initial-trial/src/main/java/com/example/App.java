package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App {
    public static void main(String[] args) throws IOException {
        String path = "src/input/sampleData.csv";
        String address = "src/output/Processed_File_";
        
        Date day=new Date();
        SimpleDateFormat dayformat =new SimpleDateFormat("dd-mm-yyy");
        address=address+dayformat.format(day)+".csv";
        
        BufferedWriter writer =new BufferedWriter(new FileWriter(address));
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            // String list = "";
            String line = "";
            String[] arr = new String[10];
            String gid="";
            line=reader.readLine();
            String uid="";
            arr = line.split(",");
            for(String s: arr){
                if(s.equalsIgnoreCase("contractor")){
                    writer.write("Display Name,");
                }else{
                    writer.write(s+",");
                }
            }
            writer.write("UID,Epoch Date\n");
            int count=1;
            while ((line = reader.readLine()) != null) {
                arr = line.split(",");
                gid=String.format("%05d", Integer.parseInt(arr[0]));
                writer.write(gid+",");
                writer.write(arr[1]+",");
                writer.write(arr[2]+",");
                writer.write(arr[3]+",");
                writer.write(arr[4]+",");
                
                dayformat=new SimpleDateFormat("MM-DD-YYYY");
                String doj =arr[5].replace(".", "-");
                day=dayformat.parse(doj);
                writer.write(day.toString().substring(4, 11)+day.toString().substring(24)+",");
                if(arr[6].equals("TRUE")){
                    writer.write(arr[1]+" "+arr[2]+" (Contractor),");
                }
                else{
                    writer.write(arr[1]+" "+arr[2]+",");
                }
                if(Integer.parseInt(arr[7]) == 0){
                    writer.write("Inactive"+",");
                }else{
                    writer.write("Active"+",");
                }
                writer.write(arr[8]+",");
                writer.write(arr[9]+",");
                String userid= arr[1].toLowerCase().charAt(0)+arr[2].substring(0, 4).toLowerCase();
                if(uid.equalsIgnoreCase(userid)){
                    userid=userid.substring(0, 3)+count;
                    count++;
                    uid+=userid+",";
                }else{
                    uid+=userid+",";

                }
                writer.write(userid+",");
                dayformat=new SimpleDateFormat("dd-mm-yyyy");
                day=dayformat.parse(doj);
                doj=String.format("%ts", day);
                writer.write(doj+",\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Issue with Buffer");
        }
    }
}
