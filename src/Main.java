import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.print.Doc;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.stream.StreamSupport;

public class Main {
    private static final String urlDB="jdbc:sqlite:date\\Specializare.db";
    private static List<Specializare> specializari= new ArrayList<>();
    private static List<Specializare> specializariDB= new ArrayList<>();


    private static void creareTabele(){

        try(Connection conexiune= DriverManager.getConnection(urlDB)){
            Statement comanda=conexiune.createStatement();
            comanda.executeUpdate("Create table if not exists SPECIALIZARE(cod_sectie Integer, nume text, etaj integer, nr_locuri integer)");
            comanda.executeUpdate("Delete from Specializare");
            System.out.println("Tabela s-a creat :)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void inserareTabela(){
    try(Connection conexiune= DriverManager.getConnection(urlDB)){
        PreparedStatement comanda=conexiune.prepareStatement("Insert into specializare values (?,?,?,?)");
        for (var specializare:specializari) {
            comanda.setInt(1,specializare.getCodSectie());
            comanda.setString(2,specializare.getNume());
            comanda.setInt(3,specializare.getEtaj());
            comanda.setInt(4,specializare.getNrLocuri());
            comanda.execute();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    private static List<Doctor> citireJSON(String caleFisier){
        List<Doctor> rezultat=new ArrayList<>();
        try(var fisier=new FileInputStream(caleFisier)){
            var tokener= new JSONTokener(fisier);
            var jsonDoctori= new JSONArray(tokener);
            for(int i=0;i<jsonDoctori.length();i++){
                var jsonDoctor=jsonDoctori.getJSONObject(i);
                rezultat.add(new Doctor(jsonDoctor.getInt("cod_doctor"),
                        jsonDoctor.getString("nume"),jsonDoctor.getInt("cod_sectie")));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    private static Map<Integer,Pacient> citireTXT(String caleFisier){
        Map<Integer,Pacient> rezultat= new HashMap<>();
        try(Scanner sc=new Scanner(new File(caleFisier))){
            while(sc.hasNext()){
                String linie=sc.nextLine();
                var linieScanner=new Scanner(linie);
                linieScanner.useDelimiter(",");
                Pacient pacient=new Pacient(linieScanner.nextInt(),linieScanner.next(),
                        linieScanner.nextInt(), linieScanner.nextInt());
                rezultat.put(pacient.getCodDoctor(),pacient);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    private static void citireTabela(){
        Specializare specializare;
        try(Connection conexiune= DriverManager.getConnection(urlDB)){
            Statement comanda =conexiune.createStatement();
            ResultSet rs=comanda.executeQuery("select * from specializare");
            while (rs.next()) {
                int codSectie=rs.getInt("cod_sectie");
                String nume= rs.getString("nume");
                int etaj=rs.getInt("etaj");
                int nr=rs.getInt("nr_locuri");
                specializare=new Specializare(codSectie,nume,etaj,nr);
                specializariDB.add(specializare);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void scriereTxtMap(String caleFisier, Map<Integer, Pacient> pacienti){
        try(var fisier=new PrintWriter(caleFisier)){
            for(var entry: pacienti.entrySet())
            {
                fisier.printf("%d,%s,%d,%d\n", entry.getValue().getCodPacient(),entry.getValue().getNume(),entry.getValue().getVarsta(),
                        entry.getValue().getCodDoctor());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void scriereTxtList(String caleFisier,List<Doctor> doctors){
        try(var fisier=new PrintWriter(caleFisier)){
            for (var doctor:doctors) {
                fisier.printf("%d,%s,%d\n",doctor.getCodDoctor(),doctor.getNume(),doctor.getCodSectie());
            }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    }

    private static void scriereJsonMap(String caleFisier, Map<Integer, Pacient> pacienti){
        JSONArray jsonPacienti= new JSONArray();
        for (var pacient: pacienti.entrySet()) {
            JSONObject jsonPacient=new JSONObject();
            jsonPacient.put("Cod_pacient",pacient.getValue().getCodPacient());
            jsonPacient.put("Nume",pacient.getValue().getNume());
            jsonPacient.put("Varsta",pacient.getValue().getVarsta());
            jsonPacient.put("Cod_doctor",pacient.getValue().getCodDoctor());
            jsonPacienti.put(jsonPacient);
        }
        try(var fisier=new FileWriter(caleFisier)){
            jsonPacienti.write(fisier,3,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void scriereJsonList(String caleFisier,List<Doctor> doctors){
        JSONArray jsonDoctori=new JSONArray();
        for(var doctor:doctors){
            JSONObject jsonDoctor=new JSONObject();
            jsonDoctor.put("Cod_doctor",doctor.getCodDoctor());
            jsonDoctor.put("Nume",doctor.getNume());
            jsonDoctor.put("Cod_sectie",doctor.getCodSectie());
            jsonDoctori.put(jsonDoctor);
        }
        try(var fisier=new FileWriter(caleFisier)){
            jsonDoctori.write(fisier,3,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void serializare(String caleFisier, List<Specializare> specializarii){
        try(ObjectOutputStream out=new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(caleFisier))))){
            for(var entry: specializarii){
                out.writeObject(entry);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deserializare(String caleFisier){
        try(ObjectInputStream in=new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(caleFisier))))){
             while(true){
                 Specializare specializare=(Specializare)in.readObject();
                 System.out.println(specializare.toString());
             }
            }catch (EOFException e){
                e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }



    public static void main(String[] args) {
        Map<Integer,Pacient> pacienti= new HashMap<>();
        creareTabele();
        specializari.add(new Specializare(1,"Dermatologie",1,5));
        specializari.add(new Specializare(2,"Radiologie",2,18));
        specializari.add(new Specializare(3,"Psihiatrie",1,2));
        inserareTabela();
        List<Doctor> doctori=citireJSON("date\\doctori.json");
        doctori.stream().forEach(System.out::println);
        pacienti=citireTXT("date\\pacienti.txt");
        pacienti.entrySet().stream().forEach(System.out::println);
        System.out.println("---citire db---");
        citireTabela();
        specializariDB.stream().forEach(System.out::println);
        scriereTxtList("date\\doctoriTxt.txt",doctori);
        scriereTxtMap("date\\pacientiTxt.txt", pacienti);
        scriereJsonList("date\\doctoriJson.json",doctori);
        scriereJsonMap("date\\pacientiJson.json",pacienti);
        serializare("date\\specializareSERIALIZARE.dat",specializari);
        //deserializare("date\\specializareSERIALIZARE.dat");
    }
////////////////////TODO:exercitiiPeStreamuri,TCP
}