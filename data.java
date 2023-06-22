import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.sound.midi.Synthesizer;
import javax.sql.rowset.spi.SyncResolver;

public class data {
    public static void main(String Args[]) throws ParseException {
        List<String> list = new ArrayList<String>();
        String path = "C:/Users/tteli/Desktop/sample_10.csv", line = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block

        }
        String gid[] = new String[list.size()];
        String fname[] = new String[list.size()];
        String lname[] = new String[list.size()];
        char gender[] = new char[list.size()];
        String uid[] = new String[list.size()];
        String status[] = new String[list.size()];
        String pnumber[] = new String[list.size()];
        String Designation[] = new String[list.size()];
        String dispname[] = new String[list.size()];
        String edate[] = new String[list.size()];
        String djoin[] = new String[list.size()];
        // for (String s : list) {
        // System.out.println(s);
        // }

        String sampled[][] = new String[list.size()][10];
        for (int i = 0; i < list.size(); i++) {
            String arr[] = list.get(i).split(",");
            for (int j = 0; j < 10; j++) {
                sampled[i][j] = arr[j];
                // System.out.println(sampled[i][j]);
            }
        }
        gid[0] = sampled[0][0];
        fname[0] = sampled[0][2];
        lname[0] = sampled[0][3];
        gender[0] = 'G';
        uid[0] = "UID";
        status[0] = "Status";
        pnumber[0] = "Phone Number";
        Designation[0] = "Designation";
        dispname[0] = "Display Name";
        edate[0] = "Epoch Date";
        djoin[0] = "Date of Joining";
        /*
         * for (int i = 0; i <= 10; i++) {
         * if (i == 4 || i == 6) {
         * continue;
         * } else if (i == 5) {
         * System.out.print("UID" + "   | ");
         * } else if (i == 10) {
         * System.out.print("Display Name" + "   | ");
         * System.out.print("Epoch Date" + "   | ");
         * } else {
         * System.out.print(sampled[0][i] + "   | ");
         * }
         * 
         * }
         */
        System.out.println();
        String a = "Active";
        String ina = "Inactive";
        // String uid = "";
        String temp = "", dname = "";
        Set<String> dupe = new HashSet<String>();
        int idcount = 1;

        for (int i = 1; i < sampled.length; i++) {

            gid[i] = String.format("%05d", Integer.parseInt(sampled[i][0]));
            fname[i] = sampled[i][1];
            lname[i] = sampled[i][2];
            // System.out.print(" " + sampled[i][1] + " " + sampled[i][2]);
            gender[i] = sampled[i][3].charAt(0);
            uid[i] = sampled[i][1].charAt(0) + sampled[i][2].substring(0, 3);
            // System.out.println(dupe);
            /*
             * if (dupe.contains(temp)) {
             * dupe.add(temp.substring(0, temp.length() - 1) + idcount);
             * } else {
             * 
             * dupe.add(temp);
             * }
             */
            if (sampled[i][6].equalsIgnoreCase("TRUE")) {
                // System.out.print(" " + sampled[i][2] + "(Contractor)");
                dname = sampled[i][1] + " " + sampled[i][2] + " (Contractor)";
            } else {
                dname = sampled[i][1] + " " + sampled[i][2];
                // System.out.print(" " + sampled[i][2].substring(0, 3));
            }

            // System.out.print(" " + sampled[i][3].charAt(0) + " ");
            // System.out.print(" " + temp + " ");

            // System.out.print(date);
            // Conversion to epoch date
            String date = sampled[i][5].replace(".", "-");
            String pattern1 = "dd-MM-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern1);
            Date d = simpleDateFormat.parse(date);
            String epoch_date = String.format("%ts", d);
            // System.out.println(" " + epoch_date);
            edate[i] = epoch_date;
            String dc = sampled[i][5].replace(".", "-");
            String pattern = "MM-dd-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date doj = sdf.parse(dc);
            sampled[i][5] = doj + "";
            djoin[i] = sampled[i][5];

            // System.out.print(d);

            // date.getTime();
            if (Integer.parseInt(sampled[i][7]) == 1) {
                // System.out.print(" " + a);
                status[i] = a;
            } else {
                // System.out.print(" " + ina);
                status[i] = ina;
            }
            pnumber[i] = sampled[i][8];
            Designation[i] = sampled[i][9];
            // System.out.print(" " + sampled[i][8] + " " + sampled[i][9]);
            dispname[i] = dname;
            // System.out.print(" " + dname);

            dname = "";
            temp = "";
        }
        String outputpath = "C:/Users/tteli/Desktop/processed_file_";
        Date dat = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        outputpath += dateFormat.format(dat) + ".csv";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputpath));
            for (int i = 0; i < gid.length; i++) {
                writer.write(gid[i] + "," + fname[i] + "," + lname[i] + "," + gender[i] + "," + uid[i] + "," + djoin[i]
                        + "," + status[i]
                        + "," + pnumber[i] + "," + Designation[i] + "," + dispname[i] + "," + edate[i]);
                for (String j : uid) {
                    if (j.equals(uid[i])) {
                        uid[i] = uid[i].substring(0, 3) + Integer.toString(idcount);
                    }
                }
                writer.write("\n");
                idcount += 1;
            }
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}