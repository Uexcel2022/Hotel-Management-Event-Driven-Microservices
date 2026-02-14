package com.uexcel.common.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryResultSummaryForCheckin {
    private final List<RoomModelForCheckinQuery> availableRoomsNumberForCheckin;
    private final Integer availableRoomsForCheckin;
    private final String message;
}
