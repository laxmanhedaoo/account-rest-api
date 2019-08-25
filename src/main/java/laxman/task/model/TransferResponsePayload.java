package laxman.task.model;

import java.util.UUID;

import laxman.task.enums.TransferStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponsePayload extends ResponsePayload {
	private UUID transferId;
	private TransferStateEnum transferState;
	private String transferDate;

}
