package com.trade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trade.controller.TradeController;
import com.trade.database.sqlserver.repository.TradeRepository;
import com.trade.jwt.AuthenticationFilter;
import com.trade.jwt.JwtService;
import com.trade.services.TradeService;
import com.trade.utils.CustomLogger;
import com.trade.utils.JacksonUtil;


@WebMvcTest(TradeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TestTradeController {


	    @Autowired
	    private MockMvc mvc;

	    @MockBean
	    private TradeService tradeService; 

	    @MockBean
	    private CustomLogger logger; 
	    
	    @MockBean
	    private TradeRepository tradeRepository;
	    
	    
	    @MockBean
	    private JwtService jwtService;

	    @MockBean
	    private AuthenticationFilter authenticationFilter;

	    @BeforeEach
	    void setupAuthentication() {
	        Authentication authentication = Mockito.mock(Authentication.class);
	        Mockito.when(authentication.getName()).thenReturn("testUser");

	        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
	        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
	        SecurityContextHolder.setContext(securityContext);
	    }

	    @Test
	    void testCreateTrade() throws Exception {
	        String data = """
	            [
	              {
	                "traderUUID": "048e0e4a-3ccf-4f6e-a14f-7d8aac213698",
	                "metal": "1AHD",
	                "buyer": "LME10099",
	                "seller": "LHOUSE",
	                "comment": " CANCEL",
	                "lots": 501,
	                "createdTimestamp": "2025-01-15T10:46:00Z",
	                "price": 2450.75,
	                "tradeType": "CANCEL",
	                "rowNumber": 2,
	                "lastModifiedBy": "Wai",
	                "lastModifiedTimestamp": "2025-01-15T10:48:00Z",
	                "createdBy": "srush"
	              }
	            ]
	            """;

	        ObjectNode mockResponse = JacksonUtil.mapper.createObjectNode();
	        when(tradeService.createTrade(any(List.class), Mockito.eq("testUser"))).thenReturn(mockResponse);

	        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/save")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(data)
	                        .header("x-auth", "Bearer abc"))
	                .andReturn();

	        int status = mvcResult.getResponse().getStatus();
	        assertEquals(200, status);

	       
	    }
}
