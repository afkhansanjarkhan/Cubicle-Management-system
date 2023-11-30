package com.modeln.spaceit;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modeln.spaceit.entities.CSICubicle;
import com.modeln.spaceit.enums.CSICubicleStatus;
import com.modeln.spaceit.repositories.ISICubicleRepository;
import com.modeln.spaceit.repositories.ISILocationRepository;
import com.modeln.spaceit.services.CSICubicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class CSICubicalTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CSICubicleService csicubicleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    ISICubicleRepository cubicleRepository;

    @Autowired
    ISILocationRepository locationRepository;

    @Test
    public void SaveEmployeeAndReturnEmployee() throws Exception{
        CSICubicle cubicle = new CSICubicle();
        cubicle.setStatus(CSICubicleStatus.OPEN);
        cubicle.setCubicleId("123");
        given(csicubicleService.insert(any(CSICubicle.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/cubicle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cubicle)));
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cubicleId").
                        value(cubicle.getCubicleId()))
                .andExpect(jsonPath("$.status")
                        .value(cubicle.getStatus().name()));
        System.out.println(response);

    }
    //get cubicle
    @Test
    public void GetCubicleDataAndReturnData() throws Exception{
        List<CSICubicle> csiCubicle = new ArrayList<>();
        csiCubicle.add((new CSICubicleBuilder()).withCubicleId("123").withStatus(CSICubicleStatus.OPEN).build());

        given(csicubicleService.getCubicles(null)).willReturn(csiCubicle);

        ResultActions response = mockMvc.perform(get("/cubicle"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect((jsonPath("$.size()").value(csiCubicle.size())));
    }

    //delete cubicle
    @Test
    public void GivenCubicalIdAndReturn200() throws Exception {
        long cubicleId = 123L;
        willDoNothing().given(csicubicleService).deleteCubicle(cubicleId);

        ResultActions response = mockMvc.perform(delete("/cubicle/{cubicleId}",cubicleId));
        response.andExpect(status().isNoContent())
                .andDo(print());
    }


    //update cubicle positive scenario
    @Test
    public void GivenUpdateCubicleAndReturnUpdatedCubicle() throws Exception {
        long  cubicleId = 123L;
        CSICubicle cubicle = new CSICubicle();
        cubicle.setStatus(CSICubicleStatus.OPEN);
        cubicle.setCubicleId("123");


        CSICubicle cubicle1 = new CSICubicle();
        cubicle1.setStatus(CSICubicleStatus.RESERVED);
        cubicle1.setCubicleId("456");
        given(csicubicleService.getCubicleById(cubicleId)).willReturn(cubicle);
        given(csicubicleService.updateCubicle(cubicleId, cubicle))
                .willAnswer((invocation)->invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/cubicle/{cubicleId}",cubicleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cubicle1)));

        response.andExpect(status().isOk());
    }

    //updateCubicle negative scenario
    public void GivenUpdatedCubicleAndExpectError() throws Exception {
        long  cubicleId = 123L;
        CSICubicle cubicle = new CSICubicle();
        cubicle.setStatus(CSICubicleStatus.OPEN);
        cubicle.setCubicleId("123");

        CSICubicle cubicle1 = new CSICubicle();
        cubicle1.setStatus(CSICubicleStatus.RESERVED);
        cubicle1.setCubicleId("456");
        given(csicubicleService.getCubicleById(cubicleId))
                .willReturn(null);
        given(csicubicleService.updateCubicle(cubicleId, cubicle))
                .willAnswer((invocation)-> invocation.getArgument(0));


        ResultActions response = mockMvc.perform(put("/cubicle/{cubicleId}",cubicleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cubicle1)));

        response.andExpect(status().isNotFound())
                .andDo(print());

    }




}




