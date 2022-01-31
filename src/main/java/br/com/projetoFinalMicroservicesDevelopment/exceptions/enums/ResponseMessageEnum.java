package br.com.projetoFinalMicroservicesDevelopment.exceptions.enums;

public enum ResponseMessageEnum {
	
	UNKNOWN_ERROR(0, "", ""),
	ADD_EXPENSE_SUCCESS(1, "Gasto Criado com Sucesso", "Gasto Criado"),
	SEARCH_EXPENSE_BY_USER_CODE_SUCCESS(2, "Busca pelo Cliente Realizado com Sucesso", "Gastos do Usuário Encontrados"),
	EXPENSE_BADLY_FORMATTED_DATE(3, "Data mal Formatada", "A Data deve estar no Formato ddMMyyyy"),
	EXPENSE_NOT_FOUND(4, "Gasto não Encontrado", "Não foi Encontrado Gasto para o ID Informado"),
	SEARCH_EXPENSE_BY_USER_CODE_AND_DATE_SUCCESS(5, "Busca Realizada com Sucesso", "Busca por Código do Usuário e pela Data Realizada com Sucesso"),
	UPDATE_EXPENSE_SUCCESS(6, "Gasto Atualizado com Sucesso", "Gasto Encontrado e Atualizado"),
	ADD_CATEGORY_SUCCESS(7, "Categoria Adicionada com Sucesso", "Categoria Criada"),
	SUGGESTION_CATEGORY_SUCCESS(8, "Busca da Categoria Realizada com Sucesso", "Lista com Categorias");
	
	private Integer code;
	private String userMessage;
	private String internalMessage;
	
	private ResponseMessageEnum(Integer code, String userMessage, String internalMessage) {
		this.code = code;
		this.userMessage = userMessage;
		this.internalMessage = internalMessage;
	}

	public Integer getCode() {
		return code;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public String getInternalMessage() {
		return internalMessage;
	}
}