package project.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class BankAccount {
    @Getter
    private int id;
    @Getter
    private String name;
    @Getter
    @Setter
    private double balance;
}
