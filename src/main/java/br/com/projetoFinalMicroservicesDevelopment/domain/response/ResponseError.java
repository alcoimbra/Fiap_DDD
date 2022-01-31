package br.com.projetoFinalMicroservicesDevelopment.domain.response;

public class ResponseError extends Response {
	
	private Integer internalCode;
	
	public ResponseError(long statusCode, String userMessage, String internalMessage, Integer internalCode) {
		super(statusCode, userMessage, internalMessage);
		this.internalCode = internalCode;
	}

	public Integer getInternalCode() {
		return internalCode;
	}

	public void setInternalCode(Integer internalCode) {
		this.internalCode = internalCode;
	}
}