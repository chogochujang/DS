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
				if(X==1)//1. world cup
					World_cup(conn,st);
				else if(X==2) {
					}
				else return;
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
	public static void World_cup(Connection conn, Statement st)throws SQLException
	{
		Integer[] pal_gang = new Integer[9];
		Integer[] sa_gang = new Integer[5];
		Integer[] match = new Integer[3];
		int win;
		String[] fname = new String[65];
		Double[] calorie = new Double[65];
		Double[] c = new Double[65];
		Double[] p = new Double[65];
		Double[] f = new Double[65];
		Integer[] price = new Integer[65];
		int i = 0, choice;
		Scanner scan = new Scanner(System.in);
		String stmt = "select name, calorie, carbonhydrate, protein, fat, avg_price from food";
		ResultSet rs = st.executeQuery(stmt);
		while(rs.next()) {//food table 가져오기
			fname[i] = rs.getString(1);
			calorie[i] = rs.getDouble(2);
			c[i] = rs.getDouble(3);
			p[i] = rs.getDouble(4);
			f[i] = rs.getDouble(5);
			price[i] = rs.getInt(6);
			i++;
		}

		//난수 생성
		Integer[] rand = new Integer[16];
		for(i=0; i<16;i++) {
			rand[i] = (int)((Math.random()*100)%64);
			for( int j=0; j<i;j++) {
				if(rand[i]==rand[j]) {i--; break;}
			}
		}
		System.out.println("***********************************");
		System.out.println(" ----------- 당신의 선택 -----------");
		System.out.println("               16강               ");
		
		for(i=0;i<16;i+=2) //16강
		{
			System.out.println(" ┌------------------------------┐ ");
			System.out.println(" |          Round "+((i/2)+1)+"/8           |");
			System.out.println(" |    # 1                #2     |" );
			if(fname[rand[i]].length()<3)
				System.out.println(" |   "+ fname[rand[i]]+"\t\t\t"+fname[rand[i+1]]+"\t|");
			else
				System.out.println(" |   "+ fname[rand[i]]+"\t\t"+fname[rand[i+1]]+"\t|");
			if(calorie[rand[i+1]]<100)
				System.out.println(" |  "+calorie[rand[i]]+"cal\t\t"+calorie[rand[i+1]]+"cal |");
			else
				System.out.println(" |  "+calorie[rand[i]]+"cal\t\t"+calorie[rand[i+1]]+"cal|");
			System.out.println(" |  탄 "+ c[rand[i]]+"g\t\t"+c[rand[i+1]]+"g\t|");
			System.out.println(" |  단 "+ p[rand[i]]+"g\t\t"+p[rand[i+1]]+"g\t|");
			System.out.println(" |  지 "+f[rand[i]]+"g\t\t"+f[rand[i+1]]+"g\t|");
			System.out.println(" |  가격 "+price[rand[i]]+"$\t\t"+price[rand[i+1]]+"$\t|");
			System.out.println(" └------------------------------┘ ");
			System.out.printf("What is your choice? :");
			choice = scan.nextInt()-1;
			pal_gang[i/2]=rand[choice+i];
		}
		System.out.println(" ----------- 당신의 선택 -----------");
		System.out.println("                8강               ");
		for(i=0;i<8;i+=2) //8강
		{
			System.out.println(" ┌------------------------------┐ ");
			System.out.println(" |          Round "+((i/2)+1)+"/4           |");
			System.out.println(" |    # 1                #2     |" );
			if(fname[pal_gang[i]].length()<3)
				System.out.println(" |   "+ fname[pal_gang[i]]+"\t\t\t"+fname[pal_gang[i+1]]+"\t|");
			else
				System.out.println(" |   "+ fname[pal_gang[i]]+"\t\t"+fname[pal_gang[i+1]]+"\t|");
			if(calorie[pal_gang[i+1]]<100)
				System.out.println(" |  "+calorie[pal_gang[i]]+"cal\t\t"+calorie[pal_gang[i+1]]+"cal |");
			else
				System.out.println(" |  "+calorie[pal_gang[i]]+"cal\t\t"+calorie[pal_gang[i+1]]+"cal|");
			System.out.println(" |  탄 "+ c[pal_gang[i]]+"g\t\t"+c[pal_gang[i+1]]+"g\t|");
			System.out.println(" |  단 "+ p[pal_gang[i]]+"g\t\t"+p[pal_gang[i+1]]+"g\t|");
			System.out.println(" |  지 "+f[pal_gang[i]]+"g\t\t"+f[pal_gang[i+1]]+"g\t|");
			System.out.println(" |  가격 "+price[pal_gang[i]]+"$\t\t"+price[pal_gang[i+1]]+"$\t|");
			System.out.println(" └------------------------------┘ ");
			System.out.printf("   What is your choice? :");
			choice = scan.nextInt()-1;
			sa_gang[i/2]=pal_gang[choice+i];
		}
		System.out.println(" ----------- 당신의 선택 -----------");
		System.out.println("                4강               ");
		for(i=0;i<4;i+=2) //4강
		{
			System.out.println(" ┌------------------------------┐ ");
			System.out.println(" |          Round "+((i/2)+1)+"/2           |");
			System.out.println(" |    # 1                #2     |" );
			if(fname[sa_gang[i]].length()<3)
				System.out.println(" |   "+ fname[sa_gang[i]]+"\t\t\t"+fname[sa_gang[i+1]]+"\t|");
			else
				System.out.println(" |   "+ fname[sa_gang[i]]+"\t\t"+fname[sa_gang[i+1]]+"\t|");
			if(calorie[sa_gang[i+1]]<100)
				System.out.println(" |  "+calorie[sa_gang[i]]+"cal\t\t"+calorie[sa_gang[i+1]]+"cal |");
			else
				System.out.println(" |  "+calorie[sa_gang[i]]+"cal\t\t"+calorie[sa_gang[i+1]]+"cal|");
			System.out.println(" |  탄 "+ c[sa_gang[i]]+"g\t\t"+c[sa_gang[i+1]]+"g\t|");
			System.out.println(" |  단 "+ p[sa_gang[i]]+"g\t\t"+p[sa_gang[i+1]]+"g\t|");
			System.out.println(" |  지 "+f[sa_gang[i]]+"g\t\t"+f[sa_gang[i+1]]+"g\t|");
			System.out.println(" |  가격 "+price[sa_gang[i]]+"$\t\t"+price[sa_gang[i+1]]+"$\t|");
			System.out.println(" └------------------------------┘ ");
			System.out.printf("   What is your choice? :");
			choice = scan.nextInt()-1;
			match[i/2]=sa_gang[choice+i];
		}
		//결승
		System.out.println(" ┌---------- 당신의 선택 ----------┐");
		System.out.println(" |              결승전            |");
		System.out.println(" |    # 1                #2     |" );
		if(fname[match[0]].length()<3)
			System.out.println(" |   "+ fname[match[0]]+"\t\t\t"+fname[match[1]]+"\t|");
		else
			System.out.println(" |   "+ fname[match[0]]+"\t\t"+fname[match[1]]+"\t|");
		if(calorie[match[1]]<100)
			System.out.println(" |  "+calorie[match[0]]+"cal\t\t"+calorie[match[1]]+"cal |");
		else
			System.out.println(" |  "+calorie[match[0]]+"cal\t\t"+calorie[match[1]]+"cal|");
		System.out.println(" |  탄 "+ c[match[0]]+"g\t\t"+c[match[1]]+"g\t|");
		System.out.println(" |  단 "+ p[match[0]]+"g\t\t"+p[match[1]]+"g\t|");
		System.out.println(" |  지 "+f[match[0]]+"g\t\t"+f[match[1]]+"g\t|");
		System.out.println(" |  가격 "+price[match[0]]+"$\t\t"+price[match[1]]+"$\t|");
		System.out.println(" └------------------------------┘ ");
		System.out.printf("   What is your choice? :");
		choice = scan.nextInt()-1;
		String win_menu = fname[match[choice]];
		System.out.println("************ "+ win_menu + " WIN! ************");
		
		//음식점 리스트
		stmt = "select name,address,contact from good_restaurant where main_menu like '%"+win_menu+"%'";
		rs = st.executeQuery(stmt);
		i=1;
		System.out.printf("[ 모범 업소 ]\n");
		if(!rs.next()) System.out.printf("등록된 음식점이 없습니다.\n");
		else System.out.printf("#%d %s / %s / %s\n",i++,rs.getString(1),rs.getString(2),rs.getString(3));
		while(rs.next()) {//food table 가져오기
			System.out.printf("#%d %s / %s / %s\n",i++,rs.getString(1),rs.getString(2),rs.getString(3));
		}
		String gpr;
		ResultSet gprr;
		gpr="select name,address,contact from good_price_restaurant where main_menu like '%"+win_menu+"%'";
		gprr = st.executeQuery(gpr);
		System.out.printf("[ 착한 가격 업소 ]\n");
		i=1;
		if(!gprr.next()) System.out.printf("등록된 음식점이 없습니다.\n");
		else System.out.printf("#%d %s / %s / %s\n",i++,gprr.getString(1),gprr.getString(2),gprr.getString(3));
		while(gprr.next()) {//food table 가져오기
			System.out.printf("#%d %s / %s / %s\n",i++,gprr.getString(1),gprr.getString(2),gprr.getString(3));
		}
	}
	
}
