package com.uexcel.checkinservice.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Value;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckinQueryResponse {
    private String checkinId;
    private String message;
    private String reason;
}
