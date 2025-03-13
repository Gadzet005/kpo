package project.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import project.consts.OperationType;

@AllArgsConstructor
@ToString
public class Category {
    @Getter
    private int id;
    @Getter
    private String name;
    @Getter
    private OperationType type;
}
