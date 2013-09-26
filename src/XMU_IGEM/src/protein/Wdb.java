package protein;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Wdb {

	public static void main(String[] args) throws IOException {
			Wdb.dropTable();
			Wdb.createTable();
			BufferedReader txt = new BufferedReader(new FileReader("wdb.txt"));
			String str=txt.readLine();
			int m=0;				//m用于保存行数
			while(str!=null&&!str.isEmpty())
			{
				String[] strs=str.split("\t");
				if(strs.length!=64)
					System.out.println("不是64列");
				for(int i=0; i<64; i++)
					Wdb.insertTable(m, i, Double.parseDouble(strs[i]));
				str=txt.readLine();
				m++;
			}
			txt.close();
			double[][] w=Wdb.selectTable();
			for(int i=0; i<w.length; i++)
			{
				for(int j=0; j<w[i].length; j++)
					System.out.print(w[i][j]+"\t");
				System.out.println();
			}
	}

	// ACGT从0-3
	public static int parse(char ch) {
		int m = 0;
		if (ch >= 97)
			ch -= 32;
		switch (ch) {
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

	public static char parse(int i) {
		switch (i) {
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

	// 密码子第一位权重是16，第二位权重是4，第三位权重是1。AGCT从1-4，T跟U等价，数据库中以T存储
	public static int parseInt(String str) {
		int id = 0;
		// System.out.println("str="+str);
		if (str.length() != 3)
			System.out.println("出现错误，在codondb类parseint方法中");
		str = str.toUpperCase();
		id = 16 * parse(str.charAt(0)) + 4 * parse(str.charAt(1))
				+ parse(str.charAt(2));
		return id;
	}

	// 从id得到密码子
	public static String parseStr(int id) {
		String str = "";
		str += parse(id / 16);
		id %= 16;
		str += parse(id / 4);
		id %= 4;
		str += parse(id);
		return str;
	}

	// 连接数据库
	public static Connection getCon() {
		Connection con;
		String url = "jdbc:derby:codondb;create=true";

		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			con = DriverManager.getConnection(url);
			return con;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 创建表
	public static boolean createTable() {
		Connection con = getCon();
		boolean result = false;
		try {
			Statement stm = con.createStatement();
			String sql = "create table wdb(firstcodon int, secondcodon int, w decimal(5,2))";
			stm.execute(sql);
			result = true;
			stm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 删除表
	public static boolean dropTable() {
		Connection con = getCon();
		boolean result = false;
		try {
			Statement stm = con.createStatement();
			String sql = "drop table wdb";
			stm.execute(sql);
			result = true;
			stm.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	// 增加一条记录
	public static boolean insertTable(String firstCodon, String secondCodon,
			double w) {
		Connection con = getCon();
		boolean result = false;
		String sql = "insert into wdb values(?,?)";
		PreparedStatement pstm = null;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, parseInt(firstCodon));
			pstm.setInt(2, parseInt(secondCodon));
			pstm.setDouble(3, w);
			pstm.executeUpdate();
			result = true;
			pstm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	// 增加一条记录
	public static boolean insertTable(int firstCodon, int secondCodon,
			double w) {
		Connection con = getCon();
		boolean result = false;
		String sql = "insert into wdb values(?,?,?)";
		PreparedStatement pstm = null;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, firstCodon);
			pstm.setInt(2, secondCodon);
			pstm.setDouble(3, w);
			pstm.executeUpdate();
			result = true;
			pstm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	// 删除一条记录seq
	public static boolean deleteTable(String firstCodon, String secondCodon) {
		Connection con = getCon();
		boolean result = false;
		PreparedStatement pstm = null;
		String sql = "delete from wdb where firstCodon=? and secondCodon=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, parseInt(firstCodon));
			pstm.setInt(2, parseInt(secondCodon));
			pstm.executeUpdate();
			result = true;
			pstm.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static boolean updateTable(String firstCodon, String secondCodon,
			double w) {
		Connection con = getCon();
		boolean result = false;
		try {
			String sql = "update wdb set w=? where firstcodon=? and secondcodon=?";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setDouble(1, w);
			pstm.setInt(2, parseInt(firstCodon));
			pstm.setInt(3, parseInt(secondCodon));
			pstm.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	// 查询记录,从seq得codon
	public static String selectTable(String firstCodon, String secondCodon) {
		Connection con = getCon();
		String codon = "";
		String sql = "select * from wdb where firstCodon=? and secondCodon=?";
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, parseInt(firstCodon));
			pstm.setInt(2, parseInt(secondCodon));
			ResultSet rs = pstm.executeQuery();
			rs.next();
			codon = rs.getString(2);
			pstm.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return codon;
	}

	// 输出全部
	public static double[][] selectTable() {
		Connection con = getCon();
		String sql = "select * from wdb order by firstcodon,secondcodon";
		double[][] w = new double[64][64]; // 此处是输出密码子的int值（见parseInt和parseStr方法），不同氨基酸的同义密码子之间以不小于64的数字隔开
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			for (int i = 0; i < 64; i++)
			{
				for (int j = 0; j < 64; j++)
				{
					rs.next();
					w[i][j] = rs.getDouble(3);
				}
			}
			pstm.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return w;
	}
}
