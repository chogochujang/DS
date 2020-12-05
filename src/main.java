import java.sql.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class main {
	static int u_num;
	static boolean admin = false;
	static String sub_gu1, sub_gu2;
	public static void main(String[] args) throws SQLException, IOException, ParseException{
		try{
			Scanner scan = new Scanner(System.in);
			boolean isLogedIn = false;
			
			String dbacct, passwrd, url;
			url = "jdbc:postgresql:DSDB";
			dbacct = "postgres";
			System.out.println("Enter password:");
			passwrd = scan.next();
			System.out.println("Connecting PostgreSQL database");
			// JDBC�� �̿��� PostgreSQL ���� �� �����ͺ��̽� ����
			Connection conn = DriverManager.getConnection(url, dbacct, passwrd);

			Statement st = conn.createStatement();
			//String CreateSql = "create table Good_restaurant(name varchar(20), main_menu varchar(50), address varchar(50), contact varchar(20))";
			//st.executeUpdate(CreateSql);
			
			//1. �α���
			while(!isLogedIn) {
				System.out.println("***********************************");
				System.out.println(" ��---------- ����� ���� ----------�� ");
				System.out.println(" |          1. sign up          | ");
				System.out.println(" |          2. sign in          | ");
				System.out.println(" |          3. exit             | ");
				System.out.println(" ��------------------------------�� ");
				System.out.println("***********************************");
				System.out.printf("select : ");
				Integer X = scan.nextInt();
				if(X==1)//1.sign up
					Sign_up(conn,st);
				else if(X==2)  //2.sign in
					isLogedIn=Sign_in(conn,st);
				else return;
				continue;
			}
			//2. �α��� �� ��ɻ��
			
			if(admin)
			{
				while(isLogedIn) {
					System.out.println("***********************************");
					System.out.println(" ��---------- ����� ���� ----------�� ");
					System.out.println(" |          1. setting          | ");
					System.out.println(" |          2. world cup        | ");
					System.out.println(" |          3. view chart       | ");
					System.out.println(" |          4. exit             | ");
					System.out.println(" ��------------------------------�� ");
					System.out.println("***********************************");
					System.out.printf("select : ");
					Integer X = scan.nextInt();
					if(X==1) {//1. world cup
						while(true) {
							System.out.println("***********************************");
							System.out.println(" ��---------- ����� ���� ----------�� ");
							System.out.println(" |          1. table�߰�         | ");
							System.out.println(" |          2. table����         | ");
							System.out.println(" |          3. trigger�߰�       | ");
							System.out.println(" |          4. trigger����       | ");
							System.out.println(" |          5. next             | ");
							System.out.println(" ��------------------------------�� ");
							System.out.println("***********************************");
							System.out.printf("select : ");
							X = scan.nextInt();
							if(X==1) {//1.sign up
								create_table(conn, "Gwangjin");//������
								create_table(conn, "Sd");//������
								create_table(conn, "Sb");//���ϱ�
								create_table(conn, "Seocho","Sc");//���ʱ�
								create_table(conn, "Songpa","Sp","seoul");//���ı�
								create_table(conn, "Ep");//����
								create_table(conn, "Gd");//������
								create_table(conn, "Jongno");//���α�
								create_table(conn, "Gangnam","Gn");//������
								create_table(conn, "Dobong");//���ɱ�
								create_table(conn, "Mapo","Mp");//������
								create_table(conn, "Geumcheon");//��õ��
								create_table(conn, "Yongsan","Ys");//��걸
								create_table(conn, "Dongjak","Dj");//���۱�
								create_table(conn, "Gangseo","Gangseo","seoul");//������
								create_table(conn, "Junggu","Junggu","seoul");//�߱�
								create_table(conn, "Ydp");//��������
								create_table(conn, "Guro");//���α�
								create_table(conn, "Gangbuk","Gb");//���ϱ�
								create_table(conn, "Jungnang");//�߶���
								create_table(conn, "Sdm","Seodaemun");//���빮��
								create_table(conn, "Ddm","Dongdeamoon");//���빮��
								create_table(conn, "Yangcheon","Yc");//��õ��
								create_table(conn, "Nowon","Nw");//�����
								create_table(conn, "Gwanak","Ga");//���Ǳ�
							}
							else if(X==2)  //2.sign in
								drop_table(conn,st);
							else if(X==3)
								create_trigger(conn,st);
							else if(X==4)
								drop_trigger(conn,st);
							else break;
						}
						continue;
					}
					else if(X==2) {
						World_cup(conn,st);
						continue;
						}
					else if(X==3) {
						view_chart(conn,st);
						continue;
					}
					else return;
				}
			}
			else
			{
				while(isLogedIn) {
					System.out.println("***********************************");
					System.out.println(" ��---------- ����� ���� ----------�� ");
					System.out.println(" |          1. world cup        | ");
					System.out.println(" |          2. view chart       | ");
					System.out.println(" |          3. exit             | ");
					System.out.println(" ��------------------------------�� ");
					System.out.println("***********************************");
					System.out.printf("select : ");
					Integer X = scan.nextInt();
					if(X==1) {//1. world cup
						World_cup(conn,st);
						continue;
					}
					else if(X==2) {
						view_chart(conn,st);
						continue;
						}
					else return;
				}
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
			System.out.println(" ��---------- ����� ���� ----------�� ");
			System.out.printf(" |   Name : ");
			uName = scan.nextLine();
			System.out.printf(" |   age :  ");
			age = scan.nextInt();
			System.out.printf(" |   sex - 1.male 2.femal : ");
			sex = scan.nextInt();
			System.out.printf(" |   address : ");
			address = scan.nextLine();
			address = scan.nextLine();
			while(true) {//ID Duplicate ó��
				System.out.printf(" |   uID : ");
				uID = scan.nextLine();
				//uID = scan.nextLine();
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
			
			//uNUM �ο�
			String nst = "select count(*) from student";
			ResultSet nrs= st.executeQuery(nst);
			nrs.next();
			int unum = nrs.getInt(1);
			
			stmt = "Insert into student values ("+unum+",'"+uName+"',"+age+",'"+address+"',"+sex+",'"+uID+"','"+uPW+"')";
			st.executeUpdate(stmt);
			System.out.println(" |           Welcome!           |");
			System.out.println(" ��------------------------------�� ");
			
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
			System.out.println(" ��---------- ����� ���� ----------�� ");
			System.out.printf(" |        ID : ");
			uID = scan.nextLine();
			System.out.printf(" |        PW : ");
			uPW = scan.nextLine();
			 String stmt = 
			"select uPW from student where uID = '"+uID+"';";
			rs=st.executeQuery(stmt);
	
			if(rs.next()) {
				if(uPW.equals(rs.getString(1))) {
					stmt = "select num from student where uID = '" + uID + "'";
					u_num = rs.getInt(1);
					if(u_num==0||u_num==1) admin=true;
					System.out.println(" |         login SUCCESS        |");
					System.out.println(" ��------------------------------�� ");
	                return true;
				}
				else {
					System.out.println(" |         WRONG password       |");
					System.out.println(" ��------------------------------�� ");
					return false;
				}
			}
			System.out.println(" |         login Failed         |");
			System.out.println(" ��------------------------------�� ");
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
		while(rs.next()) {//food table ��������
			fname[i] = rs.getString(1);
			calorie[i] = rs.getDouble(2);
			c[i] = rs.getDouble(3);
			p[i] = rs.getDouble(4);
			f[i] = rs.getDouble(5);
			price[i] = rs.getInt(6);
			i++;
		}

		//���� ����
		Integer[] rand = new Integer[16];
		for(i=0; i<16;i++) {
			rand[i] = (int)((Math.random()*100)%64);
			for( int j=0; j<i;j++) {
				if(rand[i]==rand[j]) {i--; break;}
			}
		}
		String gu=choose_gu();//�� ����
		System.out.println("***********************************");
		System.out.println(" ----------- ����� ���� -----------");
		System.out.println("               16��               ");
		
		for(i=0;i<16;i+=2) //16��
		{
			System.out.println(" ��------------------------------�� ");
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
			System.out.println(" |  ź "+ c[rand[i]]+"g\t\t"+c[rand[i+1]]+"g\t|");
			System.out.println(" |  �� "+ p[rand[i]]+"g\t\t"+p[rand[i+1]]+"g\t|");
			System.out.println(" |  �� "+f[rand[i]]+"g\t\t"+f[rand[i+1]]+"g\t|");
			System.out.println(" |  ���� "+price[rand[i]]+"$\t\t"+price[rand[i+1]]+"$\t|");
			System.out.println(" ��------------------------------�� ");
			System.out.printf("What is your choice? :");
			choice = scan.nextInt()-1;
			pal_gang[i/2]=rand[choice+i];
		}
		System.out.println(" ----------- ����� ���� -----------");
		System.out.println("                8��               ");
		for(i=0;i<8;i+=2) //8��
		{
			System.out.println(" ��------------------------------�� ");
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
			System.out.println(" |  ź "+ c[pal_gang[i]]+"g\t\t"+c[pal_gang[i+1]]+"g\t|");
			System.out.println(" |  �� "+ p[pal_gang[i]]+"g\t\t"+p[pal_gang[i+1]]+"g\t|");
			System.out.println(" |  �� "+f[pal_gang[i]]+"g\t\t"+f[pal_gang[i+1]]+"g\t|");
			System.out.println(" |  ���� "+price[pal_gang[i]]+"$\t\t"+price[pal_gang[i+1]]+"$\t|");
			System.out.println(" ��------------------------------�� ");
			System.out.printf("   What is your choice? :");
			choice = scan.nextInt()-1;
			sa_gang[i/2]=pal_gang[choice+i];
		}
		System.out.println(" ----------- ����� ���� -----------");
		System.out.println("                4��               ");
		for(i=0;i<4;i+=2) //4��
		{
			System.out.println(" ��------------------------------�� ");
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
			System.out.println(" |  ź "+ c[sa_gang[i]]+"g\t\t"+c[sa_gang[i+1]]+"g\t|");
			System.out.println(" |  �� "+ p[sa_gang[i]]+"g\t\t"+p[sa_gang[i+1]]+"g\t|");
			System.out.println(" |  �� "+f[sa_gang[i]]+"g\t\t"+f[sa_gang[i+1]]+"g\t|");
			System.out.println(" |  ���� "+price[sa_gang[i]]+"$\t\t"+price[sa_gang[i+1]]+"$\t|");
			System.out.println(" ��------------------------------�� ");
			System.out.printf("   What is your choice? :");
			choice = scan.nextInt()-1;
			match[i/2]=sa_gang[choice+i];
		}
		//���
		System.out.println(" ��---------- ����� ���� ----------��");
		System.out.println(" |              �����            |");
		System.out.println(" |    # 1                #2     |" );
		if(fname[match[0]].length()<3)
			System.out.println(" |   "+ fname[match[0]]+"\t\t\t"+fname[match[1]]+"\t|");
		else
			System.out.println(" |   "+ fname[match[0]]+"\t\t"+fname[match[1]]+"\t|");
		if(calorie[match[1]]<100)
			System.out.println(" |  "+calorie[match[0]]+"cal\t\t"+calorie[match[1]]+"cal |");
		else
			System.out.println(" |  "+calorie[match[0]]+"cal\t\t"+calorie[match[1]]+"cal|");
		System.out.println(" |  ź "+ c[match[0]]+"g\t\t"+c[match[1]]+"g\t|");
		System.out.println(" |  �� "+ p[match[0]]+"g\t\t"+p[match[1]]+"g\t|");
		System.out.println(" |  �� "+f[match[0]]+"g\t\t"+f[match[1]]+"g\t|");
		System.out.println(" |  ���� "+price[match[0]]+"$\t\t"+price[match[1]]+"$\t|");
		System.out.println(" ��------------------------------�� ");
		System.out.printf("   What is your choice? :");
		choice = scan.nextInt()-1;
		String win_menu = fname[match[choice]];
		stmt = "select * from winner where u_num="+u_num + "and f_name='"+win_menu +"'";
		rs = st.executeQuery(stmt);
		if(!rs.next()) {
			stmt = "insert into winner values('"+win_menu+"',"+u_num+",1)";
			st.executeUpdate(stmt);
		} else {
			stmt = "update winner set win_count = win_count + 1 where f_name = '" + win_menu +"' and u_num =" +u_num;
			st.executeUpdate(stmt);
		}
		System.out.println("************ "+ win_menu + " WIN! ************");
		//�� ����
		
		//������ ����Ʈ
		stmt = "select name,address,contact from "+gu+" where main_menu like '%"+win_menu+"%' or name like '%"+win_menu+"%'";
		rs = st.executeQuery(stmt);
		i=1;
		System.out.printf("[ ���� �� ������ ]\n");
		if(!rs.next()) System.out.printf("��ϵ� �������� �����ϴ�.\n");
		else System.out.printf("#%d %s / %s / %s\n",i++,rs.getString(1),rs.getString(2),rs.getString(3));
		while(rs.next()) {//food table ��������
			System.out.printf("#%d %s / %s / %s\n",i++,rs.getString(1),rs.getString(2),rs.getString(3));
		}
		// �ֺ� �� ������ (union)
		stmt = "select name,address,contact from "+sub_gu1+" where main_menu like '%"+win_menu+"%' or name like '%"+win_menu+"%'\n"
				+ "union\n"
				+ "select name,address,contact from "+sub_gu2+" where main_menu like '%"+win_menu+"%' or name like '%"+win_menu+"%'\n"
						+ "order by address";
		rs = st.executeQuery(stmt);
		System.out.printf("[ �ֺ� �� ������ ]\n");
		if(!rs.next()) System.out.printf("��ϵ� �������� �����ϴ�.\n");
		else System.out.printf("#%d %s / %s / %s\n",i++,rs.getString(1),rs.getString(2),rs.getString(3));
		while(rs.next()) {//food table ��������
			System.out.printf("#%d %s / %s / %s\n",i++,rs.getString(1),rs.getString(2),rs.getString(3));
		}
		//�ֺ� ���� view �����
		/*create_sub_gu_view(conn,st,gu);
		stmt="select name,address,contact from "+gu+"_sub_view where main_menu like '%"+win_menu+"%' or name like '%"+win_menu+"%'";
		rs = st.executeQuery(stmt);
		System.out.printf("[ �ֺ� �� ������ ]\n");
		i=1;
		if(!rs.next()) System.out.printf("��ϵ� �������� �����ϴ�.\n");
		else System.out.printf("#%d %s / %s / %s\n",i++,rs.getString(1),rs.getString(2),rs.getString(3));
		while(rs.next()) {//food table ��������
			System.out.printf("#%d %s / %s / %s\n",i++,rs.getString(1),rs.getString(2),rs.getString(3));
		}*/
	}
	public static void view_chart(Connection conn,Statement st) throws SQLException {
		while(true) {
			System.out.println("***********************************");
			System.out.println(" ��---------- ��Ʈ Ȯ���ϱ� ----------�� ");
			System.out.println(" |          1. ��ü ����Ȯ��       	  | ");
			System.out.println(" |          2. ���� ���� ����Ȯ��	  | ");
			System.out.println(" |          3. �̿��� �� ���� Ȯ�� 	  | ");
			System.out.println(" |          4. �ڷ� ����         	  | ");
			System.out.println(" ��--------------------------------�� ");
			System.out.println("***********************************");
			System.out.printf("select : ");
			Scanner scan = new Scanner(System.in);
			Integer X = scan.nextInt();
			if(X==1) {
				view_all_chart(conn, st);
				continue;
			}else if (X==2) {
				view_my_chart(conn, st);
				continue;
			}else if(X==3) {
				view_part_chart(conn, st);
				continue;
			}else {
				return;
			}
		}
	}
	public static void view_all_chart(Connection conn,Statement st)throws SQLException {
		String stmt = "select * from food order by win_count desc";
		System.out.println("			��ü ���� ����");
		System.out.println("***********************************************************");
		ResultSet rs = st.executeQuery(stmt);
		System.out.println("����	\t���Ƚ��\t�̸�\tĮ�θ�\tź��ȭ��\t�ܹ���\t����\t��հ���");
		int i = 1;
		while(rs.next()) {
			String name = rs.getString(1);
			double cal = rs.getDouble(2);
			double c = rs.getDouble(3);
			double p = rs.getDouble(4);
			double f = rs.getDouble(5);
			int avg_price = rs.getInt(6);
			int win_count = rs.getInt(7);
			System.out.println(i + "\t" + win_count + "\t" + name + "\t" + cal + "\t" + c + "\t" + p + "\t" + f + "\t" + avg_price);
			i++;
		}
		System.out.println("�ڷ� ������ �ƹ� Ű�� ��������");
		Scanner scan = new Scanner(System.in);
		String back = scan.nextLine();
		return;
	}
	
	public static void view_my_chart(Connection conn, Statement st) throws SQLException {
//		String stmt = "select *\n"
//				+ "from food\n"
//				+ "where name in (select f_name from winner where u_num ="+u_num+")\n"
//				+ "order by win_count desc";
		String stmt = "select f_name, win_count\n"
				+ "from winner\n"
				+ "where u_num ="+u_num+"\n"
						+ "order by win_count desc";
		System.out.println("			���� ���� ���� ����");
		System.out.println("***********************************************************");
		ResultSet rs = st.executeQuery(stmt);
//		PreparedStatement p = conn.prepareStatement(stmt);
//		p.clearParameters();
//		p.setInt(1, u_num);
//		rs = p.executeQuery();
//		System.out.println("����\t���Ƚ��\t�̸�\tĮ�θ�\tź��ȭ��\t�ܹ���\t����\t��հ���");
		System.out.println("����\t���Ƚ��\t�̸�");
		int i = 1;
//		while(rs.next()) {
//			String name = rs.getString(1);
//			double cal = rs.getDouble(2);
//			double c = rs.getDouble(3);
//			double protein = rs.getDouble(4);
//			double f = rs.getDouble(5);
//			int avg_price = rs.getInt(6);
//			int win_count = rs.getInt(7);
//			System.out.println(i + "\t" + win_count + "\t" + name + "\t" + cal + "\t" + c + "\t" + protein + "\t" + f + "\t" + avg_price);
//			i++;
//		}
		while(rs.next()) {
			System.out.println(i+"\t"+rs.getInt(2)+"\t"+rs.getString(1));i++;
		}
		System.out.println("�ڷ� ������ �ƹ� Ű�� ��������");
		Scanner scan = new Scanner(System.in);
		String back = scan.nextLine();
		return;
	}
	
	public static void view_part_chart(Connection conn, Statement st) throws SQLException {
		while(true) {
			int sex, max, min;
			Scanner scan = new Scanner(System.in);
			PreparedStatement p;
			ResultSet r;
			System.out.println("***********************************");
			System.out.println(" ��---------- �̿��� �� ���� Ȯ�� ----------�� ");
			System.out.println("�׷��� ������ ������ (���� : 1, ���� : 2, ���� ����: 3");
			sex = scan.nextInt();
			System.out.println("�׷��� �ּ� ���̸� �Է��ϼ���");
			min = scan.nextInt();
			System.out.println("�׷��� �ִ� ���̸� �Է��ϼ���");
			max = scan.nextInt();
			String stmt;
			if(sex != 1 && sex !=2) {
				System.out.println(min+"�� �̻�"+max+"�� ���� ���� ����");
				stmt = "select *\n"
						+ "from food\n"
						+ "where name in (select f_name from winner"
						+ "	where u_num in (select u_num"
						+ "		from student"
						+ "		where age >= "+min+" and age <= "+max+" ))"
						+ "order by win_count desc;";
			} else {
				if(sex == 1) {
					System.out.println("����" + min+"�� �̻�"+max+"�� ���� ���� ����");
				}else {
					System.out.println("����" + min+"�� �̻�"+max+"�� ���� ���� ����");
				}
				stmt = "select *\n"
						+ "from food\n"
						+ "where name in (select f_name from winner"
						+ "	where u_num in (select u_num"
						+ "		from student"
						+ "		where sex = "+sex+" and age >= "+min+" and age <= "+max+" ))"
						+ "order by win_count desc;";
			}
//			p = conn.prepareStatement(stmt);
//			p.clearParameters();
//			p.setInt(1, sex);
//			p.setInt(2, min);
//			p.setInt(3, max);
//			r = p.executeQuery();
			r = st.executeQuery(stmt);
			System.out.println("����\t���Ƚ��\t�̸�\tĮ�θ�\tź��ȭ��\t�ܹ���\t����\t��հ���");
			int i = 1;
			while(r.next()) {
				String name = r.getString(1);
				double cal = r.getDouble(2);
				double c = r.getDouble(3);
				double protein = r.getDouble(4);
				double f = r.getDouble(5);
				int avg_price = r.getInt(6);
				int win_count = r.getInt(7);
				System.out.println(i + "\t" + win_count + "\t" + name + "\t" + cal + "\t" + c + "\t" + protein + "\t" + f + "\t" + avg_price);
				i++;
			}
			System.out.println("***********************************");
			System.out.println(" ��--------------------------------�� ");
			System.out.println(" |          1. �ٸ��׷� ���� Ȯ��   	  | ");
			System.out.println(" |          2. �ڷ� ����			  | ");
			System.out.println(" ��--------------------------------�� ");
			System.out.println("***********************************");
			Integer X = scan.nextInt();
			if(X==1) {
				continue;
			} else {
				return;
			}
		}
	}
	public static void create_table(Connection conn, String gu) throws SQLException, IOException, ParseException {
		String stmt = "create table "+ gu +" (name varchar(50),snt varchar(20), main_menu varchar(20), address varchar(100), contact varchar(20))";
		PreparedStatement p = conn.prepareStatement(stmt);
		p.executeUpdate();
		String urlStr = "http://openapi."+gu+".go.kr:8088/5263746654686f6f37395a67737a79/json/"+gu+"ModelRestaurantDesignate/1/1000";
		URL url = new URL(urlStr);
		BufferedReader bf; String line = ""; 
		String result="";
		bf = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
		while((line=bf.readLine())!=null){
			result=result.concat(line);
		}
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(result);
		JSONObject tong = (JSONObject) obj.get(gu+"ModelRestaurantDesignate");
		int list_count = ((Long)tong.get("list_total_count")).intValue();
		JSONArray rows = (JSONArray) tong.get("row");
		for(int i = 0; i < list_count; i++) {
			JSONObject row = (JSONObject) rows.get(i);
			String name = (String) row.get("UPSO_NM");
			String address = (String) row.get("SITE_ADDR_RD");
			String main_menu = (String) row.get("MAIN_EDF");
			String contact = (String) row.get("UPSO_SITE_TELNO");
			String snt = (String) row.get("SNT_UPTAE_NM");
			stmt = "insert into "+gu+" values (?, ?, ?, ?,?)";
			p = conn.prepareStatement(stmt);
			p.clearParameters();
			p.setString(1, name);
			p.setString(2, snt);
			p.setString(3, main_menu);
			p.setString(4, address);
			p.setString(5, contact);
			p.executeUpdate();
		}
	}
	public static void create_table(Connection conn, String gu1, String gu2) throws SQLException, IOException, ParseException {
		String stmt = "create table "+ gu1 +" (name varchar(50),snt varchar(20), main_menu varchar(20), address varchar(100), contact varchar(20))";
		PreparedStatement p = conn.prepareStatement(stmt);
		p.executeUpdate();
		String urlStr = "http://openapi."+gu1+".go.kr:8088/5263746654686f6f37395a67737a79/json/"+gu2+"ModelRestaurantDesignate/1/1000";
		URL url = new URL(urlStr);
		BufferedReader bf; String line = ""; 
		String result="";
		bf = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
		while((line=bf.readLine())!=null){
			result=result.concat(line);
		}
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(result);
		JSONObject tong = (JSONObject) obj.get(gu2+"ModelRestaurantDesignate");
		int list_count = ((Long)tong.get("list_total_count")).intValue();
		JSONArray rows = (JSONArray) tong.get("row");
		for(int i = 0; i < list_count; i++) {
			JSONObject row = (JSONObject) rows.get(i);
			String name = (String) row.get("UPSO_NM");
			String address = (String) row.get("SITE_ADDR_RD");
			String main_menu = (String) row.get("MAIN_EDF");
			String contact = (String) row.get("UPSO_SITE_TELNO");
			String snt = (String) row.get("SNT_UPTAE_NM");
			stmt = "insert into "+gu1+" values (?, ?, ?, ?,?)";
			p = conn.prepareStatement(stmt);
			p.clearParameters();
			p.setString(1, name);
			p.setString(2, snt);
			p.setString(3, main_menu);
			p.setString(4, address);
			p.setString(5, contact);
			p.executeUpdate();
		}
	}
	public static void create_table(Connection conn, String gu1, String gu2, String go) throws SQLException, IOException, ParseException {
		String stmt = "create table "+ gu1 +" (name varchar(50),snt varchar(20), main_menu varchar(20), address varchar(100), contact varchar(20))";
		PreparedStatement p = conn.prepareStatement(stmt);
		p.executeUpdate();
		String urlStr = "http://openapi."+gu1+"."+go+".kr:8088/5263746654686f6f37395a67737a79/json/"+gu2+"ModelRestaurantDesignate/1/1000";
		URL url = new URL(urlStr);
		BufferedReader bf; String line = ""; 
		String result="";
		bf = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
		while((line=bf.readLine())!=null){
			result=result.concat(line);
		}
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(result);
		JSONObject tong = (JSONObject) obj.get(gu2+"ModelRestaurantDesignate");
		int list_count = ((Long)tong.get("list_total_count")).intValue();
		JSONArray rows = (JSONArray) tong.get("row");
		for(int i = 0; i < list_count; i++) {
			JSONObject row = (JSONObject) rows.get(i);
			String name = (String) row.get("UPSO_NM");
			String address = (String) row.get("SITE_ADDR_RD");
			String main_menu = (String) row.get("MAIN_EDF");
			String contact = (String) row.get("UPSO_SITE_TELNO");
			String snt = (String) row.get("SNT_UPTAE_NM");
			stmt = "insert into "+gu1+" values (?, ?, ?, ?, ?)";
			p = conn.prepareStatement(stmt);
			p.clearParameters();
			p.setString(1, name);
			p.setString(2, snt);
			p.setString(3, main_menu);
			p.setString(4, address);
			p.setString(5, contact);
			p.executeUpdate();
		}
	}
	public static void drop_table(Connection conn, Statement st) throws SQLException {
		String stmt = "drop table Gwangjin cascade;"
				+ "drop table Sd cascade;"
				+ "drop table Sb cascade;"
				+ "drop table Seocho cascade;"
				+ "drop table Songpa cascade;"
				+ "drop table Ep cascade;"
				+ "drop table Gd cascade;"
				+ "drop table Jongno cascade;"
				+ "drop table Gangnam cascade;"
				+ "drop table Dobong cascade;"
				+ "drop table Mapo cascade;"
				+ "drop table Geumcheon cascade;"
				+ "drop table Yongsan cascade;"
				+ "drop table Dongjak cascade;"
				+ "drop table Gangseo cascade;"
				+ "drop table Junggu cascade;"
				+ "drop table Ydp cascade;"
				+ "drop table Guro cascade;"
				+ "drop table Gangbuk cascade;"
				+ "drop table Jungnang cascade;"
				+ "drop table Sdm cascade;"
				+ "drop table Ddm cascade;"
				+ "drop table Yangcheon cascade;"
				+ "drop table Nowon cascade;"
				+ "drop table Gwanak cascade;";

		st.execute(stmt);
	}
	public static void create_trigger(Connection conn, Statement st) throws SQLException {
		//winner table�� update �� �� food table win_count+1 
		String CreateTriggerSql = "create or replace function test() returns trigger as $$"
				 +"begin\n"
			 		+ "update food\n"
			 		+ "set win_count = win_count+1\n"
			 		+"where name=New.f_name;\n"
			 		+"return old;\n"
			 		+"end;\n"
			 		+"$$\n"
			 		+"language 'plpgsql';\n"
				 +"create trigger R1\n"
		 		+ "after update of win_count on winner\n"
		 		+ "for each row\n"
		 		+ "execute procedure test();";
			st.executeUpdate(CreateTriggerSql);
		//winner table�� insert �� �� food table win_count+1 
		CreateTriggerSql = "create or replace function test1() returns trigger as $$"
					 +"begin\n"
				 		+ "update food\n"
				 		+ "set win_count = win_count+1\n"
				 		+"where name=New.f_name;\n"
				 		+"return old;\n"
				 		+"end;\n"
				 		+"$$\n"
				 		+"language 'plpgsql';\n"
					 +"create trigger R2\n"
			 		+ "after insert on winner\n"
			 		+ "for each row\n"
			 		+ "execute procedure test1();";
		st.executeUpdate(CreateTriggerSql);
	}
	public static void drop_trigger(Connection conn, Statement st) throws SQLException {
		String stmt = "drop trigger R1 on winner;"
				+ "drop trigger R2 on winner";
		st.executeUpdate(stmt);
	}
	public static String choose_gu() {
		System.out.println(" ��---------- ����� ���� ----------��");
		System.out.println(" |             �� ����            |");
		System.out.println(" | 1.������ 2.������ 3.���ϱ� 4.���ʱ� |");
		System.out.println(" | 5.���ı� 6.���� 7.������ 8.���α� |");
		System.out.println(" |    9.������ 10.������ 11.������   |");
		System.out.println(" |   12.��õ�� 13.��걸 14.���۱�   |");
		System.out.println(" |   15.������ 16.�߱� 17.��������   |");
		System.out.println(" |   18.���α� 19.���ϱ� 20.�߶���   |");
		System.out.println(" |     21.���빮�� 22.���빮��      |");
		System.out.println(" |   23.��õ�� 24.����� 25.���Ǳ�   |");
		System.out.println(" ��------------------------------��");
		System.out.println("***********************************");
		System.out.printf("select : ");
		Scanner scan = new Scanner(System.in);
		Integer X = scan.nextInt();
		if(X==1) {sub_gu1="Sd"; sub_gu2="jungnang"; return "Gwangjin";}
		else if(X==2) {sub_gu1="Gwangjin"; sub_gu2="Ddm"; return "Sd";}
		else if(X==3) {sub_gu1="Gangbuk"; sub_gu2="Ddm"; return "Sb";}
		else if(X==4) {sub_gu1="Gangnam"; sub_gu2="Dongjak"; return "Seocho";}
		else if(X==5) {sub_gu1="Gangnam"; sub_gu2="Gd"; return "Songpa";}
		else if(X==6) {sub_gu1="Jongno"; sub_gu2="Sdm"; return "Ep";}
		else if(X==7) {sub_gu1="Songpa"; sub_gu2="Gwangjin"; return "Gd";}
		else if(X==8) {sub_gu1="Sb"; sub_gu2="Sdm"; return "Jongno";}
		else if(X==9) {sub_gu1="Seocho"; sub_gu2="Songpa"; return "Gangnam";}
		else if(X==10) {sub_gu1="Gangbuk"; sub_gu2="Nowon"; return "Dobong";}
		else if(X==11) {sub_gu1="Sdm"; sub_gu2="Yongsan"; return "Mapo";}
		else if(X==12) {sub_gu1="Gwanak"; sub_gu2="Guro"; return "Geumcheon";}
		else if(X==13) {sub_gu1="Mapo"; sub_gu2="Junggu"; return "Yongsan";}
		else if(X==14) {sub_gu1="Gwanak"; sub_gu2="Ydp"; return "Dongjak";}
		else if(X==15) {sub_gu1="Yangcheon"; sub_gu2="Mapo"; return "Gangseo";}
		else if(X==16) {sub_gu1="Yongsan"; sub_gu2="Jongno"; return "Junggu";}
		else if(X==17) {sub_gu1="Dongjak"; sub_gu2="Guro"; return "Ydp";}
		else if(X==18) {sub_gu1="Yangcheon"; sub_gu2="Gangseo"; return "Guro";}
		else if(X==19) {sub_gu1="Dobong"; sub_gu2="Sb"; return "Gangbuk";}
		else if(X==20) {sub_gu1="Ddm"; sub_gu2="Nowon"; return "Jungnang";}
		else if(X==21) {sub_gu1="Mapo"; sub_gu2="Jongno"; return "Sdm";}
		else if(X==22) {sub_gu1="Sb"; sub_gu2="Jungnang"; return "Ddm";}
		else if(X==23) {sub_gu1="Gangseo"; sub_gu2="Guro"; return "Yangcheon";}
		else if(X==24) {sub_gu1="Dobong"; sub_gu2="Jungnang"; return "Nowon";}
		else if(X==25) {sub_gu1="Dongjak"; sub_gu2="Geumcheon"; return "Gwanak";}
		else return null;
	}
	/*public static void create_sub_gu_view(Connection conn, Statement st, String gu) throws SQLException 
	{
		String stmt = "select * from "+gu+"_sub_view";
		ResultSet rs = st.executeQuery(stmt);
		if(!rs.next()) {
				String createViewSql = "create view "+gu+"_sub_view as\n"
						+ "select name, main_menu, address, contact\n"
						+ "from (select name, main_menu, address, contact from "+sub_gu1+"\n"
								+ "union\n"
								+ "select name, main_menu, address, contact from "+sub_gu2+");";
				st.executeUpdate(createViewSql);
		}
	}*/
	
	
}

