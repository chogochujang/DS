import java.sql.*;
import java.util.Scanner;

public class main {
	public static void main(String[] args) throws SQLException{
		try{
			Scanner scan = new Scanner(System.in);
			//PostgreSQL server 및 DB연결
			String url = "jdbc:postgresql:DSDB";
			String user = "postgres";
			String password = "58778285";
			
			Connection conn = DriverManager.getConnection(url,user,password);
			System.out.println("Connecting PostgreSQL database");
			
			//creat user table
			Statement st = conn.createStatement();
			//String CreateSql = "create table Student(num int, uname varchar(20), age int, address varchar(20), sex int, uID varchar(20), uPW varchar(20))";
			//st.executeUpdate(CreateSql);
			ResultSet rs;
			String stmt;
			//기능 소개
			System.out.println("----------당신의 선택----------");
			System.out.println("1. sign up");
			System.out.println("2. sign in");
			System.out.println("3. exit");
			System.out.println("----------------------------");
			Integer X = scan.nextInt();
			if(X==1){//1.sign up
				String uName,address,uID,uPW;
				Integer age,sex;
				System.out.println("Name : ");
				uName = scan.nextLine();
				uName = scan.nextLine();
				System.out.println("age : ");
				age = scan.nextInt();
				System.out.println("sex - 1.male 2.femal : ");
				sex = scan.nextInt();
				System.out.println("address : ");
				address = scan.nextLine();
				address = scan.nextLine();
				while(true) {//ID Duplicate 처리
					System.out.println("uID : ");
					uID = scan.nextLine();
					stmt = "select count(*) from student where uID = '"+uID+"';";
					rs=st.executeQuery(stmt);
					rs.next();
					if(rs.getInt(1)==1) {
						System.out.println("ID Duplicate!");
					}
					else
						break;
				}
				System.out.println("uPW : ");
				uPW = scan.nextLine();

				stmt = "Insert into student values (1,'"+uName+"',"+age+",'"+address+"',"+sex+",'"+uID+"','"+uPW+"')";
				st.executeUpdate(stmt);
				System.out.println("Welcome!");
			}
			else if(X==2) {//2.sign in
				String uID,uPW;
				System.out.println("uID : ");
				uID = scan.nextLine();
				uID = scan.nextLine();
				System.out.println("uPW : ");
				uPW = scan.nextLine();
				 stmt = 
				"select uPW from student where uID = '"+uID+"';";
				rs=st.executeQuery(stmt);

				if(rs.next()) {
					if(uPW.equals(rs.getString(1))) {
						System.out.println("login SUCCESS");
                        System.out.println("==========================================");
					}
					else {
						System.out.println("WRONG password");
                        System.out.println("==========================================");
					}
				}
			}
		} catch(SQLException ex)
		{
			System.out.println("Failed!");
			throw ex;
		}
	}

}
