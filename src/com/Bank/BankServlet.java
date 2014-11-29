package com.Bank;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class BankServlet
 */
@WebServlet("/BankServlet")
public class BankServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BankServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String name = request.getParameter("account_holder_name");
		long routing_number = Long.parseLong(request.getParameter("routing_number"));
		long acc_number = Long.parseLong(request.getParameter("account_number"));
		double total_cost = Double.parseDouble(request.getParameter("total_cost"));
		double new_balance;
		
		String error_message="";
		boolean success_flag = true; // This flag is used to check if transaction is a success or not
		
		System.out.println("Total Ticket cost: "+ total_cost);
		
		Bank t = new Bank();
		try {
			if(t.validateBankDetails(acc_number, routing_number, total_cost)){ // validated
				
				// check if routing number is valid one or not
				if(t.getRouting_number() != routing_number){
					error_message = error_message + "Routing Number is Wrong <br/>";
					success_flag = false;
				}			
				// check if there is enough balance to buy a ticket
				if(t.getBalance() < total_cost){
					error_message = error_message + "Insufficient Balance <br/>";
					success_flag = false;
				}
				
				// If the flag is true it means that there were no errors in routing no and balance
				// hence add the booking details to the booking history
				// and update the balance in the accounts table
				if(success_flag){
					//error_message = null;
					error_message = "success";					
					System.out.println("old balance: "+ t.getBalance());
					
					//updating the balance in the accounts
					new_balance = t.getBalance() - total_cost;
					System.out.println("Updated Balance = " + new_balance);
					t.updateBalance(new_balance,t.getAccount_number());

				}
				
			}else{ // not validated
				error_message = "The Account Number entered is not valid! Please try again.";
			}
			
			System.out.println("Status Message: " +error_message);
			response.getWriter().write(error_message);

	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}
}
