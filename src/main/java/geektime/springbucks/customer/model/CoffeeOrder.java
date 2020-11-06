package geektime.springbucks.customer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeOrder implements Serializable {
    private Long id;
    private String name;
    private List<Coffee> items;
    private OrderState orderState;
    private Date createTime;
    private Date updateTime;
}
