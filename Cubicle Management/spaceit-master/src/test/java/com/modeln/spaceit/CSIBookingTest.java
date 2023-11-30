package com.modeln.spaceit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modeln.spaceit.entities.CSIBooking;
import com.modeln.spaceit.enums.CSIBookingStatus;
import com.modeln.spaceit.repositories.ISIBookingRepository;
import com.modeln.spaceit.services.CSIBookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
@ActiveProfiles("test")
public class CSIBookingTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CSIBookingService csibookingService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    ISIBookingRepository bookingRepository;

/*    @Before("")
    public void setUp() {
        CSIBooking booking = new CSIBooking();
        booking.setBookingId(123L);
        Mockito.when(bookingRepository.findById(booking.getBookingId())).thenReturn(Optional.of(booking));
    }*/
    @Test
    public void SaveEmployeeAndReturnEmployee() throws Exception{
        CSIBooking booking = new CSIBooking();
        booking.setStatus(CSIBookingStatus.OPEN);
        booking.setBookingId(123L);
        given(csibookingService.insert(any(CSIBooking.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booking)));
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookingId").
                        value(booking.getBookingId()))
                .andExpect(jsonPath("$.status")
                        .value(booking.getStatus().name()));
        System.out.println(response);

    }
/*    @Test
    public void GetBookingDataAndReturnData() throws Exception{
        SaveEmployeeAndReturnEmployee();

        List<CSIBooking> csiBooking = new ArrayList<>();
        csiBooking.add((new CSIBookingBuilder()).withBookingId(123L).withStatus(CSIBookingStatus.OPEN).build());

        given(csibookingService.getBooking(null)).willReturn(csiBooking);

        ResultActions response = mockMvc.perform(get("/booking"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect((jsonPath("$.size()").value(csiBooking.size())));
    }*/
    @Test
    public void GivenBookingIdAndReturn200() throws Exception {
        SaveEmployeeAndReturnEmployee();
        long bookingId = 123L;
        willDoNothing().given(csibookingService).deleteBooking(bookingId);

        ResultActions response = mockMvc.perform(delete("/booking/{bookingId}",bookingId));
        response.andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    public void GivenUpdateBookingAndReturnUpdatedBooking() throws Exception {
        SaveEmployeeAndReturnEmployee();
        long bookingId = 123L;
        CSIBooking booking = new CSIBooking();
        booking.setStatus(CSIBookingStatus.OPEN);
        booking.setBookingId(123L);
        CSIBooking booking1 = new CSIBooking();
        booking1.setStatus(CSIBookingStatus.RESERVED);
        booking1.setBookingId(456L);
        given(csibookingService.getBookingById(bookingId)).willReturn(booking);
        given(csibookingService.updateBooking(bookingId, booking))
                .willAnswer((invocation)->invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/booking/{bookingId}",bookingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booking1)));
        response.andExpect(status().isOk());
    }
/*    @Test
    public void GivenUpdatedBookingAndExpectError() throws Exception {
        SaveEmployeeAndReturnEmployee();
        long  bookingId = 123L;
        CSIBooking booking= new CSIBooking();
        booking.setStatus(CSIBookingStatus.OPEN);
        booking.setBookingId(123L);
        CSIBooking booking1 = new CSIBooking();
        booking1.setStatus(CSIBookingStatus.RESERVED);
        booking1.setBookingId(456L);
        given(csibookingService.getBookingById(bookingId))
                .willReturn(null);
        given(csibookingService.updateBooking(bookingId, booking))
                .willAnswer((invocation)-> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/booking/{bookingId}",bookingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booking1)));
        response.andExpect(status().isNotFound())
                .andDo(print());

    }*/
/*    @Test
    public void successfullybooked() throws Exception{

        long bookingId=123L;
        // create a booking withh id 123 and cubicle id W101
        // given some method return this booking id
        // now create another booking with differnt id but same cubicle
        // call the api with the new booking id and it should return code we have given
        CSIBooking booking=new CSIBooking();


    }*/


}
