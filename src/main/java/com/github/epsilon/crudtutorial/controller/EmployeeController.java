package com.github.epsilon.crudtutorial.controller;

import com.github.epsilon.crudtutorial.exception.BadServerException;
import com.github.epsilon.crudtutorial.exception.InternalServerException;
import com.github.epsilon.crudtutorial.http.*;
import com.github.epsilon.crudtutorial.model.Employee;
import com.github.epsilon.crudtutorial.model.JSONRes;
import com.github.epsilon.crudtutorial.repo.EmployeeRepo;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    private final EmployeeRepo empRepo;
    private final Client clientID1;
    private final Client2 clientID2;

    @Autowired
    public EmployeeController(EmployeeRepo empRepo, @Qualifier("clientID1") Client client, @Qualifier("clientID2") Client2 client3, ExternalApi externalApi, ExternalApi2 externalApi2, ExternalClientCB externalClientCB) {
        this.empRepo = empRepo;
        this.clientID1 = client;
        this.clientID2 = client3;
        this.externalApi = externalApi;
        this.externalApi2 = externalApi2;
        this.externalClientCB = externalClientCB;
    }

    @PostMapping("/employees")
    public void createEmployee(@RequestBody Employee employee) throws InternalServerException {
        System.out.println(employee.getName());
        System.out.println(employee.getId());
        try {

            JSONRes c = (JSONRes) clientID1.get("sd");
            JSONRes c3 = (JSONRes) clientID2.get("sd");

            System.out.println(c);
            System.out.println(c3);
            //empRepo.save(employee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw (new InternalServerException("cannot do intended action"));
        }

    }


    @GetMapping("/employees")
    public void getEmployee() throws InternalServerException, URISyntaxException, BadServerException {


        int c = clientID1.getCode();
        if (c == 503) {
            throw new BadServerException("reandom");
        }
        int c3 = clientID2.getCode();
        if (c3 == 503) {
            throw new BadServerException("reandom");
        }

        System.out.println(c);
        System.out.println(c3);
        //empRepo.save(employee);
    }

    private final ExternalApi externalApi;
    private final ExternalApi2 externalApi2;
    private final ExternalClientCB externalClientCB;

    @GetMapping("/foo")
    public String foo(@RequestParam int id, @RequestParam int aggregator) throws BadServerException {
        try {
            if (id == 1) {
                return externalApi.callExternalApiFoo();
            } else if(id ==2) {
                return externalApi2.callExternalApiFoo();
            }else {
                return externalClientCB.callExternalApiFoo(aggregator);
            }
        } catch (CallNotPermittedException c) {
            throw new BadServerException("lol");
        }

    }
}
