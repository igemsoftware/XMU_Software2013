package codon;

//从根目录下的“sigma因子模体.txt“中读取信息到promotertbl表中

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Promoterdb {

	public static void main(String[] args) {
		Promoterdb.dropTable();
		Promoterdb.createTable();
		try {
			BufferedReader txt = new BufferedReader(new FileReader(
					"sigma因子模体.txt"));
			String str = "#"; // 初始化为”#“是为了方便排除文件开头的注释

			while (str.substring(0, 1).equals("#")) {
				str = txt.readLine();
			}
			// 开头文件已经跳过

			label: while (true) {
				String[] strs = str.split("\t");
				if (strs.length != 6) {
					System.out.println("文件格式错误！");
					return;
				}
				SigmaDetailed sigma = new SigmaDetailed();

				// 读入第一行，六个基本数据
				sigma.setName(strs[0]);
				sigma.setMotifWeight(Double.valueOf(strs[1]));
				sigma.setSpaceWeight(Double.valueOf(strs[2]));
				sigma.setSpaceMin(Integer.valueOf(strs[3]));
				sigma.setSpaceMax(Integer.valueOf(strs[4]));
				sigma.setAdjustE(Double.valueOf(strs[5]));

				// 读入第一个矩阵
				str = txt.readLine();
				strs = str.split("\t");
				GeneFreMatrix matrix1 = new GeneFreMatrix(strs.length);
				for (int i = 1; i <= 4; i++) {
					for (int j = 1; j <= matrix1.getColumns(); j++)
						matrix1.setElement(i, j, Double.valueOf(strs[j - 1]));
					str = txt.readLine();
					if (str != null)
						strs = str.split("\t");
				}

				// 读入第二个矩阵，此处加一个if判断主要是为了防止sigmaS这种只有一个模体的矩阵
				GeneFreMatrix matrix2;
				if (str != null) {
					matrix2 = new GeneFreMatrix(strs.length);
					for (int m = 1; m <= 4; m++) {
						for (int n = 1; n <= matrix2.getColumns(); n++)
							matrix2.setElement(m, n,
									Double.valueOf(strs[n - 1]));
						str = txt.readLine();
						if (str != null)
							strs = str.split("\t");
					}
				}else{
					matrix2 = new GeneFreMatrix(matrix1.getColumns());
					for(int i=1; i<=4; i++)
						for(int j=1; j<=matrix2.getColumns(); j++)
							matrix2.setElement(i, j, matrix1.getElement(i, j));
				}

				// 设置两个matrix到sigma因子里
				sigma.setMatrix1(matrix1);
				sigma.setMatrix2(matrix2);

				// 读入数据库
				Promoterdb.insertTable(sigma);

				// 判断接下来一行是不是文件结束
				if (str == null)
					break;

				if (str.isEmpty())
					str = txt.readLine();
			}
			txt.close();
			SigmaDetailed[] sigma = Promoterdb.selectTable();
			for (int i = 0; i < sigma.length; i++)
				System.out.println(sigma[i].getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 将genefrematrix矩阵的每行转换成一个字符串，转换成的字符串长度不能超过100，以空格为分隔符
	private static String[] parseStr(GeneFreMatrix geneMatrix) {
		String[] str = { "", "", "", "" };
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < geneMatrix.getColumns(); j++)
				str[i] = str[i] + " "
						+ Double.toString(geneMatrix.getElement(i + 1, j + 1));
		return str;
	}

	// 将从列中读出的String转换成GeneFreMatrix
	private static GeneFreMatrix parseGeneMatrix(String[] str, int matrixColumn) {
		if (str.length != 4) {
			System.out
					.println("wrong at Promoterdb.parseGeneMatrix.see str.length="
							+ str.length);
			return null;
		}
		GeneFreMatrix geneMatrix = new GeneFreMatrix(matrixColumn);
		for (int i = 0; i < 4; i++) {
			String strs[] = str[i].trim().split(" ");
			if (geneMatrix.getColumns() != strs.length) {
				System.out.println("wrong at Promoterdb.parseGeneMatrix.");
			}
			for (int j = 0; j < strs.length; j++)
				geneMatrix
						.setElement(i + 1, j + 1, Double.parseDouble(strs[j]));
		}
		return geneMatrix;
	}

	// 连接数据库
	public static Connection getCon() {
		Connection con;
		String url = "jdbc:derby:promoterdb;create=true";

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
			String sql = "create table promotertbl(id varchar(10) primary key,motif1_line1 varchar(100),motif1_line2 varchar(100),"
					+ "motif1_line3 varchar(100),motif1_line4 varchar(100),motif1_length int,motif2_line1 varchar(100),motif2_line2 varchar(100),"
					+ "motif2_line3 varchar(100),motif2_line4 varchar(100),motif2_length int,motifweight decimal(4,3),spaceweight decimal(4,3),"
					+ "spacemin int,spacemax int,adjustE decimal(5,2))";
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
			String sql = "drop table promotertbl";
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
	public static boolean insertTable(SigmaDetailed sigma) {
		Connection con = getCon();
		boolean result = false;
		String sql = "insert into promotertbl values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstm = null;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, sigma.getName());
			String[] strs = new String[4];
			strs = parseStr(sigma.getMatrix1());
			for (int i = 1; i <= 4; i++)
				pstm.setString(i + 1, strs[i - 1]);
			pstm.setInt(6, sigma.getMatrix1().getLength());
			strs = parseStr(sigma.getMatrix2());
			for (int i = 1; i <= 4; i++)
				pstm.setString(i + 6, strs[i - 1]);
			pstm.setInt(11, sigma.getMatrix2().getLength());
			pstm.setDouble(12, sigma.getMotifWeight());
			pstm.setDouble(13, sigma.getSpaceWeight());
			pstm.setInt(14, sigma.getSpaceMin());
			pstm.setInt(15, sigma.getSpaceMax());
			pstm.setDouble(16, sigma.getAdjustE());
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

	// 删除一条记录id
	public static boolean deleteTable(String id) {
		Connection con = getCon();
		boolean result = false;
		PreparedStatement pstm = null;
		String sql = "delete from promotertbl where id=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, id);
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

	// 查询记录,从id得sigma
	public static SigmaDetailed selectTable(String id) {
		Connection con = getCon();
		SigmaDetailed sigma = new SigmaDetailed();
		String sql = "select * from promotertbl where id=?";
		try {
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, id);
			ResultSet rs = pstm.executeQuery();
			rs.next();
			sigma.setName(rs.getString(1));
			String strs[] = new String[4];
			for (int i = 0; i < 4; i++)
				strs[i] = rs.getString(i + 2);
			sigma.setMatrix1(parseGeneMatrix(strs, rs.getInt(6)));
			for (int i = 0; i < 4; i++)
				strs[i] = rs.getString(i + 7);
			sigma.setMatrix2(parseGeneMatrix(strs, rs.getInt(11)));
			sigma.setMotifWeight(rs.getDouble(12));
			sigma.setSpaceWeight(rs.getDouble(13));
			sigma.setSpaceMin(rs.getInt(14));
			sigma.setSpaceMax(rs.getInt(15));
			sigma.setAdjustE(rs.getDouble(16));
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
		return sigma;
	}

	// 查询记录,得到所有的sigma
	public static SigmaDetailed[] selectTable() {
		Connection con = getCon();
		int count = 0;
		SigmaDetailed[] sigma = null;
		String sql0 = "select count(*) from promotertbl";
		String sql = "select * from promotertbl";
		try {
			PreparedStatement pstm = con.prepareStatement(sql0);
			ResultSet rs = pstm.executeQuery();
			// 得到数据库中保存的个数
			rs.next();
			count = rs.getInt(1);
			sigma = new SigmaDetailed[count];

			// 再进行数据读取
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			int k = 0; // 用k保存次数
			while (rs.next()) {
				sigma[k] = new SigmaDetailed();
				sigma[k].setName(rs.getString(1));
				String strs[] = new String[4];
				for (int i = 0; i < 4; i++)
					strs[i] = rs.getString(i + 2);
				sigma[k].setMatrix1(parseGeneMatrix(strs, rs.getInt(6)));
				for (int i = 0; i < 4; i++)
					strs[i] = rs.getString(i + 7);
				sigma[k].setMatrix2(parseGeneMatrix(strs, rs.getInt(11)));
				sigma[k].setMotifWeight(rs.getDouble(12));
				sigma[k].setSpaceWeight(rs.getDouble(13));
				sigma[k].setSpaceMin(rs.getInt(14));
				sigma[k].setSpaceMax(rs.getInt(15));
				sigma[k].setAdjustE(rs.getDouble(16));
				k++;
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
		return sigma;
	}
}
