package project.domains;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.consts.OperationType;

@AllArgsConstructor
public class Operation {
    @Getter
    private int id;
    @Getter
    @Setter
    private OperationType type;
    @Getter
    @Setter
    private double amount;
    @Getter
    @Setter
    private Date date;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private BankAccount account;
    @Getter
    @Setter
    private Category category;
}
