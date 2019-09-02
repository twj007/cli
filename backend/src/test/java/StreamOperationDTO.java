import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/09/02
 **/
@Getter
@Setter
@ToString
public class StreamOperationDTO {

    private Long id;
    private float point;
    private String name;


    public StreamOperationDTO(Long id) {
        this.id = id;
    }

    public StreamOperationDTO() {
    }
}
