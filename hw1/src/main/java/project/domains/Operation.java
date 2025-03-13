package project.domains;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.consts.OperationType;

@AllArgsConstructor
public class Operation {
    @Getter
    private int id;
    @Getter
    private OperationType type;
    @Getter
    private double amount;
    @Getter
    private Date date;
    @Getter
    private String description;
    @Getter
    private BankAccount account;
    @Getter
    private Category category;
}
