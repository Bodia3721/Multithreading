package com.company;

import java.sql.*;

public class Map {

    private  String URL = "";
    private  String USERNAME = "";
    private  String PASSWORD = "";

    private Connection connection = null;
    private Statement statement = null;

    public Map(String DBName, String ip, int port, String userName, String password){
        URL = "jdbc:mysql://"+ ip + ":" + port + "/" + DBName + "?serverTimezone=UTC";
        USERNAME = userName;
        PASSWORD = password;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean addCountry(int id, String name) {
        String sql = "INSERT INTO COUNTRIES (ID_CO, NAME)" + "VALUES ("+id+", '"+name+"')";

        try {
            statement.executeUpdate(sql);
            System.out.println("[Map::addCountry()] Ñòğàíà " + name + " óñïåøíî äîáàâëåíà!");
            return true;
        } catch (SQLException e) {
            System.out.println("[Map::addCountry()] ÎØÈÁÊÀ! Ñòğàíà " + name + " íå äîáàâëåíà!");
            System.out.println(" >> "+e.getMessage());
            return false;
        }
    }

    public boolean deleteCountry(int id){
        String sql = "DELETE FROM COUNTRIES WHERE ID_CO = " + id;
        try {
            int c = statement.executeUpdate(sql);

            if (c>0) {
                System.out.println("[Map::deleteCountry()] Ñòğàíà ñ èäåíòèôèêàòîğîì " + id +" óñïåøíî óäàëåíà!");
                return true;
            } else {
                System.out.println("[Map::deleteCountry()] Ñòğàíà ñ èäåíòèôèêàòîğîì " + id +" íå íàéäåíà!");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("[Map::deleteCountry()] ÎØÈÁÊÀ ïğè óäàëåíèè ñòğàíû ñ èäåíòèôèêàòîğîì " + id + ", â ıòîé ñòğàíå åñòü ãîğîäà (óäàëèòå ñíà÷àëà èõ)");
//            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public String findCountry(int id){
        String sql = "SELECT ID_CO, NAME FROM COUNTRIES";

        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next())
            {
                 int idTmp = rs.getInt("ID_CO");

                 if (idTmp == id) {
                     String name = rs.getString("NAME");
                     System.out.println("[Map::findCountry()] "+ id + " - " + name);
                     rs.close();
                     return name;
                 }
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    public void changeCountryInfo(int id, String name){
        System.out.println("[Map::changeCountryInfo()]");

        String sql1 = "UPDATE COUNTRIES SET NAME = '"+name+"' WHERE ID_CO = '" + id + "'";
        try {
            statement.executeUpdate(sql1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void showCountries() {
        String sql = "SELECT ID_CO, NAME FROM COUNTRIES";
        try
        {
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("[Map::showCountries()] ÑÏÈÑÎÊ ÑÒĞÀÍ:");
            while (rs.next())
            {
                int id = rs.getInt("ID_CO");
                String name = rs.getString("NAME");
                System.out.println(" >> "+ id + " - " + name);
            }
            rs.close();
        } catch (SQLException e)
        {
            System.out.println("[Map::showCountries()] ÎØÈÁÊÀ ïğè ïîëó÷åíèè ñïèñêà ñòğàí");
            System.out.println(" >> "+e.getMessage());
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean addCity(int idCity, int idCountry, String name, int count, int isCapital) {
        String sql = "INSERT INTO CITIES (ID_CI, ID_CO, NAME, COUNT, ISCAPITAL)" + "VALUES ("+idCity+","+idCountry+", '"+name+"',"+count+","+isCapital+")";

        try {
            statement.executeUpdate(sql);
            System.out.println("[Map::addCountry()] Ñòğàíà " + name + " óñïåøíî äîáàâëåíà!");
            return true;
        } catch (SQLException e) {
            System.out.println("[Map::addCountry()] ÎØÈÁÊÀ! Ñòğàíà " + name + " íå äîáàâëåíà!");
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCity(int id){
        String sql = "DELETE FROM CITIES WHERE ID_CI = " + id;
        try {
            int c = statement.executeUpdate(sql);

            if (c>0) {
                System.out.println("[Map::deleteCity()] Ãîğîä ñ èäåíòèôèêàòîğîì " + id +" óñïåøíî óäàëåíà!");
                return true;
            } else {
                System.out.println("[Map::deleteCity()] Ãîğîä ñ èäåíòèôèêàòîğîì " + id +" íå íàéäåíà!");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("[Map::deleteCity()] ÎØÈÁÊÀ ïğè óäàëåíèè ãîğîäà ñ èäåíòèôèêàòîğîì " + id);
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public String findCity(int id){
        String sql = "SELECT ID_CI, NAME FROM CITIES";

        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next())
            {
                int idTmp = rs.getInt("ID_CI");

                if (idTmp == id) {
                    String name = rs.getString("NAME");
                    System.out.println("[Map::findCity()] "+ id + " - " + name);
                    rs.close();
                    return name;
                }
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    public void changeCityInfo(int idCity,int idCountry, String name, int count, int isCapital){
        System.out.println("[Map::changeCountryInfo()]");

        String sql1 = "UPDATE CITIES SET NAME = '"+name+"' WHERE ID_CI = '" + idCity + "'";
        String sql2 = "UPDATE CITIES SET ID_CO = '"+idCountry+"' WHERE ID_CI = '" + idCity + "'";
        String sql3 = "UPDATE CITIES SET COUNT = '"+count+"' WHERE ID_CI = '" + idCity + "'";
        String sql4 = "UPDATE CITIES SET ISCAPITAL = '"+isCapital+"' WHERE ID_CI = '" + idCity + "'";
        try {
            statement.executeUpdate(sql1);
            statement.executeUpdate(sql2);
            statement.executeUpdate(sql3);
            statement.executeUpdate(sql4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void showCities(int idCountry){
        String sql = "SELECT ID_CI, ID_CO, NAME, COUNT, ISCAPITAL FROM CITIES";
        try
        {
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("[Map::showCities()] ÑÏÈÑÎÊ ÃÎĞÎÄÎÂ " + " :");
            while (rs.next())
            {
                int idCity = rs.getInt("ID_CI");
                int idCountryTmp = rs.getInt("ID_CO");
                String nameCity = rs.getString("NAME");
                Integer count = rs.getInt("COUNT");
                Boolean isCapital = (rs.getInt("ISCAPITAL") == 1 ? true : false);

                if (idCountry == idCountryTmp)
                System.out.println("idCity: " + idCity + "  idCountry:" + idCountryTmp + "   nameCity:" + nameCity + "   count:" + count + "   isCapital:"  + isCapital);
            }

            rs.close();
        } catch (SQLException e)
        {
            System.out.println("[Map::showCities()] ÎØÈÁÊÀ ïğè ïîëó÷åíèè ñïèñêà ãîğîäîâ");
            System.out.println(" >> "+e.getMessage());
        }
    }

    public void showAllCities(){
        String sql = "SELECT ID_CI, ID_CO, NAME, COUNT, ISCAPITAL FROM CITIES";
        try
        {
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("[Map::showAllCities()] ÑÏÈÑÎÊ ÂÑÅÕ ÃÎĞÎÄÎÂ " + " :");
            while (rs.next())
            {
                int idCity = rs.getInt("ID_CI");
                int idCountryTmp = rs.getInt("ID_CO");
                String nameCity = rs.getString("NAME");
                Integer count = rs.getInt("COUNT");
                Boolean isCapital = (rs.getInt("ISCAPITAL") == 1 ? true : false);

                System.out.println("idCity: " + idCity + "  idCountry:" + idCountryTmp + "   nameCity:" + nameCity + "   count:" + count + "   isCapital:"  + isCapital);
            }

            rs.close();
        } catch (SQLException e)
        {
            System.out.println("[Map::showCities()] ÎØÈÁÊÀ ïğè ïîëó÷åíèè ñïèñêà ãîğîäîâ");
            System.out.println(" >> "+e.getMessage());
        }
    }

    public int countCities(int idCountry){
        String sql = "SELECT ID_CI, ID_CO, NAME, COUNT, ISCAPITAL FROM CITIES";
        int count = 0;
        try
        {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next())
            {
                int idCountryTmp = rs.getInt("ID_CO");
                if (idCountry == idCountryTmp) count++;
            }
            rs.close();
            System.out.println("[Map::countCities()] count cities for country (ID: " + idCountry +") is " + count);
            return count;
        } catch (SQLException e) {
            System.out.println("[Map::showCities()] ÎØÈÁÊÀ ïğè ïîëó÷åíèè ñïèñêà ãîğîäîâ");
            System.out.println(" >> "+e.getMessage());
        }
        return -1;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
