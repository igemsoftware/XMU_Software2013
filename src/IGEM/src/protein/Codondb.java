package protein;

import java.io.*;
import java.sql.*;

public class Codondb {
	
	public static void main(String[] args) throws IOException
	{
		Codondb.dropTable();
		Codondb.createTable();
		String s = "";
		BufferedReader txt = new BufferedReader(new FileReader("密码子.txt"));
		String str = txt.readLine();
		while(str!=null)
		{
			String strs[] = str.split(" ");
			for(int i = 1; i< strs.length; i++)
			{
				
				Codondb.insertTable(strs[i], strs[0]);
				s = strs[i];
			}
			str = txt.readLine();
		}
		txt.close();
		int[] codon=Codondb.selectTable();
		for(int i=0; i<codon.length; i++)
		{
			if(codon[i]>=64)
			{
				System.out.println();
				continue;
			}
			System.out.print(codon[i]+"\t");
		}
	}
	
	//ACGT从0-3
	public static int parse(char ch)
	{
		int m = 0;
		if(ch >= 97)
			ch -= 32;
		switch(ch)
		{
		case 'A':
			m = 0;
			break;
		case 'C':
			m = 1;
			break;
		case 'G':
			m = 2;
			break;
		case 'T':
			m = 3;
			break;
		case 'U':
			m = 3;
			break;
			default:
				m = 1;
		}
		
		return m;
	}
	
	public static char parse(int i)
	{
		switch(i)
		{
		case 0:
			return 'A';
		case 1:
			return 'C';
		case 2:
			return 'G';
		case 3:
			return 'T';
			default:
				return 'A';
		}
	}
	
	//密码子第一位权重是16，第二位权重是4，第三位权重是1。AGCT从1-4，T跟U等价，数据库中以T存储
	public static int parseInt(String str)
	{
		int id = 0;
		//System.out.println("str="+str);
		if(str.length()!=3)
			System.out.println("出现错误，在codondb类parseint方法中");
		str = str.toUpperCase();
		id = 16*parse(str.charAt(0))+4*parse(str.charAt(1))+parse(str.charAt(2));
		return id;
	}
	
	//从id得到密码子
	public static String parseStr(int id)
	{
		String str = "";
		str += parse(id/16);
		id %= 16;
		str += parse(id/4);
		id %= 4;
		str += parse(id);
		return str;
	}
	
	//连接数据库
	public static Connection getCon()
	{
		Connection con;
		String url = "jdbc:derby:codondb;create=true";
		
		try{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			con = DriverManager.getConnection(url);
			return con;
		}catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	//创建表 
	public static boolean createTable(){
		Connection con = getCon();
		boolean result = false;
		try{
			Statement stm = con.createStatement();
			String sql = "create table codondb(id int primary key, codon varchar(3))";
			stm.execute(sql);
			result = true;
			stm.close();
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}
	
	//删除表
	public static boolean dropTable(){
		Connection con = getCon();
		boolean result = false;
		try{
			Statement stm = con.createStatement();
			String sql = "drop table codondb";
			stm.execute(sql);
			result = true;
			stm.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		try{
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	//增加一条记录
	public static boolean insertTable(String seq, String codon){
		Connection con = getCon();
		boolean result = false;
		String sql = "insert into codondb values(?,?)";
		PreparedStatement pstm = null;
		try{
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, parseInt(seq));
			pstm.setString(2, codon);
			pstm.executeUpdate();
			result = true;
			pstm.close();
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	//删除一条记录seq
	public static boolean deleteTable(String seq){
		Connection con = getCon();
		boolean result = false;
		PreparedStatement pstm = null;
		String sql = "delete from codondb where id=?";
		try{
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, parseInt(seq));
			pstm.executeUpdate();
			result = true;
			pstm.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		try{
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static boolean updateTable(String seq, String codon){
		Connection con = getCon();
		boolean result = false;
		try{
			String sql = "update codondb set name=? where id =?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1,codon);
			pstm.setInt(2, parseInt(seq));
			pstm.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		try{
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	//查询记录,从seq得codon
	public static String selectTable(String seq){
		Connection con = getCon();
		String codon = "";
		String sql = "select * from codondb where id=?";
		try{
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, parseInt(seq));
			ResultSet rs = pstm.executeQuery();
			rs.next();
			codon = rs.getString(2);
			pstm.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		try{
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return codon;
	}
	
	//查询记录,从codon得seq
		public static String[] selectTable(String codon,int d){
			Connection con = getCon();
			int id[];
			String seq[] = null;
			String sql0 = "select count(*) from codondb where codon=?";
			String sql = "select * from codondb where codon=?";
			try{
				PreparedStatement pstm = con.prepareStatement(sql0);
				pstm.setString(1, codon);
				ResultSet rs = pstm.executeQuery();
				rs.next();
				int count = rs.getInt(1);
				pstm = con.prepareStatement(sql);
				pstm.setString(1, codon);
				rs = pstm.executeQuery();
				id = new int[count];
				seq = new String[count];
				for(int i=0; i<id.length; i++)
				{
					rs.next();
					id[i] = rs.getInt(1);
					seq[i] = parseStr(id[i]);
				}
				pstm.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			return seq;
		}
		
		//输出全部
		public static int[] selectTable()
		{
			Connection con = getCon();
			String sql = "select * from codondb order by codon";
			int[] codon=new int[64+21];			//此处是输出密码子的int值（见parseInt和parseStr方法），不同氨基酸的同义密码子之间以不小于64的数字隔开
			try{
				PreparedStatement pstm = con.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery();
				String cur="";				//保存当前氨基酸
				String pre="";				//保存上一次的氨基酸
				int index=0;					//保存前一次的索引
				int curAnimoAcid=0;		//表示当前氨基酸出现在第几次
				rs.next();
				label:
				while(true){
					//此语段将氨基酸对应的大于64的数字输入到数组中
					{
						codon[index]=64+curAnimoAcid;
						curAnimoAcid++;
						index++;
					}
					pre=rs.getString(2);			//这两句是给出一个初始条件
					cur=pre;
					//接下来是输入同义密码子
					while(pre.equals(cur))
					{
						codon[index]=rs.getInt(1);
						//System.out.println("id:"+rs.getInt(1)+",codon:"+rs.getString(2));
						index++;
						if(!rs.next())
							break label;							//由于是在第二层循环判断是否结束，所以要加上标签
						cur=rs.getString(2);
					}
				}
				pstm.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
			return codon;
		}
}
