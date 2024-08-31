package org.noix.api.manager.tasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicTaskDTO {

    private Long id;

    private String name;
    private String description;

    private Date created;
    private Date updated;
}
