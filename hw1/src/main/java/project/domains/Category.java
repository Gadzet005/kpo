package project.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.consts.OperationType;

@AllArgsConstructor
@ToString
public class Category {
    @Getter
    private int id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private OperationType type;
}
