package com.trade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.trade.controller.TradeController;
import com.trade.services.TradeService;
import com.trade.utils.CustomLogger;
import com.trade.utils.JacksonUtil;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ObjectNode;

@WebMvcTest(TradeController.class)
public class TestTradeController {


	    @Autowired
	    private MockMvc mvc;

	    @MockBean
	    private TradeService tradeService; // <-- use @MockBean, not @Mock

	    @MockBean
	    private CustomLogger logger;       // <-- use @MockBean, not @Mock

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

	        String responseContent = mvcResult.getResponse().getContentAsString();
	        JsonNode jsonResponse = JacksonUtil.mapper.readTree(responseContent);
	        assertEquals(true, jsonResponse.get("success").asBoolean());
	        assertEquals(200, jsonResponse.get("status").asInt());
	    }
}
