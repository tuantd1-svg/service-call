package com.example.muadeeservice.controller;

import com.example.commonapi.model.ResultMessage;
import com.example.muadeeservice.model.InitRegister;
import com.example.muadeeservice.model.TokenResponse;
import com.example.muadeeservice.service.UPHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@RestController
public class UPHController {

    @Autowired
    private UPHService uphService;
    @RequestMapping(value = "register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
    public ResultMessage<TokenResponse> initRegister(@Valid @RequestBody InitRegister initRegister) throws SQLException {
        return uphService.initRegister(initRegister);
    }
}
