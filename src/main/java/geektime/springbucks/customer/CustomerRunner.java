package geektime.springbucks.customer;

import geektime.springbucks.customer.model.Coffee;
import geektime.springbucks.customer.model.CoffeeOrder;
import geektime.springbucks.customer.request.NewOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
public class CustomerRunner implements ApplicationRunner {

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public void run(ApplicationArguments args) throws Exception{
        readMenu();
        Long id = orderCoffee();
        queryOrder(id);
       /* URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8080/coffee/?name={name}")
                .build(1);
        RequestEntity<Void> req = RequestEntity.get(uri)
                .accept(MediaType.APPLICATION_XML)  //接收的响应类型
                .build();

        ResponseEntity<String> resp = restTemplate.exchange(req,String.class);
        log.info("Response Status: {},Response Headers: {}",resp.getStatusCode(),resp.getHeaders().toString());
        log.info("Coffee: {}",resp.getBody());

        String coffeeUri = "http://localhost:8080/coffee/";
        Coffee request = Coffee.builder()
                .name("delicious fruit")
                .price(Money.of(CurrencyUnit.of("CNY"),25.00))
                .build();
        Coffee response = restTemplate.postForObject(coffeeUri,request,Coffee.class);
        log.info("New Coffee: {}",response);

        ParameterizedTypeReference<List<Coffee>> ptr = new ParameterizedTypeReference<List<Coffee>>() {};
        ResponseEntity<List<Coffee>> list = restTemplate.exchange(coffeeUri, HttpMethod.GET,null,ptr);
        list.getBody().forEach(c->log.info("Coffee: {}",c));*/


    }

    private void queryOrder(Long id) {
        CoffeeOrder order = restTemplate.getForObject("http://localhost:8080/order/{id}",CoffeeOrder.class,id);
        log.info("Order: {}",id);
    }

    private Long orderCoffee() {
        NewOrderRequest orderRequest = NewOrderRequest.builder()
                .customer("lilei")
                .items(Arrays.asList("macchiato"))
                .build();
        RequestEntity<NewOrderRequest> request = RequestEntity
                .post(UriComponentsBuilder.fromUriString("http://localhost:8080/order/").build().toUri())
                .body(orderRequest);
        ResponseEntity<CoffeeOrder> reponse = restTemplate.exchange(request,CoffeeOrder.class);
        log.info("Order Request Status Code : {}",reponse.getStatusCode());
        Long id = reponse.getBody().getId();
        log.info("Order ID: {}",id);
        return id;
    }



    private void readMenu() {
        ParameterizedTypeReference<List<Coffee>> ptr = new ParameterizedTypeReference<List<Coffee>>() {};
        ResponseEntity<List<Coffee>> list = restTemplate
                .exchange("http://localhost:8080/coffee/",HttpMethod.GET,null,ptr);
        list.getBody().forEach(c->log.info("Menu->Coffee:{}",c));
    }




}
