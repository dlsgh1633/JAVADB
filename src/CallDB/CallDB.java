package CallDB;



import java.sql.*;
import java.util.Scanner;

class CallData{ // 주는 GET과 셋팅하는(받는) SET 클래스.
    String a, b, c; // 문자열 a,b,c 선언

    public String getA() {      return a;   }
    public void setA(String a) {      this.a = a;   }
    public String getB() {      return b;   }
    public void setB(String b) {      this.b = b;   }
    public String getC() {      return c;   }
    public void setC(String c) {      this.c = c;   }

}

class CallSQLC {
    private static Connection conn;

    private static PreparedStatement pstmt;

    CallSQLC() throws SQLException {

        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbtest"
                , "root", "1234");
    }

    void DataInsert(CallData c) {//입력
        try {

            pstmt = conn.prepareStatement(" insert into phondb values (?,?,?);");
            pstmt.setString(1, c.getA());
            pstmt.setString(2, c.getB());
            pstmt.setString(3, c.getC());


            int num = pstmt.executeUpdate();
            System.out.println(num);
        } catch (SQLException e) {
            e.printStackTrace(); // catch로 에러잡기 .
        }
    }

    void callbookAll() throws SQLException {

        String sql = "select * from phondb;"; //쿼리문입력

        pstmt = conn.prepareStatement(sql);


        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {





            System.out.print(rs.getString("name") + "/");
            System.out.print(rs.getString("phoneNumber") + "/");
            System.out.print(rs.getString("address") + "/");
            System.out.println();
        }
    }

    void findcallbook(String name) throws SQLException {
        String sql = "select * from phondb where name = ?;";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();


        if (rs.next()) {


            System.out.print(rs.getString("name") + "/");
            System.out.print(rs.getString("phoneNumber") + "/");
            System.out.print(rs.getString("address"));
            System.out.println();


        }
        else {
            System.out.println("전화번호부에 없습니다.");
        }
    }


    void deletecallbook(String name) throws SQLException {
        String sql = "delete from phondb where name = ?;";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.executeUpdate();
    }
}


class InputCB1
{
    CallData valueReturn() {
        CallData d = new CallData();

        Scanner scS = new Scanner(System.in);


        System.out.print("이름을 입력하세요 : ");
        d.setA(scS.nextLine());
        System.out.print("전화번호를 입력하세요 : ");
        d.setB(scS.nextLine());
        System.out.print("주소를 입력하세요 : ");
        d.setC(scS.nextLine());
        return d;

    }
    String findname(){
        Scanner scS = new Scanner(System.in);
        System.out.print("이름을 입력해주세요 : ");
        return scS.next();

    }
}











public class CallDB {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        CallSQLC cq = new CallSQLC();

        InputCB1 cb = new InputCB1();

        while (true){
            System.out.println(" 1.입력 2.검색 3.삭제 4.출력 5.종료");
            int num = sc.nextInt();
            if(num==1){
                cq.DataInsert(cb.valueReturn());
            }
            else if(num==2){
                cq.findcallbook(cb.findname());
            }
            else if(num==3){
                 cq.deletecallbook(cb.findname());


            }
            else if(num==4){
                cq.callbookAll();

            }
            else if(num==5){
                System.out.println("종료합니다.");
                break;
            }
            else{
                System.out.println("잘못된 입력입니다.");
            }
        }
    }
}
