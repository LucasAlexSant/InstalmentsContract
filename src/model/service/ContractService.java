package model.service;

import java.time.LocalDate;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinePaymentService;

	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}
	
	// Método para processar um contrato e calcular as parcelas
	public void processContract(Contract contract, int months) {
		
		double basicQuota = contract.getTotalValue()/ months;
		
		for (int i = 1; i< months; i++) {
			
			LocalDate dueDate= contract.getDate().plusMonths(i);
			double Interest = onlinePaymentService.interest(basicQuota, i);
			double fee = onlinePaymentService.paymentFee(basicQuota+ Interest);
			double quota = basicQuota + Interest + fee;
			
		// Adiciona uma nova parcela ao contrato com a data de vencimento e o valor calculado
			contract.getInstallment().add(new Installment(dueDate,quota)); 
		}
		
	}
	
}
