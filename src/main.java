import java.sql.*;
import java.util.Scanner;

public class main {
	public static void main(String[] args) throws SQLException{
		try{
			Scanner scan = new Scanner(System.in);
			boolean isLogedIn = false;
			//PostgreSQL server 및 DB연결
			String url = "jdbc:postgresql:DSDB";
			String user = "postgres";
			String password = "58778285";
			
			Connection conn = DriverManager.getConnection(url,user,password);
			System.out.println("---Connecting PostgreSQL database---");

			Statement st = conn.createStatement();
			//String CreateSql = "create table Good_restaurant(name varchar(20), main_menu varchar(50), address varchar(50), contact varchar(20))";
			//st.executeUpdate(CreateSql);
			
			//1. 로그인
			while(!isLogedIn) {
				System.out.println("***********************************");
				System.out.println(" ┌---------- 당신의 선택 ----------┐ ");
				System.out.println(" |          1. sign up          | ");
				System.out.println(" |          2. sign in          | ");
				System.out.println(" |          3. exit             | ");
				System.out.println(" └------------------------------┘ ");
				System.out.println("***********************************");
				System.out.printf("select : ");
				Integer X = scan.nextInt();
				if(X==1)//1.sign up
					Sign_up(conn,st);
				else if(X==2)  //2.sign in
					isLogedIn=Sign_in(conn,st);
				else return;
			}
			//2. 로그인 후 기능사용
			while(true) {
				System.out.println("***********************************");
				System.out.println(" ┌---------- 당신의 선택 ----------┐ ");
				System.out.println(" |          1. world cup        | ");
				System.out.println(" |          2. view chart       | ");
				System.out.println(" |          3. exit             | ");
				System.out.println(" └------------------------------┘ ");
				System.out.println("***********************************");
				System.out.printf("select : ");
				Integer X = scan.nextInt();
				//if(X==1)//1.sign up
				//else if(X==2)  //2.sign in
				//else return;
			}
		} catch(SQLException ex)
		{
			System.out.println("Failed!");
			throw ex;
		}
	}
	public static void Sign_up(Connection conn,Statement st)throws SQLException
	{
		Scanner scan = new Scanner(System.in);
		String uName,address,uID,uPW;
		Integer age,sex;
		String stmt;
		ResultSet rs;
		try{
			System.out.println("***********************************");
			System.out.println(" ┌---------- 당신의 선택 ----------┐ ");
			System.out.printf(" |   Name : ");
			uName = scan.nextLine();
			System.out.printf(" |   age :  ");
			age = scan.nextInt();
			System.out.printf(" |   sex - 1.male 2.femal : ");
			sex = scan.nextInt();
			System.out.printf(" |   address : ");
			address = scan.nextLine();
			address = scan.nextLine();
			while(true) {//ID Duplicate 처리
				System.out.printf(" |   uID : ");
				uID = scan.nextLine();
				stmt = "select count(*) from student where uID = '"+uID+"';";
				rs=st.executeQuery(stmt);
				rs.next();
				if(rs.getInt(1)==1) {
					System.out.println(" |       **ID Duplicate!**      |");
				}
				else
					break;
			}
			System.out.printf(" |   uPW : ");
			uPW = scan.nextLine();
			stmt = "Insert into student values (1,'"+uName+"',"+age+",'"+address+"',"+sex+",'"+uID+"','"+uPW+"')";
			st.executeUpdate(stmt);
			System.out.println(" |           Welcome!           |");
			System.out.println(" └------------------------------┘ ");
		}catch(SQLException ex) {
			System.out.println("Sign_up Failed!");
			throw ex;
		}
		//scan.close();
	}
	public static boolean Sign_in(Connection conn,Statement st)throws SQLException
	{
		Scanner scan = new Scanner(System.in);
		String uID,uPW;
		ResultSet rs;
		try {
			System.out.println("***********************************");
			System.out.println(" ┌---------- 당신의 선택 ----------┐ ");
			System.out.printf(" |        ID : ");
			uID = scan.nextLine();
			System.out.printf(" |        PW : ");
			uPW = scan.nextLine();
			 String stmt = 
			"select uPW from student where uID = '"+uID+"';";
			rs=st.executeQuery(stmt);
	
			if(rs.next()) {
				if(uPW.equals(rs.getString(1))) {
					System.out.println(" |         login SUCCESS        |");
					System.out.println(" └------------------------------┘ ");
	                return true;
				}
				else {
					System.out.println(" |         WRONG password       |");
					System.out.println(" └------------------------------┘ ");
				}
			}
			return false;
		}catch(SQLException ex) {
			System.out.println("Sign_in Failed!");
			throw ex;
		}
		//scan.close();
	}

}
