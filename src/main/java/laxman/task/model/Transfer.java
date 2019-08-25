package laxman.task.model;

import java.util.UUID;

import laxman.task.enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hedaoo
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

	private UUID transferId;
	private Long sourceAccountId;
	private Long targetAccountId;
	private Double amount;
	private CurrencyEnum currency;
	private String comment;
}
