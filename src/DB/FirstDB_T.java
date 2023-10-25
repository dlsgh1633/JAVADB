package DB;

import java.sql.*; // my sql - connector-j-8.1.0 
import java.util.Scanner;

class Data
{
    String a, b, c; // 문자열 a , b, c 선언
    int d, e, f; // 정수 d , e,  f 선언 .
     // get , set 생성
    public String getA() {		return a;	} // 주고 안받고 , get -> 준다 .
    public void setA(String a) {		this.a = a;	}  // set이니까 안주고 받고, (담는다)  get A를 set A에 담는다 .
    public String getB() {		return b;	} // 주고안받고, getB, b를 준다 .
    public void setB(String b) {		this.b = b;	} // set B에 담는다 .
    public String getC() {		return c;	} // 동일
    public void setC(String c) {		this.c = c;	} //
    public int getD() {		return d;	} //
    public void setD(int d) {		this.d = d;	} //
    public int getE() {		return e;	} //
    public void setE(int e) {		this.e = e;	}
    public int getF() {		return f;	}
    public void setF(int f) {		this.f = f;	}
}
class SQLC //클래스 SQLC 생성
{
    // Connction 데이터 베이스를 연결하기 위한 클래스
    private static Connection conn;
    // Statement 데이터 베이스에 명령(SQL 쿼리문 ) 실행
    private static PreparedStatement pstmt;

    SQLC() throws SQLException {
        // DriveManager : SQL Driver 연결을 위한 Manager
        // getConnection : 데이터베이스에 연결시 객체를 제공 Connection , static이다 .
        //1번인자 : Mysql url -> MYSQL 서버(DB서버) , 스키마(test) jdbc:mysql 은 정의가 되어 있는것이다 .
        //2번인자 : 아이디
        //3번인자 : 비번
        // 실제로 mysql이랑 연결이 되면 Connction 객체를 리턴
        // 리턴하면 conn 객체를 받습니다 .conn을 이용해서 이제 한다 .
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test"
                ,"root","1234");
    }

    void DataInsert(Data d)
    {
        try {
            // 쿼리문을 세팅하는 작업
            // 연결된 객체가 -> conn
            // conn.prepareStatement(쿼리문)
            // Connction을 이용해서 쿼리문 객체 생성 PrepareStatment
            // PrepareStatment 객체가 생성된다 . -> pstmt
            // ? ? ? ? ? ?

            pstmt = conn.prepareStatement(" insert into 대리점 values (?,?,?,?,?,?);");// pstmt= conn.prepareStatement -> pstmt에 넣는게 아니라 객체생성
            pstmt.setString(1, d.getA());// 첫번쨰 ?
            pstmt.setString(2, d.getB());// 두번째 ?
            pstmt.setString(3, d.getC());// 세번째 ?
            pstmt.setInt(4, d.getD()); // 네번쨰 ??
            pstmt.setInt(5, d.getE()); // 5 번째 ??
            pstmt.setInt(6, d.getF()); // 6 ??
            // 쿼리문을 실행합니다.
            pstmt.executeUpdate(); //
        } catch (SQLException e) {
            e.printStackTrace(); // 에러 잡는 catch
        }
    }



    void selectAll() throws SQLException {
        //문자열 쿼리
        String sql = "select * from 대리점;";
        // conn.prepareStatement(sql)
        pstmt=conn.prepareStatement(sql);
        //executeQuery Select용 -> return set (ReslultSet)
        // rs <- 쿼리 결과
        ResultSet rs =pstmt.executeQuery(); //execute = 보내주는거 .

        // Set-> iterator hasNext(), next()
        // ResultSet -> rs.next() iterator 변환필요 X
        while(rs.next()){
            // Set 객체
            // rs.getString("지점명") 지점명 찾아봐
            // 값 -> 강남점
            System.out.print(rs.getString("지점명")+"/");
            System.out.print(rs.getString("도시")+"/");
            System.out.print(rs.getString("전화번호")+"/");
            System.out.print(rs.getInt("종업원수")+"/");
            System.out.print(rs.getInt("자본금")+"/");
            System.out.print(rs.getDate("지점개설일")+"/");
            System.out.println();
        }
    }

}
class InputClass // 입력
{
    Data valueReturn() {
        Data d = new Data(); // class Data 객체생성 .

        Scanner scS = new Scanner(System.in);
        Scanner scI = new Scanner(System.in);

        System.out.print("지점을 입력하세요 : ");
        d.setA(scS.nextLine());
        System.out.print("도시를 입력하세요 : ");
        d.setB(scS.nextLine());
        System.out.print("전화번호을 입력하세요 : ");
        d.setC(scS.nextLine());
        System.out.print("종업원수를 입력하세요 : ");
        d.setD(scI.nextInt());
        System.out.print("자본금을 입력하세요 : ");
        d.setE(scI.nextInt());
        System.out.print("지점개설일을 입력하세요 : ");
        d.setF(scI.nextInt());
        return d;

    }
    void findString(){

    }
}




public class FirstDB_T {
    public static void main(String[] args) throws SQLException {
        // TODO Auto-generated method stub
        Scanner sc = new Scanner(System.in);

        SQLC sq = new SQLC();
        InputClass ic = new InputClass();
        while(true)
        {
            System.out.print("1. 입력 2.전체출력 3.지점명 검색 4.지점 삭제 5.종료 :");
            int num = sc.nextInt();
            if(num==1)
            {
                sq.DataInsert(ic.valueReturn()); // Data타입의 d를 DataInsert함수에 넣어 쿼리문 만들어 던져
            }
            else if(num == 2){
                sq.selectAll();
            }
            else if(num == 3)
            {


                
            }
            else if(num==4){

            }
            else if (num ==5){
                System.out.println("프로그램을 종료합니다.");
                break;
            }
        }
    }

}
