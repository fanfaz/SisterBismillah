package SisterBismillah;
//aa
import SisterBismillah.Account;
import SisterBismillah.BankInterface;
import java.io.Serializable;
import java.rmi.Naming;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.Vector;

public class ATM implements Serializable{
        private static Account akun, akun2;
	private static BankInterface bankInterface;
	
	// decimal formatting to 2 decimal places
	private static DecimalFormat precision2 = new DecimalFormat("0.00");

	public static void main(String[] args) throws Exception{
		
		Scanner sc = new Scanner(System.in);
                
		System.out.println("Client is connected to server");
                System.out.println("Selamat Datang di Bank kami");
                System.out.print("Masukkan No. Rekening : ");
		int accountNum = sc.nextInt();
		// connecting to remote server                
                bankInterface = (BankInterface) Naming.lookup("rmi://192.168.249.105:1106/BankInterface");
                if (bankInterface.ambilAkun(accountNum) == null){
                    bankInterface = (BankInterface) Naming.lookup("rmi://192.168.249.106:1107/BankInterface");
                }
                
		//Runtime.getRuntime().exec("cls");
                System.out.println("Selamat Datang Nomor Rekening " +accountNum+ " di Bank kami");
                System.out.println("Silahkan pilih transaksi yang akan anda pilih :\n" 
                    +"1. Menabung\n"
                    +"2. Tarik Tunai\n"
                    +"3. Cek Saldo\n"
                    +"4. Transfer");
        
		int pil = sc.nextInt();         
		switch (pil) {
		
		case 1:
			System.out.print("Jumlah yang akan di tabung : ");
			double amount = sc.nextInt();
                        akun = bankInterface.ambilAkun(accountNum);
			double resultDeposit = bankInterface.deposit(akun, amount);
			System.out.println("Jumlah Uang yang tersedia : Rp " + precision2.format(resultDeposit));
			break;
		
		case 2:
			System.out.print("Jumlah yang akan di Tarik Tunai : ");
			amount = sc.nextInt();
			akun = bankInterface.ambilAkun(accountNum);
			double resultWithdraw = bankInterface.withdraw(akun, amount);
                        System.out.print("Jumlah Uang Setelah di tarik tunai : Rp " + precision2.format(resultWithdraw));
			break;

		case 3:
			System.out.print("Cek Saldo");
                        akun = bankInterface.ambilAkun(accountNum);
			double resultInquiry = bankInterface.inquiry(akun);
			System.out.print("Jumlah Uang saat ini : Rp " + precision2.format(resultInquiry));
			break;

		case 4:
			System.out.print("Masukkan Nomor Rekening Tujuan : ");
			int accountNum2 = sc.nextInt();
			System.out.print ("Jumlah yang akan di transfer : ");
			amount = sc.nextInt();
			akun = bankInterface.ambilAkun(accountNum); 
                        akun2 = bankInterface.ambilAkun(accountNum2); 
                        bankInterface.transfer(akun, akun2, amount);
			break;
		}
	}
}
