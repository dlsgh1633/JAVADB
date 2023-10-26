package CallDB;



import java.sql.*;
import java.util.Scanner;

class CallData{ // 주는 GET과 셋팅하는(받는) SET 클래스.
    String str1, str2, str3; // 문자열 a,b,c 선언

    public String getStr1() {      return str1;   }
    public void setStr1(String a) {      this.str1 = a;   }
    public String getStr2() {      return str2;   }
    public void setStr2(String b) {      this.str2 = b;   }
    public String getStr3() {      return str3;   }
    public void setStr3(String c) {      this.str3 = c;   }

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
            pstmt.setString(1, c.getStr1());
            pstmt.setString(2, c.getStr2());
            pstmt.setString(3, c.getStr3());


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
        d.setStr1(scS.nextLine());
        System.out.print("전화번호를 입력하세요 : ");
        d.setStr2(scS.nextLine());
        System.out.print("주소를 입력하세요 : ");
        d.setStr3(scS.nextLine());
        return d;

    }
    String findname(){
        Scanner scS = new Scanner(System.in);
        System.out.print("이름을 입력해주세요 : ");
        return scS.nextLine();

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
